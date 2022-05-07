package com.fernandodefez.app.views.task;

import com.fernandodefez.app.dao.DAO;
import com.fernandodefez.app.models.Task;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TasksAdapter extends AbstractTableModel {

    private final DAO<Task, Integer> taskDAO;
    private List<Task> tasks;

    public TasksAdapter(DAO<Task, Integer> taskDAO) {
        this.taskDAO = taskDAO;
        this.tasks = this.taskDAO.getAll();
    }

    public void refresh() {
        this.tasks = this.taskDAO.getAll();
    }

    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    @Override
    public int getRowCount() {
        return this.tasks.size();
    }

    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     */
    @Override
    public int getColumnCount() {
        return 5;
    }


    /**
     * Returns a default name for the column using spreadsheet conventions:
     * A, B, C, ... Z, AA, AB, etc.  If <code>column</code> cannot be found,
     * returns an empty string.
     *
     * @param column the column being queried
     * @return a string containing the default name of <code>column</code>
     */
    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "Id";
        } else if (column == 1) {
            return "Title";
        } else if (column == 2) {
            return "Description";
        } else if (column == 3) {
            return "Done";
        } else if (column == 4) {
            return "Created At";
        }
        return "Unknown";
    }


    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param rowIndex    the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     * @return the value Object at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Task selectedTask = tasks.get(rowIndex);
        if (columnIndex == 0) {
            return selectedTask.getId();
        } else if (columnIndex == 1) {
            return selectedTask.getTitle();
        } else if (columnIndex == 2) {
            return selectedTask.getDescription();
        } else if (columnIndex == 3) {
            return selectedTask.isDone();
        } else if (columnIndex == 4) {
            return selectedTask.getCreatedAt();
        }
        return null;
    }
}
