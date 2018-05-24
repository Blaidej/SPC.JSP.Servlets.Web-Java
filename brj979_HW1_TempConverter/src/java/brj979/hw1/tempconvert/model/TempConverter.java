/*
 * Name: Blaine Jacques
 * CSCI2466 HW1: TempConverter
 */
package brj979.hw1.tempconvert.model;

import java.io.Serializable;//import added for implenting serializable

/**
 * This Temp converter Bean plugs into the TempEntry.html and sends the
 * converted temp to the TempConvertResult.jsp displaying the original temp,
 * original Temperature Unit and the conversion of each. It uses an enum 
 * TempUnit to represent F and C for Fahrenheit and Celsius.
 * @author Blaine Jacques
 */
//implements serializable
public class TempConverter implements Serializable
{

    // add properties and any helper methods
    private double origTemp; //Original Temperature
    private TempUnit origUnit; // Original Unit, Fahrenheit or Celsius
    private double convertedTemp; // Converted Temperature
    private TempUnit convertedUnit; // Converted Unit

    /**
     * Default constructor
     */
    public TempConverter() {

    }

    /**
     * Getter for the originally entered temperature
     * @return origTemp, the original user entered temperature
     */
    public double getOrigTemp() {
        return origTemp;
    }

    /**
     * Setter for the originally entered temperature
     * @param origTemp the temperature the user entered
     */
    public void setOrigTemp(double origTemp) {
        this.origTemp = origTemp;
    }

    /**
     * Getter for the originally entered Temperature unit
     * @return origUnit, the unit the user originally entered
     */
    public TempUnit getOrigUnit() {
        return origUnit;
    }

    /**
     * Setter for the originally entered Temperature unit
     * @param origUnit the unit the user originally entered
     */
    public void setOrigUnit(TempUnit origUnit) {
        this.origUnit = origUnit;
    }

    /**
     * Getter for the converted temperature
     * @return origTemp the temp that is converted
     */
    public double getConvertedTemp() {
        //origUnit is F then convert to Fahrenheit 
        //origUnit not F, then it's C, and convert to Celcius
        return origUnit == TempUnit.F ? ((origTemp - 32) * 5) / 9 : ((origTemp
                * 9) / 5) + 32;
    }

    /**
     * Getter for the converted Unit- Fahrenheit or Celsius
     * @return origUnit, the unit that is being converted to.
     */
    public TempUnit getConvertedUnit() { 
        //origUnit if original TempUnit is F then the unit is Fahrenheit 
        //origUnit if the TempUnit is not F, then the TempUnit is C
        origUnit = origUnit == TempUnit.F ? TempUnit.C : TempUnit.F;
        return origUnit;
    }

}
