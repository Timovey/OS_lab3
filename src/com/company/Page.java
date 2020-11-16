package com.company;

public class Page {
    private int ID;
    private boolean isInPhysicalMemory;
    private boolean read = false;
    private int physicalPageID;
    private final int processID;

    public Page(int ID, int processID) {
        this.ID = ID;
        this.processID = processID;
    }

    public int getID() {
        return ID;
    }

    public boolean getRead() {
        return read;
    }

    public void setRead(boolean read) {

        this.read = read;
    }

    public int getPhysicalPageID() {

        return physicalPageID;
    }

    public void setPhysicalPageID(int physicalPageID) {

        this.physicalPageID = physicalPageID;
    }

    public boolean isInPhysicalMemory() {
        return isInPhysicalMemory;
    }

    public void setInPhysicalMemory(boolean inPhysicalMemory) {

        isInPhysicalMemory = inPhysicalMemory;
    }


    public int getProcessID() {
        return processID;
    }
}
