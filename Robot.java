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
import edu.wpi.first.cameraserver.CameraServer;
/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private Joystick logitechController = new Joystick(0);
  private int driveState;
  
  private DifferentialDrive m_drive;

  @Override
  public void robotInit() {
    //left_m_myRobot = new DifferentialDrive(new PWMTalonSRX(0), new PWMVictorSPX(1)); 
    //right_m_myRobot = new DifferentialDrive(new PWMTalonSRX(8), new PWMVictorSPX(9)); 
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
  public void teleopPeriodic() {
    updateToggle();
    switch(driveState){
    case 1:
      //A Semi-Arcade-Drive, left joystick controls forward/backward movement, right joystick controls side-to-side movement
      m_drive.arcadeDrive(logitechController.getRawAxis(1), logitechController.getRawAxis(4));
      break;
    case 2:
      //B Tank-Drive, left joystick controls left wheels, right joystick controls right wheels
      m_drive.tankDrive(logitechController.getRawAxis(1),logitechController.getRawAxis(5));
      break;
    case 3:    
      //X Aracde-Drive, left joystick controls left wheels, right wheels
      m_drive.arcadeDrive(logitechController.getRawAxis(1), logitechController.getRawAxis(0));
      break;
  }
}
  public void updateToggle()
    {
        if(logitechController.getRawButton(1)){
            driveState = 1;
        }
        if(logitechController.getRawButton(2)){
          driveState = 2;
        }
        if(logitechController.getRawButton(3)){
          driveState = 3;
        }
    }

}
