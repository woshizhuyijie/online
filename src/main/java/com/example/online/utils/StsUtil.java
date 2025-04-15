package com.example.campus.utils;



import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *
 * */

public class StsUtil {

    // 从系统环境变量中获取阿里云相关配置
    private static final String ACCESS_KEY_ID = System.getenv("OSS_ACCESS_KEY_ID");
    private static final String ACCESS_KEY_SECRET = System.getenv("OSS_ACCESS_KEY_SECRET");
    private static final String STS_ROLE_ARN = System.getenv("OSS_ROLE_ARN");
    private static final String POLICY_DOCUMENT = null; // 不需要设置策略文档时可以为null

    // 获取 STS 临时凭证
    // 从系统环境变量中获取阿里云相关配置

    private static final String OSS_BUCKET_NAME = "campus-zzdrrt555new";
    private static final String REGION = "cn-hangzhou";  // 阿里云 OSS 区域

    // 获取 STS 临时凭证
    public static Map<String, String> getStsToken() {
        try {
            DefaultProfile profile = DefaultProfile.getProfile(
                    REGION,  // 这里是阿里云的区域，确保与您的 OSS Bucket 匹配
                    ACCESS_KEY_ID,
                    ACCESS_KEY_SECRET
            );
            IAcsClient client = new DefaultAcsClient(profile);

            AssumeRoleRequest request = new AssumeRoleRequest();
            request.setRoleArn(STS_ROLE_ARN);
            request.setRoleSessionName("oss-session");

            // 获取临时凭证
            AssumeRoleResponse response = client.getAcsResponse(request);

            // 临时凭证
            String accessKeyId = response.getCredentials().getAccessKeyId();
            String accessKeySecret = response.getCredentials().getAccessKeySecret();
            String securityToken = response.getCredentials().getSecurityToken();
            String expiration = response.getCredentials().getExpiration();

            // 生成上传策略 policy
            String policy = generatePolicy(OSS_BUCKET_NAME);

            // Base64编码 policy
            String encodedPolicy = new String(Base64.encodeBase64(policy.getBytes()));

            // 生成签名
            String signature = generateSignature(accessKeySecret, encodedPolicy);

            // 返回凭证信息
            Map<String, String> credentials = new HashMap<>();
            credentials.put("accessKeyId", accessKeyId);
            credentials.put("accessKeySecret", accessKeySecret);
            credentials.put("securityToken", securityToken);
            credentials.put("expiration", expiration);
            credentials.put("policy", encodedPolicy);
            credentials.put("signature", signature);

            return credentials;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 生成上传策略 policy
    // 生成上传策略 policy
    // 生成 Policy 方法
    private static String generatePolicy(String bucketName) {
        // 获取当前时间
        long currentTimeMillis = System.currentTimeMillis();

        // 设置过期时间为当前时间的1小时后
        long expirationTimeMillis = currentTimeMillis + 3600 * 1000;  // 1小时后

        // 格式化过期时间为 ISO8601 格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String expiration = sdf.format(new Date(expirationTimeMillis));

        // 创建并返回 policy
        return "{\"expiration\":\"" + expiration + "\",\"conditions\":[" +
                "{\"bucket\":\"" + bucketName + "\"}," +
                "[\"content-length-range\", 0, 1048576000], " +  // 最大文件大小限制 1GB
                "[\"eq\", \"$success_action_status\", \"200\"]" +  // 成功上传时的返回状态
                "]}";
    }

    // 生成签名的方法
    private static String generateSignature(String accessKeySecret, String encodedPolicy) {
        try {
            String signature = com.aliyun.oss.common.auth.ServiceSignature.create().computeSignature(accessKeySecret, encodedPolicy);
            return signature;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
