package com.pq.protocol.message;

import com.pq.protocol.Packet;
import lombok.Data;

import static com.pq.protocol.command.Command.MESSAGE_RECV;

/**
 * 消息接收对象
 */
@Data
public class MessageResponsePacket extends Packet {
    private String message;     //接收消息
    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return MESSAGE_RECV;
    }

    /**
     * 消息发送方用户ID
     */
    private String fromUserID;

    /**
     * 消息发送方用户名
     */
    private String fromUserName;
}
