package br.usp.icmc.ssc01032015.bibliotecus.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book
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

    public Book(String title, String author, Type type, int totalQuantity)
    {
        this.title = new SimpleStringProperty();
        setTitle(title);

        this.author = new SimpleStringProperty();
        setAuthor(author);

        this.type = new SimpleObjectProperty<Type>();
        setType(type);

        this.totalQuantity = new SimpleIntegerProperty();
        setTotalQuantity(totalQuantity);
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

    public String getTypeName()
    {
        return type.get().name;
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

    public void addTotalQuantity(int quantity)
    {
        totalQuantity.set(totalQuantity.get()+ quantity);
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
