package eus.solaris.solaris.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskForm {
    private List<Long> tasksId;
    private MultipartFile sign;    
}
