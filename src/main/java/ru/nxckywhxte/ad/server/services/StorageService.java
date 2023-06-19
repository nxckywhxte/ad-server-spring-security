package ru.nxckywhxte.ad.server.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    public void init();

    public void save(MultipartFile file);

    public Resource load(String filename) throws MalformedURLException;

    public void deleteAll();

    public Stream<Path> loadAll();
}
