package com.h2rd.refactoring.usermanagement;

import java.util.HashMap;
import java.util.List;
import java.util.Collection;

/**
 * The UserDao class maintains informatioin for all users who are currently 
 * registered.  The users are uniquely identified by their email address.
 */
public class UserDao {

    private static HashMap<String, User> users;    // Maps email to user entry

    public static UserDao userDao;

    /**
     * Return Data Access Object to User entries.  If first call, initialze
     * the access object and user database.
     */
    public static UserDao getUserDao() {
        if (userDao == null) {
            users = new HashMap<String, User>();
            userDao = new UserDao();
        }
        return userDao;
    }

    /**
     * Return collection of all users that are currently registered..
     *
     */
    public Collection<User> getUsers() {
        return users.values();
    }

    /**
     * Save user info.  If already saved, return null.  Else, return user object.
     */
    public User saveUser(String name, String email, List<String> roles) {
        User user;

        if (users.containsKey(email)==true) return null;
        user = new User(name, email, roles);
        users.put(email, user);
        return user;
    }

    /**
     * Delete user.  If not previously saved, return null. Else, return user object.
     */
    public User deleteUser(String email) {
        return users.remove(email);
    }

    /**
     * Update user name and roles.  If user was not previously saved, return null. 
     * Else, return user object.
     */
    public User updateUser(String name, String email, List<String> roles) {
        User user;

        user = users.get(email);
        if (user == null) return null;

        user.update(name, roles);
        return user;
    }

    /**
     * Find user.  If not found, return null.  Else, return user object.
     */
    public User findUser(String email) {
        return users.get(email);
    }
}
