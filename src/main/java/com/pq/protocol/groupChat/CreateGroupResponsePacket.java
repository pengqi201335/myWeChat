package com.pq.protocol.groupChat;

import com.pq.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.pq.protocol.command.Command.GROUP_CREATE_RESPONSE;

/**
 * 创建群组命令响应对象
 * @version 1.0
 * @author pengqi
 */
@Data
public class CreateGroupResponsePacket extends Packet {
    /**
     * 请求处理是否成功标志
     */
    private Boolean success;

    /**
     * 群组ID
     */
    private String groupID;

    /**
     * 群聊成员名列表
     */
    private List<String> groupUserNameList;

    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return GROUP_CREATE_RESPONSE;
    }
}
