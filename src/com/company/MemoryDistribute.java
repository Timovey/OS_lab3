package com.company;

import java.util.ArrayList;

public class MemoryDistribute {
    private final Table physicalMemory;
    private final ArrayList<Process> processList = new ArrayList<>();
    private int sizeOfLoop = 30;

    public MemoryDistribute(int size) {
        physicalMemory = new Table(size);
        for (int i = 0; i < getRandomPHMemory(); i++) {
            processList.add(new Process(i));
        }
        System.out.println();
    }

    private int getRandomPHMemory() {
        return (int)(Math.random() * 6) + 2;
    }

    public static int getRandomIndex(int index) {
        return (int)(Math.random() * index);
    }

    public void run() {
        for (int j = 0; j < sizeOfLoop; j++) {
            for (int m = 0; m < processList.size(); m++) {
                Process process = processList.get(m);

                if (j % 2 == 0) {
                    swapping(process);
                    continue;
                }
                int index = getRandomIndex(process.getVirtualMemory().size());
                Page usefulPage = process.getVirtualMemory().get(index);

                //если эта страница находится в физической памяти, то устанавливаем обращение
                if (usefulPage.isInPhysicalMemory()) {
                    usefulPage.setRead(true); // устанавливаем обращение
                    System.out.println("Страница " + usefulPage.getID() + " процесса " + process.getID() +
                            " находится в ФП с позицией: " + usefulPage.getPhysicalPageID() + "; установлено обращение");

                    //если страницы нет, но ФП не заполнена
                } else if (!physicalMemory.isFilled()) {
                    physicalMemory.add(usefulPage);
                    usefulPage.setInPhysicalMemory(true);
                    usefulPage.setPhysicalPageID(physicalMemory.getCount());
                    System.out.println("Страница " + usefulPage.getID() + " процесса " + process.getID() + " теперь находится в физической памяти на позиции:  " + physicalMemory.getCount());

                    //если в ФП нет места
                } else {
                    System.out.println("Страничное прерывание: ");

                    Page page = physicalMemory.poll();
                    if (page.getRead()) { // если у страницы обращение
                        boolean addPage = false;
                        while(!addPage) {
                            page.setRead(false);
                            physicalMemory.add(page);
                            System.out.println("Помещаем страницу " + page.getPhysicalPageID() + " процесса " + page.getProcessID() + " в конец очереди");
                            page = physicalMemory.poll();
                            if(!page.getRead()) {
                                addPage = true;
                                System.out.println("Выгружаем страницу " + page.getPhysicalPageID() + " процесса " + page.getProcessID());
                                usefulPage.setInPhysicalMemory(true);
                                usefulPage.setPhysicalPageID(page.getPhysicalPageID());
                                page.setInPhysicalMemory(false);
                                physicalMemory.add(usefulPage);
                                System.out.println("Загружаем страницу " + page.getID() + " процесса " + process.getID());
                            }
                        }

                    } else {
                        System.out.println("Выгружаем страницу " + page.getPhysicalPageID() + " процесса " + page.getProcessID());
                        usefulPage.setInPhysicalMemory(true);
                        usefulPage.setPhysicalPageID(page.getPhysicalPageID());
                        page.setInPhysicalMemory(false);
                        physicalMemory.add(usefulPage);
                        System.out.println("Загружаем страницу " + usefulPage.getID() + " процесса " + process.getID());
                    }

                    System.out.println("Страничное прерывание завершено ");
                }

                System.out.println();

            }
        }
    }

    public void swapping(Process process) {
        System.out.println("Своппинг: ");

        if (physicalMemory.getCount() == 0) { // в самом начале
            for (int i = 0; i < process.getVirtualMemory().size(); i++) {
                Page trashPage;
                trashPage = process.getVirtualMemory().get(i);
                physicalMemory.add(trashPage);
                System.out.println("Добавление страницы " + trashPage.getID() + " процесса " + trashPage.getProcessID());
            }
        } else if (process.getVirtualMemory().size() - physicalMemory.getCount() > 0) { // если у процесса больше страниц, чем в очереди
            for (int i = 0; i < physicalMemory.getCount(); i++) {
                Page trashPage = physicalMemory.poll();
                if (trashPage != null) {
                    System.out.println("Запись на диск страницы " + trashPage.getID() + " процесса " + trashPage.getProcessID());
                }
                trashPage = process.getVirtualMemory().get(i);
                physicalMemory.add(trashPage);
                System.out.println("Добавление страницы " + trashPage.getID() + " процесса " + trashPage.getProcessID());
            }
            for (int i = physicalMemory.getCount(); i < process.getVirtualMemory().size(); i++) {
                Page trashPage;
                trashPage = process.getVirtualMemory().get(i);
                physicalMemory.add(trashPage);
                System.out.println("Добавление страницы " + trashPage.getID() + " процесса " + trashPage.getProcessID());
            }
        } else { // если у процесса не больше страниц, чем у очереди
            for (int i = 0; i < process.getVirtualMemory().size(); i++) {
                Page trashPage = physicalMemory.poll();
                if (trashPage != null) {
                    System.out.println("Запись на диск страницы " + trashPage.getID() + " процесса " + trashPage.getProcessID());
                }
                trashPage = process.getVirtualMemory().get(i);
                physicalMemory.add(trashPage);
                System.out.println("Добавление страницы " + trashPage.getID() + " процесса " + trashPage.getProcessID());
            }
        }
        System.out.println("Завершение своппинга \n");
    }

}
