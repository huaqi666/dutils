package com.wobangkj.api.sms;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.wobangkj.bean.Pageable;
import com.wobangkj.utils.JsonUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 短信api
 *
 * @author cliod
 * @since 8/7/20 2:43 PM
 */
public interface Sms {

	static Sms getInstance(String regionId, String accessKeyId, String secret) {
		return null;
	}

	/**
	 * 获取短信签名
	 *
	 * @return 短信签名
	 */
	String getSignName();

	/**
	 * 设置短信签名
	 *
	 * @param signName 短信签名
	 */
	void setSignName(String signName);

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param signName     签名
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 */
	AcsResponse send(final String template, String params, String signName, String phoneNumbers) throws ClientException;

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param signName     签名
	 * @param phoneNumbers 手机号, 多个
	 * @return 结果
	 */
	default AcsResponse send(final String template, String params, String signName, String... phoneNumbers) throws ClientException {
		return this.send(template, params, signName, String.join(",", phoneNumbers));
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param signName     签名
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 */
	default AcsResponse send(final String template, String params, String signName, List<String> phoneNumbers) throws ClientException {
		return this.send(template, params, signName, String.join(",", phoneNumbers));
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 */
	default AcsResponse send(final String template, String params, String phoneNumbers) throws ClientException {
		return this.send(template, params, getSignName(), phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 */
	default AcsResponse send(final String template, String params, String... phoneNumbers) throws ClientException {
		return this.send(template, params, getSignName(), phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 */
	default AcsResponse send(final String template, String params, List<String> phoneNumbers) throws ClientException {
		return this.send(template, params, getSignName(), phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param signName     签名
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 */
	default AcsResponse send(final String template, Map<String, Object> params, String signName, String phoneNumbers) throws ClientException {
		return this.send(template, JsonUtils.toJson(params), signName, phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param signName     签名
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 */
	default AcsResponse send(final String template, Map<String, Object> params, String signName, String... phoneNumbers) throws ClientException {
		return this.send(template, JsonUtils.toJson(params), signName, phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param signName     签名
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 */
	default AcsResponse send(final String template, Map<String, Object> params, String signName, List<String> phoneNumbers) throws ClientException {
		return this.send(template, JsonUtils.toJson(params), signName, phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 */
	default AcsResponse send(final String template, Map<String, Object> params, String phoneNumbers) throws ClientException {
		return this.send(template, params, getSignName(), phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 */
	default AcsResponse send(final String template, Map<String, Object> params, String... phoneNumbers) throws ClientException {
		return this.send(template, params, getSignName(), phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 */
	default AcsResponse send(final String template, Map<String, Object> params, List<String> phoneNumbers) throws ClientException {
		return this.send(template, params, getSignName(), phoneNumbers);
	}

	/**
	 * 批量发送短信(单个模板,但是不同签名和多个手机号)
	 *
	 * @param template     模板
	 * @param params       模板参数
	 * @param signNames    签名
	 * @param phoneNumbers 手机号
	 * @return 结果
	 */
	AcsResponse batchSend(final String template, List<Map<String, Object>> params, String signNames, String phoneNumbers) throws ClientException;

	/**
	 * 批量发送短信(单个模板,但是不同签名和多个手机号)
	 *
	 * @param template     模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号
	 * @return 结果
	 */
	default AcsResponse batchSend(final String template, List<Map<String, Object>> params, String phoneNumbers) throws ClientException {
		return batchSend(template, params, getSignName(), phoneNumbers);
	}

	/**
	 * 批量发送短信(单个模板,但是不同签名和多个手机号)
	 *
	 * @param template     模板
	 * @param params       模板参数
	 * @param signNames    签名
	 * @param phoneNumbers 手机号
	 * @return 结果
	 */
	default AcsResponse batchSend(final String template, List<Map<String, Object>> params, String signNames, String... phoneNumbers) throws ClientException {
		return batchSend(template, params, signNames, String.join(",", phoneNumbers));
	}

	/**
	 * 批量发送短信(单个模板,但是不同签名和多个手机号)
	 *
	 * @param template     模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号
	 * @return 结果
	 */
	default AcsResponse batchSend(final String template, List<Map<String, Object>> params, String... phoneNumbers) throws ClientException {
		return batchSend(template, params, getSignName(), phoneNumbers);
	}

	/**
	 * 批量发送短信(单个模板,但是不同签名和多个手机号)
	 *
	 * @param template     模板
	 * @param params       模板参数
	 * @param signNames    签名
	 * @param phoneNumbers 手机号
	 * @return 结果
	 */
	default AcsResponse batchSend(final String template, List<Map<String, Object>> params, String signNames, List<String> phoneNumbers) throws ClientException {
		return batchSend(template, params, signNames, String.join(",", phoneNumbers));
	}

	/**
	 * 批量发送短信(单个模板,但是不同签名和多个手机号)
	 *
	 * @param template     模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号
	 * @return 结果
	 */
	default AcsResponse batchSend(final String template, List<Map<String, Object>> params, List<String> phoneNumbers) throws ClientException {
		return batchSend(template, params, getSignName(), phoneNumbers);
	}

	/**
	 * 查看短信发送记录和发送状态
	 *
	 * @param phoneNumber 手机号
	 * @param date        日期支持查询最近30天的记录。格式为yyyyMMdd
	 * @param bizId       回执id
	 * @param pageable    分页查看发送记录
	 * @return 结果
	 */
	AcsResponse querySendDetails(String phoneNumber, LocalDate date, String bizId, Pageable pageable) throws ClientException;

	/**
	 * 查看短信发送记录和发送状态
	 *
	 * @param phoneNumber 手机号
	 * @param date        日期支持查询最近30天的记录。格式为yyyyMMdd
	 * @param bizId       回执id
	 * @param page        分页查看发送记录，指定发送记录的的当前页码
	 * @param size        分页查看发送记录，指定每页显示的短信记录数量。取值范围为1~50
	 * @return 结果
	 */
	default AcsResponse querySendDetails(String phoneNumber, LocalDate date, String bizId, Integer page, Integer size) throws ClientException {
		return querySendDetails(phoneNumber, date, bizId, Pageable.of(page, size));
	}

	/**
	 * 获取短信操作
	 *
	 * @return 短信
	 */
	default Sms getSms() {
		return this;
	}

	/**
	 * 获取短信签名操作
	 *
	 * @return 短信签名
	 */
	SmsSign getSmsSign();

	/**
	 * 获取短信模板操作
	 *
	 * @return 短信模板
	 */
	SmsSign getSmsTemplate();
}