/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pearemu.network;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author moonlight83340
 */
public class MinaServerIoHandlerAdapter extends IoHandlerAdapter{

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
       System.out.println("Received << " + (String)message);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
          session.close(true);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("Sent >> " + (String)message);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("New session created");
    }
 
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
       System.out.println( "IDLE " + session.getIdleCount( status ));
    }
    
}
