# __myWeChat__
####`一个基于netty的仿微信IM即时通讯系统，具有用户登录、退出登录，登录鉴权，用户一对一单聊、创建群聊，群聊成员管理（入群、退群、打印群聊成员列表），群内成员聊天等功能。包含自定义通信协议、对象的序列化与反序列化、编解码器、客户端与服务端Session管理等通用功能模块。基于netty的异步-事件驱动线程模型，提供高性能的网络通信，同时依赖于netty的优秀设计，将网络通信与业务逻辑解耦。`
## __Netty__
Java中的`NIO`和`BIO`是对操作系统层面的IO模型的封装，而Netty进一步地，是对Java的原生BIO及NIO的封装，并提供了大量易用的IO读写API以及工具类，通过`channelHandler`和`EventLoopGroup`等设计将网络通信和业务逻辑处理解耦，使用起来非常方便。
## __服务端和客户端的启动__
服务端和客户端分别通过`ServerBootStrap`和`BootStrap`类来启动，启动类通过链式调用在应用程序启动时进行以下配置：
* 线程模型(EventLoopGroup)
* IO模型(BIO/NIO)
* 管道属性
* 业务逻辑处理器(ChannelHandler)

配置完成后，客户端通过调用`connect()`方法与远程节点进行连接，并创建一个`Channel`实例，服务端则通过调用`bind()`方法监听指定的端口，并创建一个`ServerChannel`实例，该实例用于接收连接，并创建子Channel实例，子Channel实例则用于处理具体的业务逻辑。
## ByteBuf
Java中的阻塞IO(BIO)是面向字节流来进行读写操作的，而非阻塞IO(NIO)则是面向缓冲区来进行读写操作的，即读写的数据先放在缓冲区中，对数据进行批量的读取与发送，Java原生的NIO通过`ByteBuffer`类实现该功能，而Netty中则通过`ByteBuf`实现（实际上也是对ByteBuffer进行了封装）。ByteBuf底层又分为堆内存和直接内存，分别对应用户空间和内核空间，后者可以实现“零拷贝”。
ByteBuf通过提供读写指针将分配的内存分为`“可丢弃字段”`、`“可读段”`和`“可写段”`，进而控制二进制数据的读写。
## __自定义通信协议与编解码器__
要实现客户端与服务端通信，必须自定义`通信协议`，即规定字节流的什么位置代表什么含义，本项目的自定义协议格式如下所示：

`魔数(4字节) |版本号(1字节)   |序列化算法(1字节) |指令(1字节)    |数据长度(4)字节  |数据`

编码器将Java对象序列化为二进制数据之后，再组装其它协议字段，然后一起写入缓冲区，发送到网络中。解码器则是从缓冲区取出数据包，根据其它字段反序列化出对应的Java对象，再交由业务逻辑处理。
## __Netty拆包粘包__
由于底层的TCP协议是面向字节流的，可能导致发送方的ByteBuf与接收方的ByteBuf不对等：
* 收到的数据不完整，称为“拆包”（或半包）现象
* 收到的数据长度大于原始数据长度，称为“粘包”现象

拆包器可以解决上述问题，即拆包器可以将从缓冲区读取到的数据拆分成一个一个完整的数据包，再交由解码器解码。

Netty中常用的拆包器有`FixedLengthFrameDecoder`(固定长度拆包器)/`LineBasedFrameDecoder`(行拆包器，即使用换行符分隔数据包)/`DelimiterBasedFrameDecoder`(分隔符拆包器)/`LengthFieldBasedFrameDecoder`(长度域拆包器)
## __ChannelPipeline与ChannelHandler__
Netty使用了责任链模式来处理事件，即每个Channel与一个ChannelPipeline绑定，而每个ChannelPipeline上注册了若干个ChannelHandler，互相连接组成了一个逻辑处理链，事件在这条链上传递，直到遇到能处理该事件的handler，若当前handler不能处理该事件，就将其传递至下一个handler。入站事件和出站事件的传递方向是相反的，且无状态的handler可以在不同的channel之间共享，但必须加上`@Sharable`注解。
## __基于EventLoop的高效线程模型__
Netty的线程模型是`Reactor模式`的一个变种，在服务端，通常是一个`BossEventGroup`和一个`WorkerEventGroup`，前者通常是一个单线程的EventLoop，该EventLoop注册了一个`Selector`，不断轮询连接事件，然后将accept到的`SocketChannel`交由`WorkerEventGroup`处理，而后再选择一个EventLoop，将该channel注册到其维护的Selector上并对后续IO事件进行处理。

而在客户端，只有一个`WorkerEventGroup`管理其EventLoop，这个EventLoop负责处理客户端的IO事件，事实上，只是起到一个分发的作用，真正的处理是在ChannelHandler中。

对一些耗时比较长的事件来说，使用单线程的EventLoop来处理的效率是很低的，因为线程会一直阻塞在此，一般的做法是创建一个线程池，将耗时的操作交给子线程来执行，然后`异步返回`执行结果，需要通过添加监听回调的方式来判断是否执行完成。
