/*
 * This class holds all physical connections slots, channels, etc.
 * It provides us with a way to change a number in one place to change it everywhere.
 *
 * BE SURE TO MAKE AN OBJECT OF THIS CLASS IN THE BEGINNING OF THE CLASS USING IT!
 *
 */

package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author joshuf26
 */
public class Connections {
    public static final int LeftMotorChannel1 = 1;
    public static final int LeftMotorChannel2 = 2;
    public static final int RightMotorChannel1 = 3;
    public static final int RightMotorChannel2 = 4;
    public static final int LeftEncoderChannelA = 2;
    public static final int LeftEncoderChannelB = 1;
    public static final int RightEncoderChannelA = 4;
    public static final int RightEncoderChannelB = 3;
    public static final int LeftJoystickChannel = 1;
    public static final int RightJoystickChannel = 2;
    public static final int GyroChannel = 1;
    public static final double RateRatio = 1.0; //SET THIS
    public static final double EncoderDistancePerPulse = 1.0; //SET THIS
}