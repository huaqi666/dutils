package com.wobangkj.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wobangkj.utils.JsonUtils;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 授权者
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-04 11:42:08
 */
public class Author extends Authorized {
	/**
	 * id
	 */
	private Object id;
	/**
	 * 用户ip或者其他访问源
	 */
	private Object key;
	/**
	 * 角色
	 */
	private Object role;
	/**
	 * 自定义数据
	 */
	private Object data;
	/**
	 * 有效时长
	 */
	private Long time;

	public static Builder builder() {
		return new Builder();
	}

	@JsonIgnore
	public Duration getDuration() {
		if (this.getTime() == null) {
			this.setTime(0L);
		}
		return Duration.ofMinutes(this.getTime());
	}

	@JsonIgnore
	public void setDuration(@NotNull Duration duration) {
		this.setTime(duration.toMillis());
		this.setExpireAt(Instant.now().plus(duration));
	}

	public Object getId() {
		return Optional.ofNullable(id).orElse(this.get("id"));
	}

	public void setId(Object id) {
		this.id = id;
		this.put("id", id);
	}

	public Object getKey() {
		return Optional.ofNullable(key).orElse(this.get("key"));
	}

	public void setKey(Object key) {
		this.key = key;
		this.put("key", key);
	}

	public Object getRole() {
		return Optional.ofNullable(role).orElse(this.get("role"));
	}

	public void setRole(Object role) {
		this.role = role;
		this.put("role", role);
	}

	public Object getData() {
		return Optional.ofNullable(data).orElse(this.get("data"));
	}

	public void setData(Object data) {
		this.data = data;
		this.put("data", data);
		this.put("data_str", JsonUtils.toJson(data));
	}

	/**
	 * 对象转成json
	 *
	 * @return json
	 */
	@Override
	public String toJson() {
		Map<String, Object> data = new HashMap<>(this.size() * 2);
		data.putAll(this);
		data.remove("data_str");
		return JsonUtils.toJson(data);
	}

	/**
	 * 获取授权数据
	 *
	 * @param type 指定类型
	 * @return 结果数据
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getData(@NotNull Class<T> type) {
		Object data = this.getData();
		if (data == null) {
			return null;
		}
		if (this.isBaseType(type) || type.equals(data.getClass())) {
			return (T) data;
		}
		Object json = this.get("data_str");
		if (json == null) {
			json = JsonUtils.toJson(data);
		}
		if (json instanceof String) {
			return JsonUtils.fromJson((String) json, type);
		}
		return super.getData(type);
	}

	public Long getTime() {
		return Optional.ofNullable(time).orElse((Long) this.get("time"));
	}

	public void setTime(Long time) {
		this.time = time;
		this.put("time", time);
	}

	public static class Builder {
		private final Map<String, Object> data;

		public Builder() {
			this.data = new HashMap<>();
		}

		public Builder key(Object key) {
			this.data.put("key", key);
			return this;
		}

		public Builder role(Object role) {
			this.data.put("role", role);
			return this;
		}

		public Builder data(Object data) {
			this.data.put("data", data);
			this.data.put("data_str", JsonUtils.toJson(data));
			return this;
		}

		public Builder id(Object id) {
			this.data.put("id", id);
			return this;
		}

		public Builder expireAt(@NotNull Date date) {
			this.data.put("expireAt", date);
			this.data.put("time", date.getTime() - System.currentTimeMillis());
			return this;
		}

		public Builder time(long time) {
			this.data.put("expireAt", new Date(System.currentTimeMillis() + time));
			this.data.put("time", time);
			return this;
		}

		public Author build() {
			Author author = new Author();
			author.putAll(this.data);
			return author;
		}
	}
}
