/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.Jaguar;
/**
 *
 * @author Josh
 */
public class driveTrain
{
    private double leftSpeed, rightSpeed;
    private Jaguar leftMotor1, rightMotor1, leftMotor2, rightMotor2;
    private double leftFactor, rightFactor;
    public driveTrain(Jaguar leftMotor1, Jaguar rightMotor1, Jaguar leftMotor2, Jaguar rightMotor2)
    {
        this.leftMotor1 = leftMotor1;
        this.rightMotor1 = rightMotor1;
        this.leftMotor2 = leftMotor2;
        this.rightMotor2 = rightMotor2;
        leftSpeed = rightSpeed = 0.0;
        leftFactor = -1.0;
        rightFactor = 1.0;
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
        leftMotor1.set(leftSpeed);
        leftMotor2.set(leftSpeed);
        rightMotor1.set(rightSpeed);
        rightMotor2.set(rightSpeed);
    }
    public void set(double leftSpeed, double rightSpeed)
    {
        leftSpeed *= leftFactor;
        rightSpeed *= rightFactor;
        this.leftSpeed += leftSpeed;
        this.rightSpeed += rightSpeed;
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
    public void startLoop()
    {
        leftSpeed = 0.0;
        rightSpeed = 0.0;
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