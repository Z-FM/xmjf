package com.shsxt.xm.constant;

/**
 * Created by lp on 2017/11/7.
 */
public class P2pConstant {

    public final static String OP_SUCCESS_MSG="操作成功";
    public final static Integer OP_SUCCESS_CODE=200;
    public final static String OP_FAILED_MSG="操作失败";
    public final static Integer OP_FAILED_CODE=300;
    // 验证码session key
    public  final  static  String PICTURE_VERIFY_KEY="XM_00001";


    //  短信发送常量
    //public  final  static  String TAOBAO_SEND_PHONE_URL="http://gw.api.taobao.com/router/rest";
    public  final  static  String TAOBAO_ACCESS_KEY_ID="LTAIW8yrbuevDVJf";
    public  final  static  String TAOBAO_ACCESS_KEY_SECRET="xgBle43wWDOvn3oFV0NnTbiYdBrIQR";
    //public  final  static  String TAOBAO_SMS_TYPE="normal";
    public  final  static  String TAOBAO_SMS_FREE_SIGN_NAME="来自飞明的问候";
    public  final  static  String TAOBAO_SMS_TEMPLATE_CODE="SMS_109455148";

    // 手机验证码session key 值
    public  final  static  String PHONE_VERIFY_CODE="XM_00001_";
    public  final  static  int PHONE_VERIFY_CODE_EXPIR_TIME=60;

    /**
     * 第三方支付参数配置
     */
    /**
     * 合作身份者PID，签约账号，由16位纯数字组成的字符串，请登录商户
     */
    public final static String  PARTNER = "9796636105929474";

    /**
     *  MD5密钥，安全检验码，由数字和字母组成的32位字符串，请登录商户后
     */
    public final static String KEY = "23fd93167d2141708cdd67ef0ca5531d";

    /**
     * 商户号（8位数字)
     */
    public final static String  USER_SELLER = "74754404";

    /**
     *  服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可
     */
    public final static String  NOTIFY_URL = "http://www.shsxt.com:9999/account/callback";

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public final static String  RETURN_URL = "http://www.shsxt.com:9999/account/callBack";

    /**
     * 支付地址，必须外网可以正常访问
     */
    public final static String  GATEWAY_NEW = "http://payment.passpay.net/PayOrder/payorder";

    public  final  static  String BODY="PC端用户充值操作";
    public  final  static  String SUBJECT="PC端用户充值";
    public  final  static  String ORDER_FAILED_MSG="订单异常，请联系管理员!";
    public  final  static  String ORDER_SUCCESS="TRADE_SUCCESS";


}
