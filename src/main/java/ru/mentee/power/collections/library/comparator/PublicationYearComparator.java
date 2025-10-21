package ru.mentee.power.collections.library.comparator;

import ru.mentee.power.collections.library.Book;

import java.util.Comparator;

public class PublicationYearComparator implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        if(o1 == null)
            return -1;
        if(o2 == null)
            return 1;
        return o2.getPublicationYear() - o1.getPublicationYear();
    }
}
