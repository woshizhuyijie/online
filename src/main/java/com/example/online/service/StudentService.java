



import com.example.online.pojo.Student;

import java.util.List;

/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
public interface StudentService {
    int addStudent(Student student);//新增学员
    int updateStudent(Student student);// 即可修改又可以更新状态

    // 绑定班级
    //int bindClass(Student student);//废弃

    //绑定用户 service 中 开启事物 先更新关联id 然后再查询用户是否被绑定
    int bindUser(Student student);
    List<Student> query(Student student);
    //学员管理查询时后能查到绑定得账号 未作 已做
    //通过userId 查询学员id
    int getStuIdByUseId(Integer userId);
}
