/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import java.util.Vector;
import java.util.Random;

/**
 *
 * @author joshuf26
 */
public class PositiveCriticismAssistant
{
    private static Vector theWeapons;
    private static Random electroMagneticQuantamNumericSymbolGenerator;
    public PositiveCriticismAssistant()
    {
        theWeapons = new Vector();
        electroMagneticQuantamNumericSymbolGenerator = new Random();
        theWeapons.addElement("The problem is software, of course.");
        theWeapons.addElement("I wish we could hang ourselves...");
        theWeapons.addElement("BRAD IS WRONG!!!");
        theWeapons.addElement("It's Kieks' fault!");
        theWeapons.addElement("OMG IT'S ALIVE!!!");
        theWeapons.addElement("You mean a human foot?");
        theWeapons.addElement("Science has recently proven the existance of steam in a box.");
        theWeapons.addElement("Chuck Norris was here!");
        theWeapons.addElement("LabView is epitomy of evil, and it burns in LabView hell.");
        theWeapons.addElement("Looking like a foo with your robot upside-down!");
        theWeapons.addElement("Zach, if you can read this, you're doing your job!");
        theWeapons.addElement("");
    }
    static String criticizePositively()
    {
        int middleMan = electroMagneticQuantamNumericSymbolGenerator.nextInt(theWeapons.size());
        try
        {
            return (String) theWeapons.elementAt(middleMan);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            return "OMGWTFBBQ THE PROBLEM ACTUALLY IS THE CODE THIS TIME! not joking ... tell Josh error 1";
        }
        catch(ClassCastException e)
        {
            return "OMGWTFBBQ THE PROBLEM ACTUALLY IS THE CODE THIS TIME! not joking ... tell Josh error 2";
        }
    }
}
