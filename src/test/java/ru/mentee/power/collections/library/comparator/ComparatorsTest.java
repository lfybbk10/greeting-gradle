package ru.mentee.power.collections.library.comparator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mentee.power.collections.library.Book;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ComparatorsTest {

    @Test
    @DisplayName("TitleComparator должен сортировать книги по названию в алфавитном порядке")
    void titleComparatorShouldSortBooksAlphabetically() {
        Book book1 = new Book("111", "Август", 2000, Book.Genre.CHILDREN);
        Book book2 = new Book("111", "Поезд", 2000, Book.Genre.CHILDREN);
        Book book3 = new Book("111", "Поездка", 2000, Book.Genre.CHILDREN);
        Book book4 = new Book("111", "Явление", 2000, Book.Genre.CHILDREN);
        Book book5 = new Book("111", "Поезд", 2000, Book.Genre.CHILDREN);


        TitleComparator titleComparator = new TitleComparator();
        assertThat(titleComparator.compare(book1, book2)).isLessThan(0);
        assertThat(titleComparator.compare(book2, book3)).isLessThan(0);
        assertThat(titleComparator.compare(book3, book4)).isLessThan(0);
        assertThat(titleComparator.compare(book2, book5)).isEqualTo(0);
    }

    @Test
    @DisplayName("TitleComparator должен корректно обрабатывать null-значения")
    void titleComparatorShouldHandleNullValues() {
        Book book1 = new Book("111", "Август", 2000, Book.Genre.CHILDREN);
        Book book2 = new Book("111", "Поезд", 2000, Book.Genre.CHILDREN);
        Book book3 = new Book("111", "Поездка", 2000, Book.Genre.CHILDREN);
        Book book4 = new Book("111", "Явление", 2000, Book.Genre.CHILDREN);
        Book book5 = new Book("111", null, 2000, Book.Genre.CHILDREN);


        TitleComparator titleComparator = new TitleComparator();
        assertThat(titleComparator.compare(book2, book5)).isGreaterThan(0);
    }

    @Test
    @DisplayName("PublicationYearComparator должен сортировать книги от новых к старым")
    void publicationYearComparatorShouldSortBooksFromNewToOld() {
        Book book1 = new Book("111", "Август", 1990, Book.Genre.CHILDREN);
        Book book2 = new Book("111", "Поезд", 1975, Book.Genre.CHILDREN);
        Book book3 = new Book("111", "Поездка", 1800, Book.Genre.CHILDREN);
        Book book4 = new Book("111", "Явление", 1700, Book.Genre.CHILDREN);

        PublicationYearComparator comparator = new PublicationYearComparator();
        assertThat(comparator.compare(book1, book2)).isLessThan(0);
        assertThat(comparator.compare(book2, book3)).isLessThan(0);
        assertThat(comparator.compare(book3, book4)).isLessThan(0);
    }

    @Test
    @DisplayName("AvailabilityComparator должен сортировать сначала доступные, потом недоступные книги")
    void availabilityComparatorShouldSortAvailableFirst() {
        Book book1 = new Book("111", "Август", 1990, Book.Genre.CHILDREN);
        Book book2 = new Book("111", "Поезд", 1975, Book.Genre.CHILDREN);
        Book book3 = new Book("111", "Поездка", 1800, Book.Genre.CHILDREN);
        Book book4 = new Book("111", "Явление", 1700, Book.Genre.CHILDREN);

        book3.setAvailable(false);
        book4.setAvailable(false);

        PublicationYearComparator comparator = new PublicationYearComparator();
        assertThat(comparator.compare(book1, book2)).isLessThan(0);
        assertThat(comparator.compare(book2, book3)).isLessThan(0);
        assertThat(comparator.compare(book3, book4)).isLessThan(0);
    }

    @Test
    @DisplayName("GenreAndTitleComparator должен сортировать сначала по жанру, потом по названию")
    void genreAndTitleComparatorShouldSortByGenreThenByTitle() {
        Book book1 = new Book("111", "Август", 1990, Book.Genre.FANTASY);
        Book book2 = new Book("111", "Поезд", 1975, Book.Genre.CHILDREN);
        Book book3 = new Book("111", "Поездка", 1800, Book.Genre.FANTASY);
        Book book4 = new Book("111", "Явление", 1700, Book.Genre.CHILDREN);

        List<Book> books = new ArrayList<>(List.of(book1, book2, book3, book4));

        // Act — сортируем с помощью GenreAndTitleComparator
        books.sort(new GenreAndTitleComparator());

        // Assert — проверяем порядок: сначала CLASSIC по названию, потом FANTASY по названию
        assertThat(books)
                .extracting(Book::getTitle)
                .containsExactly(
                        "Август",
                        "Поездка",
                        "Поезд",
                        "Явление"
                );
    }
}