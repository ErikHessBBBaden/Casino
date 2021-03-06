/*
 * This peace of Software has been written by Nick Flückiger
 * You are free to use and modifiy this software to your needs
 * For information and contact with the developer please write to
 * Mail: nick.flueckiger@outlook.de
 */
package com.team1.casino.User.Util;

import com.team1.casino.User.Spieler;
import com.team1.casino.database.Connection.DatabaseConnection;
import com.team1.casino.database.DatabaseQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Nick Flückiger
 */
public class UserUtil {

    public static String getHashedPassword(String password) {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashed;
    }

    public boolean validPassword(String givenPassword, String hashedPassword) {
        boolean isValidPassword = BCrypt.checkpw(givenPassword, hashedPassword);
        return isValidPassword;
    }

    public boolean isValidUser(Spieler user) {
        String storedPassword = retreaveStoredUserPassword(user.getUsername());
        if (storedPassword.equals("None") || storedPassword.isEmpty()) {
            return false;
        }
        return validPassword(user.getPassword(), storedPassword);
    }

    public String retreaveStoredUserPassword(String username) {
        try {
            DatabaseQuery query = new DatabaseQuery(DatabaseConnection.getInstance().getDatabaseConnection(), false);
            ArrayList<String> retreavedHashes = query.runQueryWithReturn("SELECT password FROM user WHERE username = ?", username);
            if (retreavedHashes.isEmpty() == true) {
                return "None";
            } else {
                return retreavedHashes.get(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "None";
    }

    public boolean isUniqueUsername(String username) {
        try {
            DatabaseQuery query = new DatabaseQuery(DatabaseConnection.getInstance().getDatabaseConnection(), false);
            ArrayList<String> retreavedHashes = query.runQueryWithReturn("SELECT username FROM user WHERE username = ?", username);
            if (retreavedHashes.isEmpty() == true) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String getUserRoleByUsername(String username) {
        try {
            DatabaseQuery query = new DatabaseQuery(DatabaseConnection.getInstance().getDatabaseConnection(), false);
            ArrayList<String> retreavedInfromation = query.runQueryWithReturn("SELECT role FROM user WHERE username = ?", username);
            if (retreavedInfromation.isEmpty() == true) {
                return "";
            } else {
                return retreavedInfromation.get(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public int getIDFromUserByUsername(String username) {
        try {
            DatabaseQuery query = new DatabaseQuery(DatabaseConnection.getInstance().getDatabaseConnection(), false);
            ArrayList<String> retreavedInfromation = query.runQueryWithReturn("SELECT id FROM user WHERE username = ?", username);
            if (retreavedInfromation.isEmpty() == true) {
                return -1;
            } else {
                return Integer.parseInt(retreavedInfromation.get(0));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public double loadCurrentBalanceFromGivenUsername(String username) {
        try {
            DatabaseQuery query = new DatabaseQuery(DatabaseConnection.getInstance().getDatabaseConnection(), false);
            ArrayList<String> retreavedInfromation = query.runQueryWithReturn("SELECT b.chips FROM balance b, user p WHERE p.balance_id = b.id AND p.username = ?", username);
            if (retreavedInfromation.isEmpty() == true) {
                return 0.0;
            } else {
                return Double.valueOf(retreavedInfromation.get(0));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }

    public double loadCurrentMoneyFromUsername(String username) {
        try {
            DatabaseQuery query = new DatabaseQuery(DatabaseConnection.getInstance().getDatabaseConnection(), false);
            ArrayList<String> retreavedInfromation = query.runQueryWithReturn("SELECT b.money FROM balance b, user p WHERE p.balance_id = b.id AND p.username = ?", username);
            if (retreavedInfromation.isEmpty() == true) {
                return 0.0;
            } else {
                return Double.valueOf(retreavedInfromation.get(0));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }

}
