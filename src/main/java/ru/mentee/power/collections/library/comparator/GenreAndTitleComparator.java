package ru.mentee.power.collections.library.comparator;

import ru.mentee.power.collections.library.Book;

import java.util.Comparator;

public class GenreAndTitleComparator implements Comparator<Book> {
    @Override
    public int compare(Book o1, Book o2) {
        if(o1 == null)
            return -1;
        if(o2==null)
            return 1;

        int genreCompare = o1.getGenre().compareTo(o2.getGenre());
        if (genreCompare != 0) {
            return genreCompare;
        }

        return o1.getTitle().compareToIgnoreCase(o2.getTitle());
    }
}
