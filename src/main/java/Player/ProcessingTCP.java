package Player;

import Librifier.Library;
import Message.Msg;
import Message.MsgReader;
import Message.MsgRequest;

import java.io.*;
import java.net.Socket;

public class ProcessingTCP extends Thread {

    private Socket s;
    private Library mLibrary;

    ProcessingTCP(Socket s, Library mLibrary) {
        this.s = s;
        this.mLibrary = mLibrary;
    }

    @Override
    public void run() {
        try {

            DataInputStream dataInputStream = new DataInputStream(s.getInputStream());

            byte[] msg = MsgReader.MsgStreamReader(dataInputStream);

            Msg mMsg = MsgReader.read(msg);
            System.out.println();

            int msg_request = ((MsgRequest)mMsg).getIndex();
            File file = null;
            switch (msg_request){
                case 0:
                    if(msg_request>=0 && msg_request< 10){
                        file = new File("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\with_file\\parts\\"+mLibrary.getStuffName()+".00"+msg_request);
                    }

                    if (msg_request>9 && msg_request<=99){
                        file = new File("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\with_file\\parts\\"+mLibrary.getStuffName()+".0"+msg_request);
                    }

                    if(msg_request>99){
                        file = new File("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\with_file\\parts\\"+mLibrary.getStuffName()+"."+msg_request);
                    }

                    BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
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
                    break;
                case 1:
                    System.out.println(msg_request);
                    break;
                case 2:
                    System.out.println(msg_request);
                    break;
                case 3:
                    System.out.println(msg_request);
                    break;

                default:
                    System.out.println(msg_request);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
