package com.example.online.service.serviceImpl;


import com.example.online.mapper.UnitMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *
 * */
@Slf4j
@Service
public class UnitServiceImpl implements com.example.campus.service.UnitService {

    @Autowired
    private UnitMapper unitMapper;

    @Override
    public int addUnitDoc(int unitId, String docUrl) {

        return  unitMapper.insertUnitDoc(unitId, docUrl);
    }

    @Override
    public int addUnitImg(int unitId, String imgUrl) {

        return  unitMapper.insertUnitImg(unitId, imgUrl);
    }

    @Override
    public int addUnitVideo(int unitId, String videoUrl) {

        return  unitMapper.insertUnitVideo(unitId, videoUrl);
    }

    @Override
    public List<String> getDocUrlsByUnitId(int unitId) {
        return unitMapper.selectDocUrlsByUnitId(unitId);
    }

    @Override
    public List<String> getImgUrlsByUnitId(int unitId) {
        return unitMapper.selectImgUrlsByUnitId(unitId);
    }

    @Override
    public List<String> getVideoUrlsByUnitId(int unitId) {
        return unitMapper.selectVideoUrlsByUnitId(unitId);
    }

    @Override
    public Map<String, Object> getResources(int unitId) {
        Map<String, Object> resources = new HashMap<>();

        // 查询图片、文档、视频的资源链接
        List<String> imgUrls = getImgUrlsByUnitId(unitId);
        List<String> docUrls = getDocUrlsByUnitId(unitId);
        List<String> videoUrls =getVideoUrlsByUnitId(unitId);
        log.info("imgs:{}",imgUrls);
        log.info("docUrl:{}",docUrls);
        log.info("viddeoUrl:{}",videoUrls);
        resources.put("imgUrls", imgUrls);
        resources.put("docUrls", docUrls);
        resources.put("videoUrls", videoUrls);
        return resources;
    }
}
