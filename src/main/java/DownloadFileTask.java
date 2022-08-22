import java.io.*;
import java.net.URL;


public class DownloadFileTask implements Runnable{
    private String url;
    private final String filename;

    public DownloadFileTask(String url, String filename) {
        this.url = url;
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            System.out.println("Downloading: " + this.toString());
            new File(filename);
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            byte dataBuffer[] = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("Completed: " + this.toString());
            Thread.sleep(10000);
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        } catch (InterruptedException e){
            System.out.println("DownloadFileTask run interrupted: " + e.getMessage());
        } catch (Exception e){
            System.out.println("Unknown exception occurred in DownloadFileTask run: ");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "{url='" + url + '\'' +
                ", toPath='" + filename + '}';
    }

}