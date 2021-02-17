package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class MecanumHardware {

    public DcMotor frontRight = null;
    public DcMotor frontLeft = null;
    public DcMotor backRight = null;
    public DcMotor backLeft = null;
    public DcMotor launcherLeft = null;
    public DcMotor launcherRight = null;
    public DcMotor ramp = null;
    public Servo wheelIntake = null;
    public Servo flicker = null;
    //public Servo shooterServo = null;
    //public Servo intakeServo = null;
    //public Vuforia webcam = null;


    public void initialize(HardwareMap hardwareMap) {
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        ramp = hardwareMap.get(DcMotor.class, "ramp");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        launcherLeft = hardwareMap.get(DcMotor.class, "launcherLeft");
        launcherRight = hardwareMap.get(DcMotor.class, "launcherRight");

        launcherLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        wheelIntake = hardwareMap.get(Servo.class, "wheelIntake");

        flicker = hardwareMap.get(Servo.class, "flicker");
        flicker.setPosition(0.6);










    }
}