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

/**
 *
 * @author Josh
 */
public class driveTrain
{
    private Connections connections;
    private double leftSpeed, rightSpeed;
    private Jaguar leftMotor1, rightMotor1, leftMotor2, rightMotor2;
    private double leftFactor, rightFactor;
    private rateEncoder leftEncoder, rightEncoder;
    private Gyro gyro;
    private PID leftPID, rightPID;
    private SoftPID leftPIDOut, rightPIDOut;
    private PIDController leftController, rightController;
    public boolean PIDControlled;
    private double rateRatio;
    private double x, y;
    public driveTrain(PID leftPID, PID rightPID, boolean PIDControlled)
    {
        leftMotor1 = new Jaguar(connections.LeftMotorChannel1);
        leftMotor2 = new Jaguar(connections.LeftMotorChannel2);
        rightMotor1 = new Jaguar(connections.RightMotorChannel1);
        rightMotor2 = new Jaguar(connections.RightMotorChannel2);
        leftSpeed = rightSpeed = 0.0;
        x = y = 0;
        leftFactor = -1.0;
        rightFactor = 1.0;
        this.PIDControlled = PIDControlled;
        this.leftPID = leftPID;
        this.rightPID = rightPID;
        leftPIDOut = new SoftPID();
        rightPIDOut = new SoftPID();
        leftEncoder = new rateEncoder(connections.LeftEncoderChannelA, connections.LeftEncoderChannelB);
        rightEncoder = new rateEncoder(connections.RightEncoderChannelA, connections.RightEncoderChannelB);
        leftEncoder.setDistancePerPulse(connections.EncoderDistancePerPulse);
        leftEncoder.setDistancePerPulse(connections.EncoderDistancePerPulse);
        gyro = new Gyro(connections.GyroChannel);
        leftController = new PIDController(leftPID.p, leftPID.i, leftPID.d, leftEncoder, leftPIDOut);
        rightController = new PIDController(rightPID.p, rightPID.i, rightPID.d, rightEncoder, rightPIDOut);
        leftController.setContinuous();
        leftController.setOutputRange(-1.0, 1.0);
        rightController.setContinuous();
        rightController.setOutputRange(-1.0, 1.0);
        rateRatio = connections.RateRatio;
    }
    public void update(double leftSpeed, double rightSpeed)
    {
        checkSpeed();
        this.leftSpeed = leftSpeed*leftFactor;
        this.rightSpeed = rightSpeed*rightFactor;
        update();
    }
    public void update()
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
                *(leftEncoder.getCountChange()+rightEncoder.getCountChange())/2
                *connections.EncoderDistancePerPulse;
        y += java.lang.Math.sin(java.lang.Math.toRadians(gyro.getAngle()-90))
                *(leftEncoder.getCountChange() +rightEncoder.getCountChange())/2
                *connections.EncoderDistancePerPulse;
        leftSpeed = 0.0;
        rightSpeed = 0.0;
    }
    public void set(double leftSpeed, double rightSpeed)
    {
        this.leftSpeed += leftSpeed*leftFactor;
        this.rightSpeed += rightSpeed*rightFactor;
    }
    public void rotate(double rotationalSpeed)
    {
        leftSpeed += rotationalSpeed;
        rightSpeed += rotationalSpeed;
    }
    public void oneWheelRotate(double rotationalSpeed)
    {
        if(rotationalSpeed > 0.0)
        {
            if(rightSpeed > 0.0 || leftSpeed + rotationalSpeed > 1.0) //We can't speed up leftSpeed, so we slow down rightSpeed
            {
                rightSpeed -= rotationalSpeed;
            }
            else if(rightSpeed <= 0.0) //We know that we have enough speed to add to leftSpeed
            {
                leftSpeed += rotationalSpeed;
            }
        }
        else if(rotationalSpeed < 0.0)
        {
            if(leftSpeed > 0.0 || rightSpeed + rotationalSpeed > 1.0)
            {
                leftSpeed += rotationalSpeed; //Add because we already know it's negative
            }
            else if(leftSpeed <= 0.0)
            {
                rightSpeed -= rotationalSpeed;
            }
        }
    }
    public void straight(double speed)
    {
        leftSpeed += speed*leftFactor;
        rightSpeed += speed*rightFactor;
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
    private void checkSpeed()
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
}