package Player;

import FileProcessing.MergeStuff;
import FileProcessing.StuffStatus;
import Librifier.Library;
import Librifier.LibraryReader;
import Messages.P_H_Message;
import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SendingTCP extends Thread {

    public static String ip_address = "localhost";
    SendingMessage sendingMessage;
    Socket s;
    static Boolean[] pDownload;
    String bEncoded;
    P_H_Message p_h_message;

    @Override
    public void run() {

        int a = 0;
        switch (a) {
            case 0: // sending download request
                try {
                    LibraryReader reader = new LibraryReader();
                    Library mLibrary = reader.read("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\with_file\\music2.libr");
//            mLibrary.print();
                    pDownload = new Boolean[mLibrary.getBooks().size()];
                    Arrays.fill(pDownload, false);
                    StuffStatus stuffStatus = new StuffStatus(pDownload);
//                    do {
                        s = new Socket(ip_address, 8080);
                        sendingMessage = new SendingMessage(s, stuffStatus, mLibrary);
                        Random rand = new Random();
                        List<Integer> rBooks = stuffStatus.getNotDownFile();
                        if (rBooks.size() > 0) {
                            int rBook = rBooks.get(rand.nextInt(rBooks.size()));
                            sendingMessage.setRequest(rBook);
                            sendingMessage.start();
                        } else {
                            System.out.println("All File Downloaded with success");
                            MergeStuff fileInfo = new MergeStuff(mLibrary);
                            fileInfo.fileMerging();
                        }

//                    } while (Arrays.asList(pDownload).contains(false));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1: // notify
                try {
                    s = new Socket(ip_address, 8000);
                    P_H_Message p_h_message = new P_H_Message(10,651651651,"192.168.0.101",8080,1,1,0,1);
                    String bencoded = p_h_message.bencoding();

                    DataOutputStream out = new DataOutputStream(s.getOutputStream());

                    out.writeUTF(bencoded);

                    out.close();
                    s.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2: // Request Players
                try {
                    s = new Socket(ip_address, 8000);
                    p_h_message = new P_H_Message(12,651651651,"192.168.0.101",8080,1,1,0,1);
                    bEncoded = p_h_message.bencoding();

                    DataOutputStream out = new DataOutputStream(s.getOutputStream());

                    out.writeUTF(bEncoded);

                    DataInputStream dataInputStream = new DataInputStream(s.getInputStream());

                    bEncoded = dataInputStream.readUTF();

                    Bencode bencode = new Bencode();

                    Map<String, Object> document = bencode.decode(bEncoded.getBytes(), Type.DICTIONARY);

                    System.out.println(document.get("player"));


                    out.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }

    }
}
