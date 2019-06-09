package com.pq.protocol;

import com.pq.protocol.groupChat.*;
import com.pq.protocol.login.LogInRequestPacket;
import com.pq.protocol.login.LogInResponsePacket;
import com.pq.protocol.logout.LogoutRequestPacket;
import com.pq.protocol.logout.LogoutResponsePacket;
import com.pq.protocol.message.MessageRequestPacket;
import com.pq.protocol.message.MessageResponsePacket;
import com.pq.serializer.JsonSerializer;
import com.pq.serializer.Serializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static com.pq.protocol.command.Command.*;

/**
 * 编解码工具类
 * @version 1.0
 * @author pengqi
 */
public class PacketCodec {
    public static final int MAGIC_NUMBER = 0x12345678; //数据包文件魔数
    private static Map<Byte,Class<? extends Packet>> packetTypeMap;   //存储数据包类型与对应对象类型的map
    private static Map<Byte, Serializer> serializeMap; //存储序列化算法与对应序列化接口实现的map
    public static final PacketCodec INSTANCE = new PacketCodec();

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQ, LogInRequestPacket.class);                     //登录请求指令
        packetTypeMap.put(LOGIN_RES, LogInResponsePacket.class);                    //登录响应指令
        packetTypeMap.put(MESSAGE_SEND, MessageRequestPacket.class);                //消息发送指令
        packetTypeMap.put(MESSAGE_RECV, MessageResponsePacket.class);               //消息接收指令
        packetTypeMap.put(GROUP_CREATE_REQUEST, CreateGroupRequestPacket.class);    //创建群聊请求指令
        packetTypeMap.put(GROUP_CREATE_RESPONSE, CreateGroupResponsePacket.class);  //创建群聊响应指令
        packetTypeMap.put(GROUP_JOIN_REQUEST, JoinGroupRequestPacket.class);        //申请入群指令
        packetTypeMap.put(GROUP_JOIN_RESPONSE, JoinGroupResponsePacket.class);      //申请入群响应指令
        packetTypeMap.put(GROUP_EXIT_REQUEST, ExitGroupRequestPacket.class);        //申请退群指令
        packetTypeMap.put(GROUP_EXIT_RESPONSE,ExitGroupResponsePacket.class);       //申请退群响应指令
        packetTypeMap.put(LOGOUT_REQ, LogoutRequestPacket.class);                   //注销登录指令
        packetTypeMap.put(LOGOUT_RES, LogoutResponsePacket.class);                  //注销登录响应指令
        packetTypeMap.put(GROUP_MEMBERS_REQ,ListGroupMembersRequestPacket.class);   //打印群组成员列表指令
        packetTypeMap.put(GROUP_MEMBERS_RES,ListGroupMembersResponsePacket.class);  //打印群组成员列表请求响应指令
        packetTypeMap.put(GROUP_MESSAGE_SEND_REQ,GroupMessageRequestPacket.class);  //发送群聊消息请求指令
        packetTypeMap.put(GROUP_MESSAGE_SEND_RES,GroupMessageResponsePacket.class); //响应发送群聊消息请求指令

        serializeMap = new HashMap<>();
        Serializer serializer = new JsonSerializer();
        serializeMap.put(serializer.getSerializerAlgorithm(),serializer);
    }

    /**
     * 编码方法
     * @param packet 待编码对象
     * @param buf 为编码对象分配的内存对象
     */
    public void encode(ByteBuf buf,Packet packet){
        //序列化packet对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        buf.writeInt(MAGIC_NUMBER);                     //写魔数（4字节）
        buf.writeByte(packet.getVersion());             //写协议版本号（1字节）
        buf.writeByte(Serializer.DEFAULT_ALGORITHM);    //写序列化算法标识（1字节）
        buf.writeByte(packet.getCommand());             //写指令标识（1字节）
        buf.writeInt(bytes.length);                     //写数据长度（4字节）
        buf.writeBytes(bytes);                          //写数据

    }

    /**
     * 解码方法
     * @param buf 待解码ByteBuf对象
     * @return Packet对象
     */
    public Packet decode(ByteBuf buf){
        //跳过魔数
        buf.skipBytes(4);
        //跳过版本号
        buf.skipBytes(1);
        //获取序列化算法标识，根据此标识选择特定的反序列化算法
        Byte serializer = buf.readByte();
        //获取指令标识，用于获取目标对象的类类型，进而反序列化
        Byte command = buf.readByte();
        //数据长度
        int len = buf.readInt();
        byte[] bytes = new byte[len];
        //将序列化数据从buf中写入字节数组
        buf.readBytes(bytes);

        //根据序列化算法标识得到对应的序列化工具
        Serializer serializer1Algorithm = getSerialize(serializer);
        //根据指令标识得到对应的指令对象
        Class<? extends Packet> requestType = getRequestType(command);

        if(serializer1Algorithm!=null && requestType!=null){
            //反序列化
            return serializer1Algorithm.deSerialize(requestType,bytes);
        }

        return null;
    }

    //根据序列化算法标识选择特定的反序列化算法
    private Serializer getSerialize(Byte serializer){
        return serializeMap.get(serializer);
    }

    //根据指令类型标识获取对应的目标对象
    private Class<? extends Packet> getRequestType(Byte req){
        return packetTypeMap.get(req);
    }
}
