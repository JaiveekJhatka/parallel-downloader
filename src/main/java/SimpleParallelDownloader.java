import utils.DownloadFileInfo;
import utils.DownloadFileInfoFetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class SimpleParallelDownloader implements Downloader{
    // List of downloads to be performed.
    private List<Runnable> tasks;
    private final DownloadOptions downloadConfig;
    private DownloadFileInfoFetcher fileInfoFetcher;
//    private static final int SIZE_LIMIT_FOR_PARALLEL_DOWNLOADING = 5242880;  //5MB
    private static final int SIZE_LIMIT_FOR_PARALLEL_DOWNLOADING = 10240;  //5MB

    public SimpleParallelDownloader(DownloadOptions options){
        tasks = new ArrayList<Runnable>();
        fileInfoFetcher = new DownloadFileInfoFetcher();
        downloadConfig = options;
    }

    public List<String> download(List<String> urls) throws DownloadException {
        for (String url : urls){
            String[] temp = url.split("/");
            String filename = downloadConfig.getDownloadDir() + temp[temp.length-1];

            DownloadFileInfo fileInfo = fileInfoFetcher.getInfo(url);
            Runnable task = null;
            if (fileInfo.isParallelDownloadAllowed() && fileInfo.getSizeInBytes() > SIZE_LIMIT_FOR_PARALLEL_DOWNLOADING){
                task = new ParallelDownloadFileTask(url, filename, downloadConfig.getNumParallelParts(), fileInfo.getSizeInBytes());
            }
            else {
                task = new DownloadFileTask(url, filename);
            }
            tasks.add(task);
        }
        downloadInThreads();
        return urls;
    }


    private void downloadInThreads() throws DownloadException {
        System.out.println("Starting downloads.");
        final ExecutorService threads = Executors.newFixedThreadPool(downloadConfig.getNumParallelFiles());
        try {
            final CountDownLatch latch = new CountDownLatch(tasks.size());
            for (final Runnable task : tasks) {
                threads.execute(task);
                latch.countDown();
            }
            latch.await();
        } catch(InterruptedException e) {
            throw new DownloadException("Interrupted downloads:" + e.getMessage());
        } finally {
            threads.shutdown();
        }
    }
}
