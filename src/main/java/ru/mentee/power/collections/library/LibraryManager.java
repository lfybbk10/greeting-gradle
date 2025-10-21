package ru.mentee.power.collections.library;

import ru.mentee.power.collections.library.comparator.AvailabilityComparator;
import ru.mentee.power.collections.library.comparator.PublicationYearComparator;
import ru.mentee.power.collections.library.comparator.TitleComparator;

import java.time.LocalDate;
import java.util.*;

public class LibraryManager {
    // TODO: Объявить коллекцию для хранения книг (Map<String, Book> с ключом ISBN)
    private Map<String, Book> books;

    // TODO: Объявить коллекцию для хранения читателей (Map<String, Reader> с ключом ID)
    private Map<String, Reader> readers;

    // TODO: Объявить коллекцию для хранения истории выдач (List<Borrowing>)
    private List<Borrowing> borrowingsHistory;

    // TODO: Объявить коллекцию для группировки книг по жанрам (Map<Book.Genre, Set<Book>>)
    private Map<Book.Genre, Set<Book>> booksByGenres;

    // TODO: Объявить коллекцию для хранения авторов и их книг (Map<String, List<Book>>)
    private Map<String, List<Book>> booksByAuthors;

    // TODO: Объявить дополнительные коллекции для эффективной работы с данными (по необходимости)

    // TODO: Реализовать конструктор, который инициализирует все коллекции

    public LibraryManager() {
        books = new HashMap<>();
        readers = new HashMap<>();
        borrowingsHistory = new ArrayList<>();
        booksByGenres = new EnumMap<>(Book.Genre.class);
        booksByAuthors = new HashMap<>();
    }

    // ============ Методы для работы с книгами ============

    /**
     * Добавляет книгу в библиотеку
     * @param book книга для добавления
     * @return true если книга добавлена, false если книга с таким ISBN уже существует
     */
    public boolean addBook(Book book) {
        if(books.containsKey(book.getIsbn())) {
            return false;
        }

        books.put(book.getIsbn(), book);

        if(booksByGenres.containsKey(book.getGenre())) {
            booksByGenres.get(book.getGenre()).add(book);
        }
        else{
            booksByGenres.put(book.getGenre(), new HashSet<>(List.of(book)));
        }

        for (String author : book.getAuthors()) {
            if(!booksByAuthors.containsKey(author)) {
                booksByAuthors.put(author, new ArrayList<>(List.of(book)));
            }
            else{
                booksByAuthors.get(author).add(book);
            }
        }
        return true;
    }

    /**
     * Получает книгу по ISBN
     * @param isbn ISBN книги
     * @return книга или null, если книга не найдена
     */
    public Book getBookByIsbn(String isbn) {
        return books.get(isbn);
    }

    /**
     * Удаляет книгу из библиотеки
     * @param isbn ISBN книги для удаления
     * @return true если книга удалена, false если книга не найдена
     */
    public boolean removeBook(String isbn) {
        Book removed = books.remove(isbn);
        if (removed == null) return false;

        booksByGenres.get(removed.getGenre()).remove(removed);
        removed.getAuthors().forEach(author -> booksByAuthors.get(author).remove(removed));
        return true;
    }

    /**
     * Возвращает список всех книг
     * @return список книг
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    /**
     * Возвращает список книг определенного жанра
     * @param genre жанр
     * @return список книг
     */
    public List<Book> getBooksByGenre(Book.Genre genre) {
        return new ArrayList<>(booksByGenres.get(genre));
    }

    /**
     * Возвращает список книг определенного автора
     * @param author автор
     * @return список книг
     */
    public List<Book> getBooksByAuthor(String author) {
        return booksByAuthors.get(author);
    }

