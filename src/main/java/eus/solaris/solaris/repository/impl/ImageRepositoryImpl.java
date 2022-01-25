package eus.solaris.solaris.repository.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import eus.solaris.solaris.exception.FileNotFoundException;
import eus.solaris.solaris.repository.ImageRepository;

@Repository
public class ImageRepositoryImpl implements ImageRepository {

    private static final String BASE_PATH = System.getProperty("user.dir");
    private static final String PATH_SIGNATURES = BASE_PATH + "/signatures/";
    private static final String RELATIVE_PROFILES_PATH = "/img/profile/";
    private static final String ABSOLUTE_PROFILES_PATH = BASE_PATH + RELATIVE_PROFILES_PATH;

    @Override
    public String save(MultipartFile file) throws IOException {
        File convFile = new File(PATH_SIGNATURES + file.getOriginalFilename());
        convFile.getParentFile().mkdirs();
        if (convFile.createNewFile()) {
            try (FileOutputStream out = new FileOutputStream(convFile)) {
                out.write(file.getBytes());
            } catch (Exception e) {
                return null;
            }
        }
        return convFile.getAbsolutePath();
    }

    @Override
    public String save(byte[] bytes, String name) throws IOException {
        File convFile = new File(ABSOLUTE_PROFILES_PATH + name);
        convFile.getParentFile().mkdirs();
        if (convFile.createNewFile()) {
            try (FileOutputStream out = new FileOutputStream(convFile)) {
            out.write(bytes);
            } catch (Exception e) {
                return null;
            }
        }
        return RELATIVE_PROFILES_PATH + convFile.getName();
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
