package com.pq.protocol.groupChat;

import com.pq.protocol.Packet;
import com.pq.session.Session;
import lombok.Data;

import java.util.List;

import static com.pq.protocol.command.Command.GROUP_MEMBERS_RES;

/**
 * 打印群聊列表的响应对象
 * @version 1.0
 * @author pengqi
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {
    /**
     * 获取群聊成员列表是否成功的标志
     */
    private boolean success;

    /**
     * 群号
     */
    private String groupID;

    /**
     * 群聊中的channel列表
     */
    private List<Session> sessions;

    /**
     * 拉取群成员列表失败原因
     */
    private String reason;

    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return GROUP_MEMBERS_RES;
    }
}
