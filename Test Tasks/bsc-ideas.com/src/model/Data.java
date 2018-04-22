package model;

/**
 * Data - data of currency accounts
 *
 * @author Sergey Iryupin
 * @version 0.2 dated Apr 22, 2018
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.HashMap;

public class Data {
    private Map<String, Integer> data;

    public Data() {
        data = new HashMap<>();
    }

    public void processLine(String line) {
        String[] str = line.split(" ");
        if (str.length > 1)
            try {
                add(str[0], Integer.parseInt(str[1]));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }

    public void add(String key, int value) {
        data.put(key, data.getOrDefault(key, 0) + value);
    }

    public void addFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null)
                processLine(line);
       } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (Map.Entry<String, Integer> item : data.entrySet())
            if (item.getValue() != 0)
                result += item.getKey() + " " +  item.getValue() + "\n";
        return result;
    }
}