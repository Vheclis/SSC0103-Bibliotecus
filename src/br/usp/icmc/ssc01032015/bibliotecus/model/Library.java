package br.usp.icmc.ssc01032015.bibliotecus.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Library
{
    public final static Library instance = new Library();

    public static Library getInstance()
    {
        return instance;
    }

    private SimpleObjectProperty<User> currentUser;

    private final List<Book> allBooks;
    private final ObservableList<Book> books;

    private final List<User> allUsers;
    private final ObservableList<User> users;

    private final List<Loan> allLoans;
    private final ObservableList<Loan> loans;

    private SimpleObjectProperty<LocalDate> date;

    private Library()
    {
        allBooks = new ArrayList<>();
        books = FXCollections.observableArrayList();

        allUsers = new ArrayList<>();
        users = FXCollections.observableArrayList();

        allLoans = new ArrayList<>();
        loans = FXCollections.observableArrayList();
        loans.addListener(this::onLoansChanged);

        currentUser = new SimpleObjectProperty<User>(null);
        date = new SimpleObjectProperty<LocalDate>(LocalDate.now());
    }

    private void onLoansChanged(ListChangeListener.Change<? extends Loan> change)
    {
        while (change.next())
        {
            for (Loan loan : change.getAddedSubList())
            {
                loan.getBook().decreaseCurrentQuantity();
            }
        }
    }

    public int calculateUserSuspension(User user)
    {
        if (user == null) return 0;
        return getLoans()
                .stream()
                .filter(loan -> loan.getUser().getName().equals(user.getName()))
                .mapToInt(loan ->
                {
                    LocalDate dueDate = loan.getDueDate();
                    LocalDate checkIn = loan.getCheckIn();

                    //if loan has not been checked back in,
                    if (checkIn == null)
                    {
                        checkIn = Library.getInstance().getDate();
                    }

                    int daysAfter = (int) (checkIn.toEpochDay() - dueDate.toEpochDay());
                    return Math.max(0, daysAfter);
                }).sum();
    }

    public int calculateCopiesInStock(Book book)
    {
        if (book == null) return 0;
        long copiesLent = getLoans()
                .stream()
                .filter(loan -> loan.getBook().getTitle().equals(book.getTitle()))
                .filter(loan -> loan.getCheckIn() == null)
                .count();

        return (int) (book.getTotalQuantity() - copiesLent);
    }

    public LocalDate calculateDueDateFor(LocalDate from, User.Type type)
    {
        //sorry John Carmack for the hardcoding
        switch (type)
        {
            case Student:
                return from.plusDays(15);
            case Professor:
                return from.plusDays(60);
            case Community:
                return from.plusDays(15);
        }

        return null;
    }

    public int getMaxLoanFor(User.Type type)
    {
        //sorry John Carmack for the hardcoding
        switch (type)
        {
            case Student:
                return 4;
            case Professor:
                return 6;
            case Community:
                return 2;
        }

        return 0;
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
