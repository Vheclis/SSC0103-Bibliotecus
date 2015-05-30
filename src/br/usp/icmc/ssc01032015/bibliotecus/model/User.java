package br.usp.icmc.ssc01032015.bibliotecus.model;

import br.usp.icmc.ssc01032015.bibliotecus.serialization.CSVSerializable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User extends CSVSerializable
{
    private SimpleStringProperty name;
    private SimpleObjectProperty<Type> type;
    private SimpleObjectProperty<LocalDate> registration;

    public User()
    {
        this.name = new SimpleStringProperty();
        this.type = new SimpleObjectProperty<>();
        this.registration = new SimpleObjectProperty<>();
    }

    public User(String name, Type type)
    {
        this.name = new SimpleStringProperty();
        setName(name);

        this.type = new SimpleObjectProperty<>();
        setType(type);

        this.registration = new SimpleObjectProperty<>();
        setRegistration(Library.getInstance().getCurrentDate());
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
    protected List<String> customOutputData() {
        List<String> data = new ArrayList<>();
        data.add(getName());
        data.add(getTypeName());
        data.add(getRegistration().toString());
        return data;
    }

    @Override
    public void customInputData(Iterator<String> itr) {
        setName(itr.next());
        setType(User.Type.valueOf(itr.next()));
        setRegistration(LocalDate.parse(itr.next()));
    }

    public enum Type
    {
        Student("Student"),
        Professor("Professor"),
        Community("Community");

        final String name;

        Type(String name)
        {
            this.name = name;
        }
    }
}
