package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.DigitalModule;

/**
 *
 * @author charrisTTI
 */
public class I2CAccelerometer extends SensorBase
{
    private I2C i2c;
    // default address
    private static final byte kAddress = 0x3A;
    // register map from datasheet
    private static final byte OFSX = 0x1E;
    private static final byte OFSY = 0x1F;
    private static final byte OFSZ = 0x20;
    private static final byte BW_RATE = 0x2C;
    private static final byte  POWER_CTL = 0x2D;
    private static final byte DATA_FORMAT = 0x31;
    private static final byte DATAX0 = 0x32;
    private static final byte DATAY0 = 0x34;
    private static final byte DATAZ0 = 0x36;
    private static final byte FIFO_CTL = 0x38;
    private static final byte FIFO_STATUS = 0x39;

    // would use enums here if we had them
    // BW_RATE 0x2C
    private static final byte BW_RATE_R3200B1600 = 0x0F;
    private static final byte BW_RATE_R1600B0800 = 0x0E;
    private static final byte BW_RATE_R0800B0400 = 0x0D;
    private static final byte BW_RATE_R0400B0200 = 0x0C;
    private static final byte BW_RATE_R0200B0100 = 0x0B;
    private static final byte BW_RATE_R0100B0050 = 0x0A;
    private static final byte BW_RATE_R0050B0025 = 0x09;
    private static final byte BW_RATE_R0025B0012 = 0x08;
    private static final byte BW_RATE_R0012B0006 = 0x07;
    private static final byte BW_RATE_R0006B0003 = 0x06;

    private static final byte BW_RATE_LOW_POWER = 0x10;

    // POWER_CTL 0x2D
    private static final byte POWER_CTL_LINK = 0x20;
    private static final byte POWER_CTL_AUTO_SLEEP = 0x10;
    private static final byte POWER_CTL_MEASURE = 0x08;
    private static final byte POWER_CTL_SLEEP = 0x04;
    private static final byte POWER_CTL_WAKEUP8 = 0x00;
    private static final byte POWER_CTL_WAKEUP4 = 0x01;
    private static final byte POWER_CTL_WAKEUP2 = 0x02;
    private static final byte POWER_CTL_WAKEUP1 = 0x03;

    // DATA_FORMAT
    public static final byte DATA_FORMAT_02G = 0x00;
    public static final byte DATA_FORMAT_04G = 0x01;
    public static final byte DATA_FORMAT_08G = 0x02;
    public static final byte DATA_FORMAT_16G = 0x03;

    // store the current
    private byte range = DATA_FORMAT_02G;

    private double xPosition, yPosition, zPosition;
    private double xVelocity, yVelocity, zVelocity;
    private double xZero, yZero, zZero;
    private double threshold;



    public class ADXL345Exception extends RuntimeException
    {

        /**
        * Create a new exception with the given message
        * @param message the message to pass with the exception
        */
        public ADXL345Exception(String message)
        {
            super(message);
        }
    }
    public I2CAccelerometer(int slot)
    {
        i2c = new I2C( DigitalModule.getInstance(slot), kAddress );
    }

    // initialize the sensor
    public void intitialize()
    {
        // set BW_RATE
        i2c.write(BW_RATE, BW_RATE_R0100B0050);
        // set POWER_CTL
        i2c.write(POWER_CTL, POWER_CTL_MEASURE);
        xPosition = yPosition = zPosition = 0;
        xVelocity = yVelocity = zVelocity = 0;
        xZero = yZero = 0;
        zZero = 9.81;
        threshold = 0.02;
    }

    // set the range (default is =/- 2g
    public void setRange( byte rangeParam )
    {
        if ( !( rangeParam == DATA_FORMAT_02G ||
                rangeParam == DATA_FORMAT_04G ||
                rangeParam == DATA_FORMAT_08G ||
                rangeParam == DATA_FORMAT_16G ) )
        {
            throw new ADXL345Exception("Invalid range!");
        }


        range = rangeParam;

        i2c.write(DATA_FORMAT, range);
    }

    // get acceleration routines
    public double getXAxis()
    {
        double returnValue = getAxis( DATAX0 )-xZero;
        if(java.lang.Math.abs(returnValue) < threshold)
        {
            returnValue = 0;
        }
        return returnValue;
    }

    public double getYAxis()
    {
        double returnValue = getAxis( DATAY0 )-yZero;
        if(java.lang.Math.abs(returnValue) < threshold)
        {
            returnValue = 0;
        }
        return returnValue;
    }

    public double getZAxis()
    {
        double returnValue = getAxis( DATAZ0 )-zZero;
        if(java.lang.Math.abs(returnValue) < threshold)
        {
            returnValue = 0;
        }
        return returnValue;
    }

    protected double getAxis( byte registerParam )
    {
        // setup array for our data
        byte[] data = new byte[2];
        // read consecutive registers
        this.i2c.read( registerParam, (byte) data.length, data);

        // convert to 2s complement integer
        // [0] has low byte [1] has the high byte
        // java does not have unsigned so we have to do it this way
        int intResult = ( data[0] & 0xFF ) | ( data[1] << 8 );

        // convert to double based on 10 bit result
        double returnValue = (double)intResult / 512.0 ;

        // now scale based upon our range
        switch( range )
        {
            case DATA_FORMAT_02G:
                returnValue *= 2.0;
                break;
            case DATA_FORMAT_04G:
                returnValue *= 4.0;
                break;
            case DATA_FORMAT_08G:
                returnValue *= 8.0;
                break;
            case DATA_FORMAT_16G:
                returnValue *= 16.0;
                break;
        }
        return returnValue;
    }
    public void update()
    {
        xVelocity += getXAxis()*7.7244;
        yVelocity += getYAxis()*7.7244;
        zVelocity += getZAxis()*7.7244;
        xPosition += xVelocity*0.02;
        yPosition += yVelocity*0.02;
        zPosition += zVelocity*0.02;
    }

    public double getXPosition()
    {
        return xPosition;
    }
    public double getYPosition()
    {
        return yPosition;
    }
    public double getZPosition()
    {
        return zPosition;
    }
    public void zero()
    {
        xZero = getAxis(DATAX0);
        yZero = getAxis(DATAY0);
        zZero = getAxis(DATAZ0);
        xPosition = yPosition = zPosition = 0;
        xVelocity = yVelocity = zVelocity = 0;
    }
}
