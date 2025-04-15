package com.example.online.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/alipay")
@RequiredArgsConstructor
public class AlipayController {

    private final AlipayConfig alipayConfig;

    // 1️⃣ 支付请求接口
    @GetMapping("/pay")
    public void pay(@RequestParam String orderNo,
                    @RequestParam Double amount,
                    HttpServletResponse response) throws Exception {

        AlipayClient client = alipayConfig.getClient();

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(alipayConfig.getReturnUrl());
        request.setNotifyUrl(alipayConfig.getNotifyUrl());

        request.setBizContent("{" +
            "\"out_trade_no\":\"" + orderNo + "\"," +
            "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
            "\"total_amount\":\"" + amount + "\"," +
            "\"subject\":\"订单-" + orderNo + "\"" +
        "}");

        String form = client.pageExecute(request).getBody();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(form); // 直接输出支付宝页面
        response.getWriter().flush();
        response.getWriter().close();
    }

    // 2️⃣ 支付异步通知接口（重要）
    @PostMapping("/notify")
    public String notify(HttpServletRequest request) throws Exception {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            params.put(name, request.getParameter(name));
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(
            params,
            alipayConfig.getPublicKey(),
            "UTF-8",
            "RSA2"
        );

        if (signVerified) {
            String orderNo = params.get("out_trade_no");
            String tradeStatus = params.get("trade_status");

            if ("TRADE_SUCCESS".equals(tradeStatus)) {
                // ✅ 支付成功，更新业务订单状态
                System.out.println("订单 " + orderNo + " 支付成功！");
            }
            return "success";
        }
        return "fail";
    }

    // 3️⃣ 退款接口
    @PostMapping("/refund")
    public String refund(@RequestParam String orderNo,
                         @RequestParam Double refundAmount) throws Exception {
        AlipayClient client = alipayConfig.getClient();

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent("{" +
            "\"out_trade_no\":\"" + orderNo + "\"," +
            "\"refund_amount\":\"" + refundAmount + "\"," +
            "\"refund_reason\":\"用户申请退款\"" +
        "}");

        AlipayTradeRefundResponse response = client.execute(request);
        if (response.isSuccess()) {
            return "退款成功：" + response.getBody();
        } else {
            return "退款失败：" + response.getSubMsg();
        }
    }
}
