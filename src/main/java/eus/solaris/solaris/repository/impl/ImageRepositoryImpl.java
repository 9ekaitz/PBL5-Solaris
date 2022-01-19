package eus.solaris.solaris.repository.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import eus.solaris.solaris.exception.FileNotFoundException;
import eus.solaris.solaris.repository.ImageRepository;

@Repository
public class ImageRepositoryImpl implements ImageRepository{

    public String PATH_PRODUCTS = "products/";
    public String PATH_SIGNATURES = "signatures/";
    private String UPLOADS_DIR = "file:uploads/";

    @Override
    public String save(MultipartFile file) throws Exception {
        File convFile = new File(PATH_SIGNATURES + file.getOriginalFilename() );
        convFile.getParentFile().mkdirs();
        convFile.createNewFile();
        try (FileOutputStream out = new FileOutputStream(convFile)){
            out.write(file.getBytes());
        } catch (Exception e) {
        }
        return convFile.getAbsolutePath();
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
