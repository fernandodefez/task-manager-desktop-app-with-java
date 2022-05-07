package com.fernandodefez.app.views.task;

import com.fernandodefez.app.models.Task;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import java.awt.Dimension;
import java.awt.Cursor;

public class TaskPanel extends JPanel {

    private Task task;

    /**
     * Creates a new <code>JPanel</code>
     */
    TaskPanel() {
        Border border = BorderFactory.createTitledBorder("Task");
        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        TitleTextField = new JTextField(17);
        TitleTextField.setMaximumSize(new Dimension(this.getMaximumSize().width,17));
        TitleTextField.setEnabled(false);
        TitleTextField.setName("Title");

        DescriptionTextArea = new JTextArea();
        DescriptionTextArea.setLineWrap(true);
        DescriptionTextArea.setWrapStyleWord(true);
        DescriptionTextArea.setEnabled(false);
        DescriptionTextArea.setName("Description");
        JScrollPane TextAreaScrollPane = new JScrollPane(DescriptionTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        TextAreaScrollPane.setMaximumSize(new Dimension(this.getMaximumSize().width, this.getMaximumSize().height));

        DoneCheckBox = new JCheckBox("Done");
        DoneCheckBox.setEnabled(false);
        DoneCheckBox.setBorderPainted(false);
        DoneCheckBox.setFocusPainted(false);
        DoneCheckBox.setCursor(new Cursor(Cursor.HAND_CURSOR));

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(TitleLabel)
                                .addComponent(TitleTextField)
                                .addComponent(DescriptionLabel)
                                .addComponent(TextAreaScrollPane)
                        )
                        .addComponent(DoneCheckBox)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(TitleLabel)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(TitleTextField)
                                .addComponent(DoneCheckBox)
                        )
                        .addComponent(DescriptionLabel)
                        .addComponent(TextAreaScrollPane)

        );

        add(TitleLabel);
        add(TitleTextField);
        add(DoneCheckBox);
        add(DescriptionLabel);
        add(TextAreaScrollPane);
        setLayout(layout);
        setBorder(border);
    }

    public void setTask(Task task){
        this.task = task;
    }

    public Task getTask() { return this.task; }

    /**
     * This method prints the task info into textfield, checkbox and textarea
     * */
    public void load() {
        if (this.task != null) {
            TitleTextField.setText(this.task.getTitle());
            DescriptionTextArea.setText(this.task.getDescription());
            DoneCheckBox.setSelected(this.task.isDone());

        } else {
            TitleTextField.setText("");
            DescriptionTextArea.setText("");
            DoneCheckBox.setSelected(false);
        }
        TitleTextField.requestFocus();
    }

    /**
     * This method stores the task title, description and status.
     * */
    public void save() {
        if (this.task == null){
            this.task = new Task();
        }
        this.task.setTitle(TitleTextField.getText());
        this.task.setDescription(DescriptionTextArea.getText());
        this.task.setIsDone(DoneCheckBox.isSelected());
    }

    public final JLabel TitleLabel = new JLabel("Title");
    public final JTextField TitleTextField;

    public final JLabel DescriptionLabel = new JLabel("Description");
    public final JTextArea DescriptionTextArea;

    public final JCheckBox DoneCheckBox;
}