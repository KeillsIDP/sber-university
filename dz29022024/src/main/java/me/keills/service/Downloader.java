package me.keills.service;

import java.net.MalformedURLException;
import java.util.List;

public interface Downloader {
    void download(List<String> links) throws MalformedURLException;
}
