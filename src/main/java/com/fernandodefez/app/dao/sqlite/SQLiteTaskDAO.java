package com.fernandodefez.app.dao.sqlite;

import com.fernandodefez.app.dao.DAOFactory;
import com.fernandodefez.app.dao.TaskDAO;
import com.fernandodefez.app.models.Task;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLiteTaskDAO implements TaskDAO {

   private static final String INSERT = "INSERT INTO tasks(title, description) values(?,?)";
   private static final String UPDATE = "UPDATE tasks SET title=?, description=?, is_done=? WHERE id=?";
   private static final String DELETE = "DELETE FROM tasks WHERE id=?";
   private static final String GET_ALL_TASKS = "SELECT * FROM tasks";
   private static final String GET_BY_ID = "SELECT * FROM tasks WHERE id=?";

   @Override
   public int insert(Task task) {
      DAOFactory database = DAOFactory.getInstance(DAOFactory.SQLITE);
      Connection conn = database.connect();
      PreparedStatement stmt = null;
      int wasInserted = 0;

      try {
         stmt = conn.prepareStatement(INSERT);
         stmt.setString(1, task.getTitle());
         stmt.setString(2, task.getDescription());
         if (stmt.executeUpdate() == 0) {
            JOptionPane.showMessageDialog(
                  null,
                  "Task " + task.getId() + " could not be inserted",
                  "Task Insertion Failure", JOptionPane.ERROR_MESSAGE);
         }
         wasInserted = 1;
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(
               null,
               "Something went wrong when trying to insert a new task",
               "Task Insertion Failure", JOptionPane.ERROR_MESSAGE);
      } finally {
         close(conn, stmt, null);
      }
      return wasInserted;
   }

   @Override
   public boolean update(Task task) {
      DAOFactory database = DAOFactory.getInstance(DAOFactory.SQLITE);
      Connection conn = database.connect();
      PreparedStatement stmt = null;
      try {
         stmt = conn.prepareStatement(UPDATE);
         stmt.setString(1, task.getTitle());
         stmt.setString(2, task.getDescription());
         stmt.setBoolean(3, task.isDone());
         stmt.setInt(4, task.getId());
         if (stmt.executeUpdate() == 0) {
            JOptionPane.showMessageDialog(
                  null,
                  "Task " + task.getId() + " could not be updated",
                  "Task Update Failure", JOptionPane.ERROR_MESSAGE);
         }
         return true;
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(
               null,
               "Something went wrong when trying to update the task",
               "Task Update Failure", JOptionPane.ERROR_MESSAGE);
         return false;
      } finally {
         close(conn, stmt, null);
      }
   }

   @Override
   public boolean delete(Task task) {
      DAOFactory database = DAOFactory.getInstance(DAOFactory.SQLITE);
      Connection conn = database.connect();
      PreparedStatement stmt = null;
      try {
         stmt = conn.prepareStatement(DELETE);
         stmt.setInt(1, task.getId());
         if (stmt.executeUpdate() == 0) {
            JOptionPane.showMessageDialog(
                  null,
                  "Task " + task.getId() + " could not be removed",
                  "Task Remove Failure", JOptionPane.ERROR_MESSAGE);
         }
         return true;
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(
               null,
               "Something went wrong when trying to remove the task",
               "Task Remove Failure", JOptionPane.ERROR_MESSAGE);
         return false;
      } finally {
         close(conn, stmt, null);
      }
   }

   @Override
   public Task getById(Integer id) {
      DAOFactory database = DAOFactory.getInstance(DAOFactory.SQLITE);
      Connection conn = database.connect();
      PreparedStatement stmt = null;
      ResultSet rs = null;
      Task task = null;
      try {
         stmt = conn.prepareStatement(GET_BY_ID);
         stmt.setLong(1, id);
         rs = stmt.executeQuery();
         if (rs.next()) {
            task = map(rs);
         } else {
            JOptionPane.showMessageDialog(
                  null,
                  "Task " + id + " does not exist",
                  "Non-existent task", JOptionPane.ERROR_MESSAGE);
         }
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(
               null,
               "Something went wrong when trying to fetch a task",
               "Task Fetch Failure", JOptionPane.ERROR_MESSAGE);
      } finally {
         close(conn, stmt, rs);
      }
      return task;
   }

   @Override
   public List<Task> getAll() {
      DAOFactory database = DAOFactory.getInstance(DAOFactory.SQLITE);
      Connection conn = database.connect();
      PreparedStatement stmt = null;
      ResultSet rs = null;
      List<Task> tasks = new ArrayList<>();
      try {
         stmt = conn.prepareStatement(GET_ALL_TASKS);
         rs = stmt.executeQuery();
         while (rs.next()) {
            tasks.add(map(rs));
         }
      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(
               null,
               "Something went wrong when fetching all tasks",
               "Tasks Fetch Failure", JOptionPane.ERROR_MESSAGE);
      } finally {
         close(conn, stmt, rs);
      }
      return tasks;
   }

   @Override
   public Task map(ResultSet rs) {
      try {
         return new Task(
               rs.getInt("id"),
               rs.getString("title"),
               rs.getString("description"),
               rs.getBoolean("is_done"),
               rs.getString("created_at")
         );
      } catch (SQLException ex) {
         System.out.println(ex.getMessage());
      }
      return null;
   }

   @Override
   public void close(Connection conn, Statement stmt, ResultSet rs) {
      if (rs != null) {
         try {
            rs.close();
         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
         }
      }
      if (stmt != null) {
         try {
            stmt.close();
         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
         }
      }
      if (conn != null) {
         try {
            conn.close();
         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
         }
      }
   }
}
