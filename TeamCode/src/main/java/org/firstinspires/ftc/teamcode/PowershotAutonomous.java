package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="PowershotAutonomous", group="Pushbot")
public class PowershotAutonomous extends MecanumAutonomous {

    @Override
    public void runOpMode() {

        robot.initialize(hardwareMap);

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.resetEncoders();

        waitForStart();

        sleep(500);
        drive(DRIVE_SPEED, 25 , 5 );
        sleep(1000);
        faceAngle(0,2);

        robot.launcherLeft.setPower(1);
        robot.launcherRight.setPower(1);
        sleep(3000);
        robot.flicker.setPosition(0);
        sleep(1000);
        robot.flicker.setPosition(0.6);
        strafeLeft(DRIVE_SPEED, 4, 2);

        sleep(3000);
        robot.flicker.setPosition(0);
        sleep(1000);
        robot.flicker.setPosition(0.6);
        sleep(500);
        strafeLeft(DRIVE_SPEED, 4, 2);


        sleep(3000);
        robot.flicker.setPosition(0);
        sleep(1000);
        robot.flicker.setPosition(0.6);
        sleep(500);
        robot.launcherRight.setPower(0);
        robot.launcherLeft.setPower(0);
        sleep(1000);

        drive(DRIVE_SPEED,7, 4);
        sleep(500);

    }
}