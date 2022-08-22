public class DownloaderFactory {

    public DownloaderFactory(){
    }

    public Downloader getNewDownloader(DownloadOptions options) {
        if(options.getNumParallelFiles() == 1 && options.getNumParallelParts() == 1){
            return new SerialDownloader();
        } else {
            System.out.println("Selected Simple Parallel Downloader for the task.");
            return new SimpleParallelDownloader(options);
        }
    }
}
