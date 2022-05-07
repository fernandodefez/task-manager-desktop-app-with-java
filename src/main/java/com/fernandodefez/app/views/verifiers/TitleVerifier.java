package com.fernandodefez.app.views.verifiers;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.InputVerifier;

public class TitleVerifier extends InputVerifier {

    public static final Integer TITLE_MAX_LENGTH = 39;

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
        if (input instanceof JTextField) {
            String title = ((JTextField) input).getText().trim();
            if (!title.isEmpty()) {
                if ((input.getName().equals("Title") && title.length() <= TITLE_MAX_LENGTH)) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            input.getName() + " only allows " + TITLE_MAX_LENGTH + " chars.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        input.getName() + " is required.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }


}
