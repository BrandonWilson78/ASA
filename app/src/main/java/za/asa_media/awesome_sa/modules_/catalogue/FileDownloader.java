package za.asa_media.awesome_sa.modules_.catalogue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Snow-Dell-05 on 6/9/2017.
 */

public class FileDownloader {
    private static final int MEGABYTE = 1024*1024;

    public static boolean downloadFile(String fileUrl, File directory) {
        try {

            URL url = new URL(fileUrl.trim());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //  urlConnection.setRequestMethod("GET");
            //  urlConnection.setDoOutput(true);
                urlConnection.connect();

            FileOutputStream fileOutputStream = new FileOutputStream(directory);
            InputStream inputStream = urlConnection.getInputStream(); //url.openConnection().getInputStream();
            //    int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bufferLength);
            }

            //   fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
