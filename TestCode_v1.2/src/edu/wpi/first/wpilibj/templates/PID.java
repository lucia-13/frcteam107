/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author joshuf26
 */
public class PID {
    public double p;
    public double i;
    public double d;
    public PID(double p, double i, double d)
    {
        this.p = p;
        this.i = i;
        this.d = d;
    }
    public void setP(double p)
    {
        this.p = p;
    }
    public void setI(double i)
    {
        this.i = i;
    }
    public void setD(double d)
    {
        this.d = d;
    }
}
