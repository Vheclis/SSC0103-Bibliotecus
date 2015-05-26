package br.usp.icmc.ssc01032015.bibliotecus.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 5/26/2015.
 */
public class Library
{
    final public static Library instance = new Library();
    public static Library getInstance()
    {
        return instance;
    }

    private User currentUser;
    private List<Book> books;
    private List<User> users;
    private List<Loan> loans;
    private LocalDate date;

    private Library()
    {
        books = new ArrayList<Book>();
        users = new ArrayList<User>();
        currentUser = null;
        date = LocalDate.now();
    }

}
