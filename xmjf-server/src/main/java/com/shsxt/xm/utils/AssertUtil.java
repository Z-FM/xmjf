package com.shsxt.xm.utils;


import com.shsxt.xm.constant.P2pConstant;
import com.shsxt.xm.exceptions.AuthExcetion;
import com.shsxt.xm.exceptions.ParamsExcetion;

public class AssertUtil {
	
	public static void isTrue(Boolean flag,String errorMsg) {
		if(flag){
			throw new ParamsExcetion(errorMsg);
		}
	}
	
	
	public static void isTrue(Boolean flag,String errorMsg,Integer errorCode) {
		if(flag){
			throw new ParamsExcetion(errorMsg,errorCode);
		}
	}

	public  static  void isLogin(Boolean flag,String errorMsg){
		if(flag){
			throw new AuthExcetion(errorMsg, P2pConstant.OP_FAILED_CODE);
		}
	}
	
}
