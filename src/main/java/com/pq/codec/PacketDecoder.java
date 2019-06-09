package com.pq.codec;

import com.pq.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 用于解码二进制数据的ChannelHandler
 * @version 1.0
 * @author pengqi
 */
public class PacketDecoder extends ByteToMessageDecoder {
    /**
     * 将ByteBuf对象解码
     * @param channelHandlerContext ChannelPipeline链节点对象
     * @param byteBuf 待解码的ByteBuf对象
     * @param list 用于将解码后的Java对象传递给下一个handler
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list){
        list.add(PacketCodec.INSTANCE.decode(byteBuf));
    }
}
