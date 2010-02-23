/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author joshuf26
 */
public class Autonomous
{
    private AutonAction[] Sequence;
    private int step;
    private int numberFilled;
    private boolean complete;
    public static double speed = -0.25;
    private static Autonomous instance = new Autonomous();
    protected Autonomous()
    {
        step = 0;
        numberFilled = 0;
    }
    public static Autonomous getInstance()
    {
        return Autonomous.instance;
    }
    public void initialize(int numberOfTasks)
    {
        Sequence = new AutonAction[numberOfTasks];
    }
    public void update()
    {
        if(!complete)
        {
            Sequence[step].execute();
            if(Sequence[step].isDone())
            {
                step++;
            }
        }
        if(step >= Sequence.length)
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
        Sequence[numberFilled] = new AutonAction()
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
        };
        numberFilled++;
    }
    public void goStraight()
    {
        Sequence[numberFilled] = new AutonAction()
        {
            public boolean isDone()
            {
                return true;
            }
            public void execute()
            {
                DriveTrain.getInstance().straight(Autonomous.speed);
            }
        };
        numberFilled++;
    }
    public void stopMoving()
    {
        Sequence[numberFilled] = new AutonAction()
        {
            public boolean isDone()
            {
                return true;
            }
            public void execute()
            {
                DriveTrain.getInstance().straight(0.0);
            }
        };
        numberFilled++;
    }
    public void kick()
    {
        Sequence[numberFilled] = new AutonAction()
        {
            public boolean isDone()
            {
                return true;
            }
            public void execute()
            {
                BallHandler.getInstance().kick();
            }
        };
        numberFilled++;
    }
    public void reload()
    {
        Sequence[numberFilled] = new AutonAction()
        {
            public boolean isDone()
            {
                return true;
            }
            public void execute()
            {
                BallHandler.getInstance().reload();
            }
        };
        numberFilled++;
    }
    public void wait(final int numberOfCycles)
    {
        Sequence[numberFilled] = new AutonAction()
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
        };
        numberFilled++;
    }
}

interface AutonAction
{
    boolean isDone();
    void execute();
}