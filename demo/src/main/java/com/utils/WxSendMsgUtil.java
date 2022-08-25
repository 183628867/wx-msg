package com.utils;

import com.alibaba.fastjson.JSONObject;
import entity.WechatTemplateMsgContent;
import entity.WechatTemplateMsgContentField;
import entity.WechatTemplateRequest;
import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.Map;

public class WxSendMsgUtil {

    /**
     * 模板id
     */
    private final static String WX_TEMPLATE_ID = "uKc7c3v3jYBtCHsQeHBv0Fd-HgxTVvZ55iIv4VMaL_Q";

    /**
     * openid
     */
    private final static String OPEN_ID = "o0YAewcVYtWWnKygV0iMPJ6DzX7M";


    public static String sendPlantMessage(JSONObject jsonObject) {
        String first = "您好," + jsonObject.getString("cp") + " 车主，您的爱车已进厂。";
        String keyword1 = jsonObject.getString("cp");
        String keyword2 = jsonObject.getString("sxr");
        String keyword3 = jsonObject.getString("sxrTel");
        String keyword4 = jsonObject.getString("jcr");
        String keyword5 = jsonObject.getString("jcsj");
        String reamk = "您的爱车维修进度，我们会通过微信通知您。";

        String access_token = "60_x_q3pr5JiDZgDUFyLNPXhfAVEZ4rCKCPeCaa3uE7n3VI_r37f-kyysP0YqTx18z32AQp-dSLn8x4F36JjlbQz-VvaluGWybnWOabam6cI48hHOrOTZ_dC0JohNx2ZW0fMQuUvAq7XaTRaaDbIEFhAGADZA";

        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;
        WechatTemplateMsgContent content = new WechatTemplateMsgContent();
        content.setFirst(new WechatTemplateMsgContentField(first));
        content.setKeyword1(new WechatTemplateMsgContentField(keyword1));
        content.setKeyword2(new WechatTemplateMsgContentField(keyword2));
        content.setKeyword3(new WechatTemplateMsgContentField(keyword3));
        content.setKeyword4(new WechatTemplateMsgContentField(keyword4));
        content.setKeyword5(new WechatTemplateMsgContentField(keyword5));
        content.setRemark(new WechatTemplateMsgContentField(reamk));
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
