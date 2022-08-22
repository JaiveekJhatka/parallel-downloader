package utils;


public class DownloadFileInfo {
    private boolean parallelDownloadAllowed;
    private long sizeInBytes;

    public DownloadFileInfo(boolean parallelDownloadAllowed, Long sizeInBytes){
        this.parallelDownloadAllowed = parallelDownloadAllowed;
        this.sizeInBytes = sizeInBytes;
    }

    public boolean isParallelDownloadAllowed() {
        return parallelDownloadAllowed;
    }

    public long getSizeInBytes() {
        return sizeInBytes;
    }
}


