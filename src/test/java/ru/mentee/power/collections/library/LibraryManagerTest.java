package ru.mentee.power.collections.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.*;

class LibraryManagerTest {

    private LibraryManager libraryManager;
    private Book book1, book2, book3;
    private Reader reader1, reader2;

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

    @Nested
    @DisplayName("Тесты CRUD операций с книгами")
    class BookCrudTests {
        @Test
        @DisplayName("Должен корректно добавлять книгу в библиотеку")
        void shouldAddBookCorrectly() {
            Book book = new Book("1111", "Test", 1869, Book.Genre.ROMANCE);
            boolean added = libraryManager.addBook(book);
            assertThat(added).isTrue();
            assertThat(libraryManager.getBookByIsbn("1111"))
                    .isNotNull()
                    .extracting(Book::getTitle)
                    .isEqualTo("Test");
        }

        @Test
        @DisplayName("Не должен добавлять дубликат книги")
        void shouldNotAddDuplicateBook() {
            Book book = new Book("111", "Test", 1869, Book.Genre.ROMANCE);
            boolean added = libraryManager.addBook(book);
            assertThat(added).isFalse();
        }

        @Test
        @DisplayName("Должен возвращать книгу по ISBN")
        void shouldReturnBookByIsbn() {
            assertThat(libraryManager.getBookByIsbn("111")).isEqualTo(book1);
        }

        @Test
        @DisplayName("Должен возвращать null при поиске книги по несуществующему ISBN")
        void shouldReturnNullForNonExistingIsbn() {
            assertThat(libraryManager.getBookByIsbn("1111")).isNull();
        }

        @Test
        @DisplayName("Должен корректно удалять книгу из библиотеки")
        void shouldRemoveBookCorrectly() {
            boolean removed = libraryManager.removeBook("111");
            assertThat(removed).isTrue();
            assertThat(libraryManager.getBookByIsbn("111")).isNull();
        }

        @Test
        @DisplayName("Должен возвращать false при попытке удалить несуществующую книгу")
        void shouldReturnFalseWhenRemovingNonExistingBook() {
            boolean removed = libraryManager.removeBook("1111");
            assertThat(removed).isFalse();
        }
    }

    @Nested
    @DisplayName("Тесты поиска и фильтрации книг")
    class BookSearchAndFilterTests {
        @Test
        @DisplayName("Должен возвращать список всех книг")
        void shouldReturnAllBooks() {
            List<Book> books = libraryManager.getAllBooks();
            assertThat(books).hasSize(3).contains(book1, book2, book3);
        }

        @Test
        @DisplayName("Должен возвращать список книг определенного жанра")
        void shouldReturnBooksByGenre() {
            List<Book> books = libraryManager.getBooksByGenre(Book.Genre.ROMANCE);
            assertThat(books).hasSize(2).contains(book1, book2);
        }

        @Test
        @DisplayName("Должен возвращать список книг определенного автора")
        void shouldReturnBooksByAuthor() {
            List<Book> books = libraryManager.getBooksByAuthor("Лев Толстой");
            assertThat(books).hasSize(2).contains(book1, book2);
        }

        @Test
        @DisplayName("Должен находить книги по части названия")
        void shouldFindBooksByTitlePart() {
            List<Book> books = libraryManager.searchBooksByTitle("ка");
            assertThat(books).hasSize(2).contains(book2, book3);
        }

        @Test
        @DisplayName("Должен возвращать только доступные книги")
        void shouldReturnOnlyAvailableBooks() {
            book1.setAvailable(false);
            book3.setAvailable(false);

            List<Book> books = libraryManager.getAvailableBooks();
            assertThat(books).hasSize(1).contains(book2);
        }
    }

    @Nested
    @DisplayName("Тесты сортировки книг")
    class BookSortingTests {
        @Test
        @DisplayName("Должен корректно сортировать книги по названию")
        void shouldSortBooksByTitle() {
            List<Book> books = libraryManager.getAllBooks();
            libraryManager.sortBooksByTitle(books);
            assertThat(books).hasSize(3).containsExactlyElementsOf(
                    Arrays.asList(book1, book3, book2)
            );
        }

