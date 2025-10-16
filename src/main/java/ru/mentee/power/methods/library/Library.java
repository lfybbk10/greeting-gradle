package ru.mentee.power.methods.library;

public class Library {
    // обратите внимание: books — это массив, а не список
    private Book[] books;
    private int bookCount;

    /**
     * Создает новую библиотеку с заданной вместимостью
     * @param capacity максимальное количество книг
     */
    public Library(int capacity) {
        this.books = new Book[capacity];
        this.bookCount = 0;
    }

    /**
     * Добавляет книгу в библиотеку
     * @param book книга для добавления
     * @return true, если книга добавлена успешно, false, если библиотека переполнена
     */
    public boolean addBook(Book book) {
        if (bookCount >= books.length) {
            return false; // библиотека заполнена
        }
        books[bookCount++] = book;
        return true;
    }

    /**
     * Ищет книгу по названию
     * @param title название книги
     * @return найденная книга или null, если книга не найдена
     */
    public Book findBookByTitle(String title) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getTitle().equalsIgnoreCase(title)) {
                return books[i];
            }
        }
        return null;
    }

    /**
     * Выдает книгу читателю
     * @param title название книги
     * @return true, если книга успешно выдана, false, если книга не найдена или уже выдана
     */
    public boolean checkoutBook(String title) {
        Book book = findBookByTitle(title);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            return true;
        }
        return false;
    }

    /**
     * Возвращает книгу в библиотеку
     * @param title название книги
     * @return true, если книга успешно возвращена, false, если книга не найдена или уже доступна
     */
    public boolean returnBook(String title) {
        Book book = findBookByTitle(title);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            return true;
        }
        return false;
    }

    /**
     * Возвращает массив доступных книг
     * @return массив доступных книг
     */
    public Book[] listAvailableBooks() {
        int count = 0;
        for (int i = 0; i < bookCount; i++) {
            if (books[i].isAvailable()) count++;
        }

        Book[] result = new Book[count];
        int index = 0;
        for (int i = 0; i < bookCount; i++) {
            if (books[i].isAvailable()) {
                result[index++] = books[i];
            }
        }
        return result;
    }

    /**
     * Возвращает массив выданных книг
     * @return массив выданных книг
     */
    public Book[] listCheckedOutBooks() {
        int count = 0;
        for (int i = 0; i < bookCount; i++) {
            if (!books[i].isAvailable()) count++;
        }

        Book[] result = new Book[count];
        int index = 0;
        for (int i = 0; i < bookCount; i++) {
            if (!books[i].isAvailable()) {
                result[index++] = books[i];
            }
        }
        return result;
    }
}
