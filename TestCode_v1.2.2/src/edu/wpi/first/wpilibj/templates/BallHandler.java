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
    private boolean reloading, firing;
    public Servo firingServo;
    public DigitalInput limitSwitch;
    private java.util.Timer controlLoop;
    public double period;
    private Victor frontRoller;
    private double servoLatched, servoOpen;
    private static BallHandler instance = new BallHandler();
    protected BallHandler()
    {
        kickerIn = new Solenoid(Connections.SolenoidModuleSlot, Connections.KickerInChannel);
        kickerOut = new Solenoid(Connections.SolenoidModuleSlot, Connections.KickerOutChannel);
        firingServo = new Servo(Connections.FiringServoChannel);
        limitSwitch = new DigitalInput(Connections.LimitSwitchChannel);
        servoLatched = 1.0;
        servoOpen = 0.0;
        firingServo.set(servoLatched);
        reloading = false;
        period = 0.02;
        controlLoop = new java.util.Timer();
        controlLoop.schedule(new UpdateTask(this), 0L, (long) (period*1000));
        frontRoller = new Victor(Connections.FrontRollerChannel);
        pullIn();
    }
    public static BallHandler getInstance()
    {
        return BallHandler.instance;
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
                //System.out.println("pushing out...");
            }
            else
            {
                //System.out.println("limit switch triggered, pulling in...");
                pullIn();
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
            //System.out.println("fire button pressed!");
            kickerOut.set(false);
            firing = true;
        }
    }
    public void reload()
    {
        //System.out.println("reload button pressed!");
        reloading = true;
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
