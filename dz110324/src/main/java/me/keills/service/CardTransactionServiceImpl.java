package me.keills.service;

import me.keills.exception.card.CardBlockedTemporary;
import me.keills.exception.card.CardDontContainEnoughMoney;
import me.keills.exception.card.CardDontExist;
import me.keills.model.Card;
import me.keills.repo.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class CardTransactionServiceImpl implements CardTransactionService{
    @Autowired
    private CardRepo cardRepo;
    @Override
    public boolean authentication(Long cardId, String pin) {
        Card cardInst = getCardFromDatabase(cardId);

        Instant unblockDate = cardInst.getUnblockDate();
        if(unblockDate!=null) {
            if (unblockDate.isAfter(Instant.now()))
                throw new CardBlockedTemporary("Карта заблокирована на" + ChronoUnit.SECONDS.between(Instant.now(), unblockDate)+ " секунд");
            else if (unblockDate.isBefore(Instant.now()))
                cardInst.setUnblockDate(null);
        }

        if(cardInst.getPin().equals(pin)) {
            cardInst.setAmountOfAttempts(0);
            cardRepo.save(cardInst);
            return true;
        }

        cardInst.setAmountOfAttempts(cardInst.getAmountOfAttempts()+1);
        if(cardInst.getAmountOfAttempts()>=5) {
            cardInst.setUnblockDate(Instant.now().plusSeconds(10));
            cardInst.setAmountOfAttempts(0);
        }
        cardRepo.save(cardInst);
        return false;
    }

    @Override
    public void putMoney(Long cardId, Long amount) {
        Card cardInst = getCardFromDatabase(cardId);

        cardInst.setBalance(cardInst.getBalance()+amount);
        cardRepo.save(cardInst);
    }

    @Override
    public void getMoney(Long cardId, Long amount) {
        Card cardInst = getCardFromDatabase(cardId);

        if(cardInst.getBalance()-amount<0)
            throw new CardDontContainEnoughMoney("Недостаточно средств!");

        cardInst.setBalance(cardInst.getBalance()-amount);
        cardRepo.save(cardInst);
    }

    @Override
    public Long checkMoney(Long cardId) {
        Card cardInst = getCardFromDatabase(cardId);
        return cardInst.getBalance();
    }

    private Card getCardFromDatabase(Long cardId){
        Optional<Card> cardFromDatabase = cardRepo.findById(cardId);
        if(!cardFromDatabase.isPresent())
            throw new CardDontExist("Карты не существует!");

        return cardFromDatabase.get();
    }
}
