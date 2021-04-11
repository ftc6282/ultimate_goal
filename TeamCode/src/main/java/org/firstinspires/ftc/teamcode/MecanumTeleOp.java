package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


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

        float newZero = 0;

        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // make sure the imu gyro is calibrated before continuing.
        while (!isStopRequested() && !robot.gyro.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }

        telemetry.addData("Mode", "waiting for start");
        telemetry.addData("imu calib status", robot.gyro.getCalibrationStatus().toString());
        telemetry.update();

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addLine("Opmode Active");

            Orientation orientation = robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Angle 1", orientation.firstAngle);
            telemetry.addData("Angle 2", orientation.secondAngle);
            telemetry.addData("Angle 3", orientation.thirdAngle);

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
                telemetry.addData("frontright", robot.frontRight.getCurrentPosition());

                robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                telemetry.addData("frontleft", robot.frontLeft.getCurrentPosition());

                robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                telemetry.addData("backright", robot.backRight.getCurrentPosition());

                robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                telemetry.addData("backleft", robot.backLeft.getCurrentPosition());


                robot.frontRight.setTargetPosition(0);
                robot.frontLeft.setTargetPosition(0);
                robot.backRight.setTargetPosition(0);
                robot.backLeft.setTargetPosition(0);


                robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                telemetry.addData("frontright", robot.frontRight.getCurrentPosition());

                robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                telemetry.addData("frontleft", robot.frontLeft.getCurrentPosition());

                robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                telemetry.addData("backright", robot.backRight.getCurrentPosition());

                robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                telemetry.addData("backleft", robot.backLeft.getCurrentPosition());

            } else {
                robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                telemetry.addData("frontright", robot.frontRight.getCurrentPosition());

                robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                telemetry.addData("frontleft", robot.frontLeft.getCurrentPosition());

                robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                telemetry.addData("backright", robot.backRight.getCurrentPosition());

                robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                telemetry.addData("backleft", robot.backLeft.getCurrentPosition());

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
                int time = 10;

                int position1 = robot.launcherRight.getCurrentPosition();
                sleep(time);
                int position2 = robot.launcherRight.getCurrentPosition();

                double distance = position2 - position1;

                double speed = distance / time;
                double rps = speed * 1000 / 1120;
                telemetry.addData("rps: ", rps);
                robot.launcherRight.setPower(0.6);

                double target = 1.5;
                double current = rps;
                if(current < target){
                    robot.launcherRight.setPower(1);
                }else{
                    robot.launcherRight.setPower(0.6);
                }
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
                robot.wheelIntake.setPower(0.6);
            } else if (gamepad2.a){
                robot.wheelIntake.setPower(-0.6);
            } else {
                robot.wheelIntake.setPower(0);
            }


            if (gamepad2.right_trigger > 0) {
                robot.flicker.setPosition(0);
            } else {
                robot.flicker.setPosition(0.6);
            }

            if(gamepad1.a){
                faceAngle(newZero);
            }else if(gamepad1.dpad_right){
                newZero --;
                faceAngle(newZero);
            }else if(gamepad1.dpad_left){
                newZero++;
                faceAngle(newZero);
            }

            telemetry.update();
        }
    }

    public void faceAngle(float targetAngle){

        Orientation orientation = robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        telemetry.addData("Angle 1", orientation.firstAngle);
        telemetry.addData("Angle 2", orientation.secondAngle);
        telemetry.addData("Angle 3", orientation.thirdAngle);
        telemetry.update();

        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        PIDController pid = new PIDController(0.02, 0.004, 0, 15);
        pid.setTelemetry(telemetry);

        while(orientation.firstAngle != targetAngle && opModeIsActive() && !gamepad1.dpad_down) {
            orientation = robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            float currentAngle = orientation.firstAngle;
            double error = targetAngle - currentAngle;
            double motorPower = pid.control(error);


            // Makes it so if motorPower gets to like 1.0x10^-whatever, it just sets it to zero
            if(error > -1 && error < 1){
                break;
            }

            robot.frontLeft.setPower(-motorPower);
            robot.backLeft.setPower(-motorPower);
            robot.frontRight.setPower(motorPower);
            robot.backRight.setPower(motorPower);

            telemetry.update();

        }

        robot.frontLeft.setPower(0);
        robot.backLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.backRight.setPower(0);
    }
}
