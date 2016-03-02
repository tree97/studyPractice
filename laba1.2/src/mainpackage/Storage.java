package mainpackage;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;

/**
 * all operations with hard disk are here
 */
public class Storage {

     private ChatData data;

     public Storage(ChatData info) {
         data = info;
     }

     void downloadFromFile(String fileName) throws IOException {
        JsonReader my = new JsonReader(new InputStreamReader(new FileInputStream(fileName) ) );
        Gson gson = new Gson();

        data.history = gson.fromJson(my, data.history.getClass() );
        // reader.close();
    }

    void saveHistory(String fileName) throws IOException {
        Writer saving = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
        Gson gson = new Gson();
        ArrayList < Message > tempMessage = new ArrayList < Message > (data.history);
        gson.toJson(tempMessage, saving);
        saving.close();
        App.log.append("Current messages are saved" + '\n');
    }

    void saveLog(String fileName) throws IOException {
        File save = new File(fileName);
        BufferedWriter output = new BufferedWriter(new FileWriter(save) );
        output.write(App.log.toString() );
        output.close();
    }
}
