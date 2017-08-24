package com.jian.github.event;

/**
 * Description：eventBus事件类型
 */
public interface EventType {
    /**
     * 登录
     */
    String TYPE_LOGIN = "login";
    /**
     * 退出登录
     */
    String TYPE_LOGOUT = "logout";
}
