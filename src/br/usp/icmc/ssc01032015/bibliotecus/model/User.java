package br.usp.icmc.ssc01032015.bibliotecus.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class User
{
    private SimpleStringProperty name;
    private SimpleObjectProperty<Type> type;

    public User(String name, Type type)
    {
        this.name = new SimpleStringProperty();
        setName(name);

        this.type = new SimpleObjectProperty<Type>();
        setType(type);
    }

    public String getName()
    {
        return name.get();
    }

    public void setName(String name)
    {
        this.name.set(name);
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

    public SimpleStringProperty nameProperty()
    {
        return name;
    }

    public SimpleObjectProperty<Type> typeProperty()
    {
        return type;
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
