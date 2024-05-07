package test;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRU implements CacheReplacementPolicy {
    private final LinkedHashMap<String, Boolean> lruMap;

    public LRU() {
        this.lruMap = new LinkedHashMap<>(16, 0.75f, true);
    }

    @Override
    public void add(String item) {
        lruMap.put(item, true);

    }

    @Override
    public String remove() {
        return lruMap.keySet().iterator().next();
    }

}
