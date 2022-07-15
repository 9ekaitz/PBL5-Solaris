package eus.solaris.solaris.repository;

import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface ImageRepository {
    
    public static final String BASE_PATH = System.getProperty("user.dir");
    public static final String PATH_SIGNATURES = BASE_PATH + "/signatures/";
    public static final String RELATIVE_SIGNATURES_PATH = "/img/signatures/";
    public static final String RELATIVE_PROFILES_PATH = "/img/profile/";
    public static final String RELATIVE_PRODUCT_PATH = "/img/products/";
    public static final String ABSOLUTE_PROFILES_PATH = BASE_PATH + RELATIVE_PROFILES_PATH;
    public static final String ABSOLUTE_PRODUCT_PATH = BASE_PATH + RELATIVE_PRODUCT_PATH;
    
    public String save(MultipartFile file, String path, String relativePath) throws IOException;
    public String save(byte[] bytes, String name) throws IOException;
    public FileSystemResource findInFileSystem(String location);

}
