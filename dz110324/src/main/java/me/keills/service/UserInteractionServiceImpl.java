package me.keills.service;

import me.keills.exception.user.*;
import me.keills.model.Card;
import me.keills.model.User;
import me.keills.repo.CardRepo;
import me.keills.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInteractionServiceImpl implements UserInteractionService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CardRepo cardRepo;
    @Override
    public User userRegistration(String name, String password) {
        Optional<User> userFromDatabase = userRepo.findByUsername(name);
        if(userFromDatabase.isPresent())
            throw new UserAlreadyExists("Пользователь с таким именем уже существует");

        User registredUser = new User(name, password);
        userRepo.save(registredUser);
        return registredUser;
    }

    @Override
    public User userLogin(String name, String password) {
        Optional<User> userFromDatabase = userRepo.findByUsername(name);
        if(!userFromDatabase.isPresent() )
            throw new UserNotExist("Пользователь с таким именем не существует");

        User userInst = userFromDatabase.get();

        if(!userInst.getPassword().equals(password))
            throw new UserIncorrectPassword("Неверный пароль!");

        return userInst;
    }

    @Override
    public Card createCard(User user, String pin) {
        User userDatabaseInst = getUserFromDatabase(user);

        if(userDatabaseInst.getCard()==null) {
            Card card = new Card(pin,0L);
            cardRepo.save(card);

            user.setCard(card);
            userRepo.save(user);
            return card;
        }

        throw new UserAlreadyHaveCard("Пользователь уже имеет карту!");
    }

    private User getUserFromDatabase(User user){
        Optional<User> userFromDatabase = userRepo.findById(user.getId());
        if(!userFromDatabase.isPresent())
            throw new UserNotExist("Пользователь с таким именем не существует!");

        return userFromDatabase.get();
    }
}
