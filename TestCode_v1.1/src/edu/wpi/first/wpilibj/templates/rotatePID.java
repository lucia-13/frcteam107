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
public class rotatePID implements PIDOutput
{
    private double value;
    driveTrain drive;
    public rotatePID(driveTrain drive)
    {
        this.drive = drive;
    }
    public void pidWrite(double output)
    {
        value = output;
    }
    public void updateDrive()
    {
        drive.rotate(value);
    }
}
