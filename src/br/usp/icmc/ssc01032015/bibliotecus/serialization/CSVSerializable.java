package br.usp.icmc.ssc01032015.bibliotecus.serialization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class CSVSerializable
{
    private int id;

    public CSVSerializable()
    {
        id = hashCode();
    }

    public List<String> outputData()
    {
        List<String> data = new ArrayList<>();
        data.add(Integer.toString(id));
        data.addAll(customOutputData());

        return data;
    }

    protected abstract List<String> customOutputData();

    public void inputData(List<String> data) throws Exception
    {
        Iterator<String> itr = data.iterator();
        id = Integer.parseInt(itr.next());
        customInputData(itr);
    }

    public abstract void customInputData(Iterator<String> itr) throws Exception;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
