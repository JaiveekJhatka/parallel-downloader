# parallel-downloader
Downloads multiple files in parallel. Big files are partitioned into smaller ones and download parallely.

# To Run:
Make config changes and add download urls in Main.java file.

This big/small file size limit is set in SimpleParallelDownloader.java

Command: ```mvn clean compile exec:java```

Uses java8


# TODO:
1) Add tests and edge case handling.
2) Add a way to verify hash of the downloaded file.
3) Better design for handling small file single thread download and multi-threaded download.
4) Cleanup and pick config variables from env/properties file.
