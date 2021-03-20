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
        drive(DRIVE_SPEED, 23 , 5 );
        sleep(200);
        faceAngle(0);

        robot.launcherLeft.setPower(1);
        robot.launcherRight.setPower(1);
        fireShot();
        strafeLeft(DRIVE_SPEED, 4, 1);
        faceAngle(0);
        fireShot();
        strafeLeft(DRIVE_SPEED, 4, 1);
        faceAngle(0);
        fireShot();
        robot.launcherRight.setPower(0);
        robot.launcherLeft.setPower(0);
        sleep(500);
        //faceAngle(0);

        if(numRings == VisionPipeline.RingPosition.FOUR){
            drive(DRIVE_SPEED, 40, 5);
            sleep(200);
            strafeRight(DRIVE_SPEED, 25, 3, false);
            sleep(500);
            robot.wobble.setPosition(0);
            sleep(200);
            drive(DRIVE_SPEED, -23, 2);

        }else if(numRings == VisionPipeline.RingPosition.ONE){
            drive(DRIVE_SPEED, 24, 1.5);
            sleep(500);
            strafeRight(DRIVE_SPEED, 10, 1.5, false);
            sleep(500);
            robot.wobble.setPosition(0);
            sleep(500);
            drive(DRIVE_SPEED, -12, 1);

        }else{
            drive(DRIVE_SPEED, 13, 1);
            sleep(200);
            faceAngle(0);
            strafeRight(DRIVE_SPEED, 25, 3, false);
            robot.wobble.setPosition(0);
            sleep(200);
            strafeLeft(DRIVE_SPEED, 10, 1);

        }
        faceAngle(0);
    }

    private void fireShot() {
        sleep(3000);
        robot.flicker.setPosition(0);
        sleep(1000);
        robot.flicker.setPosition(0.6);

    }


}