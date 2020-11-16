package com.company;

import java.util.ArrayList;

public class Process {
    private ArrayList<Page> virtualMemory = new ArrayList<>();
    private int ID;

    Process(int ID) {
        this.ID = ID;
        int pagesCount = getRandomProcess();
        for (int i = 1; i <= pagesCount; i++) {
            virtualMemory.add(new Page(i, ID));
        }
        System.out.println("Процесс " + ID + " содержит " + pagesCount + " страниц");
    }

    private int getRandomProcess() {
        return (int)(Math.random() * 3) + 3;
    }
    public ArrayList<Page> getVirtualMemory() {

        return virtualMemory;
    }

    public int getID()
    {
        return ID;
    }
}
