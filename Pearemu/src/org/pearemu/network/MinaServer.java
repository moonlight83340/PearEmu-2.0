/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pearemu.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.pearemu.commons.utils.Constants;
/**
 *
 * @author moonlight83340
 */
public class MinaServer {
    final static private int BUFFER = 512;
    
    protected NioSocketAcceptor acceptor = new NioSocketAcceptor();
    
    public MinaServer(int port) throws IOException{
        acceptor.getSessionConfig().setReadBufferSize(BUFFER);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, Constants.DECO_TIME);
        acceptor.setHandler(new MinaServerIoHandlerAdapter());
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ),"\0",
                "\n\0")));
        acceptor.bind(new InetSocketAddress(port));
    }
}
