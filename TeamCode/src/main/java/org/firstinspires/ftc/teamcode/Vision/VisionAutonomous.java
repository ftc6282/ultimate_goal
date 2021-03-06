package org.firstinspires.ftc.teamcode.Vision;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.MecanumAutonomous;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name="BlueVisionTest", group="Pushbot")
public class VisionAutonomous extends MecanumAutonomous {

    OpenCvCamera webcam;
    VisionPipeline pipeline;

    @Override
    public void runOpMode() {
        robot.initialize(hardwareMap);
        robot.resetEncoders();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "LogitechWebcam"), cameraMonitorViewId);
        pipeline = new VisionPipeline();
        webcam.setPipeline(pipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
            }
        });

        while (!isStarted()) {
            telemetry.addData("Yellowness: ", pipeline.getAnalysis());
            telemetry.addData("Rings: ", pipeline.getPosition());
            telemetry.update();
        }


        telemetry.addLine("Final Answer: " + pipeline.getPosition());
        telemetry.update();

        sleep(5000);
    }
}