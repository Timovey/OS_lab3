package com.company;


public class Main {
    private static int memorySize = 512;
    private static int pageSize = 64;

    public static void main(String[] args) {
        MemoryDistribute memoryDistribute = new MemoryDistribute(memorySize/ pageSize);
        memoryDistribute.run();
    }

}
