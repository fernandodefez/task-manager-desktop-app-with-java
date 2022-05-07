package com.fernandodefez.app.controllers;

import com.fernandodefez.app.dao.DAO;
import com.fernandodefez.app.dao.DAOFactory;
import com.fernandodefez.app.models.Model;
import com.fernandodefez.app.models.Task;
import com.fernandodefez.app.views.verifiers.DescriptionVerifier;
import com.fernandodefez.app.views.verifiers.TitleVerifier;
import com.fernandodefez.app.views.task.View;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

public class TaskController implements Controller {

   View view;
   Task task;
   DAO<Task, Integer> taskDAO;

   /**
     * @param view View
     * @param model Model
     * */
   public TaskController(View view, Model model) {
      this.view = view;
      this.task = (Task) model;
      index();
   }

   /**
     * Invoked when an action occurs in the View.
     *
     * @param e the event to be processed
     */
   @Override
   public void actionPerformed(ActionEvent e) {
      switch (e.getActionCommand()) {
         case "New":
            create();
            break;
         case "Edit":
            edit();
            break;
         case "Remove":
            remove();
            break;
         case "Save":
            save();
            break;
         case "Cancel":
            cancel();
            break;
         default:
            break;
      }
   }

   @Override
   public void index() {
      DAOFactory mysqlFactory = DAOFactory.getInstance(DAOFactory.SQLITE);
      assert mysqlFactory != null;
      taskDAO = mysqlFactory.getTaskDAO();
      this.view.initTable(taskDAO);
   }

   @Override
   public void create() {
      // Clean the task object from the Panel class
      this.view.TaskPanel.setTask(null);

      // Update the fields
      this.view.TaskPanel.load();

      // Enable all the fields so the user can enter data.
      this.view.TaskPanel.TitleTextField.setEnabled(true);
      this.view.TaskPanel.DescriptionTextArea.setEnabled(true);
      this.view.TaskPanel.DoneCheckBox.setEnabled(false);

      // Enable Save and Cancel Button
      this.view.SaveTaskButton.setEnabled(true);
      this.view.CancelTaskButton.setEnabled(true);
   }

   @Override
   public void save() {
      TitleVerifier titleVerifier = new TitleVerifier();
      DescriptionVerifier descriptionVerifier = new DescriptionVerifier();
      if (titleVerifier.verify(this.view.TaskPanel.TitleTextField) &&
         descriptionVerifier.verify(this.view.TaskPanel.DescriptionTextArea)){
         // Save the field values
         this.view.TaskPanel.save();
         this.task = this.view.TaskPanel.getTask();
         if (this.task != null) {
             if (this.task.getId() == null) { // There is a new task to be stored
               this.taskDAO.insert(this.task);
               JOptionPane.showMessageDialog(
                        null,
                        "The task was created successfully",
                        "Task Created", JOptionPane.OK_OPTION,
                        View.SUCCESS_ICON);
             } else{ // There is a task selected from the table to update
               this.taskDAO.update(this.task);
               JOptionPane.showMessageDialog(
                        null,
                        "The task " + this.task.getId() + " was updated successfully",
                        "Task Updated", JOptionPane.OK_OPTION,
                        View.SUCCESS_ICON);
            }
            this.view.TaskPanel.setTask(null);
            this.view.TaskPanel.load();
            this.view.tasksAdapter.refresh();
            this.view.tasksAdapter.fireTableDataChanged();
            this.view.SaveTaskButton.setEnabled(false);
            this.view.CancelTaskButton.setEnabled(false);
            this.view.TaskPanel.TitleTextField.setEnabled(false);
            this.view.TaskPanel.DescriptionTextArea.setEnabled(false);
            this.view.TaskPanel.DoneCheckBox.setEnabled(false);
         }
      }
   }

   @Override
   public void edit() {
      Integer id = this.view.getSelected();
      this.task = this.taskDAO.getById(id);
      this.view.TaskPanel.setTask(this.task);
      if (this.task != null) {
         this.view.TaskPanel.load();

         // Enable all the fields so the user can modify them.
         this.view.TaskPanel.TitleTextField.setEnabled(true);
         this.view.TaskPanel.DescriptionTextArea.setEnabled(true);
         this.view.TaskPanel.DoneCheckBox.setEnabled(true);

         // Enable Save and Cancel Button
         this.view.SaveTaskButton.setEnabled(true);
         this.view.CancelTaskButton.setEnabled(true);
      }
   }

   @Override
   public void cancel() {
      this.view.TaskPanel.setTask(null);
      this.view.TaskPanel.TitleTextField.setEnabled(false);
      this.view.TaskPanel.DescriptionTextArea.setEnabled(false);
      this.view.TaskPanel.DoneCheckBox.setEnabled(false);
      this.view.Table.clearSelection();
      this.view.SaveTaskButton.setEnabled(false);
      this.view.CancelTaskButton.setEnabled(false);
      this.view.TaskPanel.load();
   }

   @Override
   public void remove() {
      int input = JOptionPane.showConfirmDialog(
         null,
         "Do you want to remove the selected task?",
         "Remove Task",
         JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE
      );
      if (input == 0) {
         Integer id = this.view.getSelected();
         this.task = this.taskDAO.getById(id);
         this.taskDAO.delete(this.task);
         JOptionPane.showMessageDialog(
               null,
               "The task was removed successfully",
               "Task Removed", JOptionPane.OK_OPTION,
               View.SUCCESS_ICON);
         this.view.tasksAdapter.refresh();
         this.view.tasksAdapter.fireTableDataChanged();
         this.view.TaskPanel.setTask(null);
         this.view.TaskPanel.load();
         this.view.SaveTaskButton.setEnabled(false);
         this.view.CancelTaskButton.setEnabled(false);
         this.view.TaskPanel.TitleTextField.setEnabled(false);
         this.view.TaskPanel.DescriptionTextArea.setEnabled(false);
         this.view.TaskPanel.DoneCheckBox.setSelected(false);
         this.view.TaskPanel.DoneCheckBox.setEnabled(false);
      }
   }
}