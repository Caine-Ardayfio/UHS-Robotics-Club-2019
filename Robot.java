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
  private Joystick logitechController = new Joystick(1);
  private Joystick logitechJoystick = new Joystick(0);

  private BuiltInAccelerometer accel = new BuiltInAccelerometer();
  private DifferentialDrive m_drive;
  private final Timer m_timer = new Timer();
  DigitalInput LimitSwitch_bottom = new DigitalInput(2);
  DigitalInput LimitSwitch_top = new DigitalInput(3);
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
  public void teleopPeriodic() {
    System.out.println(accel.getX() + ", " + accel.getY() + ", " + accel.getZ());
    //Elevator
    if(logitechJoystick.getRawButton(3) && !LimitSwitch_bottom.get()){ 
      elevRight.set(.95);
      elevLeft.set(-1); 
     }
     else if(logitechJoystick.getRawButton(4) && !LimitSwitch_top.get()){
      elevRight.set(-1);
      elevLeft.set(1); 
     }
     else{
       elevRight.set(0);
       elevLeft.set(0);
     }
     //Intake
     if(logitechJoystick.getRawButton(6)){ 
      intake.set(1);
     }
     else if(logitechJoystick.getRawButton(5)){
      intake.set(-1);
     }
     else{
       intake.set(0);
     }
     //Drive
     m_drive.tankDrive(logitechController.getRawAxis(5),logitechController.getRawAxis(1));
  }
}