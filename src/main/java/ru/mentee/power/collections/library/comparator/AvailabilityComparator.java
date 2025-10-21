package ru.mentee.power.collections.library.comparator;

import ru.mentee.power.collections.library.Book;

import java.util.Comparator;

public class AvailabilityComparator implements Comparator<Book> {
    @Override
    public int compare(Book o1, Book o2) {
        if(o1 != null && o1.isAvailable())
            return -1;
        if(o2 != null && o2.isAvailable())
            return 1;
        return 0;
    }
}
