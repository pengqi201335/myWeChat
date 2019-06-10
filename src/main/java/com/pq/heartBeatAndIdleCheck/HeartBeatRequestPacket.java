package com.pq.heartBeatAndIdleCheck;

import com.pq.protocol.Packet;
import lombok.Data;

import static com.pq.protocol.command.Command.HEARTBEAT_REQUEST;

/**
 * 心跳检测命令对象
 * @version 2.0
 * @author pengqi
 */
@Data
public class HeartBeatRequestPacket extends Packet {
    /**
     * 连接依然存活的标志
     */
    private String aliveSignal;

    /**
     * 获取数据包中的指令
     *
     * @return 指令号
     */
    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }

    public HeartBeatRequestPacket(String aliveSignal){
        this.aliveSignal = aliveSignal;
    }
}
