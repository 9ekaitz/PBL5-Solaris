package eus.solaris.solaris.repository;

import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface ImageRepository {
    
    public String save(MultipartFile file) throws IOException;
    public String save(byte[] bytes, String name) throws IOException;
    public FileSystemResource findInFileSystem(String location);

}
