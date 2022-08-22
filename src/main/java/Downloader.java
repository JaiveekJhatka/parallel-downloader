import java.util.List;

public interface Downloader {
    List<String> download(List<String> urls) throws DownloadException;
}
