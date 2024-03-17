package me.keills.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DownloaderImpl implements Downloader{
    @Override
    public void download(List<String> links) throws MalformedURLException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        int i = 0;
        for(String link: links){
            URL url = new URL(link);
            final int fileInd = i;
            Thread thread = new Thread(()->{
                try {
                    FileUtils.copyURLToFile(url, new File("downloads/file"+fileInd));
                } catch (IOException e) {
                    System.out.println("Error happened");
                }
            });
            i++;
            executorService.execute(thread);
        }

        executorService.shutdown();
    }
}
