package com.zillion.yw.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.zillion.yw.constant.MResultInfo;
import com.zillion.yw.constant.ValidFieldType;
import com.zillion.yw.exception.CommonException;

import java.util.Collection;

/**
 * 入参验证
 * @author zhuss
 * @2016年1月2日 下午3:58:06
 */
public class AssertUtil {
	
    public static void notNull(Object o, MResultInfo errorCode) {
        if (o == null) {
        	throw new CommonException(errorCode);
        }
    }

    public static void notEmpty(String str, MResultInfo errorCode) {
        if (StringUtils.isEmpty(str)) {
            throw new CommonException(errorCode);
        }
    }
    
    public static <T> void notEmpty(Collection<T> collection, MResultInfo errorCode) {
        if (CollectionUtils.isEmpty(collection)) {
        	throw new CommonException(errorCode);
        }
    }
    
    /**
     * 验证字段属性是否为空
     * @param str
     * @param errorCode
     */
    public static void notBlank(String str,MResultInfo errorCode) {
        if (StringUtil.isBlank(str)) {
            throw new CommonException(errorCode);
        }
    }
    
    /**
     * 验证字段属性是否合法
     * @param str
     * @param fieldType
     */
    public static void notValid(String str,ValidFieldType fieldType) {
    	if(fieldType.equals(ValidFieldType.TELEPHONE)){//验证手机号
        	if(!VerifyUtil.verifyTelephone(str)) throw new CommonException(MResultInfo.TELEPHONE_NO_VALID);
        }else if(fieldType.equals(ValidFieldType.EMAIL)){//验证邮箱
        	if(!VerifyUtil.verifyEmail(str)) throw new CommonException(MResultInfo.EMAIL_NO_VALID);
        }else if(fieldType.equals(ValidFieldType.ID)){//验证身份证号
        	if(!VerifyUtil.verifyCardID(str)) throw new CommonException(MResultInfo.ID_NO_VALID);
        }else if(fieldType.equals(ValidFieldType.ZIPCODE)){//验证邮编
        	if(!VerifyUtil.verifyZipCode(str)) throw new CommonException(MResultInfo.ZIPCODE_NO_VALID);
        }else{
        	throw new CommonException(MResultInfo.TYPE_NOT_IN_SCOPE);
        }
    }
}
