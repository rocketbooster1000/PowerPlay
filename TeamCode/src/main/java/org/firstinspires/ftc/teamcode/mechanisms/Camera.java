package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.SleeveDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class Camera {
     private SleeveDetection sleeveDetection;
     private OpenCvCamera camera;
     private String webcamName = "Webcam 1";

     public void init(HardwareMap hardwareMap){
         int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
         camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
         sleeveDetection = new SleeveDetection();
         camera.setPipeline(sleeveDetection);

         camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
         {
             @Override
             public void onOpened()
             {
                 camera.startStreaming(320,240, OpenCvCameraRotation.SIDEWAYS_LEFT);
             }

             @Override
             public void onError(int errorCode) {}
         });
     }

     public int returnZone(){
         return sleeveDetection.getPositionSane();
     }

     public SleeveDetection.ParkingPosition returnZoneEnumerated(){
         return sleeveDetection.getPosition();
     }
}
