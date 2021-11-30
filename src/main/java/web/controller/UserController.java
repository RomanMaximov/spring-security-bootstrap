package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.Role;
import web.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/page")
    public String getPrincipal(@CurrentSecurityContext(expression = "authentication")
                                           Authentication authentication,
                                           Model model) {
        StringBuilder roles = new StringBuilder();
        for (Role role: userService.getUserByUsername(authentication.getName()).getRoles()){
            roles.append(role.getName()).append(" ");
        }
        model.addAttribute("userRoles", roles.toString());
        model.addAttribute("showUser", userService.getUserByUsername(authentication.getName()));
        return "userpage";
    }
}
