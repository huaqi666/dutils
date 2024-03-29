package com.wobangkj.api;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * value包装器
 *
 * @author cliod
 * @since 7/9/20 3:55 PM
 */
@FunctionalInterface
public interface ValueWrapper<T> extends Supplier<T>, SessionSerializable {
	/**
	 * 静态方法
	 *
	 * @param obj 对象
	 * @return 包装对象
	 */
	static ValueWrapper<Object> of(Object obj) {
		return new SimpleValueWrapper(obj);
	}

	/**
	 * 获取对象
	 *
	 * @return 对象
	 */
	T value();

	/**
	 * Gets a result.
	 *
	 * @return a result
	 */
	@Override
	default T get() {
		return this.value();
	}

	/**
	 * 反序列化
	 *
	 * @return 结果对象
	 */
	@Override
	default Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("value", value());
		return map;
	}

	/**
	 * 转成对象
	 *
	 * @return obj
	 */
	@Override
	@Deprecated
	default Object toObject() {
		return new SimpleValueWrapper(this.value());
	}

	/**
	 * 默认实现
	 */
	class SimpleValueWrapper implements ValueWrapper<Object> {
		private final Object value;

		public SimpleValueWrapper(Object value) {
			this.value = value;
		}

		@Override
		public Object value() {
			return value;
		}
	}
}
