package ru.nxckywhxte.ad.server.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.UUID;
import java.util.stream.Stream;

public interface StorageService {
    public void init();
    public void initIconsPath();

    public String saveIcon(MultipartFile file, UUID userId);

    public Resource loadIcon(String filename) throws MalformedURLException;

    public void deleteAll();

    public Stream<Path> loadAll();
}
