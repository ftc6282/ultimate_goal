package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public abstract class MecanumAutonomous extends LinearOpMode {

    protected MecanumHardware robot = new MecanumHardware();

    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1120;    // eg: HD Hex Motor Planetary 20:1
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;

    public void drive(double speed, double inches, double timeoutS) {
        int newFrontLeftTarget;
        int newBackLeftTarget;
        int newFrontRightTarget;
        int newBackRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = robot.frontLeft.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
            newBackLeftTarget = robot.backLeft.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
            newFrontRightTarget = robot.frontRight.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
            newBackRightTarget = robot.backRight.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
            robot.frontLeft.setTargetPosition(newFrontLeftTarget);
            robot.backLeft.setTargetPosition(newBackLeftTarget);
            robot.frontRight.setTargetPosition(newFrontRightTarget);
            robot.backRight.setTargetPosition(newBackRightTarget);

            // Turn On RUN_TO_POSITION
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.frontLeft.setPower(Math.abs(speed));
            robot.backLeft.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            robot.frontRight.setPower(Math.abs(speed));
            robot.backRight.setPower(Math.abs(speed));
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.frontLeft.isBusy() && robot.backLeft.isBusy() && robot.frontRight.isBusy() && robot.backRight.isBusy())) {

                writeTargetPositionTelemetry(newFrontLeftTarget, newBackLeftTarget, newFrontRightTarget, newBackRightTarget);
            }
            stopDriveMotors();
        }
    }

    public void turn(double speed, double rightInches, double leftInches, double timeoutS) {
        int newFrontLeftTarget;
        int newBackLeftTarget;
        int newFrontRightTarget;
        int newBackRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = robot.frontLeft.getCurrentPosition() + (int) (-leftInches * COUNTS_PER_INCH);
            telemetry.addData("frontleft", robot.frontLeft.getCurrentPosition());
            newBackLeftTarget = robot.backLeft.getCurrentPosition() + (int) (-leftInches * COUNTS_PER_INCH);
            telemetry.addData("backleft", robot.backLeft.getCurrentPosition());
            newFrontRightTarget = robot.frontRight.getCurrentPosition() + (int) (-rightInches * COUNTS_PER_INCH);
            telemetry.addData("frontright", robot.frontRight.getCurrentPosition());
            newBackRightTarget = robot.backRight.getCurrentPosition() + (int) (-rightInches * COUNTS_PER_INCH);
            telemetry.addData("backright", robot.backRight.getCurrentPosition());
            robot.frontLeft.setTargetPosition(newFrontLeftTarget);
            robot.backLeft.setTargetPosition(newBackLeftTarget);
            robot.frontRight.setTargetPosition(newFrontRightTarget);
            robot.backRight.setTargetPosition(newBackRightTarget);

            // Turn On RUN_TO_POSITION
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.frontLeft.setPower(Math.abs(speed));
            robot.backLeft.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            robot.frontRight.setPower(Math.abs(speed));
            robot.backRight.setPower(Math.abs(speed));
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.frontLeft.isBusy() && robot.backLeft.isBusy() && robot.frontRight.isBusy() && robot.backRight.isBusy())) {

                writeTargetPositionTelemetry(newFrontLeftTarget, newBackLeftTarget, newFrontRightTarget, newBackRightTarget);
            }
            stopDriveMotors();
        }
    }

    public void strafeLeft(double speed, double inches, double timeoutS) {
        int newFrontLeftTarget;
        int newBackLeftTarget;
        int newFrontRightTarget;
        int newBackRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = robot.frontLeft.getCurrentPosition() + (int) (-inches * COUNTS_PER_INCH);
            newBackLeftTarget = robot.backLeft.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
            newFrontRightTarget = robot.frontRight.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
            newBackRightTarget = robot.backRight.getCurrentPosition() + (int) (-inches * COUNTS_PER_INCH);
            robot.frontLeft.setTargetPosition(newFrontLeftTarget);
            robot.backLeft.setTargetPosition(newBackLeftTarget);
            robot.frontRight.setTargetPosition(newFrontRightTarget);
            robot.backRight.setTargetPosition(newBackRightTarget);

            // Turn On RUN_TO_POSITION
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.frontLeft.setPower(Math.abs(speed));
            robot.backLeft.setPower(Math.abs(speed));
            robot.frontRight.setPower(Math.abs(speed));
            robot.backRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.frontLeft.isBusy() && robot.backLeft.isBusy() && robot.frontRight.isBusy() && robot.backRight.isBusy())) {

                // Display it for the driver.
                writeTargetPositionTelemetry(newFrontLeftTarget, newBackLeftTarget, newFrontRightTarget, newBackRightTarget);
            }
            stopDriveMotors();
        }
    }

    public void faceAngle(float targetAngle){

        while (!isStopRequested() && !robot.gyro.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }

        telemetry.addData("Mode", "waiting for start");
        telemetry.addData("imu calib status", robot.gyro.getCalibrationStatus().toString());


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

        while(orientation.firstAngle != targetAngle) {
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

    public void strafeRight(double speed, double inches, double timeoutS, boolean extraFrontRightPower)
    {
        int newFrontLeftTarget;
        int newBackLeftTarget;
        int newFrontRightTarget;
        int newBackRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = robot.frontLeft.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
            newBackLeftTarget = robot.backLeft.getCurrentPosition() + (int) (-inches * COUNTS_PER_INCH);
            newFrontRightTarget = robot.frontRight.getCurrentPosition() + (int) (-inches * COUNTS_PER_INCH);
            newBackRightTarget = robot.backRight.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
            robot.frontLeft.setTargetPosition(newFrontLeftTarget);
            robot.backLeft.setTargetPosition(newBackLeftTarget);
            robot.frontRight.setTargetPosition(newFrontRightTarget);
            robot.backRight.setTargetPosition(newBackRightTarget);

            // Turn On RUN_TO_POSITION
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.frontLeft.setPower(Math.abs(speed));
            robot.backLeft.setPower(Math.abs(speed));
           if (extraFrontRightPower) {
                robot.frontRight.setPower(Math.abs(speed + 0.05));
            } else {
                robot.frontRight.setPower(Math.abs(speed));
            }

            robot.backRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.frontLeft.isBusy() && robot.backLeft.isBusy() && robot.frontRight.isBusy() && robot.backRight.isBusy())) {

                // Display it for the driver.
                writeTargetPositionTelemetry(newFrontLeftTarget, newBackLeftTarget, newFrontRightTarget, newBackRightTarget);
            }
            // Stop all motion;
            stopDriveMotors();
        }
    }



    private void stopDriveMotors() {
        robot.frontLeft.setPower(0);
        robot.backLeft.setPower(0);
        robot.frontRight.setPower(0);
        robot.frontRight.setPower(0);

        // Turn off RUN_TO_POSITION
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void writeTargetPositionTelemetry(int newFrontLeftTarget, int newBackLeftTarget, int newFrontRightTarget, int newBackRightTarget) {
        telemetry.addData("Path1", "Running to %7d : %7d", newFrontLeftTarget, newBackLeftTarget, newFrontRightTarget, newBackRightTarget);
        telemetry.addData(
                "Path2",
                "Running at %7d : %7d : %7d : %7d",
                robot.frontLeft.getCurrentPosition(),
                robot.backLeft.getCurrentPosition(),
                robot.frontRight.getCurrentPosition(),
                robot.backRight.getCurrentPosition()
        );

        telemetry.update();
    }

    public void checkWheels(boolean forward, boolean backwards) {
        int newFrontLeftTarget;
        int newBackLeftTarget;
        int newFrontRightTarget;
        int newBackRightTarget;


        if (opModeIsActive()) {

            newFrontLeftTarget = 0;
            newBackLeftTarget = 0;
            newFrontRightTarget = 0;
            newBackRightTarget = 0;

            if (forward == true && backwards == false) {

                newFrontLeftTarget = robot.frontLeft.getCurrentPosition() + (int) (50 * COUNTS_PER_INCH);
                newBackLeftTarget = robot.backLeft.getCurrentPosition() + (int) (50 * COUNTS_PER_INCH);
                newFrontRightTarget = robot.frontRight.getCurrentPosition() + (int) (50 * COUNTS_PER_INCH);
                newBackRightTarget = robot.backRight.getCurrentPosition() + (int) (50 * COUNTS_PER_INCH);
                robot.frontLeft.setTargetPosition(newFrontLeftTarget);
                robot.backLeft.setTargetPosition(newBackLeftTarget);
                robot.frontRight.setTargetPosition(newFrontRightTarget);
                robot.backRight.setTargetPosition(newBackRightTarget);

            }
            else if(forward == false && backwards == true){

                newFrontLeftTarget = robot.frontLeft.getCurrentPosition() + (int) (-50 * COUNTS_PER_INCH);
                newBackLeftTarget = robot.backLeft.getCurrentPosition() + (int) (-50 * COUNTS_PER_INCH);
                newFrontRightTarget = robot.frontRight.getCurrentPosition() + (int) (-50 * COUNTS_PER_INCH);
                newBackRightTarget = robot.backRight.getCurrentPosition() + (int) (-50 * COUNTS_PER_INCH);
                robot.frontLeft.setTargetPosition(newFrontLeftTarget);
                robot.backLeft.setTargetPosition(newBackLeftTarget);
                robot.frontRight.setTargetPosition(newFrontRightTarget);
                robot.backRight.setTargetPosition(newBackRightTarget);

            }
            else if(forward == true && backwards == true){

                newFrontLeftTarget = robot.frontLeft.getCurrentPosition() + (int) (50 * COUNTS_PER_INCH);
                newBackLeftTarget = robot.backLeft.getCurrentPosition() + (int) (50 * COUNTS_PER_INCH);
                newFrontRightTarget = robot.frontRight.getCurrentPosition() + (int) (50 * COUNTS_PER_INCH);
                newBackRightTarget = robot.backRight.getCurrentPosition() + (int) (50 * COUNTS_PER_INCH);
                robot.frontLeft.setTargetPosition(newFrontLeftTarget);
                robot.backLeft.setTargetPosition(newBackLeftTarget);
                robot.frontRight.setTargetPosition(newFrontRightTarget);
                robot.backRight.setTargetPosition(newBackRightTarget);

                robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                runtime.reset();
                robot.frontLeft.setPower(Math.abs(DRIVE_SPEED));
                sleep(5000);
                robot.backLeft.setPower(Math.abs(DRIVE_SPEED));
                sleep(5000);
                robot.frontRight.setPower(Math.abs(DRIVE_SPEED));
                sleep(5000);
                robot.backRight.setPower(Math.abs(DRIVE_SPEED));
                sleep(5000);

                newFrontLeftTarget = robot.frontLeft.getCurrentPosition() + (int) (-50 * COUNTS_PER_INCH);
                newBackLeftTarget = robot.backLeft.getCurrentPosition() + (int) (-50 * COUNTS_PER_INCH);
                newFrontRightTarget = robot.frontRight.getCurrentPosition() + (int) (-50 * COUNTS_PER_INCH);
                newBackRightTarget = robot.backRight.getCurrentPosition() + (int) (-50 * COUNTS_PER_INCH);
                robot.frontLeft.setTargetPosition(newFrontLeftTarget);
                robot.backLeft.setTargetPosition(newBackLeftTarget);
                robot.frontRight.setTargetPosition(newFrontRightTarget);
                robot.backRight.setTargetPosition(newBackRightTarget);

                runtime.reset();
                robot.frontLeft.setPower(Math.abs(DRIVE_SPEED));
                sleep(5000);
                robot.backLeft.setPower(Math.abs(DRIVE_SPEED));
                sleep(5000);
                robot.frontRight.setPower(Math.abs(DRIVE_SPEED));
                sleep(5000);
                robot.backRight.setPower(Math.abs(DRIVE_SPEED));
                sleep(5000);
            }

            // Turn On RUN_TO_POSITION


            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.

            runtime.reset();
            robot.frontLeft.setPower(Math.abs(DRIVE_SPEED));
            sleep(5000);
            robot.backLeft.setPower(Math.abs(DRIVE_SPEED));
            sleep(5000);
            robot.frontRight.setPower(Math.abs(DRIVE_SPEED));
            sleep(5000);
            robot.backRight.setPower(Math.abs(DRIVE_SPEED));
            sleep(5000);


            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < 5) &&
                    (robot.frontLeft.isBusy() && robot.backLeft.isBusy() && robot.frontRight.isBusy() && robot.backRight.isBusy())) {

                // Display it for the driver.
                writeTargetPositionTelemetry(newFrontLeftTarget, newBackLeftTarget, newFrontRightTarget, newBackRightTarget);
            }
            // Stop all motion;
            stopDriveMotors();
        }
    }

}
