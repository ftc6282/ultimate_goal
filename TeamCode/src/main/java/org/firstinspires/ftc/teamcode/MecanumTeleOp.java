package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="MecanumTeleop", group ="Linear Opmode")
public class MecanumTeleOp extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    public MecanumHardware robot = new MecanumHardware();


    @Override
    public void runOpMode() {
        telemetry.addLine("Running");
        telemetry.update();

        robot.initialize(hardwareMap);

        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addLine("Opmode Active");

            double left_stick_x = -gamepad1.left_stick_x;
            double left_stick_y = gamepad1.left_stick_y;
            double r = Math.hypot(-left_stick_x / .707, -left_stick_y / .707);
            double robotAngle = Math.atan2(-left_stick_y / .707, -left_stick_x / .707) - Math.PI / 4;

            double rightX = Math.pow(gamepad1.right_stick_x, 2);
            if (gamepad1.right_stick_x < 0) {
                rightX *= -1;
            }
            //rightX *= -1;

            final double v1 = r * Math.sin(robotAngle) - rightX;
            final double v2 = r * Math.cos(robotAngle) + rightX;
            final double v3 = r * Math.cos(robotAngle) - rightX;
            final double v4 = r * Math.sin(robotAngle) + rightX;

            //telemetry.addData("r", "r at %f %f", r, robotAngle);
            //telemetry.addData("Path0", "Power at %f %f %f %f", v1);
            telemetry.addData("Joystick: %s", "" + left_stick_x + ", " +  left_stick_y+ ", " + gamepad1.right_stick_x);
            if (left_stick_x == 0 && left_stick_y == 0 && gamepad1.right_stick_x == 0) {
                robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                robot.frontRight.setTargetPosition(0);
                robot.frontLeft.setTargetPosition(0);
                robot.backRight.setTargetPosition(0);
                robot.backLeft.setTargetPosition(0);

                robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            } else {
                robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            if (gamepad1.right_bumper) {
                robot.frontRight.setPower(v1 * 0.5);
                robot.frontLeft.setPower(v2 * 0.5);
                robot.backRight.setPower(v3 * 0.5);
                robot.backLeft.setPower(v4 * 0.5);
            } else {
                robot.frontRight.setPower(v1);
                robot.frontLeft.setPower(v2);
                robot.backRight.setPower(v3);
                robot.backLeft.setPower(v4);
            }

            if (gamepad2.left_trigger > 0) {
                robot.launcherRight.setPower(1);
                robot.launcherLeft.setPower(1);
            } else if (gamepad2.left_bumper){
                robot.launcherRight.setPower(0.6);
                robot.launcherLeft.setPower(0.6);
            } else {
                robot.launcherRight.setPower(0);
                robot.launcherLeft.setPower(0);
            }

            if (gamepad2.dpad_up) {
                robot.ramp.setPower(1);
            } else if (gamepad2.dpad_down){
                robot.ramp.setPower(-1);
            } else {
                robot.ramp.setPower(0);
            }
            if (gamepad2.y) {
                robot.wheelIntake.setPosition(1);
            } else if (gamepad2.a){
                robot.wheelIntake.setPosition(0);
            } else {
                robot.wheelIntake.setPosition(0.5);
            }


            if (gamepad2.right_trigger > 0) {
                robot.flicker.setPosition(0);
            } else {
                robot.flicker.setPosition(0.6);
            }
            telemetry.update();
        }
    }
}