        @Test
        @DisplayName("Должен корректно сортировать книги по году публикации")
        void shouldSortBooksByPublicationYear() {
            List<Book> books = libraryManager.getAllBooks();
            libraryManager.sortBooksByPublicationYear(books);
            assertThat(books).hasSize(3).containsExactlyElementsOf(
                    Arrays.asList(book3, book1, book2)
            );
        }

        @Test
        @DisplayName("Должен корректно сортировать книги по доступности")
        void shouldSortBooksByAvailability() {
            book1.setAvailable(false);
            book3.setAvailable(false);
            List<Book> books = libraryManager.getAllBooks();
            libraryManager.sortBooksByAvailability(books);
            assertThat(books).hasSize(3).containsExactlyElementsOf(
                    Arrays.asList(book2, book1, book3)
            );
        }
    }

    @Nested
    @DisplayName("Тесты CRUD операций с читателями")
    class ReaderCrudTests {
        @Test
        @DisplayName("Должен корректно добавлять читателя")
        void shouldAddReaderCorrectly() {
            Reader reader = new Reader("R3", "Test", "test@mail.ru", Reader.ReaderCategory.STUDENT);
            boolean added = libraryManager.addReader(reader);
            assertThat(added).isTrue();
            assertThat(libraryManager.getReaderById("R3")).isEqualTo(reader);
        }

        @Test
        @DisplayName("Не должен добавлять дубликат читателя")
        void shouldNotAddDuplicateReader() {
            Reader reader = new Reader("R2", "Test", "test@mail.ru", Reader.ReaderCategory.STUDENT);
            boolean added = libraryManager.addReader(reader);
            assertThat(added).isFalse();
        }

        @Test
        @DisplayName("Должен возвращать читателя по ID")
        void shouldReturnReaderById() {
            Reader reader = libraryManager.getReaderById("R2");
            assertThat(reader).isEqualTo(reader2);
        }

        @Test
        @DisplayName("Должен возвращать null при поиске читателя по несуществующему ID")
        void shouldReturnNullForNonExistingReaderId() {
            assertThat(libraryManager.getReaderById("R3")).isNull();
        }

        @Test
        @DisplayName("Должен корректно удалять читателя")
        void shouldRemoveReaderCorrectly() {
            boolean removed = libraryManager.removeReader("R2");
            assertThat(removed).isTrue();
            assertThat(libraryManager.getReaderById("R2")).isNull();
        }
    }

    @Nested
    @DisplayName("Тесты операций с выдачей книг")
    class BorrowingOperationsTests {
        @Test
        @DisplayName("Должен корректно оформлять выдачу книги")
        void shouldBorrowBookCorrectly() {
            boolean borrowStatus = libraryManager.borrowBook("111", "R1", 2);
            assertThat(borrowStatus).isTrue();
            assertThat(book1.isAvailable()).isFalse();
            assertThat(libraryManager.getAllBorrowings()).hasSize(1);
        }

        @Test
        @DisplayName("Не должен выдавать недоступную книгу")
        void shouldNotBorrowUnavailableBook() {
            boolean borrowStatus = libraryManager.borrowBook("111", "R1", 2);
            assertThat(borrowStatus).isTrue();

            borrowStatus = libraryManager.borrowBook("111", "R2", 2);
            assertThat(borrowStatus).isFalse();
        }

        @Test
        @DisplayName("Должен корректно оформлять возврат книги")
        void shouldReturnBookCorrectly() {

            libraryManager.borrowBook("111", "R1", 2);

            boolean returnStatus = libraryManager.returnBook("111", "R1");
            assertThat(returnStatus).isTrue();
            assertThat(book1.isAvailable()).isTrue();
            Borrowing borrowing = libraryManager.getAllBorrowings().getFirst();
            assertThat(borrowing.getReturnDate()).isNotNull();
        }

        @Test
        @DisplayName("Должен возвращать список просроченных выдач")
        void shouldReturnOverdueBorrowings() {
            libraryManager.borrowBook("111", "R1", -2);
            libraryManager.borrowBook("222", "R2", -3);
            libraryManager.borrowBook("333", "R3", 4);

            assertThat(libraryManager.getOverdueBorrowings()).hasSize(2).
                    allMatch(Borrowing::isOverdue);
        }

