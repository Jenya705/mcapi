package com.github.jenya705.mcapi.module.link;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.ignore.SingleIgnoreCriteria;
import com.github.jenya705.mcapi.ignore.SubIgnoreCriteria;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Jenya705
 */
public class LinkIgnores extends AbstractApplicationModule {

    private final List<Predicate<String>> truePredicates = new ArrayList<>();
    private final List<Predicate<String>> falsePredicates = new ArrayList<>();

    public LinkIgnores(ServerApplication application) {
        super(application);
    }

    @SneakyThrows
    public void init() {
        String[] rules = new String(
                core().loadSpecific("link-ignores.txt")
        )
                .replace("\r", "")
                .split("\n");
        for (String rule : rules) {
            if (rule.startsWith("-")) {
                falsePredicates.add(getPredicate(rule.substring(1)));
            }
            else {
                truePredicates.add(getPredicate(rule));
            }
        }
    }

    private Predicate<String> getPredicate(String str) {
        if (str.endsWith("*")) {
            return new SubIgnoreCriteria(str.substring(0, str.length() - 1));
        }
        return new SingleIgnoreCriteria(str);
    }

    public boolean isIgnored(String permission) {
        boolean isTrue = truePredicates
                .stream()
                .anyMatch(predicate -> predicate.test(permission));
        if (isTrue) return false;
        return falsePredicates
                .stream()
                .anyMatch(predicate -> predicate.test(permission));
    }
}
