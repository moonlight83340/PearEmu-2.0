/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pearemu.network.realm;

import org.apache.mina.core.session.IoSession;
import org.pearemu.commons.utils.Constants;

/**
 *
 * @author moonlight83340
 */
public enum MinaRealmPacketEnum {
    /**
     * Packet contenant la hash key
     */
    HELLO_CONNECTION("HC"),
    /**
     * en cas de version différente de celle de l'ému, envoit la version requise
     */
    REQUIRE_VERSION("AlEv", Constants.DOFUS_VER),
    /**
     * Erreur d'authentification (login / pass incorrect)
     */
    LOGIN_ERROR("AlEf"),
    /**
     * Connexion interdite (erreur quelconque)
     */
    ACCESS_DENIED("AlE"),
    /**
     * compte déjà connecté au serveur
     */
    LOGIN_ALREADY_CONNECTED("AlEc"),
    /**
     * Envoyé au compte déconnecté
     */
    LOGIN_DISCONNECT("AlEd"),
    /**
     * Envoit le pseudo (après login)
     */
    PSEUDO("Ad"),
    /**
     * Accès à la communité
     */
    COMMUNITY("Ac", "0"),
    /**
     * Liste des serveurs ([id];[statu];[charge];[connexion possible?])
     */
    HOSTS_LIST("AH", "1;1;110;1"),
    /**
     * Login confirmé Paramètre : admin level
     */
    LOGIN_OK("AlK"),
    /**
     * Question secrète
     */
    QUESTION("AQ"),
    /**
     * Sélection du serveur de jeu (avant co au game)
     */
    SELECT_SERVER("AYK"),
    /**
     * Sélection du serveur avec ip crypté (dofus 1.19)
     */
    SELECT_SERVER_CRYPT("AXK"),
    SERVER_LIST("AxK31536000000|1,2");
    private String packet;
    private String param;

    MinaRealmPacketEnum(String packet) {
        this.packet = packet;
        this.param = "";
    }

    MinaRealmPacketEnum(String packet, String param) {
        this.packet = packet;
        this.param = param;
    }

    /**
     * Envoit le packet avec des parametres
     *
     * @param session
     * @param param
     */
    public void send(IoSession session, String param) {
        if(session != null)
            session.write(packet + param);
    }
    
    public void send(IoSession session){
        send(session, param);
    }
}
