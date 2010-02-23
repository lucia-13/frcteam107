/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 *
 * @author Josh
 */
public class SoftPID implements PIDOutput
{
    private double value;
    public SoftPID()
    {
        value = 0;
    }
    public void pidWrite(double output)
    {
        value = output;
    }
    public double getValue()
    {
        return value;
    }
}
