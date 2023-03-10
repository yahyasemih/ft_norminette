package ma.leet.ft_norminette;

import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class AppSettingsComponent {

    private static final String PATH_REGEX = "^(/([a-zA-Z_0-9-.]*/)*)*(norminette)$";

    private final JPanel myMainPanel;
    private final JBTextField pathText = new JBTextField();

    public AppSettingsComponent() {
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Path: "), pathText, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return pathText;
    }

    @NotNull
    public String getPathText() {
        if (!Pattern.matches(PATH_REGEX, pathText.getText())) {
            pathText.setBorder(BorderFactory.createLineBorder(JBColor.RED));
        } else {
            pathText.setBorder(BorderFactory.createLineBorder(JBColor.BLUE));
        }
        return pathText.getText();
    }

    public void setPathText(@NotNull String newText) {
        if (!Pattern.matches(PATH_REGEX, newText)) {
            pathText.setBorder(BorderFactory.createLineBorder(JBColor.RED));
        } else {
            pathText.setBorder(BorderFactory.createLineBorder(JBColor.BLUE));
        }
        pathText.setText(newText);
    }

    public boolean isValid() {
        try {
            Pattern.compile(pathText.getText());
        } catch (PatternSyntaxException exception) {
            return false;
        }
        return Pattern.matches(PATH_REGEX, pathText.getText());
    }
}
