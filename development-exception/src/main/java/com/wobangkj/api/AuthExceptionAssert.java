package com.wobangkj.api;

import com.wobangkj.exception.AppException;
import com.wobangkj.exception.AuthorizeException;

import java.text.MessageFormat;

/**
 * 授权异常处理接口
 *
 * @author @cliod
 * @since 4/28/20 11:57 AM
 * package: com.wobangkj.api
 */
public interface AuthExceptionAssert extends EnumMsg, Assert {
    @Override
    default AppException newException(Object... args) {
        String msg = MessageFormat.format(this.getMsg(), args);
        return new AuthorizeException(this, msg);
    }

    @Override
    default AppException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMsg(), args);
        return new AuthorizeException(this, t, msg);
    }
}
