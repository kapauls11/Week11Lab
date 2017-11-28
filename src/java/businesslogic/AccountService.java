package businesslogic;

import dataaccess.NotesDBException;
import dataaccess.UserDB;
import domainmodel.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.naming.NamingException;

public class AccountService {

    public User checkLogin(String username, String password, String path) {
        User user;

        UserDB userDB = new UserDB();
        try {
            user = userDB.getUser(username);

            if (user.getPassword().equals(password)) {
                Logger.getLogger(AccountService.class.getName())
                        .log(Level.INFO,
                                "A user logged in: {0}", username);
                String email = user.getEmail();
                try {
                    HashMap<String, String> contents = new HashMap<>();
                    contents.put("firstname", user.getFirstname());
                    contents.put("date", ((new java.util.Date()).toString()));
                    try {
                        WebMailService.sendMail(email, "NotesKeepr Login", path + "/emailtemplates/login.html", contents);
                    } catch (IOException ex) {
                        Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (MessagingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NamingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                }
                return user;
            }
        } catch (NotesDBException ex) {
        }
        return null;
    }

    public boolean forgotPassword(String email, String path) {
        UserDB userDB = new UserDB();
        User user;
        try {
            user = userDB.getUserByEmail(email);
            if (user.getEmail().equals(email)) {
                Logger.getLogger(AccountService.class.getName())
                        .log(Level.INFO, "User logged in: {0}", user.getUsername());
                try {
                    HashMap<String, String> contents = new HashMap<>();
                    contents.put("firstname", user.getFirstname());
                    contents.put("date", ((new java.util.Date()).toString()));
                    contents.put("lastname", user.getLastname());
                    contents.put("username", user.getUsername());
                    contents.put("password", user.getPassword());

                    try {
                        WebMailService.sendMail(user.getEmail(), "NotesKeepr Login", path + "/emailtemplates/forgotpassword.html", contents);
                    } catch (IOException ex) {
                        Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (MessagingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NamingException ex) {
                    Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }
        } catch (NotesDBException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
