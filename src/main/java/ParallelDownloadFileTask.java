import utils.DownloadingThread;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ParallelDownloadFileTask implements Runnable{
    private String url;
    private final String filename;
    private int numThreads;
    private long fileSize;

    public ParallelDownloadFileTask(String url, String filename, int numThreads, long fileSize) {
        this.url = url;
        this.filename = filename;
        this.numThreads = numThreads;
        this.fileSize = fileSize;
    }

    @Override
    public void run() {
        try {
            start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void start() throws IOException {

        long[] endPoint = new long[numThreads + 1];
        long block = fileSize / numThreads;
        for (int i = 0; i < numThreads; i++) {
            endPoint[i] = block * i;
        }
        endPoint[numThreads] = fileSize+1;
        DownloadingThread[] threads = new DownloadingThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            String partialFileName = filename + "." + i + ".tmp";
            threads[i] = new DownloadingThread(url, partialFileName, endPoint[i], endPoint[i+1]-1);
        }
        try {
            downloadAndMerge(threads);
            System.out.println("Temp files download complete for: " + filename);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private void downloadAndMerge(DownloadingThread[] taskThreads) throws DownloadException {
        try {
            for (DownloadingThread taskThread:taskThreads){
                taskThread.start();
            }
            for (DownloadingThread taskThread:taskThreads){
                taskThread.join();
            }
            mergeTempFiles();
        } catch(InterruptedException e) {
            throw new DownloadException("Interrupted downloads:" + e.getMessage());
        }
    }


    public void mergeTempFiles() {
        System.out.println("Merging temp files for: " + filename);
        try (OutputStream out = new FileOutputStream(filename)) {
            byte[] buffer = new byte[8196];
            int size;
            for (int i = 0; i < numThreads; i++) {
                String tmpFile = filename + "." + i + ".tmp";
                InputStream in = new FileInputStream(tmpFile);
                while ((size = in.read(buffer)) != -1) {
                    out.write(buffer, 0, size);
                }
                in.close();
                Files.delete(Paths.get(tmpFile));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "Parallel Download {url='" + url + '\'' +
                ", toPath='" + filename + '}';
    }

}


