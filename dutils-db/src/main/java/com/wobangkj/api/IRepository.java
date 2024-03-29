package com.wobangkj.api;

import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Condition;
import com.wobangkj.domain.Pageable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * 通用Jpa, SpringBootJpa实现
 *
 * @author cliod
 * @since 9/16/20 9:23 AM
 */
public interface IRepository<T> extends IDao<T>, JpaRepository<T, Long> {
	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	default T queryById(Long id) {
		return this.findById(id).orElse(null);
	}

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	default T queryById(Object id) {
		long key;
		if (id instanceof Number) {
			key = ((Number) id).longValue();
		} else if (id instanceof CharSequence) {
			if (!StringUtils.isNumeric((CharSequence) id)) {
				throw new IllegalArgumentException("参数类型不匹配");
			}
			key = Long.parseLong(id.toString());
		} else {
			throw new IllegalArgumentException("参数类型不匹配");
		}
		return this.getOne(key);
	}

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t 实例对象
	 * @return 对象列表
	 */
	@Override
	default T queryOne(T t) {
		return this.findOne(Example.of(t)).orElse(null);
	}

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t 实例对象
	 * @return 对象列表
	 */
	@Override
	default List<T> queryAll(T t) {
		return this.findAll(Example.of(t), Sort.by("id").descending());
	}

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t      实例对象
	 * @param offset 分页
	 * @param limit  分页
	 * @return 对象列表
	 */
	@Override
	default List<T> queryAllLimit(T t, int offset, int limit) {
		Pageable pageable = Pageable.of(offset, limit);
		return this.queryAllPage(t, pageable).getData();
	}

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t         实例对象
	 * @param condition 分页
	 * @return 对象列表
	 */
	@Override
	default Pager<T> queryAllPage(T t, Condition condition) {
		Page<T> page = this.findAll(Example.of(t), PageRequest.of(condition.getJpaPage(), condition.getSize()));
		return Pager.of(page.getTotalElements(), condition, page.getContent());
	}

	/**
	 * 修改数据
	 *
	 * @param t 实例对象
	 * @return 影响行数
	 */
	@Override
	default int update(T t) {
		this.save(t);
		return 1;
	}

	/**
	 * 添加数据
	 *
	 * @param t 实例对象
	 * @return 影响行数
	 */
	default int insert(T t) {
		this.save(t);
		return 1;
	}

	/**
	 * 查找个数
	 *
	 * @param t 对象
	 * @return 行数
	 */
	@Override
	default long count(T t) {
		return this.count(Example.of(t));
	}

}
