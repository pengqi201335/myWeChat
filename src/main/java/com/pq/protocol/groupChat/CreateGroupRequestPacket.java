package com.pq.protocol.groupChat;

import com.pq.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.pq.protocol.command.Command.GROUP_CREATE_REQUEST;

@Data
public class CreateGroupRequestPacket extends Packet {
    /**
     * 群组成员列表
     */
    private List<String> groupMemberIdList;

    /**
     * 群聊名称
     */
    private String groupName;

    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return GROUP_CREATE_REQUEST;
    }
}
