package mainpackage;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;

/**
 * all operations with hard disk are here
 */
public class Storage {

    static void downloadFromFile(String fileName) throws IOException {
        ChatData.messageCount = 0;
        JsonReader my = new JsonReader(new InputStreamReader(new FileInputStream(fileName) ) );
        Gson gson = new Gson();

        ChatData.history = gson.fromJson(my, Message[].class);

        ChatData.containerSize = ChatData.messageCount = ChatData.history.length;
        //reader.close();
    }

    static void saveHistory() throws IOException {
        Writer saving = new OutputStreamWriter(new FileOutputStream("output.json"), "UTF-8");
        Gson gson = new Gson();
        Message[] tempMessage = new Message[ChatData.messageCount];
        System.arraycopy(ChatData.history, 0, tempMessage, 0, ChatData.messageCount);
        gson.toJson(tempMessage, saving);
        saving.close();
        Chat.log.append("Current messages are saved" + '\n');
    }

    static void saveLog() throws IOException {
        File save = new File("log2.txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(save) );
        output.write(Chat.log.toString() );
        output.close();
    }
}
