/*
 * 修订记录:
 * yuanxiao@cqfmbank.com 2018/12/10 17:30 创建
 *
 */
package com.cqns.demo.utils;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuanxiao@cqfmbank.com
 */
public class ResultInfo<T> implements Serializable {

	private static final long serialVersionUID = -1L;

	private boolean success;
	private String msg;
	private List<Object> otherInfoList;

	private T data;

	/**
	 * 异常对象不作序列化传输
	 */
	private transient Exception exception;

	public ResultInfo() {
	}

	public static <T> ResultInfo<T> create(Class<T> cls) {
		return new ResultInfo<T>();
	}

	public static ResultInfo<Void> create() {
		return new ResultInfo<>();
	}

	public static ResultInfo<Void> createFail(Exception e) {
		ResultInfo<Void> result = new ResultInfo<Void>();
		result.fail(e);
		return result;
	}

	public static ResultInfo<Void> createFail(String msg, Exception e) {
		ResultInfo<Void> result = new ResultInfo<Void>();
		result.fail(msg, e);
		return result;
	}

	public ResultInfo<T> resultFrom(ResultInfo<?> result) {
		this.success = result.isSuccess();
		this.msg = result.getMsg();
		return this;
	}

	public ResultInfo<T> success() {
		this.success = true;
		return this;
	}

	public ResultInfo<T> success(T data) {
		this.success = true;
		this.data = data;
		return this;
	}

	public ResultInfo<T> fail() {
		this.success = false;
		return this;
	}

	public ResultInfo<T> fail(String msg) {
		this.success = false;
		this.msg = msg;
		return this;
	}

	public ResultInfo<T> fail(Exception e) {
		this.success = false;
		if (e != null) {
			this.exception = e;
			this.msg = e.getMessage();
		}
		return this;
	}

	public ResultInfo<T> fail(String msg, Exception e) {
		this.success = false;
		this.msg = msg;
		if (e != null) {
			this.exception = e;
		}
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public ResultInfo<T> setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public ResultInfo<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public ResultInfo<T> appendMsg(String msg) {
		if (msg == null) {
			return this;
		}
		if (this.msg == null) {
			this.msg = msg;
		} else {
			this.msg += msg;
		}
		this.msg += "\n";
		return this;
	}

	public List<Object> getOtherInfoList() {
		return otherInfoList;
	}

	public ResultInfo<T> setOtherInfoList(List<Object> otherInfoList) {
		this.otherInfoList = otherInfoList;
		return this;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}

	public T getData() {
		return data;
	}

	public ResultInfo<T> setData(T data) {
		this.data = data;
		return this;
	}

	public Exception getException() {
		return exception;
	}

	public ResultInfo<T> setException(Exception exception) {
		this.exception = exception;
		return this;
	}
}
