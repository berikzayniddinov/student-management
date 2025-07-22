package com.example.homework.strategy;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SortingStrategyFactory {

    private final Map<String, StudentSortingStrategy> strategyMap;

    public SortingStrategyFactory(List<StudentSortingStrategy> strategies) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        strategy -> strategy.getClass().getAnnotation(Component.class).value(),
                        Function.identity()
                ));
    }

    public StudentSortingStrategy getStrategy(String name) {
        return strategyMap.getOrDefault(name, strategyMap.get("sortByAge"));
    }
}