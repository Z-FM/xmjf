package com.shsxt.xm.po;

import java.util.Date;

public class SysPicture {
    /**
     * id
     * 
     */
    private Integer id;

    /**
     * item_id
     * 
     */
    private Integer itemId;

    /**
     * picture_url
     * 图片超链接
     */
    private String pictureUrl;

    /**
     * picture_path
     * 图片地址
     */
    private String picturePath;

    /**
     * picture_name
     * 图片名称
     */
    private String pictureName;

    /**
     * picture_type
     * 图片类型：7-活动中心8-项目图标4-项目信息图片5-文章图片1-首页滚动图6-移动图片 9app弹屏图片
     */
    private Integer pictureType;

    /**
     * addtime
     * 添加时间
     */
    private Date addtime;

    /**
     * addip
     * 添加ip
     */
    private String addip;

    /**
     * item_picture_type
     * 项目图片类型学车宝：1身份证，2学生证，3申请表，4学信网；21分期合同，22合影，23家访1,24家访2,25担保函；车商宝：1车城外观1,2车城外观2，3车城外观3，4车城外观4,5车城内景1，6车城内景2，7车城内景3，8车城内景4；21车辆外观1，22车辆外观2，23车辆外观3，24车辆外观4，25内饰1,26内饰2,27铭牌1,28铭牌2，29铭牌3，30铭牌4；41身份证，42营业执照，43房产证1，44房产证2，45房产证3；61个人担保函1，62个人担保函2，63车城担保函1，64车城担保函2；车贷宝：1身份证，2户口本，3结婚证，4房产证，5收入证明；21外观前，22外观后，23里程表，24中控台，25铭牌，26人车合照；41购车合同，42担保函，43委托书，44行驶证，45发票，46等级证书
     */
    private Integer itemPictureType;

    /**
     * sequence
     * 排序
     */
    private Integer sequence;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public Integer getPictureType() {
        return pictureType;
    }

    public void setPictureType(Integer pictureType) {
        this.pictureType = pictureType;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip;
    }

    public Integer getItemPictureType() {
        return itemPictureType;
    }

    public void setItemPictureType(Integer itemPictureType) {
        this.itemPictureType = itemPictureType;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}