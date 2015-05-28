package br.usp.icmc.ssc01032015.bibliotecus.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;


public class Library
{
    public final static Library instance = new Library();
    public static Library getInstance()
    {
        return instance;
    }

    private SimpleObjectProperty<User> currentUser;
    private final ObservableList<Book> books;
    private final ObservableList<User> users;
    private final ObservableList<Loan> loans;
    private SimpleObjectProperty<LocalDate> date;

    private Library()
    {
        books = FXCollections.observableArrayList();
        users = FXCollections.observableArrayList();
        loans = FXCollections.observableArrayList();
        currentUser = new SimpleObjectProperty<User>(null);
        date = new SimpleObjectProperty<LocalDate>(LocalDate.now());
    }

    public User getCurrentUser()
    {
        return currentUser.get();
    }

    public ObservableList<Book> getBooks()
    {
        return books;
    }

    public ObservableList<User> getUsers()
    {
        return users;
    }

    public ObservableList<Loan> getLoans()
    {
        return loans;
    }

    public LocalDate getDate()
    {
        return date.get();
    }

    public void setCurrentUser(User currentUser)
    {
        this.currentUser.set(currentUser);
    }

    public void setDate(LocalDate date)
    {
        this.date.set(date);
    }

    public SimpleObjectProperty<User> currentUserProperty()
    {
        return currentUser;
    }

    public SimpleObjectProperty<LocalDate> dateProperty()
    {
        return date;
    }
}
