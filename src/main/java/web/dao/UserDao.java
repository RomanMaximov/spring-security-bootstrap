package web.dao;

import org.springframework.security.core.userdetails.UserDetails;
import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    User getUserById(int id);
    User getUserByUsername(String userName);
    UserDetails loadUserByUsername(String userName);
    void delete(int id);
    List<User> getAllUsers();
    void addUser(User user);
    void update(User user);
}
