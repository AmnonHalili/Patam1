package test;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFU implements CacheReplacementPolicy {
    private final Map<String, Integer> frequencyMap;
    private final Map<Integer, LinkedHashSet<String>> frequencyLists;
    private int minFrequency;

    public LFU() {
        this.frequencyMap = new HashMap<>();
        this.frequencyLists = new HashMap<>();
        this.minFrequency = 0;
        frequencyLists.put(1, new LinkedHashSet<>());
    }

    @Override
    public void add(String item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null to LFU");
        }

        int frequency = frequencyMap.getOrDefault(item, 0);
        frequencyMap.put(item, frequency + 1);

        // Check if the set for the current frequency exists
        if (frequencyLists.containsKey(frequency)) {
            frequencyLists.get(frequency).remove(item);
        }

        if (frequency == minFrequency && frequencyLists.getOrDefault(frequency, new LinkedHashSet<>()).isEmpty()) {
            minFrequency++;
        }

        frequencyLists.computeIfAbsent(frequency + 1, k -> new LinkedHashSet<>()).add(item);
    }

    @Override
    public String remove() {
        if (frequencyLists.get(minFrequency) == null || frequencyLists.get(minFrequency).isEmpty()) {
            throw new IllegalStateException("LFU cache is empty");
        }

        String victim = frequencyLists.get(minFrequency).iterator().next();
        frequencyLists.get(minFrequency).remove(victim);
        frequencyMap.remove(victim);
        return victim;
    }

}
