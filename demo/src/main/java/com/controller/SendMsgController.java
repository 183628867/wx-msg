package com.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.utils.HttpClientUtil;
import com.utils.RedisUtil;
import com.utils.WxSendMsgUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("/api/")
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
public class SendMsgController {

    //  城市编码    http://www.ip33.com/area_code.html
    private static String CITY = "330100";
    //  高德应用密钥
    private static String GD_KEY = "**************************";

    //  微信公众号appid
    private static String WX_APPID = "***************";

    private static String WX_SECRET = "********************";


    @Resource
    JedisPool jedisPool;

    /**
     * 定时任务
     * 每天7点触发
     */
//    @Scheduled(cron = "0 0 7 * * ?")
    @RequestMapping("sendMsg")
    public void sendMsg() {
        JSONObject jsonObject = new JSONObject();

        RedisUtil jedis = new RedisUtil(jedisPool);
        boolean flag = jedis.exists("access_token");
        String accessToken = jedis.getString("access_token");
        if (!flag) {
            accessToken = getAccessToken();
        }

        String url = "https://restapi.amap.com/v3/weather/weatherInfo?city=" + CITY + "&key=" + GD_KEY;
        String str = HttpClientUtil.sendGetRequest(url);
        JSONObject json = JSONObject.parseObject(str);
        JSONArray lives = json.getJSONArray("lives");
        JSONObject dataJson = JSONObject.parseObject(lives.get(0).toString());
        String province = dataJson.getString("province");
        String city = dataJson.getString("city");
        String weather = dataJson.getString("weather");
        String temperature = dataJson.getString("temperature");
        String humidity = dataJson.getString("humidity");


        String words = HttpClientUtil.sendGetRequest("https://api.1314.cool/words/api.php");


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        jsonObject.put("date", formatter.format(date));
        jsonObject.put("name", "张三");
        jsonObject.put("status", "正常");
        String string = province + city + "当前气温：\n";
        string += "天气：" + weather + "\n";
        string += "温度：" + temperature + "°C\n";
        string += "湿度：" + humidity + "\n";
        string += words;
        jsonObject.put("remark", string);
        WxSendMsgUtil.sendMessage(accessToken, jsonObject);
    }


    /**
     * 获取access_token
     *
     * @return java.lang.String
     * @author cheng
     * @date 2022-8-25 19:30
     */
    public String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WX_APPID + "&secret=" + WX_SECRET;
        String str = HttpClientUtil.sendGetRequest(url);
        JSONObject jsonObject = JSONObject.parseObject(str);
        String access_token = jsonObject.getString("access_token");
        RedisUtil jedis = new RedisUtil(jedisPool);
        jedis.setString("access_token", access_token);
        jedis.expire("access_token", 7200);
        return access_token;
    }
}
