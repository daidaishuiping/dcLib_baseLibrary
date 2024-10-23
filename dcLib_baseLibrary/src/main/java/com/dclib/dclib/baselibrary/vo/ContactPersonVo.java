package com.dclib.dclib.baselibrary.vo;

/**
 * 联系人实体
 * Created on 2019/5/24
 *
 * @author daichao
 */
public class ContactPersonVo extends BaseVo {

    /**
     * 字母索引
     */
    public static final int TYPE_LETTER = 1;
    /**
     * 内容
     */
    public static final int TYPE_CONTENT = 0;

    /**
     * 分类
     */
    private int itemType;
    /**
     * 首字母
     */
    private String firstLetter;
    /**
     * 拼音
     */
    private String pinYin;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 头像
     */
    private String headerImgUrl;
    /**
     * type=0 是没有关系  1 朋友  2 对方发了请求
     */
    private int type;
    /**
     * 用户id
     */
    private String uid;

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeaderImgUrl() {
        return headerImgUrl;
    }

    public void setHeaderImgUrl(String headerImgUrl) {
        this.headerImgUrl = headerImgUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
