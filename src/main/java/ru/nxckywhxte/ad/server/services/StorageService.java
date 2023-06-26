package ru.nxckywhxte.ad.server.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.UUID;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    void initIconsPath();

    String saveIcon(MultipartFile file, UUID userId);

    Resource loadIcon(String filename);

    void deleteAll();

    Stream<Path> loadAll();
}
