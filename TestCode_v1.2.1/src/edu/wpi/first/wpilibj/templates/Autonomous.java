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
    private int autonSelect;
    private double speed = -0.25;
    private driveTrain drive;
    private boolean stageOneComplete, stageTwoComplete, stageThreeComplete, stageFourComplete;
    private BallHandler shooter;
    public Autonomous(int autonSelect, driveTrain drive, BallHandler shooter)
    {
        stageOneComplete = stageTwoComplete = stageThreeComplete = stageFourComplete = false;
        this.autonSelect = autonSelect;
        this.drive = drive;
        this.shooter = shooter;
    }
    public void initialize()
    {
        drive.resetPositionTracking();
        drive.setShifter(false);
        shooter.setFrontRoller(true);
    }
    public void update()
    {
        if(!stageOneComplete)
        {
            stageOne();
        }
        else if(!stageTwoComplete)
        {
            stageTwo();
        }
        else if(!stageThreeComplete)
        {
            stageThree();
        }
        System.out.println("Stage one: "+stageOneComplete+" Stage two: "+stageTwoComplete+" Stage three: "+stageThreeComplete+" Encoders: "+drive.getDistance());
    }
    private void stageOne()
    {
        if(autonSelect == 1)
        {
            drive.straight(speed);
            if(drive.getDistance() > 16)
            {
                shooter.kick();
                stageOneComplete = true;
                shooter.reload();
            }
        }
        else if(autonSelect == 2)
        {

        }
        else if(autonSelect == 3)
        {

        }
    }
    private void stageTwo()
    {
        if(autonSelect == 1)
        {
            if(drive.getDistance() > 50)
            {
                shooter.kick();
                stageTwoComplete = true;
                shooter.reload();
            }
        }
        else if(autonSelect == 2)
        {

        }
        else if(autonSelect == 3)
        {

        }
    }
    private void stageThree()
    {
        if(autonSelect == 1)
        {
            if(drive.getDistance() > 87)
            {
                shooter.kick();
                if(drive.getDistance() > 92)
                {
                    drive.straight(0.0);
                    stageThreeComplete = true;
                }
            }
        }
        else if(autonSelect == 2)
        {

        }
        else if(autonSelect == 3)
        {

        }
    }
}
