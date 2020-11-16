package com.company;

import java.util.ArrayList;

public class Table {
    private ArrayList<Page> table = new ArrayList<>();
    private int size;
    private int count;

    public Table(int maxSize) {
        this.size = maxSize;
        count = 0;
    }

    public void add(Page page) {
        if (!isFilled()) {
            table.add(count,page);
            count++;
        }

    }

    public Page poll() {
        if (count != 0) {
            Page page = table.get(0);
            table.remove(0);
            count--;
            return page;
        }
        return null;
    }


    public int getCount() {
        return count;
    }

    public boolean isFilled() {
        if (count >= size - 1) {
            return true;
        }
        return false;
    }

}
