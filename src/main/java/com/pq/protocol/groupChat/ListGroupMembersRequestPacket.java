package com.pq.protocol.groupChat;

import com.pq.protocol.Packet;
import lombok.Data;

import static com.pq.protocol.command.Command.GROUP_MEMBERS_REQ;

/**
 * 打印群聊成员列表请求对象
 * @version 1.0
 * @author pengqi
 */
@Data
public class ListGroupMembersRequestPacket extends Packet {
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
        return GROUP_MEMBERS_REQ;
    }
}
