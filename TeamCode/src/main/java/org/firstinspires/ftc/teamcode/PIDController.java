package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PIDController{

    private double integral = 0;
    private double previousError = 0;
    private ElapsedTime elapsedTime = null;
    private final double Kp;
    private final double Ki;
    private final double Kd;
    private final double integralEnabledThreshold;
    private Telemetry telemetry;


    public PIDController(double Kp, double Ki, double Kd, double integralEnabledThreshold){
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.integralEnabledThreshold = integralEnabledThreshold;
        this.elapsedTime = new ElapsedTime();
    }

    public void setTelemetry(Telemetry telemetry){
        this.telemetry = telemetry;
    }


    public double control(double error) {

        double time = elapsedTime.seconds();
        elapsedTime.reset();

        double proportionalValue = error * Kp;

        double derivative = (error - previousError) / time * Kd;

        if ((previousError > 0 && error <= 0) || (previousError < 0 && error >= 0)) {
            integral = 0;
        }

        previousError = error;

        if (Math.abs(error) < integralEnabledThreshold) {
            integral = integral + (error * Ki * time);
        }

       if(telemetry != null){
           telemetry.addData("Proportional: ", proportionalValue);
           telemetry.addData("Integral: ", integral);
       }

        double power = proportionalValue + integral + derivative;
        return power;
    }
}
