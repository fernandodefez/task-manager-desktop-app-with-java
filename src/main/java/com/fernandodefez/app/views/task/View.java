package com.fernandodefez.app.views.task;

import com.fernandodefez.app.controllers.Controller;
import com.fernandodefez.app.dao.DAO;
import com.fernandodefez.app.models.Task;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JScrollPane;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Toolkit;
import java.awt.Cursor;
import java.awt.Component;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {

    public TasksAdapter tasksAdapter;
    public TaskPanel TaskPanel;

    public View() {
        super("Tasks Manager");
        initComponents();
    }

    /**
     * @param controller Controller
     * */
    public void setController(Controller controller) {
        NewTaskButton.addActionListener(controller);
        EditTaskButton.addActionListener(controller);
        RemoveTaskButton.addActionListener(controller);
        SaveTaskButton.addActionListener(controller);
        CancelTaskButton.addActionListener(controller);
    }

    public void initComponents() {
        TaskPanel = new TaskPanel();

        ToolBar = new JToolBar();
            NewTaskButton = new JButton("New", new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("new-task-icon.png"))));
            NewTaskButton.setMnemonic('O');
            NewTaskButton.setFocusPainted(false);
            NewTaskButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Separator1 = new JToolBar.Separator();
            EditTaskButton = new JButton("Edit", new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("edit-task-icon.png"))));
            EditTaskButton.setMnemonic('O');
            EditTaskButton.setEnabled(false);
            EditTaskButton.setFocusPainted(false);
            EditTaskButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            RemoveTaskButton = new JButton("Remove", new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("remove-task-icon.png"))));
            RemoveTaskButton.setMnemonic('O');
            RemoveTaskButton.setEnabled(false);
            RemoveTaskButton.setFocusPainted(false);
            RemoveTaskButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Separator2 = new JToolBar.Separator();
            SaveTaskButton = new JButton("Save", new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("save-task-icon.png"))));
            SaveTaskButton.setMnemonic('O');
            SaveTaskButton.setEnabled(false);
            SaveTaskButton.setFocusPainted(false);
            SaveTaskButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            CancelTaskButton = new JButton("Cancel", new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("cancel-task-icon.png"))));
            CancelTaskButton.setMnemonic('O');
            CancelTaskButton.setEnabled(false);
            CancelTaskButton.setFocusPainted(false);
            CancelTaskButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Separator3 = new JToolBar.Separator();
            SelectThemeButton = new JButton("Dark", new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("dark.png"))));
            SelectThemeButton.addActionListener(this);
            SelectThemeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ToolBar.add(NewTaskButton);
        ToolBar.add(Separator1);
        ToolBar.add(EditTaskButton);
        ToolBar.add(RemoveTaskButton);
        ToolBar.add(Separator2);
        ToolBar.add(SaveTaskButton);
        ToolBar.add(CancelTaskButton);
        ToolBar.add(Separator3);
        ToolBar.add(SelectThemeButton);

        Table = new JTable(){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                int rendererWidth = component.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                return component;
            }
        };
        Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Table.getTableHeader().setReorderingAllowed(false);
        ScrollPane = new JScrollPane(Table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public void initTable(DAO<Task, Integer> dao) {
        tasksAdapter = new TasksAdapter(dao);
        Table.setModel(tasksAdapter);
        Table.getSelectionModel().addListSelectionListener(e -> {
            boolean isSelected = (Table.getSelectedRow() != -1);
            EditTaskButton.setEnabled(isSelected);
            RemoveTaskButton.setEnabled(isSelected);
        });
    }

    public void run() {
        // src/main/resources/task-executable-icon.ico
        // ClassLoader.getSystemClassLoader().getResource("to-do-icon.png")
        Image icon = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("to-do-icon.png"));
        setIconImage(icon);
        setLayout(new BorderLayout());
        add(ToolBar, BorderLayout.NORTH);
        add(TaskPanel, BorderLayout.LINE_START);
        add(ScrollPane, BorderLayout.CENTER); // <-- Table

        pack();
        setSize(750, 400);
        setLocationRelativeTo(null);
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * @return id Integer
     * */
    public Integer getSelected() {
        return (Integer) Table.getValueAt(Table.getSelectedRow(), 0);
    }

    /**
     * Theme toggle
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String theme = e.getActionCommand();
        try {
            System.out.println(e.getActionCommand().strip());
            if (theme.equals("Light")) {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
                UIManager.put("Component.focusWidth", 0);
                SelectThemeButton.setText("Dark");
                SelectThemeButton.setIcon(
                        new ImageIcon(
                                Toolkit.getDefaultToolkit().getImage(
                                        ClassLoader.getSystemClassLoader().getResource("dark.png"))));
            } else if (theme.equals("Dark")) {
                UIManager.setLookAndFeel(new FlatDarculaLaf());
                UIManager.put("Component.focusWidth", 0);
                SelectThemeButton.setText("Light");
                SelectThemeButton.setIcon(
                    new ImageIcon(  
                        Toolkit.getDefaultToolkit()
                            .getImage(
                                ClassLoader.getSystemClassLoader().getResource("light.png"))));
            }
            // Apply the new theme
            FlatLaf.updateUI();
        } catch (UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    ex.getMessage(),
                    ex.getCause().getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * GUI Components
     * */
    protected JToolBar ToolBar;
    private JButton NewTaskButton;
    protected JToolBar.Separator Separator1;
    public JButton EditTaskButton;
    public JButton RemoveTaskButton;
    protected JToolBar.Separator Separator2;
    public JButton SaveTaskButton;
    public JButton CancelTaskButton;
    protected JToolBar.Separator Separator3;
    public JButton SelectThemeButton;

    protected JScrollPane ScrollPane;
    public JTable Table;

    public static ImageIcon SUCCESS_ICON = new ImageIcon(
            Toolkit.getDefaultToolkit().getImage(
                    ClassLoader.getSystemClassLoader().getResource("success-icon.png")));
}