/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.DigitalInput;
import java.util.TimerTask;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author joshuf26
 */
public class BallHandler
{
    private Solenoid kickerIn, kickerOut;
    private boolean reloading, fireFast, firing;
    public Servo firingServo;
    public DigitalInput limitSwitch;
    private java.util.Timer controlLoop;
    public double period;
    private Victor frontRoller;
    private double servoLatched, servoOpen;
    public BallHandler()
    {
        kickerIn = new Solenoid(Connections.SolenoidModuleSlot, Connections.KickerInChannel);
        kickerOut = new Solenoid(Connections.SolenoidModuleSlot, Connections.KickerOutChannel);
        firingServo = new Servo(Connections.FiringServoChannel);
        limitSwitch = new DigitalInput(Connections.LimitSwitchChannel);
        servoLatched = 1.0;
        servoOpen = 0.0;
        firingServo.set(servoLatched);
        reloading = false;
        fireFast = true;
        period = 0.02;
        controlLoop = new java.util.Timer();
        controlLoop.schedule(new UpdateTask(this), 0L, (long) (period*1000));
        frontRoller = new Victor(Connections.FrontRollerChannel);
        pullIn();
    }
    private class UpdateTask extends TimerTask
    {
        private BallHandler handler;
        public UpdateTask(BallHandler handler)
        {
            if(handler == null)
                throw new NullPointerException("Given BallHandler was null");
            this.handler = handler;
        }
        public void run()
        {
            handler.update();
        }
    }
    public void update()
    {
        if(reloading)
        {
            if(limitSwitch.get())
            {
                pushOut();
            }
            else
            {
                if(fireFast)
                {
                    pullIn();
                }
                reloading = false;
            }
        }
        if(limitSwitch.get())
        {
            firingServo.set(servoLatched);
        }
        if(firing)
        {
            if(!limitSwitch.get())
            {
                firingServo.set(servoOpen);
            }
            else
            {
                firing = false;
            }
        }
    }
    public void kick()
    {
        if(!reloading)
        {
            kickerOut.set(false);
            firing = true;
        }
    }
    public void reload(boolean fireFast)
    {
        reloading = true;
        this.fireFast = fireFast;
    }
    public void pullIn()
    {
        kickerIn.set(true);
        kickerOut.set(false);
    }
    public void pushOut()
    {
        kickerIn.set(false);
        kickerOut.set(true);
    }
    public void free() //DON'T CALL UNLESS NECESSARY
    {
        controlLoop.cancel();
        controlLoop = null;
    }
    public void setFrontRoller(boolean rollerOn)
    {
        if (rollerOn)
        {
            frontRoller.set(1.0);
        }
        else
        {
            frontRoller.set(0.0);
        }
    }
}
