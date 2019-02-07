/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends TimedRobot {
  private Joystick logitechController = new Joystick(0);
  private int driveState;
  private String intakeState;
  private  BuiltInAccelerometer accel = new BuiltInAccelerometer();
  private DifferentialDrive m_drive;
  private DifferentialDrive m_intake;
  private final Timer m_timer = new Timer();
  
  @Override
  public void robotInit() {
    PWMTalonSRX m_frontLeft = new PWMTalonSRX(0);
    PWMVictorSPX m_rearLeft = new PWMVictorSPX(1);
    SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

    PWMTalonSRX m_frontRight = new PWMTalonSRX(8);
    PWMVictorSPX m_rearRight = new PWMVictorSPX(9);

    SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);

    PWMVictorSPX intake = new PWMVictorSPX(2);
    intake.set(1);
    
    m_intake = new DifferentialDrive(intake, intake);
    m_drive = new DifferentialDrive(m_left, m_right);
    CameraServer.getInstance().startAutomaticCapture();
  }

  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }
  @Override
  public void autonomousPeriodic() {
        // Drive for 2 seconds
        if (m_timer.get() < 2.0) {
          m_drive.arcadeDrive(0.5, 0.0); // drive forwards half speed
        } else {
          m_drive.stopMotor(); // stop robot
        }    
  }

  @Override
  public void teleopPeriodic() {
    System.out.println(accel.getX() + ", " + accel.getY() + ", " + accel.getZ());

    updateToggle();
    switch(intakeState){
      case "LBDown":
        m_intake.arcadeDrive(-1,0);
        break;
      case "None":
        m_intake.arcadeDrive(0,0);
        break;
      case "RBDown":
        m_intake.arcadeDrive(0,-1);
        break;
      
    }
    switch(driveState){
    case 1:
      //A Semi-Arcade-Drive, left joystick controls forward/backward movement, right joystick controls side-to-side movement
      m_drive.arcadeDrive(logitechController.getRawAxis(1), logitechController.getRawAxis(4));
      break;
    case 2:
      //B Tank-Drive, left joystick controls left wheels, right joystick controls right wheels
      m_drive.tankDrive(logitechController.getRawAxis(5),logitechController.getRawAxis(1));
      break;
    case 3:    
      //X Aracde-Drive, left joystick controls left wheels, right wheels
      m_drive.arcadeDrive(logitechController.getRawAxis(1), logitechController.getRawAxis(0));
      break;
    }


}
  public void updateToggle()
    {
      if(logitechController.getRawButton(5)){
       intakeState = "LBDown";
      }
      else if(logitechController.getRawButton(6)){
       intakeState = "RBDown";
      }
      else if(!logitechController.getRawButton(6)&&!logitechController.getRawButton(5)){
        intakeState = "None";
      }

        if(logitechController.getRawButton(1)){
            driveState = 1;
        }
        else if(logitechController.getRawButton(2)){
          driveState = 2;
        }
        else if(logitechController.getRawButton(3)){
          driveState = 3;
        }
    }

}
