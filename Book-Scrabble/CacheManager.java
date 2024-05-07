package test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import java.util.*;

public class CacheManager {
    private final int maxSize;
    private final CacheReplacementPolicy crp;
    private final HashSet<String> cache;

    public CacheManager(int maxSize, CacheReplacementPolicy crp) {
        this.maxSize = maxSize;
        this.crp = crp;
        this.cashe = new HashSet<>();
    }

    public boolean query(String item) {
        return cache.contains(item);
    }

    public add(String item){
        crp.add(item);
        cache.add(item);
        if(cache.size() > maxSize){
            String victim = crp.remove();
            cache.remove(victim);
        }
    }
}
