package br.usp.icmc.ssc01032015.bibliotecus.model;

import br.usp.icmc.ssc01032015.bibliotecus.serialization.CSVSerializable;

import java.util.Scanner;

public class User implements CSVSerializable
{
    private String name;
    private Type type;

    public User(String csv)
    {
        Scanner scanner = new Scanner(csv);
        scanner.useDelimiter(", *");

        name = scanner.next();
        type = Type.valueOf(scanner.next());
    }

    public User(String name, Type type)
    {
        this.name = name;
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
        return name + ", " + type.name;
    }

    public enum Type
    {
        Student("Student", 4, 15),
        Professor("Professor", 6, 60),
        Community("Community", 2, 15);

        final String name;
        final int maxLoans;
        final int maxDays;

        Type(String name, int maxLoans, int maxDays)
        {
            this.name = name;
            this.maxLoans = maxLoans;
            this.maxDays = maxDays;
        }
    }
}
