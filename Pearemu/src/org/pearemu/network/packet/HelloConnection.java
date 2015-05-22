/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pearemu.network.packet;

/**
 *
 * @author moonlight83340
 */
public class HelloConnection implements Packet{
    private String key;
    
    public HelloConnection setKey(String key){
        this.key = key;
        return this;
    }
    
    @Override
    public String buil() {
       return "HC" + key;
    }
    // not use this at time
}
