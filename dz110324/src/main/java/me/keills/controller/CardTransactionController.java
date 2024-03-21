package me.keills.controller;

import jakarta.servlet.http.HttpSession;
import me.keills.exception.card.CardBlockedTemporary;
import me.keills.exception.card.CardDontContainEnoughMoney;
import me.keills.exception.card.CardDontExist;
import me.keills.model.Card;
import me.keills.service.CardTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("card")
public class CardTransactionController {
    @Autowired
    private CardTransactionService cardTransactionService;
    // authentication
    @PostMapping("/authentication")
    public String authentication(Model model, @ModelAttribute(name="card_id") String cardId, @ModelAttribute(name="card_pin") String pin, HttpSession session) {
        try{
            boolean isAuthenticated = cardTransactionService.authentication(Long.parseLong(cardId),pin);
            if(isAuthenticated)
                session.setAttribute("authenticated", isAuthenticated);
            else {
                model.addAttribute("error", "Неверный PIN!");
                return "user-cabinet";
            }
        } catch (CardBlockedTemporary e) {
            model.addAttribute("error", e.getMessage());
            return "user-cabinet";
        }

        model.addAttribute("card_id", cardId);
        return "transaction-menu";
    }
    // get money
    @PostMapping("/get-money")
    public String getMoney(Model model,@ModelAttribute(name="card_id") String cardId, @ModelAttribute(name="value") String value) {
        try{
            cardTransactionService.getMoney(Long.parseLong(cardId),Long.parseLong(value));
        } catch (CardDontContainEnoughMoney e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("card_id", cardId);
        return "transaction-menu";
    }
    // put money
    @PostMapping("/put-money")
    public String putMoney(Model model,@ModelAttribute(name="card_id") String cardId, @ModelAttribute(name="value") String value) {
        cardTransactionService.putMoney(Long.parseLong(cardId),Long.parseLong(value));
        model.addAttribute("card_id", cardId);
        return "transaction-menu";
    }
    // check money
    @GetMapping("/check-money")
    public String checkMoney(Model model,@ModelAttribute(name="card_id") String cardId) {
        model.addAttribute("card_balance", cardTransactionService.checkMoney(Long.parseLong(cardId)));
        model.addAttribute("card_id", cardId);
        return "transaction-menu";
    }

}
