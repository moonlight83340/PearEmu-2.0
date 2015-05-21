/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pearemu.commons.utils;

/**
 *
 * @author moonlight83340
 */
public class StringTools {
    public static String strRand(int size, char[] letters){
        StringBuilder sb = new StringBuilder(size);
        for(int i = 0; i < size; ++i){
            sb.append(letters[Tools.rand(letters.length)]);
        }
        return sb.toString();
    }
    public static String strRand(int size){
        return strRand(size, new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'});
    }
}
