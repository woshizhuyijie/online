package com.example.campus.service;

import java.util.List;
import java.util.Map;
/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *
 * */
public interface UnitService {

    // 插入文档资源
    int addUnitDoc(int unitId, String docUrl);

    // 插入图片资源
    int addUnitImg(int unitId, String imgUrl);

    // 插入视频资源
    int addUnitVideo(int unitId, String videoUrl);

    // 查询文档资源 URLs
    List<String> getDocUrlsByUnitId(int unitId);

    // 查询图片资源 URLs
    List<String> getImgUrlsByUnitId(int unitId);

    // 查询视频资源 URLs
    List<String> getVideoUrlsByUnitId(int unitId);
    Map<String, Object> getResources(int unitId);
}
