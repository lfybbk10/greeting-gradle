package ru.mentee.power.comparators;

import java.util.*;
import java.util.function.Predicate;

/**
 * Класс для управления каталогом книг в библиотеке
 */
public class BookCatalog {
    private List<Book> books;

    /**
     * Создает пустой каталог книг
     */
    public BookCatalog() {
        books = new ArrayList<>();
    }

    /**
     * Добавляет книгу в каталог
     * @param book книга для добавления
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Возвращает неизменяемый список всех книг в каталоге
     * @return список книг
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    /**
     * Сортирует книги по заданному компаратору
     * @param comparator компаратор для сортировки
     * @return новый отсортированный список книг (исходный список не меняется)
     */
    public List<Book> sortBooks(Comparator<Book> comparator) {
        List<Book> newList = new ArrayList<>(books);
        newList.sort(comparator);
        return newList;
    }

    /**
     * Фильтрует книги по заданному предикату
     * @param predicate условие фильтрации
     * @return новый список книг, удовлетворяющих условию
     */
    public List<Book> filterBooks(Predicate<Book> predicate) {
        if (predicate == null) {
            throw new IllegalArgumentException("Предикат не может быть null");
        }

        List<Book> result = new ArrayList<>();

        for (Book book : books) {
            if (predicate.test(book)) {
                result.add(book);
            }
        }

        return result;
    }

    // Статические компараторы для удобства использования

    /**
     * @return компаратор для сортировки по названию (по алфавиту)
     */
    public static Comparator<Book> byTitle() {
        return (o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle());
    }

    /**
     * @return компаратор для сортировки по автору (по алфавиту)
     */
    public static Comparator<Book> byAuthor() {
        return Comparator.comparing(Book::getAuthor);
    }

    /**
     * @return компаратор для сортировки по году издания (от старых к новым)
     */
    public static Comparator<Book> byYearPublished() {
        return Comparator.comparing(Book::getYearPublished);
    }

    /**
     * @return компаратор для сортировки по количеству страниц (от меньшего к большему)
     */
    public static Comparator<Book> byPageCount() {
        return Comparator.comparing(Book::getPageCount);
    }

    /**
     * Создает сложный компаратор для сортировки по нескольким критериям
     * @param comparators список компараторов в порядке приоритета
     * @return композитный компаратор
     */
    public static Comparator<Book> multipleComparators(List<Comparator<Book>> comparators) {
        return new Comparator<Book>() {

            @Override
            public int compare(Book o1, Book o2) {
                for (Comparator<Book> comparator : comparators) {
                    if(comparator.compare(o1, o2) != 0) {
                        return comparator.compare(o1, o2);
                    }
                }
                return 0;
            }
        };
    }
}