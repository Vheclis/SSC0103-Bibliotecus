package br.usp.icmc.ssc01032015.bibliotecus.serialization;

import java.io.*;
import java.util.ArrayList;
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

    public static <T extends CSVSerializable> List<T> importItems(File file, Class<T> c) throws IOException
    {
        List<T> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file))))
        {
            reader.lines().forEach(line ->
            {
                try
                {
                    items.add(CSVSerializer.read(line, c));
                } catch (IllegalAccessException | InstantiationException e)
                {
                    e.printStackTrace();
                }
            });
        }
        return items;
    }

    public static <T extends CSVSerializable> void defaultExport(File file, List<T> list)
            throws FileNotFoundException
    {
        FileOutputStream fileOS = new FileOutputStream(file);
        for (T object : list)
            CSVSerializer.write(object, fileOS);
    }

}
