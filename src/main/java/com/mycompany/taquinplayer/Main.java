/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.taquinplayer;

import entities.Player;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            
            Integer[][] t = {
                {1, 2, 9 ,10},
                {13 ,15, 7 ,6},
                {3 ,5 ,8, 11},
                { 4, 14 ,12, null}
            };
            
            /*Integer[][] t = {
                {5, 12, 20 ,2,14},
                {23 , 22,11 ,13,21},
                {6 ,9 ,1, 4,10},
                { 19, 24 , 15 , 7,17 },
                { 8, 18, 3, 16, null }
            };*/
            //1 2 9 10 13 15 7 6 3 5 8 11 4 14 12 0
            Player p = new Player("nana", 4, 1, 1, "DCD", 3, 3);
            p.setTaquin( t );
            System.out.println("Empieza");
            p.solve();
            System.out.println("termina");
            System.out.println( );
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
