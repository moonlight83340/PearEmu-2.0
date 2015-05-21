/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pearemu.network.realm;

import java.io.IOException;
import org.pearemu.commons.utils.Config;
import org.pearemu.network.MinaServer;

/**
 *
 * @author moonlight83340
 */
public class MinaRealm {
    public MinaRealm(){
        try{
            System.out.println("Lancement du Serveur...");
            MinaServer server = new MinaServer(Config.REALM_PORT);
            System.out.println("OK !");
        }
        catch(IOException e){
            System.out.println("Impossible d'accèder au serveur, au port : " + Config.REALM_PORT);
            System.exit(1);
        }
        
    }
}
