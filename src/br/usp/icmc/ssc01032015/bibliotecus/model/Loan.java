package br.usp.icmc.ssc01032015.bibliotecus.model;


import java.time.LocalDate;

public class Loan
{
    private User user;
    private Book book;

    private LocalDate checkOut;
    private LocalDate checkIn;

    public User getUser()
    {
        return user;
    }

    public Book getBook()
    {
        return book;
    }

    public LocalDate getCheckOut()
    {
        return checkOut;
    }

    public LocalDate getCheckIn()
    {
        return checkIn;
    }

    public Loan(User user, Book book)
    {
        this.user = user;
        this.book = book;
    }

}
