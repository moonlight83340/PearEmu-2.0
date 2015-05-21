/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pearemu.network.realm;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.pearemu.commons.utils.Constants;
import org.pearemu.commons.utils.StringTools;

/**
 *
 * @author moonlight83340
 */
public class MinaRealmIoHandlerAdapter extends IoHandlerAdapter{
     @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
       System.out.println("Received << " + (String)message);
       String packet = ((String)message).trim();
       
       if (packet.length() == 0)
           return;
       
       System.out.println("Received << " + packet);
       int  numPacket = session.containsAttribute(1) ? (int)session.getAttribute(1) : 1;
        
        switch(numPacket){
            case 1 : 
                if(!packet.equals(Constants.DOFUS_VER)){
                    MinaRealmPacketEnum.REQUIRE_VERSION.send(session);
                    session.close(true);
                }
            break;  
        }
        session.setAttribute(1,numPacket+1);
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
       session.close(true);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        System.out.println("Logout");
        MinaRealmPacketEnum.HELLO_CONNECTION.send(session, StringTools.strRand(32));
    }
    
}
