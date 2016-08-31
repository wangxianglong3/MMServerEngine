package com.mm.engine.framework.entrance.code.net.netty;

import com.mm.engine.framework.entrance.code.net.NetPacket;
import com.mm.engine.framework.entrance.code.net.NetPacketImpl;
import com.mm.engine.framework.server.SysConstantDefine;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by a on 2016/8/29.
 */
public class DefaultNettyDecoder extends ByteToMessageDecoder {
    private static final int headSize = 4;
    int size = headSize;
    boolean isReadHead = false;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        int readAble =in.readableBytes();
        if (readAble < size) {
            return;
        }
        if(!isReadHead) {
            size = in.readInt();
            isReadHead = true;
            if(size>readAble - headSize){
                return;
            }
        }
        ByteBuf b = in.readBytes(size); // 这里有data
        byte[]  bbb = new byte[size];
        b.getBytes(0,bbb);
        // add之后好像in就被重置了
        ByteArrayInputStream bis = new ByteArrayInputStream(bbb);
        ObjectInputStream oin = new ObjectInputStream(bis);
        list.add(oin.readObject());
        oin.close();
        // 清理临时变量
        size = headSize;
        isReadHead = false;
    }
}