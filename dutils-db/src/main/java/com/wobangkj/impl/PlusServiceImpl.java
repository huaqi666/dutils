package com.wobangkj.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wobangkj.api.IPlusMapper;
import com.wobangkj.api.Serializer;
import com.wobangkj.bean.Pager;
import com.wobangkj.domain.*;
import com.wobangkj.utils.BeanUtils;
import com.wobangkj.utils.RefUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

/**
 * 默认实现
 *
 * @author cliod
 * @since 11/24/20 1:44 PM
 */
public class PlusServiceImpl<M extends BaseMapper<T>, T> extends com.wobangkj.api.ServiceImpl<T> implements com.wobangkj.api.IService<T> {

	protected IService<T> service;

	public PlusServiceImpl(IService<T> service) {
		this.service = service;
	}

	protected PlusServiceImpl() {
	}

	/**
	 * 获取Dao
	 *
	 * @return 通用dao
	 */
	@Override
	public IPlusMapper<T> getDao() {
		return PlusProvider.apply(this.service.getBaseMapper());
	}

	/**
	 * 设置service
	 *
	 * @param service service对象
	 */
	@Resource
	public void setService(IService<T> service) {
		this.service = service;
	}

	/**
	 * 设置service
	 *
	 * @param service service对象
	 */
	public void setService(M service) throws ReflectiveOperationException {
		this.service = new ServiceImpl<>();
		RefUtils.setFieldValue(this.service, "baseMapper", service);
	}

	/**
	 * 新增数据
	 *
	 * @param t 实例对象
	 * @return 实例对象
	 */
	@Override
	public T insert(T t) {
		this.service.save(t);
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
		return this.service.removeById(id);
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public boolean deleteById(Object id) {
		if (id instanceof Serializer) {
			return this.service.removeById((Serializable) id);
		}
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		return this.service.removeByMap(param);
	}

	/**
	 * 查询
	 *
	 * @param t         条件
	 * @param condition 分页
	 * @return 列表
	 */
	@Override
	@SneakyThrows
	public Pager<T> queryAll(T t, Condition condition) {
		Page<T> page = new Page<>(condition.getMybatisPage(), condition.getLimit());
		// 排序
		if (StringUtils.isNotEmpty(condition.getOrder())) {
			String[] orders = condition.getOrder().split(",");
			for (String order : orders) {
				String[] f = order.split(" ");
				if (f.length == 1) {
					page.addOrder(OrderItem.asc(f[0]));
				} else if (f.length > 1) {
					String ff = f[1].trim();
					if (ff.toLowerCase(Locale.ROOT).equals("desc")) {
						page.addOrder(OrderItem.desc(ff));
					} else {
						page.addOrder(OrderItem.asc(ff));
					}
				}
			}
		} else {
			page.addOrder(OrderItem.desc("id"));
		}
		QueryWrapper<T> wrapper = new QueryWrapper<>(t);
		// 模糊匹配
		if (StringUtils.isNotEmpty(condition.getKey())) {
			if (Objects.isNull(this.fieldCache)) {
				this.fieldCache = Columns.of(t.getClass());
			}
			for (String column : this.fieldCache.getColumns()) {
				wrapper.or().like(column, condition.getLikeKey());
			}
		}
		// 关键词匹配
		Map<String, Object> fieldValues = RefUtils.getFieldValues(t);
		if (!BeanUtils.isEmpty(fieldValues)) {
			if (Objects.isNull(this.fieldCache)) {
				this.fieldCache = Columns.of(t.getClass());
			}
			for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
				if (Arrays.asList(this.fieldCache.getColumns()).contains(entry.getKey()) && Objects.nonNull(entry.getValue())) {
					wrapper.eq(entry.getKey(), entry.getValue());
				}
			}
		}
		if (!BeanUtils.isEmpty(condition.getMatch())) {
			wrapper.allEq(condition.getMatch());
		}
		// 范围匹配
		if (!BeanUtils.isEmpty(condition.getAmong())) {
			for (Among<?> among : condition.getAmong()) {
				if (among instanceof DateAmong) {
					if (Objects.isNull(among.getCeiling())) {
						wrapper.ge(among.getColumn(), ((DateAmong) among).getDateFloor());
					} else if (Objects.isNull(among.getFloor())) {
						wrapper.lt(among.getColumn(), ((DateAmong) among).getDateCeiling());
					} else {
						wrapper.between(among.getColumn(), ((DateAmong) among).getDateFloor(), ((DateAmong) among).getDateCeiling());
					}
					continue;
				}
				if (Objects.isNull(among.getCeiling())) {
					wrapper.ge(among.getColumn(), among.getFloor());
				} else if (Objects.isNull(among.getFloor())) {
					wrapper.lt(among.getColumn(), among.getCeiling());
				} else {
					wrapper.between(among.getColumn(), among.getFloor(), among.getCeiling());
				}
			}
		}
		// 原生条件，会有sql注入的风险
		if (!BeanUtils.isEmpty(condition.getQueries())) {
			for (Query query : condition.getQueries()) {
				if (query.getRelated().equals("or")) {
					wrapper.or().apply(query.getQuery());
				} else {
					wrapper.apply(query.getQuery());
				}
			}
		}
		Page<T> res = this.service.page(page.addOrder(OrderItem.desc("id")), wrapper);
		return Pager.of(res.getTotal(), condition, res.getRecords());
	}
}
