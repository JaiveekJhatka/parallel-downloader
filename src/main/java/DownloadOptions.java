
public class DownloadOptions {
    private String downloadDir;
    private Integer numParallelParts;
    private Integer numParallelFiles;

    public DownloadOptions(String downloadDir, int numParallelParts, int numParallelFiles) throws DownloadException{
        this.downloadDir = downloadDir;
        this.numParallelParts = numParallelParts;
        this.numParallelFiles = numParallelFiles;
        this.verifyData();
    }

    private void verifyData() throws DownloadException{
        if(numParallelParts < 1){
            throw new DownloadException("Number of parallel parts must be non-negative.");
        }
        if(numParallelFiles < 1){
            throw new DownloadException("Number of parallel files must be non-negative.");
        }
        // More checks
    }

    public String getDownloadDir() {
        return downloadDir;
    }

    public Integer getNumParallelParts() {
        return numParallelParts;
    }

    public Integer getNumParallelFiles() {
        return numParallelFiles;
    }
}
