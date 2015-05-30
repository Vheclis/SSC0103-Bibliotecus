package br.usp.icmc.ssc01032015.bibliotecus.model;

import br.usp.icmc.ssc01032015.bibliotecus.serialization.CSVSerializable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Book extends CSVSerializable
{
    /**
     * Title of the book
     */
    private SimpleStringProperty title;

    /**
     * Author of the book
     */
    private SimpleStringProperty author;

    /**
     * Type of the book (textbook or general)
     */
    private SimpleObjectProperty<Type> type;

    /**
     * Numbers of copies
     */
    private SimpleIntegerProperty totalQuantity;

    /**
     * Numbers of copies
     */
    private SimpleIntegerProperty currentQuantity;

    private SimpleObjectProperty<LocalDate> registration;

    public Book()
    {
        this.title = new SimpleStringProperty();
        this.author = new SimpleStringProperty();
        this.type = new SimpleObjectProperty<>();
        this.totalQuantity = new SimpleIntegerProperty();
        this.currentQuantity = new SimpleIntegerProperty();
        this.registration = new SimpleObjectProperty<>();
    }

    public Book(String title, String author, Type type, int totalQuantity)
    {
        this();

        setTitle(title);
        setAuthor(author);
        setType(type);
        setTotalQuantity(totalQuantity);
        setCurrentQuantity(totalQuantity);
        setRegistration(Library.getInstance().getCurrentDate());
    }

    public void addCopies(int quantity)
    {
        totalQuantity.set(totalQuantity.get() + quantity);
        currentQuantity.set(currentQuantity.get() + quantity);
    }

    public String getTitle()
    {
        return title.get();
    }

    public void setTitle(String title)
    {
        this.title.set(title);
    }

    public String getAuthor()
    {
        return author.get();
    }

    public void setAuthor(String author)
    {
        this.author.set(author);
    }

    public Type getType()
    {
        return type.get();
    }

    public void setType(Type type)
    {
        this.type.set(type);
    }

    public int getTotalQuantity()
    {
        return totalQuantity.get();
    }

    public void setTotalQuantity(int totalQuantity)
    {
        this.totalQuantity.set(totalQuantity);
    }

    public SimpleStringProperty titleProperty()
    {
        return title;
    }

    public SimpleStringProperty authorProperty()
    {
        return author;
    }

    public SimpleObjectProperty<Type> typeProperty()
    {
        return type;
    }

    public SimpleIntegerProperty totalQuantityProperty()
    {
        return totalQuantity;
    }

    public int getCurrentQuantity()
    {
        return currentQuantity.get();
    }

    public SimpleIntegerProperty currentQuantityProperty()
    {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity)
    {
        this.currentQuantity.set(currentQuantity);
    }

    public LocalDate getRegistration()
    {
        return registration.get();
    }

    public SimpleObjectProperty<LocalDate> registrationProperty()
    {
        return registration;
    }

    public void setRegistration(LocalDate registration)
    {
        this.registration.set(registration);
    }

    @Override
    protected List<String> customOutputData()
    {
        List<String> data = new ArrayList<>();
        data.add(getTitle());
        data.add(getAuthor());
        data.add(getType().name);
        data.add(Integer.toString(getCurrentQuantity()));
        data.add(Integer.toString(getTotalQuantity()));
        data.add(getRegistration().toString());

        return data;
    }

    @Override
    public void customInputData(Iterator<String> itr)
    {
        setTitle(itr.next());
        setAuthor(itr.next());
        setType(Type.valueOf(itr.next()));
        setCurrentQuantity(Integer.parseInt(itr.next()));
        setTotalQuantity(Integer.parseInt(itr.next()));
        setRegistration(LocalDate.parse(itr.next()));
    }

    public enum Type
    {
        General("General"),
        Textbook("Textbook");

        public final String name;
        Type(String text)
        {
            name = text;
        }
    }
}
