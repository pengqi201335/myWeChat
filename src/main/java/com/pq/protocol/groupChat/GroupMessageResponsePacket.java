package com.pq.protocol.groupChat;

import com.pq.protocol.Packet;
import lombok.Data;

import static com.pq.protocol.command.Command.GROUP_MESSAGE_SEND_RES;

/**
 * 群聊消息响应对象
 * @version 1.0
 * @author pengqi
 */
@Data
public class GroupMessageResponsePacket extends Packet {
    /**
     * 请求发送群聊消息是否成功
     */
    private boolean success;

    /**
     * 失败原因
     */
    private String reason;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 消息发送方用户ID
     */
    private String fromUserID;

    /**
     * 消息发送方用户名
     */
    private String fromUserName;

    /**
     * 群号
     */
    private String groupID;

    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_SEND_RES;
    }
}
