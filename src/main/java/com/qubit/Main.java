package com.qubit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        LimeCache<String, String> limeCache = LimeCache.builder()
                .maxSize(10)
                .limeCacheLoader(new LimeCacheLoader<String, String>(){
                    @Override
                    public String load(String key) {
                        return key + "_val";
                    }
                } )
                .build();

        limeCache.containsKey("1");
    }


}
