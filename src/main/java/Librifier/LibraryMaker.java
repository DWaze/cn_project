package Librifier;

import BEncoding.BWriter;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LibraryMaker
{
    /**
     * Hash pieces generator method
     * @param file
     * @param pieceLength
     * @return
     * @throws IOException
     */
    public static byte[] hashPieces(File file, int pieceLength) throws IOException
    {
        MessageDigest sha1;
        try {
            sha1 = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new Error("SHA1 not supported");
        }
        InputStream in = new FileInputStream(file);
        ByteArrayOutputStream pieces = new ByteArrayOutputStream();
        byte[] bytes = new byte[pieceLength];
        int counter=0;
        int pieceByteCount  = 0, readCount = in.read(bytes, 0, pieceLength);
        while (readCount != -1) {
            pieceByteCount += readCount;
            sha1.update(bytes, 0, readCount);
            if (pieceByteCount == pieceLength) {
                counter++;
                pieceByteCount = 0;
                pieces.write(sha1.digest());
            }
            readCount = in.read(bytes, 0, pieceLength-pieceByteCount);
        }
        in.close();
        if (pieceByteCount > 0){
            pieces.write(sha1.digest());
        }
        return pieces.toByteArray();
    }

    /**
     * Library creation method
     * @param _file Library file
     * @param _sharedFile File to share
     * @param _hubURL Hub url
     * @param _author Author name
     * @throws IOException
     */
    public static void createLibrary(File _file, File _sharedFile, String _hubURL, String _author) throws IOException
    {
        final int pieceLength = 0x4000;
        Map<String,Object> info = new HashMap<>();
        info.put("length", _sharedFile.length());
        info.put("name", _sharedFile.getName());
        info.put("piece length", pieceLength);
        info.put("pieces", hashPieces(_sharedFile, pieceLength));
        Map<String,Object> metainfo = new HashMap<>();
        metainfo.put("announce", _hubURL);
        metainfo.put("creation date", System.currentTimeMillis()/1000);
        metainfo.put("created by", _author);
        metainfo.put("info", info);
        OutputStream out = new FileOutputStream(_file);
        BWriter.write(metainfo, out);
        out.close();
    }
}
