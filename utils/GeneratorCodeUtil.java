package com.zillion.yw.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memcached.JedisPoolClient;
import com.zillion.yw.constant.GeneratorCodeType;

/**
 * 随机编号生成器
 * @author zhuss
 * 2016年10月11日下午2:05:54
 */
public class GeneratorCodeUtil {
	
	private static final Logger log = LoggerFactory.getLogger(GeneratorCodeUtil.class);

	private static final String DATE_PATTERN = "yyMMdd";
	/** 商品编号索引值 */
	private static final String ITEM_CODE_INDEX = "key_idx_item";
	
	/** 订单号索引值 */
	private static final String ORDER_CODE_INDEX = "key_idx_order";
	
	private static final Long MAX_CODE = 10000000L;
	
	/** 索引字符串长度 */
	private static final Integer INDEX_STRING_LENGTH = 8;
	
	/** 编号长度 */
	private static final Integer CODE_LENGTH = 20;
	
	public static String generate(GeneratorCodeType type) {
		String dateStr = dateString();
		String indexStr =indexString(type);

		StringBuilder sb = new StringBuilder(CODE_LENGTH);
		switch (type) {
			case ITEM:
				sb.append(type.code);
				break;
			case ORDER:
				sb.append(type.code);
				break;
			default:
				throw new IllegalArgumentException("未知的编号类型");
		}
		return sb.append(dateStr).append(indexStr).toString();
	}
	
	/**
	 * 日期字符串
	 * 
	 * @return
	 */
	private static String dateString() {
		Calendar currentTime = Calendar.getInstance();
		return new SimpleDateFormat(DATE_PATTERN).format(currentTime.getTime());
	}

	/**
	 * 自增码
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String indexString(GeneratorCodeType type) {
		long currentRandom = Math.abs(System.nanoTime()%10);
		Long index = JedisPoolClient.getInstance().getIncrID((GeneratorCodeType.ORDER.equals(type) ?ORDER_CODE_INDEX: ITEM_CODE_INDEX)+currentRandom);
		if (null == index) {
			log.error("[生成自增编号异常 ] ={}","redis服务器获取自增值为空");
			index = System.currentTimeMillis();
		}
		if(currentRandom%2==0){
			index = MAX_CODE - 1 - index%MAX_CODE;
		}
		String idxStr = index.toString();
		int len = idxStr.length();
		StringBuilder sb = new StringBuilder(idxStr);
		if (len < INDEX_STRING_LENGTH) {
			return currentRandom+String.format("%07d", index);
		} else {
			return currentRandom+sb.delete(0,sb.length()-INDEX_STRING_LENGTH).toString();
		}
	}
}
