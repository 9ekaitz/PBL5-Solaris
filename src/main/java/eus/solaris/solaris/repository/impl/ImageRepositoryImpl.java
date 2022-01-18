package eus.solaris.solaris.repository.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import eus.solaris.solaris.exception.FileNotFoundException;
import eus.solaris.solaris.repository.ImageRepository;

@Repository
public class ImageRepositoryImpl implements ImageRepository{

    private String RESOURCES_DIR = ImageRepositoryImpl.class.getResource("/").getPath();

    @Override
    public String save(MultipartFile file) throws Exception {
        Path newFile = Paths.get(RESOURCES_DIR + new Date().getTime() + "-" + file.getOriginalFilename());
        Files.createDirectories(newFile.getParent());

        Files.write(newFile, file.getBytes());

        return newFile.toAbsolutePath().toString();
    }

    @Override
    public FileSystemResource findInFileSystem(String location) {
        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            throw new FileNotFoundException(location);
        }
    }

}
