package com.pq.protocol.groupChat;

import com.pq.protocol.Packet;
import lombok.Data;

import static com.pq.protocol.command.Command.GROUP_JOIN_RESPONSE;

/**
 * 申请加入群聊命令的响应对象
 * @version 1.0
 * @author pengqi
 */
@Data
public class JoinGroupResponsePacket extends Packet {
    /**
     * 申请加群请求是否成功的标志
     */
    private boolean success;

    /**
     * 用户申请加入的群号
     */
    private String groupID;

    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return GROUP_JOIN_RESPONSE;
    }
}
