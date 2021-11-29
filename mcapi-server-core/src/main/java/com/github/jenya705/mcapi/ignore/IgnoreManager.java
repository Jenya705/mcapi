package com.github.jenya705.mcapi.ignore;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.ApplicationClassActionEvent;
import com.github.jenya705.mcapi.OnInitializing;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Jenya705
 */
@Slf4j
public class IgnoreManager extends AbstractApplicationModule {

    private static final String fileName = "ignores.txt";

    private List<Predicate<String>> ignorableRules;

    @OnInitializing
    public void init() throws Exception {
        ignorableRules = new ArrayList<>();
        if (core().isExistsFile(fileName)) {
            String[] stringRules = new String(
                    core().loadSpecific(fileName)
            )
                    .replaceAll("\r", "")
                    .split("\n");
            for (String stringRule : stringRules) {
                if (stringRule.isEmpty()) continue;
                if (stringRule.endsWith("*")) {
                    String startsWith = stringRule.substring(0, stringRule.length() - 1);
                    ignorableRules.add(new SubIgnoreCriteria(startsWith));
                }
                else {
                    ignorableRules.add(new SingleIgnoreCriteria(stringRule));
                }
            }
        }
        else {
            core().saveSpecific(fileName, new byte[0]);
        }
        eventLoop()
                .handler(
                        ApplicationClassActionEvent.class,
                        event -> {
                            Class<?> currentClass = event.getBean().getClass();
                            boolean ignorable = false;
                            while (currentClass != null) {
                                Ignorable ignorableAnnotation = currentClass.getAnnotation(Ignorable.class);
                                if (ignorableAnnotation != null) {
                                    ignorable = ignorableAnnotation.value();
                                    break;
                                }
                                currentClass = currentClass.getSuperclass();
                            }
                            if (!ignorable) return;
                            if (ignorableRules
                                    .stream()
                                    .noneMatch(func -> func.test(event.getBean().getClass().getCanonicalName()))
                            ) return;
                            if (app().isDebug()) {
                                log.info("Ignoring {} class", event.getBean().getClass().getCanonicalName());
                            }
                            event.setCancelled(true);
                        }
                );
    }
}
