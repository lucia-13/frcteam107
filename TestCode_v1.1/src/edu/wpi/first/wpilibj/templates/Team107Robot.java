/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDController;

import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
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

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    private Jaguar leftMotor1, rightMotor1, leftMotor2, rightMotor2;
    private Joystick leftStick, rightStick;
    private driveTrain drive;
    private Gyro gyro;
    private PIDController angleControl;
    private SoftPID PIDout;
    //private rotatePID rotational;
    private double p, i, d;
    private boolean firstLoop;
    private Compressor compressor;
    private Solenoid leftShifter;
    private Solenoid rightShifter;

    public void robotInit()
    {
        getWatchdog().feed();
        leftMotor1 = new Jaguar(1);
        leftMotor2 = new Jaguar(2);
        rightMotor1 = new Jaguar(3);
        rightMotor2 = new Jaguar(4);
        leftStick = new Joystick(1);
        rightStick = new Joystick(2);
        drive = new driveTrain(leftMotor1, rightMotor1, leftMotor2, rightMotor2);
        gyro = new Gyro(1, 1);
        PIDout = new SoftPID();
        //rotational = new rotatePID(drive);
        p = 0.01;//0.01;
        i = 0.0;//10.0;
        d = 0.0;//0.03;
        firstLoop = true;
        angleControl = new PIDController(p, i, d, gyro, PIDout);//rotational);
        angleControl.setOutputRange(-0.75, 0.75);
        compressor = new Compressor(5, 6);
        compressor.start();
        leftShifter = new Solenoid(8, 1);
        rightShifter = new Solenoid(8, 2);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        getWatchdog().feed();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        getWatchdog().feed();
        sendDashboardData();
        drive.startLoop();
        
        //p = (leftStick.getZ()+1)/2;
        //i = (rightStick.getZ()+1)/2;
         
        
        /*
         *
         * For PID Looping
         *
         */
        
        if(rightStick.getTrigger())
        {
            leftShifter.set(true);
            rightShifter.set(true);
        }
        else
        {
            leftShifter.set(false);
            rightShifter.set(false);
        }
        drive.set(rightStick.getY(), rightStick.getY());
        drive.update(); //do this at end of loop




//        if (rightStick.getTrigger()) {
//            if (firstLoop) {
//                gyro.reset();
//                firstLoop = false;
//            }
//            if (rightStick.getY() > 0.0) {
//                angleControl.setOutputRange(-1.0, 1.0 - rightStick.getY());
//            } else if (rightStick.getY() < 0.0) {
//                angleControl.setOutputRange(-1.0 + rightStick.getY(), 1.0);
//            } else {
//                angleControl.setOutputRange(-1.0, 1.0);
//            }
//            angleControl.enable();
//            //angleControl.setPID(p, i, d);
//            angleControl.setSetpoint(leftStick.getX() * 180);
//            drive.straight(rightStick.getY());
//            //drive.rotate(PIDout.getValue());
//            drive.oneWheelRotate(PIDout.getValue());//   DOESN'T WORK YET
//            //rotational.updateDrive();
//            drive.update();
//        }
//
//
//
//        /*
//         *
//         * For balanced output
//         *
//         */
//        else if (leftStick.getTrigger()) {
//            drive.straight(leftStick.getY());
//            drive.update();
//        }
//
//
//
//        /*
//         *
//         * For normal driving
//         *
//         */ else {
//            firstLoop = true;
//            angleControl.reset();
//            drive.update(leftStick.getY(), rightStick.getY());
//        }
//
//
//        Timer.delay(0.005);
//        //System.out.println(gyro.getAngle());
//        System.out.println("p: "+p+"    i: "+i+"    d: "+d+"    gyro: "+gyro.getAngle()+
//                "   error: "+angleControl.getError()+"  output: "+PIDout.getValue());
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
