package com.pq.protocol.groupChat;

import com.pq.protocol.Packet;
import lombok.Data;

import static com.pq.protocol.command.Command.GROUP_JOIN_REQUEST;

/**
 * 申请加入群聊的命令对象
 */
@Data
public class JoinGroupRequestPacket extends Packet {
    /**
     * 申请加入的群聊ID
     */
    private String groupID;

    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return GROUP_JOIN_REQUEST;
    }
}
