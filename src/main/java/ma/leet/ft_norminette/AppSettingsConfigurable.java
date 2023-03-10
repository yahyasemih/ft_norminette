package ma.leet.ft_norminette;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AppSettingsConfigurable implements Configurable {

    private AppSettingsComponent mySettingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "42 Norminette Settings";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mySettingsComponent = new AppSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {AppSettingsState settings = AppSettingsState.getInstance();

        boolean modified = !mySettingsComponent.getPathText().equals(settings.path);

        return modified && mySettingsComponent.isValid();
    }

    @Override
    public void apply() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.path = mySettingsComponent.getPathText();
    }

    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();

        mySettingsComponent.setPathText(settings.path);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }
}
