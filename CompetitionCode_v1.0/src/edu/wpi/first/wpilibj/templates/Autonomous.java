/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import java.util.Vector;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author joshuf26
 */
public class Autonomous
{
    private Vector Sequence;
    private int step;
    private boolean complete;
    public static double speed = -0.25;
    private static Autonomous instance = new Autonomous();
    Timer timer;

    protected Autonomous()
    {
        step = 0;
        timer = new Timer();
    }
    public static Autonomous getInstance()
    {
        return Autonomous.instance;
    }
    public void initialize()
    {
        Sequence = new Vector();
        DriveTrain.getInstance().resetPositionTracking();
        timer.start();
    }
    public void update()
    {
        if(!complete)
        {
            AutonAction action;
            try
            {
                action = (AutonAction) Sequence.elementAt(step);
            }
            catch(ClassCastException e)
            {
                action = null;
                step++;
            }
            if(action != null)
            {
                action.execute();
                //System.out.println("Auton Stage: "+step);
                if(action.isDone())
                {
                    step++;
                    System.out.println(timer.get()+"     "+"step: "+step);
                }
            }
        }
        else
        {
            //System.out.println("Autonomous Completed.");
        }
        if(step >= Sequence.size())
        {
            complete = true;
        }
        else
        {
            complete = false;
        }
    }
    public void goStraightUntil(final double distance)
    {
        Sequence.addElement(new AutonAction()
        {
            public boolean isDone()
            {
                //System.out.println(DriveTrain.getInstance().getDistance());
                if(DriveTrain.getInstance().getDistance() > distance)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            public void execute()
            {
                DriveTrain.getInstance().straight(Autonomous.speed);
            }
        });
    }
    public void goStraight()
    {
        Sequence.addElement(new AutonAction()
        {
            public boolean isDone()
            {
                return true;
            }
            public void execute()
            {
                DriveTrain.getInstance().straight(Autonomous.speed);
            }
        });
    }
    public void stopMoving()
    {
        Sequence.addElement(new AutonAction()
        {
            public boolean isDone()
            {
                return true;
            }
            public void execute()
            {
                DriveTrain.getInstance().straight(0.0);
            }
        });
    }
    public void kick()
    {
        Sequence.addElement(new AutonAction()
        {
            public boolean isDone()
            {
                if(BallHandler.getInstance().limitSwitch.get())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            public void execute()
            {
                BallHandler.getInstance().kick();
            }
        });
    }
    public void reload()
    {
        Sequence.addElement(new AutonAction()
        {
            public boolean isDone()
            {
                return !BallHandler.getInstance().limitSwitch.get();
            }
            public void execute()
            {
                BallHandler.getInstance().reload(true);
            }
        });
    }
    public void wait(final int numberOfCycles)
    {
        Sequence.addElement(new AutonAction()
        {
            int cyclesCompleted = 0;
            public boolean isDone()
            {
                if(cyclesCompleted >= numberOfCycles)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            public void execute()
            {
                cyclesCompleted++;
            }
        });
    }
    public void setRoller(final boolean state)
    {
        Sequence.addElement(new AutonAction()
        {
            int cyclesCompleted = 0;
            public boolean isDone()
            {
                return true;
            }
            public void execute()
            {
                BallHandler.getInstance().setFrontRoller(state);
            }
        });
    }
}

interface AutonAction
{
    boolean isDone();
    void execute();
}