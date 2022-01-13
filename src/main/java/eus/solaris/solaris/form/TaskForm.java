package eus.solaris.solaris.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskForm {
    List<Long> tasksId;
    MultipartFile sign;    
}
