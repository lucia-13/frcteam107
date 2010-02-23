/*
 * This class controls everything that has to do with the wheels.
 *
 *
 * Update: Added full PID support for rotational rate control
 *
 *
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Solenoid;
import java.util.TimerTask;
import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author Josh
 */
public class driveTrain
{
    private double leftSpeed, rightSpeed;
    private Jaguar leftMotor1, rightMotor1, leftMotor2, rightMotor2;
    private double leftFactor, rightFactor;
    public rateEncoder leftEncoder, rightEncoder;
    private Gyro gyro;
    public PID leftPID, rightPID;
    public SoftPID leftPIDOut, rightPIDOut;
    public PIDController leftController, rightController;
    public boolean PIDControlled;
    private double rateRatio;
    private double x, y;
    private Solenoid shifter;
    private java.util.Timer controlLoop;
    private double period;
    private Relay fan;
    public driveTrain(PID leftPID, PID rightPID, boolean PIDControlled)
    {
        leftMotor1 = new Jaguar(Connections.LeftMotorChannel1);
        leftMotor2 = new Jaguar(Connections.LeftMotorChannel2);
        rightMotor1 = new Jaguar(Connections.RightMotorChannel1);
        rightMotor2 = new Jaguar(Connections.RightMotorChannel2);
        leftSpeed = rightSpeed = 0.0;
        x = y = 0;
        leftFactor = -1.0;
        rightFactor = 1.0;
        this.PIDControlled = PIDControlled;
        this.leftPID = leftPID;
        this.rightPID = rightPID;
        leftPIDOut = new SoftPID();
        rightPIDOut = new SoftPID();
        leftEncoder = new rateEncoder(Connections.LeftEncoderChannelA, Connections.LeftEncoderChannelB);
        rightEncoder = new rateEncoder(Connections.RightEncoderChannelA, Connections.RightEncoderChannelB);
        leftEncoder.setDistancePerPulse(Connections.EncoderDistancePerPulse);
        rightEncoder.setDistancePerPulse(Connections.EncoderDistancePerPulse);
        leftEncoder.setReverseDirection(false);
        rightEncoder.setReverseDirection(true);
        leftEncoder.start();
        rightEncoder.start();
        gyro = new Gyro(Connections.GyroChannel);
        leftController = new PIDController(leftPID.p, leftPID.i, leftPID.d, leftEncoder, leftPIDOut);
        rightController = new PIDController(rightPID.p, rightPID.i, rightPID.d, rightEncoder, rightPIDOut);
        leftController.setContinuous();
        leftController.setOutputRange(-1.0, 1.0);
        rightController.setContinuous();
        rightController.setOutputRange(-1.0, 1.0);
        leftController.setTolerance(50);
        rightController.setTolerance(50);
        leftController.enable();
        rightController.enable();
        rateRatio = Connections.RateRatio;
        shifter = new Solenoid(Connections.SolenoidModuleSlot, Connections.ShifterChannel);
        period = 0.02;
        controlLoop = new java.util.Timer();
        controlLoop.schedule(new UpdateTask(this), 0L, (long) (period*1000));
        fan = new Relay(Connections.FanSpikeChannel);
        fan.set(Relay.Value.kForward);
    }
    private class UpdateTask extends TimerTask
    {
        private driveTrain handler;
        public UpdateTask(driveTrain handler)
        {
            if(handler == null)
                throw new NullPointerException("Given driveTrain was null");
            this.handler = handler;
        }
        public void run()
        {
            handler.update();
        }
    }
    public synchronized void update()
    {
        checkSpeed();
        if(PIDControlled)
        {
            leftController.setSetpoint(leftSpeed*rateRatio);
            rightController.setSetpoint(rightSpeed*rateRatio);
            leftMotor1.set(leftPIDOut.getValue());
            leftMotor2.set(leftPIDOut.getValue());
            rightMotor1.set(rightPIDOut.getValue());
            rightMotor2.set(rightPIDOut.getValue());
        }
        else
        {
            leftMotor1.set(leftSpeed);
            leftMotor2.set(leftSpeed);
            rightMotor1.set(rightSpeed);
            rightMotor2.set(rightSpeed);
        }
        x += java.lang.Math.cos(java.lang.Math.toRadians(gyro.getAngle()-90))
                *(leftEncoder.getDistanceChange()+rightEncoder.getDistanceChange())/2;
        y += java.lang.Math.sin(java.lang.Math.toRadians(gyro.getAngle()-90))
                *(leftEncoder.getDistanceChange() +rightEncoder.getDistanceChange())/2;
    }
    public void set(double leftSpeed, double rightSpeed)
    {
        this.leftSpeed = leftSpeed*leftFactor;
        this.rightSpeed = rightSpeed*rightFactor;
    }
    public void rotate(double rotationalSpeed)
    {
        leftSpeed = rotationalSpeed;
        rightSpeed = rotationalSpeed;
    }
//    public void oneWheelRotate(double rotationalSpeed)
//    {
//        if(rotationalSpeed > 0.0)
//        {
//            if(rightSpeed > 0.0 || leftSpeed + rotationalSpeed > 1.0) //We can't speed up leftSpeed, so we slow down rightSpeed
//            {
//                rightSpeed -= rotationalSpeed;
//            }
//            else if(rightSpeed <= 0.0) //We know that we have enough speed to add to leftSpeed
//            {
//                leftSpeed += rotationalSpeed;
//            }
//        }
//        else if(rotationalSpeed < 0.0)
//        {
//            if(leftSpeed > 0.0 || rightSpeed + rotationalSpeed > 1.0)
//            {
//                leftSpeed += rotationalSpeed; //Add because we already know it's negative
//            }
//            else if(leftSpeed <= 0.0)
//            {
//                rightSpeed -= rotationalSpeed;
//            }
//        }
//    }
    public void straight(double speed)
    {
        leftSpeed = speed*leftFactor;
        rightSpeed = speed*rightFactor;
    }
    public void setShifter(boolean highSpeed)
    {
        if(highSpeed)
        {
            shifter.set(false);
        }
        else
        {
            shifter.set(true);
        }
    }




    public PID getLeftPID()
    {
        return leftPID;
    }
    public PID getRightPID()
    {
        return rightPID;
    }
    public void setPID(PID leftPID, PID rightPID)
    {
        this.leftPID = leftPID;
        this.rightPID = rightPID;
    }
    public void resetPositionTracking()
    {
        x = y = 0;
        leftEncoder.reset();
        rightEncoder.reset();
        gyro.reset();
    }
    public double getX()
    {
        return x;
    }
    public double getY()
    {
        return y;
    }
    public double getHeading()
    {
        return gyro.getAngle();
    }
    public void printEncoders()
    {
        System.out.println("Left Encoder: "+leftEncoder.getDistance()+"   Right Encoder: "+rightEncoder.getDistance());
    }
    private synchronized void checkSpeed()
    {
        if(leftSpeed > 1.0)
        {
            leftSpeed = 1.0;
            System.out.println("left speed over!");
        }
        if(leftSpeed < -1.0)
        {
            leftSpeed = -1.0;
            System.out.println("left speed under!");
        }
        if(rightSpeed > 1.0)
        {
            rightSpeed = 1.0;
            System.out.println("right speed over!");
        }
        if(rightSpeed < -1.0)
        {
            rightSpeed = -1.0;
            System.out.println("right speed over!");
        }
    }
    public double getDistance()
    {
        return (leftEncoder.getDistance()+rightEncoder.getDistance())/2;
    }
    public void free() //DON'T CALL UNLESS NECESSARY
    {
        controlLoop.cancel();
        controlLoop = null;
    }
}