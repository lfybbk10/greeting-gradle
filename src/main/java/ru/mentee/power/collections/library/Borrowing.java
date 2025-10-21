package ru.mentee.power.collections.library;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Borrowing {
    private String isbn;
    private String readerId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Borrowing(String isbn, String readerId, LocalDate borrowDate, LocalDate dueDate){
        this.isbn = isbn;
        this.readerId = readerId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    // TODO: Реализовать метод isOverdue(), который возвращает true, если срок возврата истек (текущая дата > dueDate),
    // а книга еще не возвращена (returnDate == null)
    public boolean isOverdue() {
        return LocalDate.now().isAfter(dueDate);
    }

    // TODO: Реализовать метод isReturned(), который возвращает true, если книга была возвращена (returnDate != null)
    public boolean isReturned() {
        return returnDate != null;
    }

    // TODO: Реализовать метод returnBook(LocalDate returnDate), который устанавливает дату возврата книги
    public void returnBook(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    // TODO: Переопределить equals и hashCode, чтобы записи считались равными, если у них одинаковые isbn, readerId и borrowDate
    @Override
    public boolean equals(Object o) {
        if(o instanceof Borrowing other) {
            return other.getIsbn().equals(isbn) && other.getReaderId().equals(readerId) && other.getBorrowDate().equals(borrowDate);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, readerId, borrowDate);
    }

    @Override
    public String toString() {
        return "Borrowing [isbn=" + isbn + ", readerId=" + readerId + "]";
    }
}