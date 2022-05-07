package com.fernandodefez.app.views.verifiers;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.InputVerifier;

public class DescriptionVerifier extends InputVerifier {

    public static final Integer DESCRIPTION_MAX_LENGTH = 199;

    /**
     * Checks whether the JComponent's input is valid. This method should
     * have no side effects. It returns a boolean indicating the status
     * of the argument's input.
     *
     * @param input the JComponent to verify
     * @return {@code true} when valid, {@code false} when invalid
     * @see JComponent#setInputVerifier
     * @see JComponent#getInputVerifier
     */
    @Override
    public boolean verify(JComponent input) {
        if (input instanceof JTextArea) {
            String description = ((JTextArea) input).getText().trim();
            if (!description.isEmpty()) {
                if (input.getName().equals("Description") && description.length() <= DESCRIPTION_MAX_LENGTH) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            input.getName() + " only allows " + DESCRIPTION_MAX_LENGTH + " chars.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        input.getName() + " is required.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }
}
