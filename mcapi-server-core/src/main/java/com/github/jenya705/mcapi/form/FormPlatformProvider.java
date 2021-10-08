package com.github.jenya705.mcapi.form;

import com.github.jenya705.mcapi.CommandSender;

/**
 * @author Jenya705
 */
public interface FormPlatformProvider {

    FormBuilder newBuilder();

    void sendMessage(CommandSender sender, Form form);
}
