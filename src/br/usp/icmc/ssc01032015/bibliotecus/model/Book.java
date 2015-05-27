package br.usp.icmc.ssc01032015.bibliotecus.model;

public class Book
{
    /**
     * Title of the book
     */
    private String title;

    /**
     * Author of the book
     */
    private String author;

    /**
     * Type of the book (textbook or general)
     */
    private Type type;

    /**
     * Numbers of copies
     */
    private int totalQuantity;

    public Book(String title, String author, Type type, int totalQuantity)
    {
        this.title = title;
        this.author = author;
        this.type = type;
        this.totalQuantity = totalQuantity;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public Type getType()
    {
        return type;
    }

    public String getTypeName()
    {
        return type.name;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public int getTotalQuantity()
    {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity)
    {
        this.totalQuantity = totalQuantity;
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
