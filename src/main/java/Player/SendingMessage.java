package Player;

import FileProcessing.StuffStatus;
import Librifier.Library;
import Message.MsgRequest;
import Message.MsgWriter;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.net.Socket;

public class SendingMessage extends Thread {
    private Socket s;
    private int request;
    private String filepath;
    private StuffStatus mStuffStatus;
    private Library mLibrary;

    SendingMessage(Socket s, StuffStatus mStuffStatus, Library mLibrary) {
        this.mLibrary = mLibrary;
        this.s = s;
        this.mStuffStatus = mStuffStatus;
    }

    void setRequest(int request) {
        this.request = request;
    }

    @Override
    public void run() {
        try {
//            BookModifier fileSplit = new BookModifier();
//            P_P_Message p_p_message = new P_P_Message(0,1,1,1,1,1,1,0,request,1,1);
            byte[] p_p_message =MsgWriter.write(new MsgRequest(request));
//            String bencoded = p_p_message.bencoding();
            ByteArrayInputStream in = new ByteArrayInputStream(p_p_message);
            BufferedOutputStream out = new BufferedOutputStream(s.getOutputStream());
            while (true){
                int line = in.read();
                if(line<0){
                    break;
                }
                out.write(line);
            }
            out.close();
            in.close();
            s.close();

//            BufferedInputStream in = new BufferedInputStream(s.getInputStream());
//            BufferedOutputStream fileOut = null;
//
//            if(request>=0 && request<=9){
//                filepath = "C:\\Users\\lhadj\\" +
//                        "IdeaProjects\\cn_project\\src\\main\\resources\\no_file\\parts\\"+mLibrary.getStuffName()+".00";
//                fileOut = new BufferedOutputStream(new FileOutputStream(new File(filepath+request)));
//            }
//            if (request>9 && request<=99){
//                filepath = "C:\\Users\\lhadj\\" +
//                        "IdeaProjects\\cn_project\\src\\main\\resources\\no_file\\parts\\"+mLibrary.getStuffName()+".0";
//                fileOut = new BufferedOutputStream(new FileOutputStream(new File(filepath+request)));
//            }
//            if(request>99){
//                filepath="C:\\Users\\lhadj\\" +
//                        "IdeaProjects\\cn_project\\src\\main\\resources\\no_file\\parts\\"+mLibrary.getStuffName()+".";
//                fileOut = new BufferedOutputStream(new FileOutputStream(new File(filepath+request)));
//            }
//
//            while(true){
//                int line = in.read();
//                if(line<0){
//                    break;
//                }
//                fileOut.write(line);
//            }
//            out.close();
//            fileOut.close();
//            s.close();
//
//            BookModifier mBookModifier = new BookModifier();
//
//            String fPath = filepath+request;
//            byte[] sha1_file = mBookModifier.createSha1(new File(fPath));
//            byte[] sha1_libr =mLibrary.getBooks().get(request);
//
//            synchronized (mStuffStatus){
//                if(Arrays.equals(sha1_file,sha1_libr)){
//                    System.out.println("SHA1 match result: "+Arrays.equals(sha1_file,sha1_libr));
//                    mStuffStatus.setBooksStatus(request,true);
//                }else{
//                    System.out.println("SHA1 match result: "+Arrays.equals(sha1_file,sha1_libr));
//                }
//            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public StuffStatus getmStuffStatus() {
        return mStuffStatus;
    }

    public void setmStuffStatus(StuffStatus mStuffStatus) {
        this.mStuffStatus = mStuffStatus;
    }
}
