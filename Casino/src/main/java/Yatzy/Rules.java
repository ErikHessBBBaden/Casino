/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Yatzy;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Erik Hess
 */
public class Rules {

    private int subresult = 0;
    private int total = 0;
    private int uppertotal = 0;
    private int lowertotal = 0;
    private int uppercounter = 0;
    private int bonusuppertotal = 0;
    private int lowercounter = 0;
    private boolean upperbool = false;
    private boolean lowerbool = false;

    
    public int createSubresult(String label, ArrayList<Dice> dicearray) {
        subresult = 0;
        HashMap<Integer, Integer> dicemap = new HashMap<>();
        for (int i = 1; i < 7; i++) {
            dicemap.put(i, 0);
        }
        for (Dice d : dicearray) {
            if (dicemap.containsKey(d.getValue())) {
                dicemap.put(d.getValue(), dicemap.get(d.getValue()) + 1);
            } else {
                dicemap.put(d.getValue(), 1);
            }
        }
        
        switch(label) {
            
            case "EINER": 
                subresult = dicemap.get(1);  
                uppertotal += subresult;
                uppercounter++;
                break;
            case "ZWEIER":
                subresult = 2 * dicemap.get(2);
                uppertotal += subresult;
                uppercounter++;
                break;
            case "DREIER":
                subresult = 3 * dicemap.get(3);
                uppertotal += subresult;
                uppercounter++;
                break;    
            case "VIERER":
                subresult = 4 * dicemap.get(4);
                uppertotal += subresult;
                uppercounter++;
                break;   
            case "FÜNFER":
                subresult = 5 * dicemap.get(5);
                uppertotal += subresult;
                uppercounter++;
                break;
            case "SECHSER":
                subresult = 6 * dicemap.get(6);
                uppertotal += subresult;
                uppercounter++;
                break;
            case "DREI GLEICHE":
                for(int i = 1; i < 7; i++){
                    if(dicemap.get(i) == 3 || dicemap.get(i) == 4 || dicemap.get(i) == 5) {
                        subresult = i * 3;
                    }
                } 
                lowertotal += subresult;
                lowercounter++;
                break;
            case "VIER GLEICHE":
                for(int i = 1; i < 7; i++){
                    if(dicemap.get(i) == 4 || dicemap.get(i) == 5) {
                        subresult = i * 4;                      
                    }
                } 
                lowertotal += subresult;
                lowercounter++;
                break;
            case "FULL HOUSE":
                int re;
                int sult;
                for(int i = 1; i < 7; i++){
                    if(dicemap.get(i) == 3) {
                        System.out.println("i = " + i);
                        System.out.println("Dice = " + dicemap.get(i));
                        re = i * 3;
                        System.out.println("re = " + re);
                        for(int j = 1; j < 7; j++){
                            if(dicemap.get(j) == 2) {
                                sult = j * 2;
                                System.out.println("sult = " + sult);
                                subresult = re + sult;
                            }
                        }                
                    }
                } 
                lowertotal += subresult;
                lowercounter++;
                break;
            case "KLEINE STRASSE":
                if(dicemap.get(1) == 1 && dicemap.get(2) == 1 && dicemap.get(3) == 1 && dicemap.get(4) == 1 && dicemap.get(5) == 1)
                {
                    subresult = 15;
                }
                lowertotal += subresult;
                lowercounter++;
                break;
            case "GROSSE STRASSE":
                if(dicemap.get(2) == 1 && dicemap.get(3) == 1 && dicemap.get(4) == 1 && dicemap.get(5) == 1 && dicemap.get(6) == 1)
                {
                    subresult = 20;
                }
                lowertotal += subresult;
                lowercounter++;
                break;
            case "YATZY":
                for (int i = 1; i < 7; i++) {
                    if(dicemap.get(i) == 5) {
                        subresult = 50;
                    }
                }
                lowertotal += subresult;
                lowercounter++;
                break;
            case "CHANCE":
                for (int i = 0; i < 5; i++) {
                    subresult = subresult + dicearray.get(i).getValue();
                }
                lowertotal += subresult;
                lowercounter++;
                break;              
        }
        return subresult;
    }
    
    public void totalChecker() {       
        if(uppercounter == 6) {
            upperbool = true;
            if(uppertotal >= 63) {
                bonusuppertotal = uppertotal + 35;
            }
            else {
                bonusuppertotal = uppertotal;
            }
        } 
        if(lowercounter == 7) {
            lowerbool = true;
        }
        
        if(upperbool == true && lowerbool == true) {
            total = lowertotal + bonusuppertotal;
        }
    }
    
    public void checkwin() {
        
    }

    public int getUppertotal() {
        return uppertotal;
    }

    public int getLowertotal() {
        return lowertotal;
    }

    public int getBonusUppertotal() {
        return bonusuppertotal;
    }

    public boolean isUpperbool() {
        return upperbool;
    }

    public boolean isLowerbool() {
        return lowerbool;
    }

    public int getTotal() {
        return total;
    }

    public void setUppercounter(int uppercounter) {
        this.uppercounter = uppercounter;
    }

    public void setLowercounter(int lowercounter) {
        this.lowercounter = lowercounter;
    }

    public void setUpperbool(boolean upperbool) {
        this.upperbool = upperbool;
    }

    public void setLowerbool(boolean lowerbool) {
        this.lowerbool = lowerbool;
    }

    public void setUppertotal(int uppertotal) {
        this.uppertotal = uppertotal;
    }

    public void setLowertotal(int lowertotal) {
        this.lowertotal = lowertotal;
    }
    
    
    
    
    
    
    

}
