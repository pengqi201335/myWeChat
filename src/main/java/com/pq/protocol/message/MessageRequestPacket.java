package com.pq.protocol.message;

import com.pq.protocol.Packet;
import lombok.Data;

import static com.pq.protocol.command.Command.MESSAGE_SEND;

/**
 * 消息发送对象
 * @version 1.0
 * @author pengqi
 */
@Data
public class MessageRequestPacket extends Packet {
    private String message;     //客户端发送的消息

    public MessageRequestPacket(String toUserID,String message){
        this.toUserID = toUserID;
        this.message = message;
    }

    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return MESSAGE_SEND;
    }

    /**
     * 消息接收方用户ID
     */
    private String toUserID;
}
