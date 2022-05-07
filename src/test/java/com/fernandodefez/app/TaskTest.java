package com.fernandodefez.app;

import com.fernandodefez.app.dao.DAOFactory;
import com.fernandodefez.app.dao.TaskDAO;
import com.fernandodefez.app.models.Task;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskTest {

   private static DAOFactory database;
   private static TaskDAO taskDAO;
   private static Task taskWithId;
   private static Task taskWithNoId;

   /**
    * Change the id in the init() method every new task creation
    */
   @BeforeAll
   public static void init() {
      database = DAOFactory.getInstance(DAOFactory.SQLITE);
      Assertions.assertNotNull(database.connect());

      taskDAO = database.getTaskDAO();
      Assertions.assertNotNull(taskDAO);

      // This object is used when creating a task for the first time, so we do not
      // need the id
      taskWithNoId = new Task(
         "First task", 
         "This is the first task to be stored after running the tests"
      );
      Assertions.assertNotNull(taskWithNoId);

      // This object is used when manipulating a task after being created.
      // So you have to change the id every new task creation
      taskWithId = new Task(
         1,
         "First task", 
         "This is the first task to be stored after running the tests",
         false
      );
      Assertions.assertNotNull(taskWithId);
   }

   @Test()
   @Order(1)
   public void a_task_can_be_inserted_test() {
      Integer wasInserted = taskDAO.insert(taskWithNoId);
      Assertions.assertEquals(1, wasInserted);
   }

   @Test()
   @Order(2)
   public void a_task_can_be_retrieved_test() {
      Task task = taskDAO.getById(taskWithId.getId());
      Assertions.assertNotNull(task);
   }

   @Test()
   @Order(3)
   public void a_task_can_be_updated_test() {
      boolean wasUpdated = taskDAO.update(taskWithId);
      Assertions.assertTrue(wasUpdated);
   }

   @Test()
   @Order(4)
   public void all_tasks_can_be_retrieved_test() {
      List<Task> tasks = taskDAO.getAll();
      Assertions.assertNotNull(tasks);
      Assertions.assertFalse(tasks.isEmpty());
   }

   @Test()
   @Order(5)
   public void a_task_can_be_deleted_test() {
      boolean wasDeleted = taskDAO.delete(taskWithId);
      Assertions.assertTrue(wasDeleted);
   }

   @AfterAll
   public static void finish() {
      taskDAO = null;
      taskWithId = null;
      database = null;
      taskWithNoId = null;
   }
}
