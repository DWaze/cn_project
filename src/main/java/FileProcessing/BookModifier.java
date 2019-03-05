package FileProcessing;

import java.io.*;
import java.nio.file.Files;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.List;

public class BookModifier {
    public static int splitFile(File f) throws IOException {
        int partCounter = 0;//I like to name parts from 001, 002, 003, ...
        //you can change it to 0 if you want 000, 001, ...

        int sizeOfFiles = 0x4000;
        byte[] buffer = new byte[sizeOfFiles];

        String fileName = f.getName();

        //try-with-resources to ensure closing stream
        try (FileInputStream fis = new FileInputStream(f);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            int bytesAmount = 0;
            while ((bytesAmount = bis.read(buffer)) > 0) {
                //write each chunk of data into separate file with different number in name
                String filePartName = String.format("%s.%03d", fileName, partCounter++);
                File newFile = new File(f.getParent()+"\\parts", filePartName);
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, bytesAmount);
                }
            }
            return partCounter;
        }
    }

    public static void mergeFiles(List<File> files, File into)
            throws IOException {
        try (FileOutputStream fos = new FileOutputStream(into);
             BufferedOutputStream mergingStream = new BufferedOutputStream(fos)) {
            for (File f : files) {
                Files.copy(f.toPath(), mergingStream);
            }
        }
    }

    public byte[] createSha1(File file) throws Exception  {
        final int pieceLength = 0x4000;
        MessageDigest digest = MessageDigest.getInstance("SHA1");
        InputStream fis = new FileInputStream(file);
        int n = 0;
        byte[] buffer = new byte[pieceLength];
        while (n != -1) {
            n = fis.read(buffer);
            if (n > 0) {
                digest.update(buffer, 0, n);
            }
        }
        return digest.digest();
    }

    public byte[] createMD5(File file) throws Exception  {
        final int pieceLength = 75*1024;
        MessageDigest digest = MessageDigest.getInstance("MD5");
        InputStream fis = new FileInputStream(file);
        int n = 0;
        byte[] buffer = new byte[pieceLength];
        DigestInputStream dis = new DigestInputStream(fis, digest);

        return digest.digest();
    }
//
//    public static void main(String[] args) throws IOException {
//        splitFile(new File("D:\\destination\\myFile.mp4"));
//    }
}
