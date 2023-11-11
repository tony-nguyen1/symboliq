package fr.umontpellier.etu;

public class TimeInfo {
    private double userTime;
    private double cpuTime;
    private double realTime;

    private double sysTime;

    private int timedOut;


    public TimeInfo(double userTime, double cpuTime, double realTime, int timedOut){
        this.userTime = userTime;
        this.cpuTime = cpuTime;
        this.realTime = realTime;
        this.timedOut = timedOut;
        sysTime = cpuTime - userTime;
    }
    /*
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
    }*/



    public String[] toStrings(){
        return new String[]{String.valueOf(realTime),String.valueOf(cpuTime),String.valueOf(userTime),
        String.valueOf(sysTime),String.valueOf(timedOut)};
    }
}

