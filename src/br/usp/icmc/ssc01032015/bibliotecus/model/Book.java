package br.usp.icmc.ssc01032015.bibliotecus.model;

import br.usp.icmc.ssc01032015.bibliotecus.serialization.CSVSerializable;

import java.util.Scanner;

public class Book implements CSVSerializable
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

    public Book(String title, String author, Type type)
    {
        this.title = title;
        this.author = author;
        this.type = type;
    }

    public Book(String csv)
    {
        Scanner scanner = new Scanner(csv);
        scanner.useDelimiter(", *");

        title = scanner.next();
        author = scanner.next();
        type = Type.valueOf(scanner.next());
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

    public void setType(Type type)
    {
        this.type = type;
    }

    @Override
    public String asCSV()
    {
        return title + ", " + author + ", " + type.name;
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
