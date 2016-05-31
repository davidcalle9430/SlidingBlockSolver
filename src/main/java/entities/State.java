/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

/**
 *
 * @author david
 */
public class State implements Serializable{
    private int iCurrent;
    private int jCurrent;
    private int correctlyPlaced;
    private int exactTotalDistance;



    public State(int iCurrent, int jCurrent, int correctlyPlaced, int exactTotalDistance) {
        this.iCurrent = iCurrent;
        this.jCurrent = jCurrent;
        this.correctlyPlaced = correctlyPlaced;
        this.exactTotalDistance = exactTotalDistance;
    }
    
    
    
    public int getiCurrent() {
        return iCurrent;
    }

    public void setiCurrent(int iCurrent) {
        this.iCurrent = iCurrent;
    }

    public int getjCurrent() {
        return jCurrent;
    }

    public void setjCurrent(int jCurrent) {
        this.jCurrent = jCurrent;
    }

    public int getCorrectlyPlaced() {
        return correctlyPlaced;
    }

    public void setCorrectlyPlaced(int correctlyPlaced) {
        this.correctlyPlaced = correctlyPlaced;
    }

    public int getExactTotalDistance() {
        return exactTotalDistance;
    }

    public void setExactTotalDistance(int exactTotalDistance) {
        this.exactTotalDistance = exactTotalDistance;
    }
    
    
}
