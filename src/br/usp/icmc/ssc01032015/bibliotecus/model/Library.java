package br.usp.icmc.ssc01032015.bibliotecus.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

    private SimpleObjectProperty<LocalDate> currentDate;

    private Library()
    {
        books = FXCollections.observableArrayList();

        users = FXCollections.observableArrayList();

        loans = FXCollections.observableArrayList();
        loans.addListener(this::onLoansChanged);

        currentUser = new SimpleObjectProperty<>(null);
        currentDate = new SimpleObjectProperty<>(LocalDate.now());
    }

    private void onLoansChanged(ListChangeListener.Change<? extends Loan> change)
    {
        while (change.next())
        {
            for (Loan loan : change.getAddedSubList())
            {
                loan.getBook().setCurrentQuantity(loan.getBook().getCurrentQuantity()-1);
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
                        checkIn = Library.getInstance().getCurrentDate();
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

    public LocalDate getCurrentDate()
    {
        return currentDate.get();
    }

    public void setCurrentUser(User currentUser)
    {
        this.currentUser.set(currentUser);
    }

    public void setCurrentDate(LocalDate currentDate)
    {
        this.currentDate.set(currentDate);
    }

    public SimpleObjectProperty<User> currentUserProperty()
    {
        return currentUser;
    }

    public SimpleObjectProperty<LocalDate> currentDateProperty()
    {
        return currentDate;
    }
}
