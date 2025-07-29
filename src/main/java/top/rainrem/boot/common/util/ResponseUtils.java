package top.rainrem.boot.common.util;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import top.rainrem.boot.common.result.Result;
import top.rainrem.boot.common.result.ResultCode;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 处理响应的工具类
 * @author LightRain
 * @since 2025/7/22
 */
@Slf4j
public class ResponseUtils {


    /**
     * 异常消息返回(适用于过滤器中处理异常响应)
     * @param response HttpServletResponse
     * @param resultCode 响应结果码
     * @param message 消息内容
     */
    public static void writeErrMsg(HttpServletResponse response, ResultCode resultCode){
        // 获取Http状态
        int status = getHttpStatus(resultCode);
        // 设置响应状态
        response.setStatus(status);
        // 设置内容类型
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 设置字符集编码
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // 获取响应编码器
        try(PrintWriter writer = response.getWriter()) {
            // 将失败结果转成JSON
            String jsonResponse = JSONUtil.toJsonStr(Result.failed(resultCode));
            // 写入数据
            writer.print(jsonResponse);
            // 确保将响应内容写入到输出流
            writer.flush();
        } catch (IOException e) {
            log.error("响应异常处理失败", e);
        }

    }

    /**
     * 异常消息返回(适用于过滤器中处理异常响应)
     * @param response HttpServletResponse
     * @param resultCode 响应结果码
     * @param message 消息内容
     */
    public static void writeErrMsg(HttpServletResponse response, ResultCode resultCode,String message){
        // 获取Http状态
        int status = getHttpStatus(resultCode);
        // 设置响应状态
        response.setStatus(status);
        // 设置内容类型
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 设置字符集编码
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // 获取响应编码器
        try(PrintWriter writer = response.getWriter()) {
            // 将失败结果转成JSON
            String jsonResponse = JSONUtil.toJsonStr(Result.failed(resultCode, message));
            // 写入数据
            writer.print(jsonResponse);
            // 确保将响应内容写入到输出流
            writer.flush();
        } catch (IOException e) {
            log.error("响应异常处理失败", e);
        }

    }

    /**
     * 根据结果码获取Http状态码
     * @param resultCode 结果码
     * @return Http状态码
     */
    private static  int getHttpStatus(ResultCode resultCode){
        return switch (resultCode){
            // 设置授权失败到安全框架
            case ACCESS_UNAUTHORIZED,ACCESS_TOKEN_INVALID,REFRESH_TOKEN_INVALID -> HttpStatus.UNAUTHORIZED.value();
            // 默认返回错误请求
            default -> HttpStatus.BAD_REQUEST.value();
        };
    }
}
