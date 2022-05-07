package com.fernandodefez.app.dao.sqlite;

import com.fernandodefez.app.dao.DAOFactory;
import com.fernandodefez.app.dao.TaskDAO;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDAOFactory extends DAOFactory {

   public Connection connect() {
      Connection conn = null;

      try {
         Class.forName("org.sqlite.JDBC");
         conn = DriverManager.getConnection("jdbc:sqlite:tasks.db");

         Statement stmt = conn.createStatement();
         String sql = "" +
            "CREATE TABLE IF NOT EXISTS tasks(" +
            " id INTEGER PRIMARY KEY," +
            " title TEXT NOT NULL," +
            " description TEXT NOT NULL," +
            " is_done BOOLEAN DEFAULT 0," +
            " created_at DATETIME DEFAULT CURRENT_TIMESTAMP" +
            ")";
         stmt.executeUpdate(sql);
         stmt.close();
         return conn;
      } catch (SQLException | ClassNotFoundException ex) {
         JOptionPane.showMessageDialog(
               null,
               "Error when trying to open database",
               "Database opening failure",
               JOptionPane.ERROR_MESSAGE);
      } finally {
         if (conn != null) {
            conn = null;
         }
      }
      return conn;
   }

   @Override
   public TaskDAO getTaskDAO() {
      return new SQLiteTaskDAO();
   }
}