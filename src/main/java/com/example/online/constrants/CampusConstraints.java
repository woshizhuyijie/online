package com.example.online.constrants;

import com.example.campus.pojo.Permission;
/*
*
 @author woshizhuyijie
 @date 2024-11-20
 074
* */
public  final class CampusConstraints {
    private CampusConstraints(){};
    public static final int Permission_Order_root=0;
    public static final int Permission_Order_Activity_Manager=1;
    public static final int Permission_Order_Course_Teacher=2;
    public static final int Permission_Order_Assistant=3;
    public static final int Permission_Order_Student=4;
    public static final String Des_Role_Name_root="root";
    public static final String Des_Role_Name_Activity_Manager="ActivityManager";
    public static final String Des_Role_Name_Course_Teacher="CourseTeacher";
    public static final String Des_Role_Name_Assistant="Assistant";
    public static final String Des_Role_Name_Student="Student";
    public static final int User_status_normal=1;
    public static final int User_status_abnormal=2;
    public static final int Activity_status_created=0;
    public static final int Activity_status_published=1;
    public static final int Activity_status_end=2;
    public static final int Activity_status_deleted=3;
    public static final String TIME_FOMRAT_IN_SECOND = "yyyy-MM-dd HH:mm:ss" ;
    public static final String TIME_FOMRAT_IN_MIL_SECOND = "yyyy-MM-dd HH:mm:SS:sss" ;
    public static final int User_isBind_No=0;
    public static final int User_isBind_Yse=1;
}
