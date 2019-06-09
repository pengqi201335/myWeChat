package com.pq.attribute;

import com.pq.session.Session;
import io.netty.util.AttributeKey;

/**
 * 服务端创建的属性集合
 * @version 1.0
 * @author pengqi
 */
public interface Attributes {
    /**
     * 登录标识的键对象
     */
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    /**
     * 添加在channel上的session属性
     */
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}
