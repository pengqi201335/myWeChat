package com.pq.codec;

import com.pq.protocol.Packet;
import com.pq.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 用于编码Java对象的Handler
 * @version 1.0
 * @author pengqi
 */
@Deprecated
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    /**
     * 将Packet类型对象编码
     * @param channelHandlerContext ChannelPipeline链节点对象
     * @param packet 待编码Java对象
     * @param byteBuf 编码得到的二进制数据
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) {
        PacketCodec.INSTANCE.encode(byteBuf,packet);
    }
}
