package br.usp.icmc.ssc01032015.bibliotecus.serialization;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CSVSerializer
{
    public static <T extends CSVSerializable> void write(T object, OutputStream stream)
    {
        //get object outputdata
        List<String> dataList = object.outputData();

        //write csv-formatted data
        Iterator<String> itr = dataList.iterator();
        PrintWriter writer = new PrintWriter(stream);
        while (itr.hasNext())
        {
            String data = itr.next();
            if (itr.hasNext())
            {
                writer.print(data + ", ");
            } else
            {
                writer.println(data);
            }
        }

        writer.flush();
    }

    public static <T extends CSVSerializable> T read(String line, Class<T> c) throws IllegalAccessException, InstantiationException
    {
        //read line and split by commas
        String[] data = line.split(",\\s");

        //return instance of object
        T object = c.newInstance();
        object.inputData(Arrays.asList(data));
        return object;
    }

}
