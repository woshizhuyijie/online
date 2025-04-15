package com.example.online.controller;

import com.example.campus.annotation.RequiresPermission;
import com.example.campus.constrants.HttpStatusConstants;
import com.example.campus.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
 *
 * @author woshizhuyijie
 * @date 2024-12-22
 * 统一管理教学单元资源上传的接口
 */
@RestController
@Slf4j
@RequestMapping("/unit")
public class UnitController {

    @Autowired
    private UnitService unitService;

    // 查询文档资源
    @PostMapping("/queryDoc")
    @RequiresPermission("user")
    @Cacheable(cacheNames = "campus:unit:doc", key = "'unitDocUrls:' + #params['unitId']")
    public ResponseResult<List<String>> queryDoc(@RequestBody Map<String, Object> params) {
        int unitId = (Integer) params.get("unitId");
        List<String> docUrls = unitService.getDocUrlsByUnitId(unitId);
        return ResponseResult.success(docUrls);
    }

    // 查询图片资源
    @PostMapping("/queryImg")
    @RequiresPermission("user")
    @Cacheable(cacheNames = "campus:unit:img", key = "'unitImgUrls:' + #params['unitId']")
    public ResponseResult<List<String>> queryImg(@RequestBody Map<String, Object> params) {
        int unitId = (Integer) params.get("unitId");
        List<String> imgUrls = unitService.getImgUrlsByUnitId(unitId);
        return ResponseResult.success(imgUrls);
    }

    // 查询视频资源
    @PostMapping("/queryVideo")
    @RequiresPermission("user")
    @Cacheable(cacheNames = "campus:unit:video", key = "'unitVideoUrls:' + #params['unitId']")
    public ResponseResult<List<String>> queryVideo(@RequestBody Map<String, Object> params) {
        int unitId = (Integer) params.get("unitId");
        List<String> videoUrls = unitService.getVideoUrlsByUnitId(unitId);
        return ResponseResult.success(videoUrls);
    }

    // 添加文档资源
    @PostMapping("/addDoc")
    @RequiresPermission("unit")
    @CacheEvict(value = "campus:unit:doc", allEntries = true , key = "'unitDocUrls:' + #params['unitId']")  // 更新时清空缓存
    public ResponseResult<String> addDoc(@RequestBody Map<String, Object> params) {
        int unitId = (Integer) params.get("unitId");
        String docUrl = (String) params.get("docUrl");
        int ret = unitService.addUnitDoc(unitId, docUrl);
        if (ret == 0) {
            return ResponseResult.failure(HttpStatusConstants.FORBIDDEN, "添加文档失败");
        } else {
            return ResponseResult.success("文档添加成功");
        }
    }

    // 添加图片资源
    @PostMapping("/addImg")
    @RequiresPermission("unit")
    @CacheEvict(value = "campus:unit:img", allEntries = true,key = "'unitImgUrls:' + #params['unitId']")  // 更新时清空缓存
    public ResponseResult<String> addImg(@RequestBody Map<String, Object> params) {
        int unitId = (Integer) params.get("unitId");
        String imgUrl = (String) params.get("imgUrl");
        int ret = unitService.addUnitImg(unitId, imgUrl);
        if (ret == 0) {
            return ResponseResult.failure(HttpStatusConstants.FORBIDDEN, "添加图片失败");
        } else {
            return ResponseResult.success("图片添加成功");
        }
    }

    // 添加视频资源
    @PostMapping("/addVideo")
    @RequiresPermission("unit")
    @CacheEvict(value = "campus:unit:video", allEntries = true, key = "'unitVideoUrls:' + #params['unitId']")  // 更新时清空缓存
    public ResponseResult<String> addVideo(@RequestBody Map<String, Object> params) {
        log.info(params.toString());
        int unitId = (Integer) params.get("unitId");
        String videoUrl = (String) params.get("videoUrl");
        int ret = unitService.addUnitVideo(unitId, videoUrl);
        if (ret == 0) {
            return ResponseResult.failure(HttpStatusConstants.FORBIDDEN, "添加视频失败");
        } else {
            return ResponseResult.success("视频添加成功");
        }
    }
    // 查询教学单元的资源（图片、文档、视频）
    @PostMapping("/getResources")
    @RequiresPermission("user")
    public ResponseResult getResources(@RequestBody Map<String, String> params) {
       String unitId = params.get("unitId");
        log.info("getResource unitId: {}",unitId);
        if (unitId == null) {
            return ResponseResult.failure(HttpStatusConstants.BAD_REQUEST, "单元ID不能为空");
        }

        // 调用service层查询资源
        Map<String, Object> resources = unitService.getResources(Integer.parseInt(unitId));

        return ResponseResult.success(resources);
    }
}
