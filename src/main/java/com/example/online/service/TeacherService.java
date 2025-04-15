



import com.example.online.pojo.Teacher;

import java.util.List;

/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
public interface TeacherService {
    int insertTeacher(Teacher teacher);
    List<Teacher> query(Teacher teacher);
    int update(Teacher teacher);
}
