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
  private PWMVictorSPX intake = new PWMVictorSPX(2);

  @Override
  public void robotInit() {
    PWMVictorSPX m_frontLeft = new PWMVictorSPX(0);
    PWMVictorSPX m_rearLeft = new PWMVictorSPX(1);
    SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

    PWMVictorSPX m_frontRight = new PWMVictorSPX(8);
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
    //Deep Space: 27 ft * 54 ft
    if(accel.getX()<1 || accel.getY()<1 || accel.getZ()<1){
      if(m_timer.get() < 1.6){
        m_drive.tankDrive(1,1);
      }
      else if(m_timer.get() > 1.9){
        elevLeft.set(-1);
        elevRight.set(1);
      }
      else if(m_timer.get() > 2.2){
        intake.set(1);
      }
    }
  }
  @Override
  public void teleopPeriodic() {
    System.out.println(accel.getX() + ", " + accel.getY() + ", " + accel.getZ());
    //Elevator
    if(logitechJoystick.getRawButton(3) /*&& !LimitSwitch_top.get()*/){ 
      //Elevator up one level
      m_timer.reset();
      m_timer.start();
      while(m_timer.get() < 1.0) {
        elevRight.set(.95);
        elevLeft.set(-1);
      }
     }
     else if(logitechJoystick.getRawButton(4) /* && !LimitSwitch_bottom.get()*/){
      //Elevator down one level
      m_timer.reset();
      m_timer.start();
      while(m_timer.get() < 1.0){
        elevRight.set(-1);
        elevLeft.set(1); 
      }
     }
     else if(logitechController.getRawButton(1) && !LimitSwitch_top.get()){
      //Elevator up two levels
      m_timer.reset();
      m_timer.start();
      while(m_timer.get() < 2.0){
        elevLeft.set(-1);
        elevRight.set(.95);
      }
     }
     else if(logitechController.getRawButton(2) && !LimitSwitch_bottom.get()){
      //Elevator down two levels
      m_timer.reset();
      m_timer.start();
      while(m_timer.get() < 2.0){
        elevLeft.set(1);
        elevRight.set(-1);
      }
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
     m_drive.tankDrive(-logitechController.getRawAxis(1),-logitechController.getRawAxis(5));
  }
}