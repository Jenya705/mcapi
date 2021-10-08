package com.github.jenya705.mcapi.command.advanced;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.command.CommandTab;
import com.github.jenya705.mcapi.module.database.safe.DatabaseGetter;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@UtilityClass
public class DefaultTabFunction {

    public TabFunction syncFunction(BiFunction<CommandSender, String, List<CommandTab>> function) {
        return (sender, permission, async) -> function.apply(sender, permission);
    }

    public TabFunction syncFunction(Supplier<List<CommandTab>> supplier) {
        return (sender, permission, async) -> supplier.get();
    }

    public TabFunction syncOldFunction(BiFunction<CommandSender, String, List<String>> function) {
        return (sender, permission, async) -> parseOld(function.apply(sender, permission));
    }

    public TabFunction syncOldFunction(Supplier<List<String>> supplier) {
        return (sender, permission, async) -> parseOld(supplier.get());
    }

    public TabFunction databaseFunction(DatabaseTabFunction databaseTabFunction, DatabaseGetter safeSync, DatabaseGetter safeAsync) {
        return (sender, permission, async) -> async ?
                databaseTabFunction.tab(sender, permission, safeAsync) :
                databaseTabFunction.tab(sender, permission, safeSync);
    }

    private List<CommandTab> parseOld(List<String> oldTab) {
        return oldTab
                .stream()
                .map(CommandTab::of)
                .collect(Collectors.toList());
    }
}
