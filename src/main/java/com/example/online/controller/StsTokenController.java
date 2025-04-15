package com.example.online.controller;

import com.example.campus.result.ResponseResult;
import com.example.campus.utils.StsUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *获取OSS前端上传sts的许可
 * */
@RestController
public class StsTokenController {

    @GetMapping("/getStsToken")
    public ResponseResult<Object> getStsToken() {
        return ResponseResult.success(StsUtil.getStsToken());
    }
}
