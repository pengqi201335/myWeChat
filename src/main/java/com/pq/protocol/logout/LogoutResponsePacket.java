package com.pq.protocol.logout;

import com.pq.protocol.Packet;
import lombok.Data;

import static com.pq.protocol.command.Command.LOGOUT_RES;

/**
 * 注销登录响应对象
 * @version 1.0
 * @author pengqi
 */
@Data
public class LogoutResponsePacket extends Packet {
    /**
     * 注销登录是否成功的标志
     */
    private boolean success;

    /**
     * 注销账号用户名
     */
    private String userID;

    /**
     * 注销账号用户ID
     */
    private String userName;

    /**
     * 注销登录失败的原因
     */
    private String reason;

    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return LOGOUT_RES;
    }
}
