package br.usp.icmc.ssc01032015.bibliotecus.model;


public class Loan
{
    private User user;
    private Book book;

    public Loan(User user, Book book)
    {
        this.user = user;
        this.book = book;
    }

}
