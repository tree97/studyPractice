package mainpackage;
import com.google.gson.*;
import java.io.*;
/**
 I hope, that adding messages will be in chronological order, if no, i will add sorting
 */
public class Chat {
    private static int messageCount=0;
    private static int containerSize=10;
    private static int lastId=0;
    private static Message history[]=new Message[containerSize];
    private static String copyArgs[];    ///  temp, may be will be removed later
    private static void sizeController(){
        Message buf[];
        if(messageCount==containerSize){
            buf=new Message[containerSize];
            for(int j=0;j<containerSize;j++)
                buf[j]=new Message(history[j]);
            history=new Message[containerSize*2];
            for(int j=0;j<containerSize;j++)
                history[j]=new Message(buf[j]);
            containerSize*=2;
        }
    }
    private static void addMessage(int i){
        sizeController();
        String text=new String(copyArgs[i+1]);
        String author=new String(copyArgs[i+2]);
        String time=new String(copyArgs[i+3]);
        history[messageCount++]=new Message(lastId,text,author,time);
        lastId++;
    }
    private static void removeMessage(int i){   /// temp, will be edited later with small changes
        int ind=Integer.parseInt(copyArgs[i+1]);
        int j;
        for(j=0;j<messageCount;j++){
            if(history[j].getId()==ind)
                break;
        }
        if(j<messageCount){
            while(j<messageCount){
                history[j]=history[j+1];
                j++;
            }
            messageCount--;
        }
    }
    private static void showHistory(){     /// temp, will be edited later with small changes
        for(int j=0;j<messageCount;j++){
            System.out.println(history[j].toString()+"\n");
        }
    }
    private static void downloadFromFile(String fileName) throws IOException{     /// temp, will be modified after changing ID
        messageCount=0;
        Reader reader=new InputStreamReader(Chat.class.getResourceAsStream(fileName),"UTF-8");
        Gson gson=new GsonBuilder().create();
        Integer temp =new Integer(gson.fromJson(reader,Integer.class));
        int count=temp.intValue();
        for(messageCount=0;messageCount<count;messageCount++){
            sizeController();
            history[messageCount]=new Message(gson.fromJson(reader,Message.class));
            lastId=history[messageCount].getId();
        }
        reader.close();
    }
    private static void saveHistory()throws IOException{
        Writer saving=new OutputStreamWriter(new FileOutputStream("output.json"),"UTF-8");
        Gson gson=new GsonBuilder().create();
        gson.toJson(new Integer(messageCount),saving);
        for(int i=0;i<messageCount;i++) {
            gson.toJson(history[i],saving);
        }
        saving.close();
    }
    public static void main(String args[]) throws IOException{

        ///    temp, will be removed later with adding the .json files
        downloadFromFile("output.json");
     /*   copyArgs = new String[args.length];
        int i=0;
        for(i=0;i<args.length;i++)
            copyArgs[i]=new String(args[i]);
        i=0;
        while(i<args.length){
            if("1".equals(args[i])){
                addMessage(i);
                i+=4;
            }
            else if("2".equals(args[i])){
                removeMessage(i);
                i+=2;
            }
            else if("3".equals(args[i])){
                showHistory();
                i++;
            }
        }
        saveHistory(); */
        showHistory();
        ///
    }
}
