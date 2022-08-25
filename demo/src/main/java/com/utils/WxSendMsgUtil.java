package com.utils;

import com.alibaba.fastjson.JSONObject;
import com.entity.WechatTemplateMsgContent;
import com.entity.WechatTemplateMsgContentField;
import com.entity.WechatTemplateRequest;
import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.Map;

public class WxSendMsgUtil {

    /**
     * 模板id
     */
    private final static String WX_TEMPLATE_ID = "E6twUrzghMoCTiEGrandI2MeCfABUgRv5b8z01y7f3k";

    /**
     * openid
     * 公众号用户管理F12查看
     */
    private final static String OPEN_ID = "o0YAewcVYtWWnKygV0iMPJ9DzX7M";


    public static String sendMessage(String accessToken,JSONObject jsonObject) {

        String first = "亲爱的baby早上好,开始签到!";
        String keyword1 = jsonObject.getString("name");
        String keyword2 = jsonObject.getString("date");
        String keyword3 = jsonObject.getString("status");
        String remark = jsonObject.getString("remark");

        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;
        WechatTemplateMsgContent content = new WechatTemplateMsgContent();
        content.setFirst(new WechatTemplateMsgContentField(first));
        content.setKeyword1(new WechatTemplateMsgContentField(keyword1));
        content.setKeyword2(new WechatTemplateMsgContentField(keyword2));
        content.setKeyword3(new WechatTemplateMsgContentField(keyword3));
        content.setRemark(new WechatTemplateMsgContentField(remark,"#FF0000"));
        WechatTemplateRequest request1 = new WechatTemplateRequest();
        request1.setData(content);
        request1.setTouser(OPEN_ID);
        request1.setTemplate_id(WX_TEMPLATE_ID);
        Map stringObjectMap = beanToMap(request1);
        return HttpClientUtil.sendPostRequest(url, stringObjectMap);
    }


    //  javabean转map
    private static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key.toString(), beanMap.get(key));
            }
        }
        return map;
    }
}
