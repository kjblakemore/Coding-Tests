package com.h2rd.refactoring.web;

import com.h2rd.refactoring.usermanagement.UserDao;
import com.h2rd.refactoring.usermanagement.User;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

@Path("/users")
@Repository
public class UserResource{

    public UserDao userDao;

    /**
     * Add user to register.  An error is returned, if the user already has been
     * registered or there is not at least one role.
     */
    @GET
    @Path("add/")
    public Response addUser(@QueryParam("name") String name,
                            @QueryParam("email") String email,
                            @QueryParam("roles") List<String> roles) {
        User user;
        int val;

        if (roles.size() <= 0) {
            user = null;
            val = 400;  // 400 = BAD_REQUEST
        } else {    
            if (userDao == null) {
                userDao = UserDao.getUserDao();
            }
            user = userDao.saveUser(name, email, roles);
            val = (user == null) ? 409 : 200;   // 409 = CONFLICT, 200 = OK
        }
        return Response.status(val).entity(user).build();
    }


    /**
     * Update user name and roles.  An error is returned if there is not at least one
     * role or the user is not already registered.
     */
    @GET
    @Path("update/")
    public Response updateUser(@QueryParam("name") String name,
                               @QueryParam("email") String email,
                               @QueryParam("roles") List<String> roles) {
        User user;
        int val;

        if (roles.size() <= 0) {
            user = null;
            val = 400;  // 400 = BAD_REQUEST
        } else {
            if (userDao == null) {
                userDao = UserDao.getUserDao();
            }
            user = userDao.updateUser(name, email, roles);
            val = (user==null)? 404 : 200;  // 404 = NOT_FOUND, 200 = OK
        }
        return Response.status(val).entity(user).build();
    }

    /**
     * Remove user's registration.  If user not found, respond with error.
     * Else, respond with success and user object.
     */
    @GET
    @Path("delete/")
    public Response deleteUser(@QueryParam("email") String email) {
                
        User user;
        int val;

        if (userDao == null) {
            userDao = UserDao.getUserDao();
        }

        user = userDao.deleteUser(email);
        val = (user==null)? 404 : 200;  // 404 = NOT_FOUND, 200 = OK
        return Response.status(val).entity(user).build();
    }

    /**
     * Return all users that are currently registered.
     */
    @GET
    @Path("find/")
    public Response getUsers() {

        Collection<User> users;
    	
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
    		"classpath:/application-config.xml"	
    	});
    	userDao = context.getBean(UserDao.class);
    	users = userDao.getUsers();
    	

        GenericEntity<Collection<User>> usersEntity = new GenericEntity<Collection<User>>(users) {};
        return Response.status(200).entity(usersEntity).build();    // 200 = OK
    }

    /**
     * Search for user in registration.  If not found, return error, else return 
     * success and user object.
     */
    @GET
    @Path("search/")
    public Response findUser(@QueryParam("email") String email) {
        User user;
        int val;

        if (userDao == null) {
            userDao = UserDao.getUserDao();
        }

        user = userDao.findUser(email);
        val = (user==null)? 404 : 200;  // 404 = NOT_FOUND, 200 = OK
        return Response.status(val).entity(user).build();
    }
}
