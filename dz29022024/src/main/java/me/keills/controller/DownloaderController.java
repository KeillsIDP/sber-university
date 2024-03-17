package me.keills.controller;

import me.keills.service.Downloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DownloaderController {
    @Autowired
    private Downloader downloader;

    @GetMapping("/")
    public ResponseEntity startDownload(@RequestBody Map<String, String> links){
        try{
            downloader.download(links.entrySet().stream().map(e->e.getValue()).collect(Collectors.toList()));
            return ResponseEntity.ok("DOWNLOADED");
        } catch (MalformedURLException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
