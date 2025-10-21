package ru.mentee.power.collections.library.iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mentee.power.collections.library.Book;
import ru.mentee.power.collections.library.Borrowing;
import ru.mentee.power.collections.library.LibraryManager;
import ru.mentee.power.collections.library.Reader;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IteratorsTest {

    private LibraryManager libraryManager;
    private Book book1;
    private Book book2;
    private Book book3;
    private Reader reader1;
    private Reader reader2;

    @BeforeEach
    void setUp() {
        libraryManager = new LibraryManager();

        book1 = new Book("111", "Война и мир", 1869, Book.Genre.ROMANCE);
        book1.setAuthors(new HashSet<>(List.of("Лев Толстой")));
        book2 = new Book("222", "Преступление и наказание", 1866, Book.Genre.ROMANCE);
        book2.setAuthors(new HashSet<>(List.of("Фёдор Достоевский", "Лев Толстой")));
        book3 = new Book("333", "Гарри Поттер и философский камень", 1997, Book.Genre.FANTASY);
        book3.setAuthors(new HashSet<>(List.of("Дж. К. Роулинг")));

        reader1 = new Reader("R1", "Иван Иванов", "ivan@mail.ru", Reader.ReaderCategory.STUDENT);
        reader2 = new Reader("R2", "Мария Петрова", "maria@mail.ru", Reader.ReaderCategory.STUDENT);

        libraryManager.addBook(book1);
        libraryManager.addBook(book2);
        libraryManager.addBook(book3);
        libraryManager.addReader(reader1);
        libraryManager.addReader(reader2);
    }

    @Test
    @DisplayName("Итератор по жанру и году должен возвращать только книги указанного жанра и года")
    void genreAndYearIteratorShouldReturnOnlyMatchingBooks() {
        Book fantasy2020 = new Book("444", "Дракон и Ворон", 2020, Book.Genre.FANTASY);
        Book fantasy2021 = new Book("555", "Башня Теней", 2021, Book.Genre.FANTASY);
        Book classic2020 = new Book("666", "Старый Дом", 2020, Book.Genre.ROMANCE);

        libraryManager.addBook(fantasy2020);
        libraryManager.addBook(fantasy2021);
        libraryManager.addBook(classic2020);

        Iterator<Book> iterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.FANTASY, 2020);

        List<Book> result = new ArrayList<>();
        iterator.forEachRemaining(result::add);

        assertThat(result)
                .hasSize(1)
                .first()
                .extracting(Book::getTitle)
                .isEqualTo("Дракон и Ворон");
    }

    @Test
    @DisplayName("Итератор по жанру и году должен вернуть пустой результат, если нет подходящих книг")
    void genreAndYearIteratorShouldReturnEmptyIteratorWhenNoMatches() {
        Book fantasy2020 = new Book("444", "Дракон и Ворон", 2020, Book.Genre.FANTASY);
        Book fantasy2021 = new Book("555", "Башня Теней", 2021, Book.Genre.FANTASY);
        Book classic2020 = new Book("666", "Старый Дом", 2020, Book.Genre.ROMANCE);

        libraryManager.addBook(fantasy2020);
        libraryManager.addBook(fantasy2021);
        libraryManager.addBook(classic2020);

        Iterator<Book> iterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.SCIENCE, 2020);

        List<Book> result = new ArrayList<>();
        iterator.forEachRemaining(result::add);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Итератор для книг с несколькими авторами должен возвращать только книги с указанным минимальным количеством авторов")
    void multipleAuthorsIteratorShouldReturnOnlyBooksWithMultipleAuthors() {
        Book multiAuthor = new Book("777", "Книга трёх авторов", 2022, Book.Genre.SCIENCE);
        multiAuthor.setAuthors(new HashSet<>(Arrays.asList("Иванов", "Петров", "Сидоров")));
        Book singleAuthor = new Book("888", "Книга одного автора", 2021, Book.Genre.SCIENCE);
        singleAuthor.setAuthors(new HashSet<>(Arrays.asList("Иванов")));

        libraryManager.addBook(multiAuthor);
        libraryManager.addBook(singleAuthor);

        Iterator<Book> iterator = libraryManager.getBooksWithMultipleAuthorsIterator(2);

        List<Book> result = new ArrayList<>();
        iterator.forEachRemaining(result::add);

        assertThat(result)
                .hasSize(2)
                .extracting(Book::getTitle)
                .containsExactlyInAnyOrderElementsOf(Arrays.asList("Книга трёх авторов", "Преступление и наказание"));
    }

    @Test
    @DisplayName("Итератор для книг с несколькими авторами должен вернуть пустой результат, если нет подходящих книг")
    void multipleAuthorsIteratorShouldReturnEmptyIteratorWhenNoMatches() {
        Book multiAuthor = new Book("777", "Книга трёх авторов", 2022, Book.Genre.SCIENCE);
        multiAuthor.setAuthors(new HashSet<>(Arrays.asList("Иванов", "Петров", "Сидоров")));
        Book singleAuthor = new Book("888", "Книга одного автора", 2021, Book.Genre.SCIENCE);
        singleAuthor.setAuthors(new HashSet<>(Arrays.asList("Иванов")));

        libraryManager.addBook(multiAuthor);
        libraryManager.addBook(singleAuthor);

        Iterator<Book> iterator = libraryManager.getBooksWithMultipleAuthorsIterator(5);

        List<Book> result = new ArrayList<>();
        iterator.forEachRemaining(result::add);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Итератор для просроченных выдач должен возвращать только просроченные и не возвращенные выдачи")
    void overdueBorrowingsIteratorShouldReturnOnlyOverdueBorrowings() {
        libraryManager.borrowBook("111", "R1", -2);
        libraryManager.borrowBook("222", "R2", -3);
        libraryManager.borrowBook("333", "R1", 2);

        Iterator<Borrowing> iterator = libraryManager.getOverdueBorrowingsIterator();

        List<Borrowing> result = new ArrayList<>();
        iterator.forEachRemaining(result::add);

        assertThat(result)
                .hasSize(2)
                .allMatch(Borrowing::isOverdue);
    }

    @Test
    @DisplayName("Итератор для просроченных выдач должен вернуть пустой результат, если нет просроченных выдач")
    void overdueBorrowingsIteratorShouldReturnEmptyIteratorWhenNoOverdues() {
        libraryManager.borrowBook("111", "R1", 2);
        libraryManager.borrowBook("222", "R2", 3);
        libraryManager.borrowBook("333", "R1", 2);

        Iterator<Borrowing> iterator = libraryManager.getOverdueBorrowingsIterator();

        List<Borrowing> result = new ArrayList<>();
        iterator.forEachRemaining(result::add);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Все итераторы должны выбрасывать NoSuchElementException при вызове next() на пустом итераторе")
    void allIteratorsShouldThrowNoSuchElementExceptionWhenEmpty() {
        Iterator<Book> booksByGenreAndYearIterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.SCIENCE, 2020);
        Iterator<Book> multipleAuthorsIterator = libraryManager.getBooksWithMultipleAuthorsIterator(5);
        Iterator<Borrowing> overdueBorrowingsIterator = libraryManager.getOverdueBorrowingsIterator();

        assertThatThrownBy(() -> booksByGenreAndYearIterator.next()).isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(() -> multipleAuthorsIterator.next()).isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(() -> overdueBorrowingsIterator.next()).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Метод remove() итератора должен удалять текущий элемент, если это поддерживается")
    void removeMethodShouldRemoveCurrentElementIfSupported() {
        Iterator<Book> booksByGenreAndYearIterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.ROMANCE, 1866);
        Iterator<Book> multipleAuthorsIterator = libraryManager.getBooksWithMultipleAuthorsIterator(2);

        Book nextBook = booksByGenreAndYearIterator.next();
        assertThatThrownBy(() -> booksByGenreAndYearIterator.remove()).isInstanceOf(UnsupportedOperationException.class);
        nextBook = multipleAuthorsIterator.next();
        assertThatThrownBy(() -> multipleAuthorsIterator.remove()).isInstanceOf(UnsupportedOperationException.class);
    }
}