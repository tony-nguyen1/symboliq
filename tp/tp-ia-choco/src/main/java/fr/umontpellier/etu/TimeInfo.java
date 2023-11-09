package fr.umontpellier.etu;

public class TimeInfo {
    private double userTime;
    private double cpuTime;
    private double realTime;

    private double sysTime;

    private boolean timedOut;


    public TimeInfo(double userTime, double cpuTime, double realTime, boolean timedOut){
        this.userTime = userTime;
        this.cpuTime = cpuTime;
        this.realTime = realTime;
        this.timedOut = timedOut;
        sysTime = cpuTime - userTime;
    }
    public double getUserTime() {
        return userTime;
    }

    public double getCpuTime() {
        return cpuTime;
    }

    public double getRealTime() {
        return realTime;
    }
    public double getSysTime(){
        return sysTime;
    }

    public boolean isTimedOut() {
        return timedOut;
    }
    /*@Override
    public String toString(){
        return
    }*/
}

