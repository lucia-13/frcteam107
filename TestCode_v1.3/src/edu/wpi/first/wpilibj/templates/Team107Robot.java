/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.
 *
 *
 *
 *
 * In this version, we are creating an Autonomous class to handle all auton
 * events and sequence them.
 * 
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
    private driveTrain drive;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        getWatchdog().feed();
        leftStick = new Joystick(Connections.LeftJoystickChannel);
        rightStick = new Joystick(Connections.RightJoystickChannel);
        drive = new driveTrain(new PID(0.01, 0.001, 0.0), new PID(0.01, 0.001, 0.0), true);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        drive.set(leftStick.getY(), rightStick.getY());
        sendDashboardData();
        drive.update(); //do this at end of loop
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
                    for (int i = 1; i <= 8; i++)
                    {
                        dash.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(i));
                    }
                }
                dash.finalizeCluster();
                dash.addCluster();
                {
                    for (int i = 1; i <= 8; i++)
                    {
                        dash.addFloat((float) AnalogModule.getInstance(2).getAverageVoltage(i));
                    }
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
