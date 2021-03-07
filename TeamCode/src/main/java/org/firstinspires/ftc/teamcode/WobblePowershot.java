package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Vision.VisionPipeline;

@Autonomous(name="Wobble Powershot", group="Pushbot")
public class WobblePowershot extends MecanumAutonomous {

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
        drive(DRIVE_SPEED, 25 , 5 );
        sleep(200);
        faceAngle(0);

        robot.launcherLeft.setPower(1);
        robot.launcherRight.setPower(1);
        fireShot();
        strafeLeft(DRIVE_SPEED, 4, 2);
        fireShot();
        strafeLeft(DRIVE_SPEED, 4, 2);
        fireShot();
        robot.launcherRight.setPower(0);
        robot.launcherLeft.setPower(0);
        sleep(200);
        //faceAngle(0);

        if(numRings == VisionPipeline.RingPosition.FOUR){
            drive(DRIVE_SPEED, 35, 2);
            sleep(200);
            strafeRight(DRIVE_SPEED, 30, 3, false);
            robot.wobble.setPosition(0);
            sleep(200);
            drive(DRIVE_SPEED, -20, 2);
        }else if(numRings == VisionPipeline.RingPosition.ONE){
            drive(DRIVE_SPEED, 20, 1.5);
            sleep(200);
            strafeRight(DRIVE_SPEED, 12, 1.5, false);
            robot.wobble.setPosition(0);
            sleep(200);
            drive(DRIVE_SPEED, -10, 1);
        }else{
            drive(DRIVE_SPEED, 10, 1);
            sleep(200);
            strafeRight(DRIVE_SPEED, 27, 3, false);
            robot.wobble.setPosition(0);
            sleep(200);
            strafeLeft(DRIVE_SPEED, 10, 1);

        }
    }

    private void fireShot() {
        sleep(2000);
        robot.flicker.setPosition(0);
        sleep(1000);
        robot.flicker.setPosition(0.6);

    }


}