import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        String downloadDirectory = "downloads/";
        int numParallelParts = 3;
        int numParallelFiles = 2;
        List<String> urls = new ArrayList<String>();
        urls.add("https://static.wikia.nocookie.net/dota2_gamepedia/images/d/d8/Vo_ember_spirit_embr_move_02.mp3");
//        urls.add("https://downloads.raspberrypi.org/raspios_lite_armhf/images/raspios_lite_armhf-2022-04-07/2022-04-04-raspios-bullseye-armhf-lite.img.xz");
        urls.add("https://dl.k8s.io/release/v1.24.0/bin/linux/amd64/kubectl");
//        urls.add("https://static.wikia.nocookie.net/dota2_gamepedia/images/a/a9/Vo_ember_spirit_embr_levelup_08.mp3");
//        urls.add("https://static.wikia.nocookie.net/dota2_gamepedia/images/6/6c/Vo_ember_spirit_embr_rare_02.mp3");
//        urls.add("https://static.wikia.nocookie.net/dota2_gamepedia/images/2/2b/Vo_ember_spirit_embr_kill_12.mp3");
//        urls.add("https://static.wikia.nocookie.net/dota2_gamepedia/images/8/86/Vo_ember_spirit_embr_notyet_01.mp3");
        try {
            DownloadOptions downloadConfig = new DownloadOptions(downloadDirectory, numParallelParts, numParallelFiles);
            DownloaderFactory downloaderFactory = new DownloaderFactory();
            Downloader downloader = downloaderFactory.getNewDownloader(downloadConfig);

            downloader.download(urls);
        } catch (DownloadException e){
            System.out.println("DownloadException occurred: " + e.getMessage());
        } catch (Exception e){
            System.out.println("Unexpected exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
