package com.wobangkj.api;

import java.util.HashMap;

/**
 * 枚举类型
 *
 * @author cliod
 * @date 2019/12/27
 * package : com.wobangkj.api
 */
public interface EnumType extends Session {

    /**
     * 获取code
     *
     * @return code
     */
    Integer getCode();

    /**
     * 获取描述
     *
     * @return 描述
     */
    String getDesc();

    /**
     * 枚举名字
     *
     * @return 枚举
     */
    String name();

    /**
     * 序列化,转成字符串
     *
     * @return Json
     */
    @Override
    String toString();

    /**
     * 转化为对象(默认Map)
     *
     * @return map
     */
    @Override
    default Object toObject() {
        return new HashMap<String, Object>(4) {{
            put("code", getCode());
            put("desc", getDesc());
        }};
    }

    /**
     * 序列化,转成Json
     *
     * @return Json
     */
    @Override
    default String toJson() {
        return "{" +
                "code: " + this.getCode() +
                ", desc: \"" + this.getDesc() + '\"' +
                '}';
    }
}
