/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.templates.CAN.*;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author Josh
 */
public class Jaguar implements SpeedController
{
    private CANJaguar CANJag;
    private edu.wpi.first.wpilibj.Jaguar PWMJag;
    private boolean CANControlled, brake;
    public Jaguar(int channel, boolean CANControlled)
    {
        this.CANControlled = CANControlled;
        if(CANControlled)
        {
            CANJag = new CANJaguar(channel);
            CANJag.configNeutralMode(CANJaguar.NeutralMode.kCoast);
        }
        else
        {
            PWMJag = new edu.wpi.first.wpilibj.Jaguar(channel);
        }
        brake = false;
    }
    public Jaguar(int PWMChannel, int CANChannel, boolean CANControlled)
    {
        PWMJag = new edu.wpi.first.wpilibj.Jaguar(PWMChannel);
        CANJag = new CANJaguar(CANChannel);
        CANJag.configNeutralMode(CANJaguar.NeutralMode.kCoast);
        this.CANControlled = CANControlled;
        brake = false;
    }
    public double getTemp()
    {
        if(CANControlled)
        {
            return CANJag.getTemperature();
        }
        else
        {
            throw new IllegalStateException();
        }
    }
    public void setBrake(boolean state)
    {
        if(CANControlled)
        {
            if(state)
            {
                CANJag.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            }
            else
            {
                CANJag.configNeutralMode(CANJaguar.NeutralMode.kCoast);
            }
        }
        else
        {
            throw new IllegalStateException();
        }
    }
    public void set(double speed)
    {
        if(CANControlled)
        {
            CANJag.set(speed);
        }
        else
        {
            PWMJag.set(speed);
        }
    }
    public double get()
    {
        if(CANControlled)
        {
            return CANJag.get();
        }
        else
        {
            return PWMJag.get();
        }
    }
    public void pidWrite(double output)
    {
        set(output);
    }
}
