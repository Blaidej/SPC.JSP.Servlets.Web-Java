/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author CompSciStudent
 */
public class vowelBean implements Serializable{

    
    private int count;

   // ArrayList<String> vowelCount = new ArrayList<>();
   //private char[] vowelChar = new char[] {'a','i','o','u','e'};
      
    
    public vowelBean() {
        
    }
//    public vowelBean(vowel) {
//        this.vowel = vowel;
//    }
    
    public int getVowelCount(String vowel) {
        
        
        char[] charArray = vowel.toCharArray();
       
        
        for(int j=0 ; j < vowel.length(); j++)
        {
            
            if(charArray[j] == 'a')
                count += 1;
            if(charArray[j] == 'i')
                count += 1;
            if(charArray[j] == 'o')
                count += 1;
            if(charArray[j] == 'u')
                count += 1;
            if(charArray[j] == 'e')
                count += 1;

        }
            
        
        return count;
    }
    
    
}
