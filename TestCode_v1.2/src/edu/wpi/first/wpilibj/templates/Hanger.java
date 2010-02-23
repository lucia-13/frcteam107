/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author joshuf26
 */
public class Hanger
{
    public Victor hangerMotor1, hangerMotor2;
    public Hanger()
    {
        hangerMotor1 = new Victor(Connections.HangerMotorChannel1);
        hangerMotor2 = new Victor(Connections.HangerMotorChannel2);
    }
    public void set(boolean up)
    {
        if(up)
        {
        }
        else
        {

        }
    }
}
