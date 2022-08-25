package entity;

/**
 * @Auth chengst
 * @Date Created in  12:01 2020/7/13
 */
public class WechatTemplateMsgContentField {
    // 消息内容
    private String value;
    // 消息字体颜色
    private String color;

    public WechatTemplateMsgContentField(String value) {
        this.value = value;
        this.color = "#000000";
    }

    public WechatTemplateMsgContentField(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
