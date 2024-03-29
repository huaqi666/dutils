package com.wobangkj.api;

import com.alibaba.excel.context.AnalysisContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 复杂操作
 *
 * @author @cliod
 * @since 7/9/20 5:03 PM
 */
@Slf4j
public abstract class SyncReadProcessListener<T> extends SyncReadListener<T> {

	protected final static transient int maxSize = 500;
	private transient boolean isInitialize;

	public SyncReadProcessListener() {
		this(maxSize);
	}

	public SyncReadProcessListener(int initCapacity) {
		super(initCapacity);
	}

	@Override
	public final void invoke(T data, AnalysisContext context) {
		if (!this.isInitialize) {
			this.initialize(context);
			this.isInitialize = true;
		}
		if (!this.filter(data, context)) {
			return;
		}
		this.cache.add(data);
		if (this.condition(context)) {
			// 满足条件, 提前处理
			this.process(context);
		}
		// 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
		if (this.cache.size() >= this.getMax()) {
			this.process(context);
		}
	}

	@Override
	public final void doAfterAllAnalysed(AnalysisContext context) {
		try {
			this.process(context);
		} finally {
			this.finish(context);
		}
	}

	/**
	 * 初始化
	 *
	 * @param context
	 */
	protected void initialize(AnalysisContext context) {
	}

	/**
	 * 在添加之前进行筛选
	 *
	 * @param data    数据
	 * @param context 上下文内容
	 */
	protected boolean filter(T data, AnalysisContext context) {
		return true;
	}

	/**
	 * 满足的条件
	 *
	 * @param context 上下文内容
	 * @return 是否满足添加，不满足去除
	 */
	protected boolean condition(AnalysisContext context) {
		return false;
	}

	/**
	 * 最大存储数量
	 *
	 * @return 数量
	 */
	protected int getMax() {
		return maxSize;
	}

	/**
	 * 处理逻辑
	 */
	public final void process(AnalysisContext context) {
		// 之前
		this.before(context);
		// 处理
		this.doProcess(context);
		// 之后
		this.after(context);
	}

	/**
	 * 之前
	 *
	 * @param context 参数
	 */
	protected void before(AnalysisContext context) {
	}

	/**
	 * 真正处理
	 *
	 * @param context 参数
	 */
	protected abstract void doProcess(AnalysisContext context);

	/**
	 * 之后
	 *
	 * @param context 参数
	 */
	protected void after(AnalysisContext context) {
		this.cache.clear();
	}

	protected void finish(AnalysisContext context) {

	}
}
