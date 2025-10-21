package ru.mentee.power.collections.library.comparator;

import ru.mentee.power.collections.library.Book;

import java.util.Comparator;

public class TitleComparator implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        if(o1.getTitle() == null)
            return -1;
        if(o2.getTitle() == null)
            return 1;

        return o1.getTitle().compareTo(o2.getTitle());
    }
}
