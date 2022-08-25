package com.controller;


import com.alibaba.fastjson.JSONObject;
import com.utils.HttpClientUtil;
import com.utils.WxSendMsgUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/")
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
public class SendMsgController {

    private static String CITY = "330100";

    private static String KEY = "435b156470a2efd79b226adbb5a5b291";


    @RequestMapping("sendMsg")
    public void sendMsg() {
        JSONObject jsonObject = new JSONObject();


        String url = "https://restapi.amap.com/v3/weather/weatherInfo?city=" + CITY + "&key=" + KEY;
        String s = HttpClientUtil.sendGetRequest(url, "");
        System.out.println(s);

        WxSendMsgUtil.sendPlantMessage(jsonObject);
    }


    /**
     * 定时任务
     * 每天7点触发
     */
//    @Scheduled(cron = "0 0 7 * * ?")
//    public void sendMsg() {
//        JSONObject jsonObject = new JSONObject();
//        WxSendMsgUtil.sendPlantMessage(jsonObject);
//    }
}
