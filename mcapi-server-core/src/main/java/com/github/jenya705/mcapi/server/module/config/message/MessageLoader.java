package com.github.jenya705.mcapi.server.module.config.message;

import lombok.Cleanup;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Jenya705
 */
public class MessageLoader {

    private static final Set<Locale> translations = Set.of(
            Locale.ENGLISH,
            new Locale("ru")
    );

    private TranslationRegistry registry;

    public void load() {
        registry = TranslationRegistry.create(Key.key("mcapi", "main"));
        registry.defaultLocale(Locale.ENGLISH);
        for (Locale translation: translations) {
            try {
                @Cleanup InputStream localeInputStream = getClass()
                        .getClassLoader()
                        .getResourceAsStream("translations/" + translation.getLanguage() + ".properties");
                if (localeInputStream == null) {
                    continue;
                }
                registry.registerAll(
                        translation,
                        new PropertyResourceBundle(new InputStreamReader(localeInputStream, StandardCharsets.UTF_8)),
                        false
                );
            } catch (Exception e) {
                // IGNORED
            }
        }
        GlobalTranslator.get().addSource(registry);
    }

}
