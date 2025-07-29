package top.rainrem.boot.system.service;

/**
 * WebSocket服务接口
 * <p>
 * 提供与WebSocket连接管理相关的功能，包括：
 * - 用户连接/断开事件处理
 * - 字典数据变更通知
 * - 系统消息推送
 * </p>
 *
 * @author LightRain
 * @since 2025年7月26日19:43:38
 */
public interface WebSocketService {

    /**
     * 处理用户连接事件
     *
     * @param username  用户名
     * @param sessionId WebSocket会话ID
     */
    void userConnected(String username, String sessionId);

    /**
     * 处理用户断开连接事件
     *
     * @param username 用户名
     */
    void userDisconnected(String username);

    /**
     * 发送系统通知给特定用户
     *
     * @param username 目标用户名
     * @param message  通知消息内容
     */
    void sendNotification(String username, Object message);
}
