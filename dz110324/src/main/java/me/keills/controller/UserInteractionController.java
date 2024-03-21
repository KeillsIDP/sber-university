package me.keills.controller;

import jakarta.servlet.http.HttpSession;
import me.keills.exception.user.UserAlreadyExists;
import me.keills.exception.user.UserIncorrectPassword;
import me.keills.exception.user.UserNotExist;
import me.keills.model.Card;
import me.keills.model.User;
import me.keills.service.UserInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/user")
public class UserInteractionController {
    @Autowired
    private UserInteractionService userInteractionService;

    //user reg
    @PostMapping("/registration")
    public String UserRegistration(Model model, @ModelAttribute(name="user_name") String username,
                                   @ModelAttribute(name = "user_password") String password, RedirectAttributes redirectAttrs, HttpSession session){

        User registratedUser = null;
        try {
            registratedUser = userInteractionService.userRegistration(username, password);
        } catch (UserAlreadyExists e){
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/registration";
        }

        session.setAttribute("user", registratedUser);
        session.setAttribute("authenticated", false);
        model.addAttribute("user", registratedUser);
        return "user-cabinet";
    }
    //user login
    @PostMapping("/login")
    public String UserLogin(Model model, @ModelAttribute(name="user_name") String username,
                            @ModelAttribute(name = "user_password") String password, RedirectAttributes redirectAttrs, HttpSession session){
        User loggedUser = null;
        try {
            loggedUser = userInteractionService.userLogin(username, password);
        } catch (UserNotExist e){
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }   catch (UserIncorrectPassword e){
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }

        session.setAttribute("user", loggedUser);
        session.setAttribute("authenticated", false);
        model.addAttribute("user", loggedUser);
        model.addAttribute("card_id", loggedUser.getCard().getId());
        return "user-cabinet";
    }
    //user card_creation
    @PostMapping("/create-card")
    public String CreateCard(Model model,@ModelAttribute(name="user") User user, @ModelAttribute(name="card_pin") String pin, HttpSession session){
        Card card = userInteractionService.createCard(user, pin);
        user.setCard(card);

        session.setAttribute("user", user);
        session.setAttribute("authenticated", true);
        model.addAttribute("user", user);
        model.addAttribute("card_id", user.getCard().getId());
        return "user-cabinet";
    }
}
