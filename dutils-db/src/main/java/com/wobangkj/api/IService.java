package com.wobangkj.api;

import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Condition;
import com.wobangkj.domain.Pageable;

/**
 * 通用Service, 兼容方法
 *
 * @author cliod
 * @since 9/4/20 11:03 AM
 */
public interface IService<T> {
	/**
	 * 获取Dao
	 *
	 * @return 通用dao
	 */
	IDao<T> getDao();

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	T queryById(Long id);

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	T queryById(Object id);

	/**
	 * 通过参数查询单条数据
	 *
	 * @param t 参数
	 * @return 实例对象
	 */
	T queryOne(T t);

	/**
	 * 查询
	 *
	 * @param t         条件
	 * @param condition 分页
	 * @return 列表
	 */
	Pager<T> queryAll(T t, Condition condition);

	/**
	 * 查询
	 *
	 * @param t        条件
	 * @param pageable 分页
	 * @return 列表
	 */
	@Deprecated
	default Pager<T> queryAll(T t, Pageable pageable) {
		return this.queryAll(t, (Condition) pageable);
	}

	/**
	 * 查询多条数据
	 *
	 * @param t      实例对象
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	default Pager<T> queryAll(T t, int offset, int limit) {
		return this.queryAll(t, Pageable.of(offset, limit));
	}

	/**
	 * 新增数据
	 *
	 * @param t 实例对象
	 * @return 实例对象
	 */
	T insert(T t);

	/**
	 * 修改数据
	 *
	 * @param t 实例对象
	 * @return 实例对象
	 */
	T update(T t);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	boolean deleteById(Long id);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	boolean deleteById(Object id);

	/**
	 * 查找行数
	 *
	 * @param t 实例对象
	 * @return 实例行数
	 */
	long count(T t);
}
