/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.
 *
 *
 *
 *
 * In this version, we are trying to get PID Loops working for the wheels.
 * Also, we are trying to get our Version Control working, and this comment
 * is proof of that fact.
 */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Compressor;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Team107Robot extends IterativeRobot
{
    private Joystick leftStick, rightStick;
    private DriveTrain drive;
    private Timer time;
    private Compressor compressor;
    private BallHandler kicker;
    private Hanger hanger;
    private Autonomous auton;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        getWatchdog().feed();
        leftStick = new Joystick(Connections.LeftJoystickChannel);
        rightStick = new Joystick(Connections.RightJoystickChannel);
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
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousInit()
    {
        auton.initialize(1);
        auton.goStraightUntil(20);
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
            kicker.reload();
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
        
        if(leftStick.getRawButton(10))
        {
            kicker.setFrontRoller(true);
        }
        else
        {
            kicker.setFrontRoller(false);
        }
        if(leftStick.getRawButton(6))
        {
            drive.leftEncoder.reset();
            drive.rightEncoder.reset();
        }
        sendDashboardData();
        drive.printEncoders();
        
        
        if(rightStick.getRawButton(6))
        {
            hanger.hangerMotor1.set(1.0);
            hanger.hangerMotor2.set(1.0);
        }
        else if(rightStick.getRawButton(7))
        {
            hanger.hangerMotor1.set(-1.0);
            hanger.hangerMotor2.set(-1.0);
        }
        else
        {
            hanger.hangerMotor1.set(0.0);
            hanger.hangerMotor2.set(0.0);
        }
    }

    public void sendDashboardData()
    {
        Dashboard dash = DriverStation.getInstance().getDashboardPackerLow();
        dash.addCluster();
        {
            dash.addCluster();
            {     //analog modules
                dash.addCluster();
                {
                    dash.addFloat((float) time.get());
                    dash.addFloat((float) drive.rightController.getSetpoint());
                    dash.addFloat((float) drive.rightPIDOut.getValue());
                    dash.addFloat((float) time.get());
                    dash.addFloat((float) drive.leftController.getSetpoint());
                    dash.addFloat((float) drive.leftPIDOut.getValue());
                    dash.addFloat((float) drive.getX());
                    dash.addFloat((float) drive.getY());
//                    for (int i = 1; i <= 8; i++)
//                    {
//                        dash.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(i));
//                    }
                }
                dash.finalizeCluster();
                dash.addCluster();
                {
                    dash.addFloat((float) drive.leftPID.p);
                    dash.addFloat((float) drive.leftPID.i);
                    dash.addFloat((float) drive.leftPID.d);
                    dash.addFloat((float) drive.rightPID.p);
                    dash.addFloat((float) drive.rightPID.i);
                    dash.addFloat((float) drive.rightPID.d);
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
}