    /**
     * Поиск книг по названию (частичное совпадение)
     * @param titlePart часть названия
     * @return список книг
     */
    public List<Book> searchBooksByTitle(String titlePart) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if(book.getTitle().toLowerCase().contains(titlePart.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Возвращает список доступных книг
     * @return список доступных книг
     */
    public List<Book> getAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if(book.isAvailable()) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Сортирует список книг по названию
     * @param books список книг для сортировки
     * @return отсортированный список
     */
    public List<Book> sortBooksByTitle(List<Book> books) {
        books.sort(new TitleComparator());
        return books;
    }

    /**
     * Сортирует список книг по году публикации (от новых к старым)
     * @param books список книг для сортировки
     * @return отсортированный список
     */
    public List<Book> sortBooksByPublicationYear(List<Book> books) {
        books.sort(new PublicationYearComparator());
        return books;
    }

    /**
     * Сортирует список книг по доступности (сначала доступные)
     * @param books список книг для сортировки
     * @return отсортированный список
     */
    public List<Book> sortBooksByAvailability(List<Book> books) {
        books.sort(new AvailabilityComparator());
        return books;
    }

    // ============ Методы для работы с читателями ============

    /**
     * Добавляет читателя
     * @param reader читатель
     * @return true если читатель добавлен, false если читатель с таким ID уже существует
     */
    public boolean addReader(Reader reader) {
        if(readers.containsKey(reader.getId())) {
            return false;
        }
        readers.put(reader.getId(), reader);
        return true;
    }

    /**
     * Получает читателя по ID
     * @param readerId ID читателя
     * @return читатель или null, если читатель не найден
     */
    public Reader getReaderById(String readerId) {
        if(!readers.containsKey(readerId)) {
            return null;
        }
        return readers.get(readerId);
    }

    /**
     * Удаляет читателя
     * @param readerId ID читателя
     * @return true если читатель удален, false если читатель не найден
     */
    public boolean removeReader(String readerId) {
        if(readers.containsKey(readerId)) {
            readers.remove(readerId);
            return true;
        }
        return false;
    }

    /**
     * Возвращает список всех читателей
     * @return список читателей
     */
    public List<Reader> getAllReaders() {
        return new ArrayList<>(readers.values());
    }

    // ============ Методы для выдачи и возврата книг ============

    /**
     * Выдает книгу читателю
     * @param isbn ISBN книги
     * @param readerId ID читателя
     * @param borrowDays количество дней, на которое выдается книга
     * @return true если книга выдана, false если книга недоступна или не найдена
     */
    public boolean borrowBook(String isbn, String readerId, int borrowDays) {
        if(books.containsKey(isbn) && readers.containsKey(readerId) && books.get(isbn).isAvailable()) {
            LocalDate dueDate = LocalDate.now().plusDays(borrowDays);
            borrowingsHistory.add(new Borrowing(isbn, readerId, LocalDate.now(), dueDate));
            books.get(isbn).setAvailable(false);
            return true;
        }
        return false;
    }

    /**
     * Возвращает книгу в библиотеку
     * @param isbn ISBN книги
     * @param readerId ID читателя
     * @return true если книга возвращена, false если запись о выдаче не найдена
     */
    public boolean returnBook(String isbn, String readerId) {
        for (Borrowing borrowing : borrowingsHistory) {
            if(borrowing.getIsbn().equals(isbn) && borrowing.getReaderId().equals(readerId)) {
                borrowing.returnBook(LocalDate.now());
                books.get(isbn).setAvailable(true);
                return true;
            }
        }
        return false;
    }

    /**
     * Получает список всех выданных книг
     * @return список выдач
     */
    public List<Borrowing> getAllBorrowings() {
        return new ArrayList<>(borrowingsHistory);
    }

    /**
     * Получает список просроченных выдач
     * @return список просроченных выдач
     */
    public List<Borrowing> getOverdueBorrowings() {
        List<Borrowing> result = new ArrayList<>();
        for (Borrowing borrowing : borrowingsHistory) {
            if(borrowing.isOverdue()) {
                result.add(borrowing);
            }
        }
        return result;
    }

    /**
     * Получает историю выдач для конкретного читателя
     * @param readerId ID читателя
     * @return список выдач
     */
    public List<Borrowing> getBorrowingsByReader(String readerId) {
        List<Borrowing> result = new ArrayList<>();
        for (Borrowing borrowing : borrowingsHistory) {
            if(borrowing.getReaderId().equals(readerId)) {
                result.add(borrowing);
            }
        }
        return result;
    }

    /**
     * Получает историю выдач для конкретной книги
     * @param isbn ISBN книги
     * @return список выдач
     */
    public List<Borrowing> getBorrowingsByBook(String isbn) {
        List<Borrowing> result = new ArrayList<>();
        for (Borrowing borrowing : borrowingsHistory) {
            if(borrowing.getIsbn().equals(isbn)) {
                result.add(borrowing);
            }
        }
        return result;
    }

    /**
     * Продлевает срок выдачи книги
     * @param isbn ISBN книги
     * @param readerId ID читателя
     * @param additionalDays дополнительные дни
     * @return true если срок продлен, false если запись о выдаче не найдена
     */
    public boolean extendBorrowingPeriod(String isbn, String readerId, int additionalDays) {
        for (Borrowing borrowing : borrowingsHistory) {
            if(borrowing.getIsbn().equals(isbn) && borrowing.getReaderId().equals(readerId)) {
                borrowing.setDueDate(borrowing.getDueDate().plusDays(additionalDays));
                return true;
            }
        }
        return false;
    }

    // ============ Методы для статистики и отчетов ============

    /**
     * Возвращает статистику по жанрам: количество книг в каждом жанре
     * @return карта "жанр -> количество книг"
     */
    public Map<Book.Genre, Integer> getGenreStatistics() {
        HashMap<Book.Genre, Integer> result = new HashMap<>();
        booksByGenres.forEach((genre, books) -> result.put(genre, books.size()));
        return result;
    }

    /**
     * Возвращает наиболее популярные книги (по количеству выдач)
     * @param limit максимальное количество книг в результате
     * @return список пар "книга -> количество выдач"
     */
    public Map<Book, Integer> getMostPopularBooks(int limit) {
        HashMap<Book, Integer> result = new HashMap<>();
        for (Borrowing borrowing : borrowingsHistory) {
            Book book = books.get(borrowing.getIsbn());
            if(result.containsKey(book)) {
                result.put(book, result.get(book) + 1);
            }
            else{
                result.put(book, 1);
            }
        }

        List<Map.Entry<Book, Integer>> entryList = new ArrayList<>(result.entrySet());
        entryList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        LinkedHashMap<Book, Integer> sortedMap = new LinkedHashMap<>();
        for (int i = 0; i < Math.min(entryList.size(), limit); i++) {
            sortedMap.put(entryList.get(i).getKey(), entryList.get(i).getValue());
        }
        return sortedMap;
    }

    /**
     * Возвращает наиболее активных читателей (по количеству выдач)
     * @param limit максимальное количество читателей в результате
     * @return список пар "читатель -> количество выдач"
     */
    public Map<Reader, Integer> getMostActiveReaders(int limit) {
        HashMap<Reader, Integer> result = new HashMap<>();
        for (Borrowing borrowing : borrowingsHistory) {
            Reader reader = readers.get(borrowing.getReaderId());
            if(result.containsKey(reader)) {
                result.put(reader, result.get(reader) + 1);
            }
            else{
                result.put(reader, 1);
            }
        }

        List<Map.Entry<Reader, Integer>> entryList = new ArrayList<>(result.entrySet());
        entryList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        LinkedHashMap<Reader, Integer> sortedMap = new LinkedHashMap<>();
        for (int i = 0; i < Math.min(entryList.size(), limit); i++) {
            sortedMap.put(entryList.get(i).getKey(), entryList.get(i).getValue());
        }
        return sortedMap;
    }

    /**
     * Находит читателей, которые не возвращают книги вовремя
     * @return список читателей с просроченными книгами
     */
    public List<Reader> getReadersWithOverdueBooks() {
        Set<Reader> result = new HashSet<>();
        for (Borrowing borrowing : borrowingsHistory) {
            if(borrowing.isOverdue()) {
                result.add(readers.get(borrowing.getReaderId()));
            }
        }
        return new ArrayList<>(result);
    }

    // ============ Методы для работы с итераторами ============

    /**
     * Создает итератор для просмотра книг определенного жанра и года издания
     * @param genre жанр книг
     * @param year год издания
     * @return итератор
     */
    public Iterator<Book> getBooksByGenreAndYearIterator(Book.Genre genre, int year) {
        List<Book> filtered = new ArrayList<>();

        for (Book book : books.values()) {
            if (book.getGenre() == genre && book.getPublicationYear() == year) {
                filtered.add(book);
            }
        }

        return new Iterator<>() {
            private final Iterator<Book> internalIterator = filtered.iterator();

            @Override
            public boolean hasNext() {
                return internalIterator.hasNext();
            }

            @Override
            public Book next() {
                return internalIterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Создает итератор для просмотра книг с несколькими авторами
     * @param minAuthorsCount минимальное количество авторов
     * @return итератор
     */
    public Iterator<Book> getBooksWithMultipleAuthorsIterator(int minAuthorsCount) {
        List<Book> filtered = new ArrayList<>();

        for (Book book : books.values()) {
            if (book.getAuthors().size() >= minAuthorsCount) {
                filtered.add(book);
            }
        }

        return new Iterator<>() {
            private final Iterator<Book> internalIterator = filtered.iterator();

            @Override
            public boolean hasNext() {
                return internalIterator.hasNext();
            }

            @Override
            public Book next() {
                return internalIterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Создает итератор для просмотра просроченных выдач
     * @return итератор
     */
    public Iterator<Borrowing> getOverdueBorrowingsIterator() {
        List<Borrowing> filtered = new ArrayList<>();

        for (Borrowing borrowing : borrowingsHistory) {
            if (borrowing.isOverdue()) {
                filtered.add(borrowing);
            }
        }

        return new Iterator<>() {
            private final Iterator<Borrowing> internalIterator = filtered.iterator();

            @Override
            public boolean hasNext() {
                return internalIterator.hasNext();
            }

            @Override
            public Borrowing next() {
                return internalIterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}