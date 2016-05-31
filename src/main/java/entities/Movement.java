/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author david
 */
public final class Movement implements Comparable<Movement>, Serializable {
    
    public static Double[][][] MANHATAN_CACHE = new Double[10][10][1000];
    private static ArrayList<Integer> VALUES;
    static{
        VALUES = new ArrayList<>();
        VALUES.add(-1);
        VALUES.add(0);
        VALUES.add(1); 
    }
    
    
    
    private Integer[][] taquin;
    private String path;
    private Movement previous;
    private int iCurrent;
    private int jCurrent;
    private int movement;
    private int moveCount;
    private int correctlyPlaced;
    private int exactTotalDistance;
    public int aTenerEnCuenta = 1;
    public int h;
    

    public Movement(Integer[][] Taquin, String path, Movement previous, int i, int j, int movement, int moveCount) throws Exception{
        this.taquin = new Integer[Taquin.length][Taquin.length];
        for (int k = 0; k < taquin.length; k++) {
            this.taquin[ k ] = Taquin[ k ].clone();
        }
        this.path = path;
        this.previous = previous;
        this.iCurrent = i;
        this.jCurrent = j;
        this.movement = movement;
        this.moveCount = moveCount;
        this.exactTotalDistance = countExactTotalDistance();
        this.correctlyPlaced = countCorrectlyPlaced();
        h = correctlyPlaced > exactTotalDistance ? correctlyPlaced : exactTotalDistance;
    }

    
    public int countExactTotalDistance(){
        if( this.previous == null ){
            int count = 0;
            double totalDistance = 0;
            double distance = 0.0;
            for (int i = 0; i < taquin.length; i++) {
                for (int j = 0; j < this.taquin.length ; j++) {
                    count++;
                    Integer value = taquin[ i ][ j ];
                    if( value != null ){
                           distance = this.getPositionDistance( taquin[ i ][ j ], i , j );
                           totalDistance+= distance;
                    }
                }
 
            }
            return (int)totalDistance;
        }else{
            int previousI = previous.getI();
            int previousJ = previous.getJ();
            
            // recalcular el movimiento de la pieza vacia
            double distance = previous.getExactTotalDistance() - getPositionDistance( taquin[ previousI ][ previousJ ],  iCurrent, jCurrent );
            distance += getPositionDistance( taquin[ previousI ][ previousJ ], previousI, previousJ );
            // agregarle a la distancia el de la negra
            // le resto como estaba el negro
            //distance -= getPositionDistance( previous.getTaquin()[ previousI ][ previousJ ] , previousI, previousJ );
            //distance += getPositionDistance( taquin[ iCurrent ][ jCurrent ],  iCurrent, jCurrent );
            
            
            int currentRow = 0;
            int error = 0,  mRow;
            Integer value = 0;
            /*
            for (int i = 0; i < this.taquin.length ; i++) {
                error = 0;
                for (int j = 0; j < taquin.length; j++) {
                    value = taquin[ i ][ j ];
                    if( value != null ) {
                        mRow = value / taquin.length;
                         if( mRow == currentRow && getPositionDistance(value , i , j ) != 0.0 ){
                             error += 1; 
                         }  
                    }
                }
                if( error > 1 ){
                    distance += error - 1;
                }
                currentRow++;
            }
*/
            return (int) distance;
        }
    }
    
    public double getPositionDistance( Integer number , int i , int j ){
        
        if( MANHATAN_CACHE[ i ][ j ][ number ] != null ){
              return MANHATAN_CACHE[ i ][ j ][ number ];
        }
        int count = 0;
        int jR = 0;
        int iR = 0;
        for(int x = 0; x < taquin.length ; x++ ) {
            for (int y = 0; y < taquin.length ; y++ ) {
                count++;
                if( count == number ){
                    iR = x;
                    jR = y;
                }
            }
        }
        MANHATAN_CACHE[ i ][ j ][ number ] = (double) Math.abs( i - iR ) + Math.abs( jR - j );
        return MANHATAN_CACHE[ i ][ j ][ number ];
    }
    
    
    public Integer[][] getTaquin() {
        return taquin;
    }

    public void setTaquin(Integer[][] Taquin) {
        this.taquin = Taquin;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Movement getPrevious() {
        return previous;
    }

    public void setPrevious(Movement previous) {
        this.previous = previous;
    }

    public int getI() {
        return iCurrent;
    }

    public void setI(int i) {
        this.iCurrent = i;
    }

    public int getJ() {
        return jCurrent;
    }

    public void setJ(int j) {
        this.jCurrent = j;
    }

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
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

    @Override
    public int compareTo(Movement o) {
        //System.out.println("Llama al compare");
        if( o == null) return 1;
        Integer first = (int) (Math.pow( exactTotalDistance + correctlyPlaced , 1.5)  + moveCount);
        Integer second = (int)(Math.pow( o.getExactTotalDistance() + o.getCorrectlyPlaced() , 1.5)  + o.getMoveCount() );
        int manhattan = first.compareTo(second);
        //Collections.shuffle( VALUES );
        
        return manhattan != 0 ? manhattan : new Integer( correctlyPlaced ).compareTo( o.getCorrectlyPlaced( )); //
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Movement) {
            Movement other = (Movement) obj;
            for (int i = 0; i < taquin.length; i++) {
                for (int j = 0; j < taquin.length; j++) {
                    if (taquin[i][j] != null && !taquin[i][j].equals(other.getTaquin()[i][j])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Arrays.deepHashCode(this.taquin);
        return hash;
    }

    private int countCorrectlyPlaced() throws IOException {
        if( this.previous == null ){
        int count = 0;
            for(int x = 0; x < taquin.length ; x++ ) {
                for (int y = 0; y < taquin.length ; y++ ) {
                    if( taquin[ x ][ y ] != null && getPositionDistance( taquin[ x ][ y ], x, y) != 0 ){
                        count++;
                    }
                }
            }
            return count;
        }else{
            int previousI = previous.getI(); // ahora en esta posicion esta el numero
            int previousJ = previous.getJ();
            
            // en el i y j acutal estaba antes
            boolean antesEstabaBien = getPositionDistance( previous.getTaquin()[ iCurrent ][ jCurrent ] , iCurrent, jCurrent ) == 0;
            boolean ahoraEstaBien = getPositionDistance( taquin[ previousI ][ previousJ ] , previousI, previousJ ) == 0;
            
            int cambio = 0;
            if( antesEstabaBien ){
                cambio = 1;
            }
            if( ahoraEstaBien ){
                cambio = -1;
            }
            return previous.getCorrectlyPlaced() + cambio;
        }
    }

    
    
    
}
