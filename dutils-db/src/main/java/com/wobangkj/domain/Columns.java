package com.wobangkj.domain;

/**
 * 数据库字段
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-08 11:35:23
 */
@Deprecated
public class Columns<T> extends EntityWrapper<T> {

	@Deprecated
	public static <T> Columns<T> of(Class<T> type) {
		Columns<T> columns = new Columns<>();
		columns.parseField(type);
		return columns;
	}
}
