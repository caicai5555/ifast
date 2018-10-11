package com.missfresh.api.exception;

import com.missfresh.common.exception.IFastException;

/**
 * <pre>
 * API异常基类
 * </pre>
 * 
 * <small> 2018年4月19日 | caigl@missfresh.cn</small>
 */
public class MissfreshApiException extends IFastException {

    private static final long serialVersionUID = -4891641110275580161L;

    public MissfreshApiException() {
        super();
    }

    public MissfreshApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MissfreshApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissfreshApiException(String message) {
        super(message);
    }

    public MissfreshApiException(Throwable cause) {
        super(cause);
    }

}
