package top.rainrem.boot.common.exception;

import lombok.Getter;
import org.slf4j.helpers.MessageFormatter;
import top.rainrem.boot.common.result.IResultCode;

/**
 * 自定义业务异常
 *
 * @author LightRain
 * @since 2025年7月22日18:43:47
 */
@Getter
public class BusinessException extends RuntimeException{
    /**
     * 异常对应的结果码对象（包含错误码、错误消息等）
     */
    public IResultCode resultCode;

    /**
     * 根据业务错误码构造异常
     * @param errorCode 业务错误码
     */
    public BusinessException(IResultCode errorCode) {
        // 调用父类 RuntimeException 的构造方法，传入错误消息
        super(errorCode.getMsg());
        this.resultCode = errorCode;
    }

    /**
     * 根据业务错误码和自定义消息构造异常
     * @param errorCode 业务错误码
     * @param message 自定义错误消息
     */
    public BusinessException(IResultCode errorCode, String message) {
        // 调用父类 RuntimeException 的构造方法，传入自定义消息
        super(message);
        this.resultCode = errorCode;
    }

    /**
     * 根据自定义消息和根异常（Throwable）构造异常
     * @param message 自定义错误消息
     * @param cause 原始异常
     */
    public BusinessException(String message, Throwable cause) {
        // 调用父类 RuntimeException 的构造方法
        super(message, cause);
    }

    /**
     * 根据根异常（Throwable）构造异常
     * @param cause 原始异常
     */
    public BusinessException(Throwable cause) {
        // 调用父类 RuntimeException 的构造方法
        super(cause);
    }

    /**
     * 根据格式化的自定义消息构造异常
     * @param message 消息模板
     * @param args 模板参数
     */
    public BusinessException(String message, Object... args) {
        // 调用父类 RuntimeException 的构造方法，传入格式化后的消息
        super(formatMessage(message, args));
    }

    /**
     * 格式化消息工具方法
     * @param message 消息模板
     * @param args 模板参数
     * @return 格式化后的字符串
     */
    private static String formatMessage(String message, Object... args) {
        // 使用 slf4j 的 MessageFormatter 格式化参数
        return MessageFormatter.arrayFormat(message, args).getMessage();
    }

}
