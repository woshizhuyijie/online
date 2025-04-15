package com.example.online.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *
 * */
@Mapper
public interface UnitMapper {//unitMapper 用于加载教学单元的资源url
    // 插入 unit_doc 表数据

    int insertUnitDoc(@Param("unitId") int unitId, @Param("docUrl") String docUrl);

    // 插入 unit_img 表数据

    int insertUnitImg(@Param("unitId") int unitId, @Param("imgUrl") String imgUrl);

    // 插入 unit_video 表数据

    int insertUnitVideo(@Param("unitId") int unitId, @Param("videoUrl") String videoUrl);

    // 查询 unit_doc 表的所有 doc_url，返回 String 类型的 List

    List<String> selectDocUrlsByUnitId(@Param("unitId") int unitId);

    // 查询 unit_img 表的所有 img_url，返回 String 类型的 List

    List<String> selectImgUrlsByUnitId(@Param("unitId") int unitId);

    // 查询 unit_video 表的所有 video_url，返回 String 类型的 List

    List<String> selectVideoUrlsByUnitId(@Param("unitId") int unitId);
}
