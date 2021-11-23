package com.github.jenya705.mcapi.form;

import com.github.jenya705.mcapi.CommandSender;

/**
 * @author Jenya705
 */
public class ComponentFormProvider implements FormPlatformProvider {

    @Override
    public FormBuilder newBuilder() {
        return new ComponentFormBuilder();
    }

    @Override
    public void sendMessage(CommandSender sender, Form form) {
        if (form instanceof ComponentForm) {
            ComponentForm platformForm = (ComponentForm) form;
            sender.sendMessage(platformForm.getMessage());
            return;
        }
        throw new IllegalArgumentException("Form is not Component form");
    }
}
