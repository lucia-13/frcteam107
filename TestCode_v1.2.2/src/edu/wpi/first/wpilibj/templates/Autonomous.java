/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import java.util.Vector;


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
    protected Autonomous()
    {
        step = 0;
    }
    public static Autonomous getInstance()
    {
        return Autonomous.instance;
    }
    public void initialize()
    {
        Sequence = new Vector();
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
                if(action.isDone())
                {
                    step++;
                }
            }
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
                return true;
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
                return true;
            }
            public void execute()
            {
                BallHandler.getInstance().reload();
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
}

interface AutonAction
{
    boolean isDone();
    void execute();
}