package br.usp.icmc.ssc01032015.bibliotecus.model;

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

    private User currentUser;
    private final ObservableList<Book> books;
    private final ObservableList<User> users;
    private final ObservableList<Loan> loans;
    private LocalDate date;

    private Library()
    {
        books = FXCollections.observableArrayList();
        users = FXCollections.observableArrayList();
        loans = FXCollections.observableArrayList();
        currentUser = null;
        date = LocalDate.now();
    }

    public User getCurrentUser()
    {
        return currentUser;
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
        return date;
    }

    public void setCurrentUser(User currentUser)
    {
        this.currentUser = currentUser;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }
}
