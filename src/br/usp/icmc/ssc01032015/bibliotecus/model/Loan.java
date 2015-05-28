package br.usp.icmc.ssc01032015.bibliotecus.model;

import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;

public class Loan
{
    private SimpleObjectProperty<User> user;
    private SimpleObjectProperty<Book> book;
    private SimpleObjectProperty<LocalDate> checkOut;
    private SimpleObjectProperty<LocalDate> checkIn;
    private SimpleObjectProperty<LocalDate> dueDate;

    public Loan(User user, Book book, LocalDate checkOut, LocalDate dueDate)
    {
        this.user = new SimpleObjectProperty<User>();
        setUser(user);

        this.book = new SimpleObjectProperty<Book>();
        setBook(book);

        this.checkOut = new SimpleObjectProperty<LocalDate>();
        setCheckOut(checkOut);

        this.checkIn = new SimpleObjectProperty<LocalDate>();
        setCheckIn(null);

        this.dueDate = new SimpleObjectProperty<LocalDate>();
        setDueDate(dueDate);
    }

    public void setUser(User user)
    {
        this.user.set(user);
    }

    public void setBook(Book book)
    {
        this.book.set(book);
    }

    public void setCheckOut(LocalDate checkOut)
    {
        this.checkOut.set(checkOut);
    }

    public void setCheckIn(LocalDate checkIn)
    {
        this.checkIn.set(checkIn);
    }

    public User getUser()
    {
        return user.get();
    }

    public Book getBook()
    {
        return book.get();
    }

    public LocalDate getCheckOut()
    {
        return checkOut.get();
    }

    public LocalDate getCheckIn()
    {
        return checkIn.get();
    }

    public LocalDate getDueDate()
    {
        return dueDate.get();
    }

    public void setDueDate(LocalDate dueDate)
    {
        this.dueDate.set(dueDate);
    }

    public SimpleObjectProperty<User> userProperty()
    {
        return user;
    }

    public SimpleObjectProperty<Book> bookProperty()
    {
        return book;
    }

    public SimpleObjectProperty<LocalDate> checkOutProperty()
    {
        return checkOut;
    }

    public SimpleObjectProperty<LocalDate> checkInProperty()
    {
        return checkIn;
    }

    public SimpleObjectProperty<LocalDate> dueDateProperty()
    {
        return dueDate;
    }

}
