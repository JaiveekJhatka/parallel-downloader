package utils;

import java.net.HttpURLConnection;

import java.net.URL;


// Make a singleton?
public class DownloadFileInfoFetcher {

    public DownloadFileInfoFetcher(){

    }

    public DownloadFileInfo getInfo(String url) {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Range", "bytes=0-");

            int httpResponseStatusCode = con.getResponseCode();
            Long downloadSize = con.getHeaderFieldLong("content-length", 0);

            boolean parallelDownloadAllowed = (httpResponseStatusCode == 206);
            if (downloadSize !=0) {
                return new DownloadFileInfo(parallelDownloadAllowed, downloadSize);
            } else{
                throw new RuntimeException("Error in fetching DownloadFileInfo for: " + url);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error in downloading FileInfo");
        }

    }
}
