package com.pq.protocol.groupChat;

import com.pq.protocol.Packet;
import lombok.Data;

import static com.pq.protocol.command.Command.GROUP_MESSAGE_SEND_REQ;

/**
 * 发送群聊消息请求对象
 * @version 1.0
 * @author pengqi
 */
@Data
public class GroupMessageRequestPacket extends Packet {
    /**
     * 群号
     */
    private String groupID;

    /**
     * 群聊消息
     */
    private String message;

    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_SEND_REQ;
    }
}
