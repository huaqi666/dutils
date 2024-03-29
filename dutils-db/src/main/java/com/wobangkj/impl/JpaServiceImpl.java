package com.wobangkj.impl;

import com.wobangkj.api.IRepository;
import com.wobangkj.api.IService;
import com.wobangkj.api.ServiceImpl;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Condition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.NumberUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 默认实现
 *
 * @author cliod
 * @since 9/4/20 11:07 AM
 */
public class JpaServiceImpl<D extends JpaRepository<T, Long>, T> extends ServiceImpl<T> implements IService<T> {

	protected IRepository<T> dao;

	public JpaServiceImpl(D dao) {
		this.dao = JpaProvider.apply(dao);
	}

	protected JpaServiceImpl() {
	}

	/**
	 * 获取Dao
	 *
	 * @return 通用dao
	 */
	@Override
	public IRepository<T> getDao() {
		return this.dao;
	}

	/**
	 * 设置repository
	 *
	 * @param repository jpa对象
	 */
	@Resource
	public void setDao(JpaRepository<T, Long> repository) {
		this.dao = JpaProvider.apply(repository);
	}

	/**
	 * 获取Dao
	 */
	@Resource
	public void setDao(IRepository<T> dao) {
		this.dao = dao;
	}

	/**
	 * 修改数据
	 *
	 * @param t 实例对象
	 * @return 实例对象
	 */
	@Override
	public T insert(T t) {
		this.dao.insert(t);
		return t;
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public boolean deleteById(Long id) {
		this.dao.deleteById(id);
		return true;
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public boolean deleteById(Object id) {
		long key;
		if (id instanceof Number) {
			key = ((Number) id).longValue();
		} else if (id instanceof CharSequence) {
			String str = id.toString();
			key = (NumberUtils.parseNumber(str, Number.class)).longValue();
		} else {
			throw new IllegalArgumentException("参数类型不匹配");
		}
		this.getDao().deleteById(key);
		return true;
	}

	/**
	 * 查询
	 *
	 * @param t         条件
	 * @param condition 分页
	 * @return 列表
	 */
	@Override
	public Pager<T> queryAll(T t, Condition condition) {
		List<Order> orders = new ArrayList<>();
		// 排序
		if (StringUtils.isNotEmpty(condition.getOrder())) {
			String[] orderStr = condition.getOrder().split(",");
			for (String order : orderStr) {
				String[] f = order.split(" ");
				if (f.length == 1) {
					orders.add(Order.asc(f[0]));
				} else if (f.length > 1) {
					String ff = f[1].trim();
					if (ff.toLowerCase(Locale.ROOT).equals("desc")) {
						orders.add(Order.desc(ff));
					} else {
						orders.add(Order.asc(ff));
					}
				}
			}
		} else {
			orders.add(Order.desc("id"));
		}
		Sort sort = Sort.by(orders);
		Example<T> example = Example.of(t);
		Page<T> page = this.dao.findAll(example, PageRequest.of(condition.getJpaPage(), condition.getSize(), sort));
		return Pager.of(page.getTotalElements(), condition, page.getContent());
	}
}
