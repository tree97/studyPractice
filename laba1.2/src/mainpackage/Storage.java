package mainpackage;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;

/**
 * all operations with hard disk are here
 */
public class Storage {

     private ChatData data;

     public Storage(ChatData info) {
         data = info;
     }

     void downloadFromFile(String fileName) throws IOException {
        data.messageCount = 0;
        JsonReader my = new JsonReader(new InputStreamReader(new FileInputStream(fileName) ) );
        Gson gson = new Gson();

        data.history = gson.fromJson(my, Message[].class);
        data.containerSize = data.messageCount = data.history.length;
        //reader.close();
    }

    void saveHistory(String fileName) throws IOException {
        Writer saving = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
        Gson gson = new Gson();
        Message[] tempMessage = new Message[data.messageCount];
        System.arraycopy(data.history, 0, tempMessage, 0, data.messageCount);
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
