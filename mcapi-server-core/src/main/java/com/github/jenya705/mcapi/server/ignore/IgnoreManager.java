package com.github.jenya705.mcapi.server.ignore;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.event.application.ApplicationClassActionEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Jenya705
 */
@Singleton
public class IgnoreManager extends AbstractApplicationModule {

    private static final String fileName = "ignores.txt";

    private final List<Predicate<String>> ignorableRules = new ArrayList<>();
    private final Logger log;

    @Inject
    public IgnoreManager(ServerApplication application, Logger log) throws Exception {
        super(application);
        this.log = log;
        if (core().isExistsFile(fileName)) {
            String[] stringRules = new String(
                    core().loadSpecific(fileName)
            )
                    .replace("\r", "")
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
                            if (debug()) {
                                log.info("Ignoring {} class", event.getBean().getClass().getCanonicalName());
                            }
                            event.setCancelled(true);
                        }
                );
    }
}
