/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Encoder;

/**
 *
 * @author joshuf26
 */
public class rateEncoder extends Encoder implements PIDSource
{
    int lastCount, currentCount;
    public rateEncoder(int aChannel, int bChannel)
    {
        super(aChannel, bChannel);
        lastCount = currentCount = 0;
    }
    public rateEncoder(int aSlot, int aChannel, int bSlot, int bChannel)
    {
        super(aSlot, aChannel, bSlot, bChannel);
    }
    public double pidGet()
    {
        return getRate();
    }
    public int getCountChange()
    {
        currentCount = get();
        int countChange = currentCount-lastCount;
        lastCount = currentCount;
        return countChange;
    }
    public double getDistanceChange()
    {
        double distanceChange = getCountChange()*Connections.EncoderDistancePerPulse;
        return distanceChange;
    }
}