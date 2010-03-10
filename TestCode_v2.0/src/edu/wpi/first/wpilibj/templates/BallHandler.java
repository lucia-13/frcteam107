/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import java.util.TimerTask;



/**
 *
 * @author joshuf26
 */
public class BallHandler
{
    private Solenoid kickerIn, kickerOut;
    private boolean reloading, firing, manualOverride, fastKick;
    public Servo firingServo;
    public DigitalInput limitSwitch;
    private java.util.Timer controlLoop;
    public double period;
    private Victor frontRoller;
    private double servoLatched, servoOpen, rollerSpeed, rollerIdle;
    private static BallHandler instance = new BallHandler();
    private int reloadTimer;
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
        reloadTimer = 0;
        manualOverride = false;
        fastKick = true;
        rollerSpeed = 1.0;
        rollerIdle = -0.25;
        setFrontRoller(false);
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
        if(!manualOverride)
        {
            if(reloading)
            {
                if(limitSwitch.get())
                {
                    pushOut();
                    //System.out.println("pushing out...");
                    reloadTimer = 0;
                }
                else
                {
                    if(reloadTimer > 20)
                    {
                        if(fastKick)
                        {
                            pullIn();
                        }
                        else
                        {
//                            kickerOut.set(true);
//                            kickerIn.set(true);
                        }
                        reloading = false;
                    }
                    reloadTimer++;
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
                    //System.out.println("Done Firing.");
                }
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
    public void reload(boolean fastKick)
    {
        //System.out.println("reload button pressed!");
        this.fastKick = fastKick;
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
            frontRoller.set(rollerSpeed);
        }
        else
        {
            frontRoller.set(rollerIdle);
        }
    }
    public void toggleFrontRoller()
    {
        if(frontRoller.get() == rollerSpeed)
        {
            frontRoller.set(rollerIdle);
        }
        else
        {
            frontRoller.set(rollerSpeed);
        }
    }
    public void setOverride(boolean state)
    {
        manualOverride = state;
    }
    public void overrideOut()
    {
        if(manualOverride)
        {
            pushOut();
        }
    }
    public void overrideIn()
    {
        if(manualOverride)
        {
            pullIn();
        }
    }
    public void overrideServo(double value)
    {
        if(manualOverride)
        {
            firingServo.set(value);
        }
    }
    public void setRollerSpeed(double rollerSpeed)
    {
        this.rollerSpeed = rollerSpeed;
    }
}
