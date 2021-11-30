package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/page")
    public String ShowAdminPage(ModelMap model, Principal principal) {
        StringBuilder roles = new StringBuilder();
        for (Role role: userService.getUserByUsername(principal.getName()).getRoles()){
            roles.append(role.getName()).append(" ");
        }
        model.addAttribute("userRoles", roles.toString());
        model.addAttribute("userNew", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("thisUser", userService.getUserByUsername(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        return "allusers";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userService.delete(id);
        return "redirect:/admin/page";
    }

    @GetMapping("/{id}/edit")
    public String edit(ModelMap model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin/page";
    }

    @PostMapping("/create")
    public String createNewUser(@ModelAttribute("user") User user, ModelMap model) {
        Set<Role> roles = new HashSet<>();
        user.getRoles().forEach((element) -> roles.add(roleService.findRole(element)));
        user.setRoles(roles);
        userService.addUser(user);
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/admin/page";
    }
}
