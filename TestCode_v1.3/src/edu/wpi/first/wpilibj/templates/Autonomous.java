/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;


/**
 *
 * @author Josh
 */
public class Autonomous
{
    private AutonAction[] Sequence;
    private int step;
    private boolean complete;
    public Autonomous()
    {
        step = 0;
    }
    void run()
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
}

interface AutonAction
{
    boolean isDone();
    void execute();
}