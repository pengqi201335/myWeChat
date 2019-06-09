package com.pq.protocol.groupChat;

import com.pq.protocol.Packet;
import lombok.Data;

import static com.pq.protocol.command.Command.GROUP_EXIT_RESPONSE;

/**
 * 申请退出群聊命令的响应对象
 * @version 1.0
 * @author pengqi
 */
@Data
public class ExitGroupResponsePacket extends Packet {
    /**
     * 退群申请是否成功的标志
     */
    private boolean success;

    /**
     * 退群申请失败原因
     */
    private String reason;

    /**
     * 申请退出的群号
     */
    private String groupID;

    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return GROUP_EXIT_RESPONSE;
    }
}
