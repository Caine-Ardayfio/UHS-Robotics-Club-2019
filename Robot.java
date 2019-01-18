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
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.cameraserver.CameraServer;
/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private Joystick logitechController = new Joystick(0);

  private int driveState = 1;
  
  @Override
  public void robotInit() {
    m_myRobot = new DifferentialDrive(new PWMTalonSRX(0), new PWMTalonSRX(1));//, new PWMVictorSPX(2), new PWMVictorSPX(3));

    CameraServer.getInstance().startAutomaticCapture();
  }

  @Override
  public void teleopPeriodic() {
    updateToggle();
    if(driveState == 1){
      m_myRobot.arcadeDrive(logitechController.getRawAxis(1),logitechController.getRawAxis(4));
    }
    if(driveState == 2){
      m_myRobot.tankDrive(logitechController.getRawAxis(1), logitechController.getRawAxis(5));
    }
    if(driveState == 3){
      m_myRobot.arcadeDrive(logitechController.getRawAxis(1),logitechController.getRawAxis(0));
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
