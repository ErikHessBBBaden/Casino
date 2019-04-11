/*
 * This peace of Software has been written by Nick Flückiger
 * You are free to use and modifiy this software to your needs
 * For information and contact with the developer please write to
 * Mail: nick.flueckiger@outlook.de
 */
package com.team1.casino.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick Flückiger
 */
public class Updater {

    private java.sql.Connection connection = DatabaseConnection.getInstance().getDatabaseConnection();

    public Updater() {
    }

    public void performUpdateWithoutArgument(String query) throws SQLException {
        Thread thread = new Thread(new Runnable() {
            private boolean result;

            public boolean getResult() {
                return this.result;
            }

            @Override
            public void run() {
                Statement statement = null;
                try {
                    statement = connection.createStatement();
                } catch (SQLException ex) {
                    Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    int queryResult = statement.executeUpdate(query);
                } catch (SQLException ex) {
                    Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                }
                int updates;
                try {
                    updates = statement.getUpdateCount();
                    if (updates == -1) {
                        this.result = false;
                    } else {
                        this.result = true;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        thread.start();
    }

    public void performUpdateWithArgument(String query, String argument) throws SQLException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    String[] arg = argument.split(";");
                    for (int i = 0; i < arg.length; i++) {
                        statement.setString(i + 1, arg[i]);
                    }
                    int queryResult = statement.executeUpdate();
                    int updates = statement.getUpdateCount();
                } catch (SQLException ex) {
                    Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        thread.start();
    }

}