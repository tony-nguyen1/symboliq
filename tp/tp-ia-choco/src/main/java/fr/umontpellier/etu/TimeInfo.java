package fr.umontpellier.etu;

public class TimeInfo {
    private double userTime;
    private double cpuTime;
    private double realTime;

    private double sysTime;


    public TimeInfo(double userTime,double cpuTime,double realTime){
        this.userTime = userTime;
        this.cpuTime = cpuTime;
        this.realTime = realTime;
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
}