        @Test
        @DisplayName("Должен корректно продлевать срок выдачи")
        void shouldExtendBorrowingPeriodCorrectly() {
            libraryManager.borrowBook("111", "R1", 2);
            LocalDate dueDate = LocalDate.now().plusDays(2);
            boolean extendStatus = libraryManager.extendBorrowingPeriod("111", "R1", 2);

            assertThat(extendStatus).isTrue();
            assertThat(dueDate.plusDays(2)).isEqualTo(libraryManager.getAllBorrowings().getFirst().getDueDate());
        }
    }

    @Nested
    @DisplayName("Тесты статистики и отчетов")
    class StatisticsAndReportsTests {
        @Test
        @DisplayName("Должен возвращать корректную статистику по жанрам")
        void shouldReturnCorrectGenreStatistics() {
            libraryManager.addBook(new Book("978-2222222222", "Теория относительности", 1916, Book.Genre.SCIENCE));
            libraryManager.addBook(new Book("978-3333333333", "Хоббит", 1937, Book.Genre.FANTASY));
            libraryManager.addBook(new Book("978-4444444444", "Властелин колец", 1954, Book.Genre.FANTASY));
            Map<Book.Genre, Integer> statistics = libraryManager.getGenreStatistics();

            assertThat(statistics).containsEntry(Book.Genre.SCIENCE, 1);
            assertThat(statistics).containsEntry(Book.Genre.FANTASY, 3);
            assertThat(statistics).containsEntry(Book.Genre.ROMANCE, 2);
            assertThat(statistics).hasSize(3);
        }

        @Test
        @DisplayName("Должен возвращать список самых популярных книг")
        void shouldReturnMostPopularBooks() {
            libraryManager.borrowBook("111", "R1", 2);
            libraryManager.borrowBook("222", "R2", 3);

            libraryManager.returnBook("111", "R1");

            libraryManager.borrowBook("111", "R2", 3);
            libraryManager.returnBook("111", "R2");

            Map<Book, Integer> popularBooks = libraryManager.getMostPopularBooks(2);
            Map<Book, Integer> expected = new LinkedHashMap<>();
            expected.put(book1, 2);
            expected.put(book2, 1);

            assertThat(popularBooks).hasSize(2);
            assertThat(popularBooks).containsExactlyEntriesOf(expected);
        }

        @Test
        @DisplayName("Должен возвращать список самых активных читателей")
        void shouldReturnMostActiveReaders() {
            libraryManager.borrowBook("111", "R1", 2);
            libraryManager.borrowBook("222", "R2", 3);

            libraryManager.returnBook("111", "R1");

            libraryManager.borrowBook("111", "R2", 3);
            libraryManager.returnBook("111", "R2");

            Map<Reader, Integer> popularBooks = libraryManager.getMostActiveReaders(2);
            Map<Reader, Integer> expected = new LinkedHashMap<>();
            expected.put(reader2, 2);
            expected.put(reader1, 1);

            assertThat(popularBooks).hasSize(2);
            assertThat(popularBooks).containsExactlyEntriesOf(expected);
        }

        @Test
        @DisplayName("Должен возвращать список читателей с просроченными книгами")
        void shouldReturnReadersWithOverdueBooks() {
            libraryManager.borrowBook("111", "R1", 2);
            libraryManager.borrowBook("222", "R2", -3);

            List<Reader> readers = libraryManager.getReadersWithOverdueBooks();
            assertThat(readers).hasSize(1);
        }
    }

    @Nested
    @DisplayName("Тесты итераторов")
    class IteratorsTests {
        @Test
        @DisplayName("Должен корректно итерироваться по книгам определенного жанра и года")
        void shouldIterateOverBooksByGenreAndYear() {
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
        @DisplayName("Должен корректно итерироваться по книгам с несколькими авторами")
        void shouldIterateOverBooksWithMultipleAuthors() {
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
        @DisplayName("Должен корректно итерироваться по просроченным выдачам")
        void shouldIterateOverOverdueBorrowings() {
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
        @DisplayName("Должен выбрасывать NoSuchElementException при отсутствии следующего элемента")
        void shouldThrowNoSuchElementExceptionWhenNoMoreElements() {
            Iterator<Book> iterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.FANTASY, 3024);

            assertThat(iterator.hasNext()).isFalse();
            assertThatThrownBy(iterator::next)
                    .isInstanceOf(NoSuchElementException.class);
        }
    }
}