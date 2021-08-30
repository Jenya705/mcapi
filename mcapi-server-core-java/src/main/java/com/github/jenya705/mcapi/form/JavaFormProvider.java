package com.github.jenya705.mcapi.form;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.JavaCommandSender;

/**
 * @author Jenya705
 */
public class JavaFormProvider implements FormPlatformProvider {

    @Override
    public FormBuilder newBuilder() {
        return new JavaFormBuilder();
    }

    @Override
    public void sendMessage(ApiCommandSender sender, Form form) {
        if (form instanceof JavaForm && sender instanceof JavaCommandSender) {
            JavaForm platformForm = (JavaForm) form;
            JavaCommandSender platformSender = (JavaCommandSender) sender;
            platformSender.sendMessage(platformForm.getMessage());
        }
        else {
            throw new IllegalStateException("Objects is not java platform objects");
        }
    }
}
