package Player;

import FileProcessing.BookModifier;
import Librifier.Library;
import Librifier.LibraryMaker;
import Librifier.LibraryReader;

import java.io.File;

public class PlayerWithStuff {

    public static void main(String[] args) {
        Library mLibrary = null;
        try {
//            new File("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\with_file\\music.libr").mkdirs();
            LibraryMaker.createLibrary(new File("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\with_file\\music2.libr"), new File("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\with_file\\music2.mp4"), "192.168.0.100/8080", "Redha");
            LibraryReader reader = new LibraryReader();
            mLibrary = reader.read("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\with_file\\music2.libr");

//            int bookLength = mLibrary.getBookLength();
//            MergeStuff fileInfo = new MergeStuff();
//            fileInfo.fileMerging();
//            BookModifier fileModifier = new BookModifier();
            BookModifier.splitFile(new File("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\with_file\\"+mLibrary.getStuffName()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        ListenningTCP listenningTCP = new ListenningTCP(mLibrary);
        listenningTCP.start();
    }
}
