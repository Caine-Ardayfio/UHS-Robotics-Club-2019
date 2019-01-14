

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;

public class RobotTemplate extends SimpleRobot {

  RobotDrive chassis = new RobotDrive(1, 2);
  Joystick leftStick = new joystick(1);
  Joystick rightStik = new Joystick(2);

        public void autonomous() {
          chassis.setSafetyEnabled(false);
          chassis.drive(-0.5, 0.0);
          Timer.delay(2.0);
          chassis.drive(0.0, 0.0);
        }

        public void operatorControl() {
          chassis.setSafetyEnabled(true);
          while (isOperatorControl()&&isEnabled()){
            myDrive.arcadeDrive(driveStick.getY(),driveStick.getX());
            Timer.delay(0.01);
        }

        Thread m_visionThread;
    }

  @Override
  public void robotInit() {
    m_visionThread = new Thread(() -> {
      // Get the UsbCamera from CameraServer
      UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
      // Set the resolution
      camera.setResolution(640, 480);

      // Get a CvSink. This will capture Mats from the camera
      CvSink cvSink = CameraServer.getInstance().getVideo();
      // Setup a CvSource. This will send images back to the Dashboard
      CvSource outputStream
          = CameraServer.getInstance().putVideo("Rectangle", 640, 480);

      // Mats are very memory expensive. Lets reuse this Mat.
      Mat mat = new Mat();

      // This cannot be 'true'. The program will never exit if it is. This
      // lets the robot stop this thread when restarting robot code or
      // deploying.
      while (!Thread.interrupted()) {
        // Tell the CvSink to grab a frame from the camera and put it
        // in the source mat.  If there is an error notify the output.
        if (cvSink.grabFrame(mat) == 0) {
          // Send the output the error.
          outputStream.notifyError(cvSink.getError());
          // skip the rest of the current iteration
          continue;
        }
        // Put a rectangle on the image
        Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400),
            new Scalar(255, 255, 255), 5);
        // Give the output stream a new image to display
        outputStream.putFrame(mat);
        }
    });
    m_visionThread.setDaemon(true);
    m_visionThread.start();
}
}
