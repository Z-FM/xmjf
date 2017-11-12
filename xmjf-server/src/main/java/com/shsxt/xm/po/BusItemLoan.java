package com.shsxt.xm.po;

import java.math.BigDecimal;
import java.util.Date;

public class BusItemLoan {
    /**
     * id
     * 
     */
    private Integer id;

    /**
     * item_id
     * 项目ID
     */
    private Integer itemId;

    /**
     * number
     * 借款人编号
     */
    private String number;

    /**
     * borrower
     * 借款人
     */
    private String borrower;

    /**
     * id_card
     * 身份证号码
     */
    private String idCard;

    /**
     * mobile
     * 手机号码
     */
    private String mobile;

    /**
     * car_brand
     * 车辆品牌
     */
    private String carBrand;

    /**
     * car_demio
     * 车系
     */
    private String carDemio;

    /**
     * car_type
     * 车型
     */
    private String carType;

    /**
     * car_color
     * 车辆颜色
     */
    private String carColor;

    /**
     * buy_time
     * 购买时间
     */
    private String buyTime;

    /**
     * buy_price
     * 购买价格
     */
    private String buyPrice;

    /**
     * kilometers
     * 行驶公里数
     */
    private String kilometers;

    /**
     * assess_price
     * 评估价格
     */
    private String assessPrice;

    /**
     * licensing_time
     * 上牌时间
     */
    private Date licensingTime;

    /**
     * is_new_car
     * 是否是新车
     */
    private Integer isNewCar;

    /**
     * first_pay_amount
     * 首付金额
     */
    private BigDecimal firstPayAmount;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarDemio() {
        return carDemio;
    }

    public void setCarDemio(String carDemio) {
        this.carDemio = carDemio;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getKilometers() {
        return kilometers;
    }

    public void setKilometers(String kilometers) {
        this.kilometers = kilometers;
    }

    public String getAssessPrice() {
        return assessPrice;
    }

    public void setAssessPrice(String assessPrice) {
        this.assessPrice = assessPrice;
    }

    public Date getLicensingTime() {
        return licensingTime;
    }

    public void setLicensingTime(Date licensingTime) {
        this.licensingTime = licensingTime;
    }

    public Integer getIsNewCar() {
        return isNewCar;
    }

    public void setIsNewCar(Integer isNewCar) {
        this.isNewCar = isNewCar;
    }

    public BigDecimal getFirstPayAmount() {
        return firstPayAmount;
    }

    public void setFirstPayAmount(BigDecimal firstPayAmount) {
        this.firstPayAmount = firstPayAmount;
    }
}