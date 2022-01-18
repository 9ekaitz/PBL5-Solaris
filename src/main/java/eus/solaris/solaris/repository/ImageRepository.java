package eus.solaris.solaris.repository;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface ImageRepository {
    
    public String save(MultipartFile file) throws Exception;
    public FileSystemResource findInFileSystem(String location);

}
