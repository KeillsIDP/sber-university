package me.keills.service;

import me.keills.model.Card;
import me.keills.model.User;

public interface UserInteractionService {

    User userRegistration(String name, String password);
    User userLogin(String name, String password);
    Card createCard(User user, String pin);
}
