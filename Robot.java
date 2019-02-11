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
import edu.wpi.first.wpilibj.DMC60;
import edu.wpi.first.wpilibj.DigitalInput;

public class Robot extends TimedRobot {
  private Joystick logitechController = new Joystick(0);
  private String driveState;
  private String elevState;
  private String intakeState;
  
  private BuiltInAccelerometer accel = new BuiltInAccelerometer();
  private DifferentialDrive m_drive;
  private final Timer m_timer = new Timer();
  DigitalInput limitSwitch = new DigitalInput(2);
  
  private PWMVictorSPX elevLeft = new PWMVictorSPX(3);
  private PWMVictorSPX elevRight = new PWMVictorSPX(4);
  private DMC60 intake = new DMC60(2);

  @Override
  public void robotInit() {
    PWMTalonSRX m_frontLeft = new PWMTalonSRX(0);
    PWMVictorSPX m_rearLeft = new PWMVictorSPX(1);
    SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

    PWMTalonSRX m_frontRight = new PWMTalonSRX(8);
    PWMVictorSPX m_rearRight = new PWMVictorSPX(9);

    SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);
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
    elevRight.set(.95);
    elevLeft.set(-1); //speed is 100%
    
    //anything below is not being used
    // updateToggle();
    // switch(elevState){
    //   case "Forward":
    //     elevLeft.set(1); //speed is 100%
    //     elevRight.set(-1);
    //     break;
    //   case "None":
    //     elevLeft.set(0); //speed is 0%
    //     elevRight.set(0);
    //     break;
    //   case "Backward":
    //     elevLeft.set(-1); //speed is 100%
    //     elevRight.set(1);
    //     break;
    //}
    // switch(driveState){
    // case "semi-arcade":
    //   //A Semi-Arcade-Drive, left joystick controls forward/backward movement, right joystick controls side-to-side movement
    //   m_drive.arcadeDrive(logitechController.getRawAxis(1), logitechController.getRawAxis(4));
    //   break;
    // case "tank-drive":
    //   //B Tank-Drive, left joystick controls left wheels, right joystick controls right wheels
    //   m_drive.tankDrive(logitechController.getRawAxis(5),logitechController.getRawAxis(1));
    //   break;
    // case "arcade-drive":    
    //   //X Aracde-Drive, left joystick controls left wheels, right wheels
    //   m_drive.arcadeDrive(logitechController.getRawAxis(1), logitechController.getRawAxis(0));
    //   break;
    // }
    

}
  public void updateToggle()
    {
      if(!logitechController.getRawButton(6)&&!logitechController.getRawButton(5)||!limitSwitch.get()){
        elevState = "None";
      }
      else if(logitechController.getRawButton(2)){ //test the controller (from 5 to 6)
       elevState = "Forward";
      }
      else if(logitechController.getRawButton(3)){
       elevState = "Backward";
      }
      
      if(0.9 > logitechController.getRawAxis(2)&&0.9 > logitechController.getRawAxis(3)){
        intakeState = "None";
       } 
       else if(logitechController.getRawAxis(3) >= 0.91){
        intakeState = "Forward";
       }
       else if(logitechController.getRawAxis(2) >= 0.91){
         intakeState = "Backward";
       }

        if(logitechController.getRawButton(1)){
            driveState = "semi-arcade";
        }
        else if(logitechController.getRawButton(2)){
          driveState = "tank-drive";
        }
        else if(logitechController.getRawButton(3)){
          driveState = "arcade-drive";
        }
    }
}