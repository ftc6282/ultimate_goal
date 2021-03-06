package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PIDController {

    private final double Kp;
    private final double Ki;
    private final double Kd;
    private final double integralCutoff;

    private final ElapsedTime timer;
    private double integral = 0;
    private double previousError = 0;

    private Telemetry telemetry = null;

    public PIDController(double Kp, double Ki, double Kd, double integralCutoff) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.integralCutoff = integralCutoff;

        this.integral = 0;
        this.timer = new ElapsedTime();
    }

    public void enableTelemetry(Telemetry t) {
        this.telemetry = t;
    }

    public double control(float error) {
        double elapsedTime = timer.seconds();
        timer.reset();
        // Kp and Ki are one line
        double proportionalValue = error * Kp;
        double derivative = (error - previousError) / elapsedTime * Kd;

        // Reset our integral value if we cross over our taret value to prevent continuous overshooting
        if ((previousError > 0 && error <= 0) || (previousError < 0 && error >= 0)) {
            integral = 0;
        }

        previousError = error;

        if (Math.abs(error) < integralCutoff) {
            integral = integral + (error * Ki * elapsedTime);
        }

        if (this.telemetry != null) {
            telemetry.addData("Proportional: ", proportionalValue);
            telemetry.addData("Integral: ", integral);
            telemetry.addData("Derivative: ", derivative);
        }
        double power = proportionalValue + integral + derivative;
        return power;
    }

}
