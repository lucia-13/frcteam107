/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.
 *
 *
 *
 *
 * We've made it to the big 2.0! We're at the point where we know for a fact
 * the code works, now all we need to do is to add key features, like the
 * hanger and (hopefully) support for CAN Motor Control.
 *
 * Also, hopes are to get a functional, usable dashboard working. Maybe that's
 * next revision. We'll see.
 * 
 */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Team107Robot extends IterativeRobot
{
    private Joystick leftStick, rightStick, thirdStick;
    private DriveTrain drive;
    private Timer time;
    private Compressor compressor;
    private BallHandler kicker;
    private Hanger hanger;
    private Autonomous auton;
    boolean rollerLock;
    private int disabledStep;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        getWatchdog().feed();
        leftStick = new Joystick(Connections.LeftJoystickChannel);
        rightStick = new Joystick(Connections.RightJoystickChannel);
        thirdStick = new Joystick(Connections.ThirdJoystickChannel);
        time = new Timer();
        time.start();
        drive = DriveTrain.getInstance();
        kicker = BallHandler.getInstance();
        hanger = Hanger.getInstance();
        auton = Autonomous.getInstance();
        compressor = new Compressor(Connections.PressureSwitchChannel, Connections.CompressorSpikeChannel);
        compressor.start();
//        cylinderIn = new Solenoid(8, Connections.SolenoidInChannel);
//        cylinderOut = new Solenoid(8, Connections.SolenoidOutChannel);
//        cylinderIsOut = false;
//        firingServo = new Servo(Connections.FiringServoChannel);
        rollerLock = false;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousInit()
    {
        auton.initialize();
        auton.reload();
        auton.setRoller(true);
        auton.goStraightUntil(16);
        auton.stopMoving();
        auton.kick();
        auton.reload();
        auton.goStraightUntil(50);
        auton.stopMoving();
        auton.kick();
        auton.reload();
        auton.goStraightUntil(87);
        auton.kick();
        auton.goStraightUntil(92);
        auton.stopMoving();
        auton.setRoller(false);
    }

    public void autonomousPeriodic()
    {
        auton.update();
        //drive.printEncoders();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        if(leftStick.getTrigger())
        {
            kicker.kick();
        }
        if(leftStick.getRawButton(3))
        {
            kicker.reload(true);
        }
        if(leftStick.getRawButton(2))
        {
            //kicker.reload(false);
        }
        if(rightStick.getRawButton(3))
        {
            drive.setShifter(true);
        }
        else if(rightStick.getRawButton(2))
        {
            drive.setShifter(false);
        }
        drive.set(leftStick.getY(), rightStick.getY());
        if(!rightStick.getTrigger() && !thirdStick.getRawButton(11))
        {
            rollerLock = false;
        }
        else
        {
            if(!rollerLock)
            {
                kicker.toggleFrontRoller();
                rollerLock = true;
            }
        }
        if(thirdStick.getRawButton(10))
        {
            drive.leftEncoder.reset();
            drive.rightEncoder.reset();
        }
        if(thirdStick.getRawButton(8))
        {
            kicker.setOverride(true);
        }
        else
        {
            kicker.setOverride(false);
        }
        if(thirdStick.getRawButton(5))
        {
            kicker.overrideIn();
        }
        if(thirdStick.getRawButton(4))
        {
            kicker.overrideOut();
        }
        kicker.overrideServo((thirdStick.getZ()+1)/2);
        if(thirdStick.getRawButton(3))
        {
            kicker.reload(true);
        }
        if(thirdStick.getRawButton(2))
        {
            //kicker.reload(false);
        }
        if(thirdStick.getTrigger())
        {
            kicker.kick();
        }
        if(leftStick.getRawButton(7))
        {
            drive.leftFactor = 1.0;
            drive.rightFactor = -1.0;
        }
        else if(leftStick.getRawButton(6))
        {
            drive.leftFactor = -1.0;
            drive.rightFactor = 1.0;
        }
        sendDashboardData();
        //drive.printEncoders();
    }

    public void sendDashboardData()
    {
        Dashboard dash = DriverStation.getInstance().getDashboardPackerLow();
        dash.addCluster();
        {
            dash.addCluster();
            {     //analog modules
//                dash.addCluster();
//                {
//                    dash.addFloat((float) time.get());
//                    dash.addFloat((float) drive.rightController.getSetpoint());
//                    dash.addFloat((float) drive.rightPIDOut.getValue());
//                    dash.addFloat((float) drive.leftController.getSetpoint());
//                    dash.addFloat((float) drive.leftPIDOut.getValue());
//                    dash.addFloat((float) drive.getX());
//                    dash.addFloat((float) drive.getY());
////                  dash.addFloat((float) time.get());
//                    dash.addFloat((float) drive.leftController.getSetpoint());
//                    dash.addFloat((float) drive.leftPIDOut.getValue());
//                    dash.addFloat((float) drive.getX());
//                    dash.addFloat((float) drive.getY());
////                    for (int i = 1; i <= 8; i++)
////                    {
////                        dash.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(i));
////                    }
//                }
//                dash.finalizeCluster();
                dash.addCluster();
                {
                    dash.addFloat((float) time.get());
                    dash.addFloat((float) kicker.getFrontRollerSpeed());
                    if(kicker.isLoaded())
                    {
                        dash.addFloat(1);
                    }
                    else
                    {
                        dash.addFloat(0);
                    }
                    if(drive.getShifter())
                    {
                        dash.addFloat(1);
                    }
                    else
                    {
                        dash.addFloat(0);
                    }
                    dash.addFloat((float) 0.0);
                    dash.addFloat((float) 0.0);
                    dash.addFloat((float) 0.0);
                    dash.addFloat((float) 0.0);
                }
                dash.finalizeCluster();
                dash.addCluster();
                {
                    dash.addFloat((float) time.get());
                    dash.addFloat((float) kicker.getFrontRollerSpeed());
                    if(kicker.isLoaded())
                    {
                        dash.addFloat(1);
                    }
                    else
                    {
                        dash.addFloat(0);
                    }
                    if(drive.getShifter())
                    {
                        dash.addFloat(1);
                    }
                    else
                    {
                        dash.addFloat(0);
                    }
                    dash.addFloat((float) 0.0);
                    dash.addFloat((float) 0.0);
                    dash.addFloat((float) 0.0);
                    dash.addFloat((float) 0.0);
                }
                dash.finalizeCluster();
            }
            dash.finalizeCluster();

            dash.addCluster();
            { //digital modules
                dash.addCluster();
                {
                    dash.addCluster();
                    {
                        int module = 4;
                        dash.addByte(DigitalModule.getInstance(module).getRelayForward());
                        dash.addByte(DigitalModule.getInstance(module).getRelayForward());
                        dash.addShort(DigitalModule.getInstance(module).getAllDIO());
                        dash.addShort(DigitalModule.getInstance(module).getDIODirection());
                        dash.addCluster();
                        {
                            for (int i = 1; i <= 10; i++)
                            {
                                dash.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        dash.finalizeCluster();
                    }
                    dash.finalizeCluster();
                }
                dash.finalizeCluster();

                dash.addCluster();
                {
                    dash.addCluster();
                    {
                        int module = 6;
                        dash.addByte(DigitalModule.getInstance(module).getRelayForward());
                        dash.addByte(DigitalModule.getInstance(module).getRelayReverse());
                        dash.addShort(DigitalModule.getInstance(module).getAllDIO());
                        dash.addShort(DigitalModule.getInstance(module).getDIODirection());
                        dash.addCluster();
                        {
                            for (int i = 1; i <= 10; i++)
                            {
                                dash.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        dash.finalizeCluster();
                    }
                    dash.finalizeCluster();
                }
                dash.finalizeCluster();

            }
            dash.finalizeCluster();

            dash.addByte(Solenoid.getAll());
        }
        dash.finalizeCluster();
        dash.commit();
    }
    public void disabledInit()
    {
        System.out.println("We're disabled, captain!");
        disabledStep = 0;
//        String s = PositiveCriticismAssistant.criticizePositively();
//        for(int i = 1; i < 4; i++)
//        {
//            DriverStationLCD.getInstance().println(intToLine(i), 1, s.substring(0, DriverStationLCD.kLineLength));
//            s = s.substring(DriverStationLCD.kLineLength);
//            if(s.length() <= 0)
//            {
//                break;
//            }
//        }
    }
//    DriverStationLCD.Line intToLine(int lineNumber)
//    {
//        if(lineNumber == 1)
//        {
//            return DriverStationLCD.Line.kUser2;
//        }
//        else if(lineNumber == 2)
//        {
//            return DriverStationLCD.Line.kUser3;
//        }
//        else if(lineNumber == 3)
//        {
//            return DriverStationLCD.Line.kUser4;
//        }
//        else if(lineNumber == 3)
//        {
//            return DriverStationLCD.Line.kUser5;
//        }
//        else if(lineNumber == 4)
//        {
//            return DriverStationLCD.Line.kUser6;
//        }
//        else
//        {
//            return null;
//        }
//    }
    public void disabledPeriodic()
    {
        if(disabledStep == 0)
        {
            System.out.print("G");
        }
        if(disabledStep == 1)
        {
            System.out.print("O");
        }
        else if(disabledStep == 2)
        {
            System.out.print(" ");
        }
        else if(disabledStep == 3)
        {
            System.out.print("F");
        }
        else if(disabledStep == 4)
        {
            System.out.print("L");
        }
        else if(disabledStep == 5)
        {
            System.out.print("O");
        }
        else if(disabledStep == 6)
        {
            System.out.print("!");
        }
        else if(disabledStep == 7 || disabledStep == 8 || disabledStep == 9 || disabledStep == 10)
        {
            System.out.println();
        }
        System.out.println();
        Timer.delay(0.3);
        disabledStep++;
        if(disabledStep == 8)
        {
            disabledStep = 0;
        }
    }
}
