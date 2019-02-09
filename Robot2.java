package frc.robot;

//importing libraries
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DMC60;
import edu.wpi.first.wpilibj.DigitalInput;

public class Robot extends TimedRobot {
  private Joystick logitechController = new Joystick(1);
  private String driveState;
  private String elevState;
  private String intakeState;
  //private BuiltInAccelerometer accel = new BuiltInAccelerometer();
  //private DifferentialDrive m_drive;
  //private DifferentialDrive m_intake;
  private final Timer m_timer = new Timer();
  DigitalInput limitSwitch = new DigitalInput(2);
  

//motor set up
  @Override
  public void robotInit() {
    //PWMTalonSRX m_frontLeft = new PWMTalonSRX(0);
    //PWMVictorSPX m_rearLeft = new PWMVictorSPX(1);
    //SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

    //PWMTalonSRX m_frontRight = new PWMTalonSRX(8);
    //PWMVictorSPX m_rearRight = new PWMVictorSPX(9);

    //SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);
    //m_drive = new DifferentialDrive(m_left, m_right);

    //DMC60 intake = new DMC60(2);
    //intake.set(1);
    //m_intake = new DifferentialDrive(intake, intake);

    //change port 3 and 4 to port 6 and 7 for troublesooting
    //port 3 has been causing problems thus far
    PWMVictorSPX elevLeft = new PWMVictorSPX(6); //causing problems from port 3 to 5 to 6
    
    PWMVictorSPX elevRight = new PWMVictorSPX(7); //connection works perfectly fine no matter what

    CameraServer.getInstance().startAutomaticCapture();

    logitechController = new Joystick(1);
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() { 
  }
  
  public void telopPeriodic() { //needs to be troubleshooting to use different buttons/triggers
      leftButton = logitechController.getButton(1);
      rightButton = logitechController.getButton(2);
      if (leftButton == true) {
          elevLeft.set(1);
          elevRight.set(1);
      }
      else if (rightButton == true) {
          elevLeft.set(-1);
          elevRight.set(-1);
      }
      else {
          elevLeft.set(0);
          elevRight.set(0);
      }
    }