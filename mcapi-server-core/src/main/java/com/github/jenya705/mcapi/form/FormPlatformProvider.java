package com.github.jenya705.mcapi.form;

import com.github.jenya705.mcapi.ApiCommandSender;

/**
 * @author Jenya705
 */
public interface FormPlatformProvider {

    FormBuilder newBuilder();

    void sendMessage(ApiCommandSender sender, Form form);

}
