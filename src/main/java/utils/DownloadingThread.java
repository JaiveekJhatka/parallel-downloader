package utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadingThread extends Thread{
    private String url;
    private String filename;
    private long startBytes;
    private long endBytes;

    public DownloadingThread(String url, String filename, long startBytes, long endBytes) throws IOException {
        this.url = url;
        this.filename = filename;
        this.startBytes = startBytes;
        this.endBytes = endBytes;
        File partialFile = new File(filename);
        partialFile.createNewFile();
    }

    @Override
    public void run() {
        try {
            System.out.println("Parallel downloading : " + this.toString());

            URLConnection httpConnection = new URL(url).openConnection();
            httpConnection.setRequestProperty("Range", String.format("bytes=%d-%d", startBytes, endBytes));
            BufferedInputStream in = new BufferedInputStream(httpConnection.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            byte dataBuffer[] = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("Completed: " + this.toString());
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        } catch (Exception e){
            System.out.println("Unknown exception occurred in DownloadFileTask run: ");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "DownloadingThread{" +
                "filename='" + filename + '}';
    }
}
