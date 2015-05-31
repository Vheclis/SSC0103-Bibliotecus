package br.usp.icmc.ssc01032015.bibliotecus.model;

import br.usp.icmc.ssc01032015.bibliotecus.serialization.CSVSerializable;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Loan extends CSVSerializable
{
    private SimpleObjectProperty<User> user;
    private SimpleObjectProperty<Book> book;
    private SimpleObjectProperty<LocalDate> checkOut;
    private SimpleObjectProperty<LocalDate> checkIn;
    private SimpleObjectProperty<LocalDate> dueDate;

    public Loan()
    {
        this.user = new SimpleObjectProperty<>();
        this.book = new SimpleObjectProperty<>();
        this.checkOut = new SimpleObjectProperty<>();
        this.checkIn = new SimpleObjectProperty<>();
        this.checkOut = new SimpleObjectProperty<>();
        this.dueDate = new SimpleObjectProperty<>();
    }

    public Loan(User user, Book book, LocalDate checkOut)
    {
        this();

        setUser(user);
        setBook(book);
        setCheckOut(checkOut);
        setCheckIn(null);
        setDueDate(Library.getInstance().calculateDueDateFor(Library.getInstance().getCurrentDate(), user.getType()));
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

    @Override
    protected List<String> customOutputData()
    {
        List<String> data = new ArrayList<>();
        data.add(getUser().getName());
        data.add(getBook().getTitle());

        if (getCheckIn() == null) data.add("");
        else data.add(getCheckIn().toString());

        data.add(getCheckOut().toString());
        data.add(getDueDate().toString());
        return data;
    }

    @Override
    public void customInputData(Iterator<String> itr) throws IllegalArgumentException
    {
        Optional<User> opUser = Library.getInstance().getUsers().stream().filter(user -> user.getName().equals(itr.next())).findFirst();
        if (opUser.isPresent()) setUser(opUser.get());
        else
        {
            throw new IllegalArgumentException("Couldn't find user of id: " + itr);
        }

        Optional<Book> opBook = Library.getInstance().getBooks().stream().filter(book -> book.getTitle().equals(itr.next())).findFirst();
        if (opBook.isPresent()) setBook(opBook.get());
        else
        {
            throw new IllegalArgumentException("Couldn't find book of id: " + itr);
        }

        if (itr.next().isEmpty()) setCheckIn(null);
        else setCheckIn(LocalDate.parse(itr.toString()));

        setCheckOut(LocalDate.parse(itr.next()));
        setDueDate(LocalDate.parse(itr.next()));
    }

}
