package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Vision.VisionPipeline;

@Autonomous(name="Wobble Powershot", group="Pushbot")
public class WobblePowershotAuton extends MecanumAutonomous {

    @Override
    public void runOpMode() {

        robot.initialize(hardwareMap);
        robot.startVisionProcessing();

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.resetEncoders();

        while(!this.isStarted()){
            telemetry.addData("Rings",  robot.pipeline.numRings);
            telemetry.update();
        }

        VisionPipeline.RingPosition numRings = robot.pipeline.numRings;
        robot.stopVisionProcessing();
        telemetry.addData("Rings", numRings);
        telemetry.update();

        sleep(500);
        drive(DRIVE_SPEED / 2,  30,8);
        sleep(200);
        faceAngle(0);

        robot.launcherRight.setPower(0.57);
        fireShot();
        strafeLeft(DRIVE_SPEED, 5, 1);
        faceAngle(0);
        fireShot();
        strafeLeft(DRIVE_SPEED, 4, 1);
        faceAngle(0);
        fireShot();
        robot.launcherRight.setPower(0);
        sleep(500);
        //faceAngle(0);

        if(numRings == VisionPipeline.RingPosition.FOUR){
            drive(DRIVE_SPEED, 28, 5);
            sleep(200);
            strafeRight(DRIVE_SPEED, 22, 2.5, false);
            sleep(500);
            robot.wobble.setPosition(0);
            sleep(200);
            drive(DRIVE_SPEED, -25, 2);

        }else if(numRings == VisionPipeline.RingPosition.ONE){
            drive(DRIVE_SPEED, 18, 2);
            sleep(500);
            strafeRight(DRIVE_SPEED, 9, 1.5, false);
            sleep(500);
            robot.wobble.setPosition(0);
            sleep(500);
            drive(DRIVE_SPEED, -13, 1.5);

        }else{
            drive(DRIVE_SPEED, 7, 1);
            sleep(200);
            faceAngle(0);
            strafeRight(DRIVE_SPEED, 20, 2, false);
            robot.wobble.setPosition(0);
            sleep(200);
            strafeLeft(DRIVE_SPEED, 9, 1.5);
        }
        faceAngle(0);
    }

    private void fireShot() {
        sleep(3500);
        robot.flicker.setPosition(0.75);
        sleep(1000);
        robot.flicker.setPosition(1);
    }
}