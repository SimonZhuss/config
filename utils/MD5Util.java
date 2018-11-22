package com.zillion.yw.util;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zillion.yw.constant.MResultInfo;
import com.zillion.yw.exception.CommonException;

/**
 * MD5工具类
 * @author zhuss
 * 2016年9月29日下午3:58:42
 */
public class MD5Util {

	private static final Logger log = LoggerFactory.getLogger(MD5Util.class);

	private static final String MD5 = "MD5";
	private static final char ZERO = '0';

	/**
	 * MD5加密 :32位加密
	 * @param text 不能为null
	 * @return
	 */
	public static String encrypt(String text) {
		try {
			if(StringUtil.isBlank(text)) throw new CommonException(MResultInfo.ENCRYPT_TEXT_NULL);
			MessageDigest md = MessageDigest.getInstance(MD5);
			md.update(text.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer();
			int len = b.length;
			for (int offset = 0; offset < len; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append(ZERO);
				buf.append(Integer.toHexString(i));
			}
			return buf.toString().toLowerCase();
		} catch (Exception e) {
			log.error("MD5加密失败 = {}", e);
			throw new CommonException(MResultInfo.MD5_ENCRYPT_ERROR);
		}
	}
}
