package com.github.jenya705.mcapi.server.form;

import com.github.jenya705.mcapi.CommandSender;
import com.google.inject.ImplementedBy;

/**
 * @author Jenya705
 */
@ImplementedBy(ComponentFormProvider.class)
public interface FormPlatformProvider {

    FormBuilder newBuilder();

    void sendMessage(CommandSender sender, Form form);
}
