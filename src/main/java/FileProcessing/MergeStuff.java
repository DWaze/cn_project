package FileProcessing;

import Librifier.Library;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MergeStuff {

    Library mLibrary;

    public MergeStuff(Library mLibrary) {
        this.mLibrary = mLibrary;
    }

    public void fileMerging(){
        BookModifier fileSplit = new BookModifier();
        try {
//             int parts = fileSplit.splitFile(new File("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\music.mp4"));
//             System.out.println(parts);
            List<File> fileList = new ArrayList<>();
            int a = 0;
            while(a <mLibrary.getBooks().size()){
                if(a>=0 && a < 10){
                    fileList.add(new File("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\no_file\\parts\\"+mLibrary.getStuffName()+".00"+a));
//                    byte[] sha1 =fileSplit.createSha1(new File("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\no_file\\music.mp4.00"+a));
//                    System.out.println("SHA1 of file number 00"+a+"is"+sha1);
                    a++;
                }
                if (a>9 && a<=99){
                    fileList.add(new File("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\no_file\\parts\\"+mLibrary.getStuffName()+".0"+a));
//                    byte[] sha1 =fileSplit.createSha1(new File("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\no_file\\music.mp4.0"+a));
//                    System.out.println("SHA1 of file number 0"+a+"is"+sha1);
                    a++;
                }

                if(a>99){
                    fileList.add(new File("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\no_file\\parts\\"+mLibrary.getStuffName()+"."+a));
//                    byte[] sha1 =fileSplit.createSha1(new File("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\no_file\\music.mp4."+a));
//                    System.out.println("SHA1 of file number "+a+"is"+sha1);
                    a++;
                }
            }

            fileSplit.mergeFiles(fileList,new File("C:\\Users\\lhadj\\IdeaProjects\\cn_project\\src\\main\\resources\\no_file\\"+mLibrary.getStuffName()));



        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
