package com.pq.codec;

import com.pq.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 继承拆包器，重写decode方法，过滤非自定义协议请求
 * @version 1.0
 * @author pengqi
 */
public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter(){
        super(Integer.MAX_VALUE,LENGTH_FIELD_OFFSET,LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception{
        //非本协议的数据包直接拒绝接收，并关闭对应的连接
        if(in.getInt(in.readerIndex())!= PacketCodec.MAGIC_NUMBER){
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx,in);
    }
}
