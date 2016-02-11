package mainpackage;

/**
 I hope, that adding messages will be in chronological order, if no, i will add sorting
 */
public class Chat {
    private static int messageCount=0;
    private static int containerSize=10;
    private static int lastId=0;
    private static Message history[]=new Message[containerSize];
    private static String copyArgs[];    ///  temp, will be removed later with adding the .json files
    private static void addMessage(int i){          /// temp, will be seriously edited later with adding the .json files support
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
    private static void showHistory(int i){     /// temp, will be edited later with small changes
        for(int j=0;j<messageCount;j++){
            System.out.println(history[j].toString()+"\n");
        }
    }
    public static void main(String args[]) {

        ///    temp, will be removed later with adding the .json files

        copyArgs = new String[args.length];
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
                showHistory(i);
                i++;
            }
        }

        ///
    }
}
