package com.pq.protocol.groupChat;

import com.pq.protocol.Packet;
import lombok.Data;

import static com.pq.protocol.command.Command.GROUP_EXIT_REQUEST;

/**
 * 申请退出群聊的命令对象
 * @version 1.0
 * @author pengqi
 */
@Data
public class ExitGroupRequestPacket extends Packet {
    private String groupID;

    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return GROUP_EXIT_REQUEST;
    }
}
