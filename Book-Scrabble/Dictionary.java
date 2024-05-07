package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;


public class Dictionary {
    
    private final CacheManager existingWordsCache;
    private final CacheManager nonExistingWordsCache;
    private final BloomFilter bloomFilter;
    private final String[] textfile;

    public Dictionary(String... fileNames) {
        existingWordsCache = new CacheManager(400, new LRU());
        nonExistingWordsCache = new CacheManager(100, new LFU());
        bloomFilter = new BloomFilter(256, "MD5", "SHA1");
        this.textfile = fileNames;
        for (String fileName : textfile) {
            try {
                FileReader fr = new FileReader(fileName);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    String[] words = line.split(" ");
                    for (String word : words) {
                        bloomFilter.add(word);
                    }
                }
                fr.close();
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

        public boolean query(String word) {
            if (existingWordsCache.query(word)) {
                return true;
            }
            if (nonExistingWordsCache.query(word)) {
                return false;
            }
            boolean isInBloomFilter = bloomFilter.contains(word);
            if (isInBloomFilter) {
                existingWordsCache.add(word); 
            } else {
                nonExistingWordsCache.add(word);
            }  
            return isInBloomFilter;
        }
    
        public boolean challenge(String word) {
            IOSearcher searcher = new IOSearcher();
            boolean result = searcher.search(word, textfile);
            try {
                if (result) {
                    existingWordsCache.add(word);
                } else {
                    nonExistingWordsCache.add(word);
                }
            } catch (Exception e) {
                return false;
            }
            return result;
        }
        
        
}
    
    
    
    
    