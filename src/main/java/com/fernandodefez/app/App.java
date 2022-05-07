package com.fernandodefez.app;

import com.fernandodefez.app.controllers.Controller;
import com.fernandodefez.app.controllers.TaskController;
import com.fernandodefez.app.models.Model;
import com.fernandodefez.app.models.Task;
import com.fernandodefez.app.views.task.View;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * @author Fernando Defez
 */
public class App 
{
    public static void main( String[] args )
    {

        SwingUtilities.invokeLater(() -> {
            FlatIntelliJLaf.setup();
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
                UIManager.put("Component.focusWidth", 0);
                Model model = new Task();
                View view = new View();
                Controller controller = new TaskController(view, model);
                view.setController(controller);
                view.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "App Initialization Failure", JOptionPane.ERROR_MESSAGE);
            }
        });

    }
}
