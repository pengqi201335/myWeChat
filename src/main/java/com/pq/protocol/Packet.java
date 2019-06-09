package com.pq.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 数据包抽象对象
 * @version 1.0
 */
@Data
public abstract class Packet {
    /**
     * 协议版本号
     */
    @JSONField(deserialize = false,serialize = false)
    private Byte version = 1;

    /**
     * 获取数据包中的指令
     * @return 指令号
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
