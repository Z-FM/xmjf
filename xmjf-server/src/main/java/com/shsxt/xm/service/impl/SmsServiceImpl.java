package com.shsxt.xm.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.shsxt.xm.constant.P2pConstant;
import com.shsxt.xm.service.IBasUserService;
import com.shsxt.xm.service.ISmsService;
import com.shsxt.xm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GXR on 2017/11/7.
 */
@Service
public class SmsServiceImpl implements ISmsService {

    @Resource
    private IBasUserService iBasUserService;
    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = P2pConstant.TAOBAO_ACCESS_KEY_ID;
    static final String accessKeySecret = P2pConstant.TAOBAO_ACCESS_KEY_SECRET;
    /**
     * 1.参数合法性校验
     *    phone 不空
     *    code 非空
     * 2.手机号唯一性校验
     * 3.执行短信发送行为
     * 1111111111
     *   13
     *   15
     *   17
     *   18
     * @param phone
     * @param code
     * @throws Exception
     */
    @Override
    public void sendPhoneCode(String phone, String code) throws Exception {
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(code),"验证码非空");
        AssertUtil.isTrue(null!=iBasUserService.queryBasUserByPhone(phone),"该手机号已注册");
        //发短信
        SendSmsResponse response = sendSms(phone,code);

        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());


    }
    public static SendSmsResponse sendSms(String phone, String code) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(P2pConstant.TAOBAO_SMS_FREE_SIGN_NAME);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(P2pConstant.TAOBAO_SMS_TEMPLATE_CODE);
        Map<String,String> map=new HashMap<String,String>();
        map.put("code",code);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(JSON.toJSONString(map));

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId(phone);

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }


}
