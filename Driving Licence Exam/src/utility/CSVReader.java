package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {

    public static List<List<String>> readFile(String file){
        List<List<String>> toReturn = new ArrayList<>();

        String line;
        BufferedReader buffer = null;
        try{
            buffer = new BufferedReader(new FileReader(file));
            while( (line = buffer.readLine()) != null){
                String[] data = line.split(",");
                toReturn.add(Arrays.asList(data));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            if(buffer != null){
                try{
                    buffer.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return toReturn;
    }

}
