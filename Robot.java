/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;
import java.lang.Math;

public class Robot extends TimedRobot {
  //Controllers
  private Joystick logitechController = new Joystick(1);
  private Joystick logitechJoystick = new Joystick(0);

  //Auxiliary
  private BuiltInAccelerometer accel = new BuiltInAccelerometer();
  private DifferentialDrive m_drive;
  private final Timer m_timer = new Timer();
  DigitalInput LimitSwitch_bottom = new DigitalInput(2);

  //Motor Controllers
  private PWMVictorSPX elevLeft = new PWMVictorSPX(3);
  private PWMVictorSPX elevRight = new PWMVictorSPX(4);
  private PWMVictorSPX intake = new PWMVictorSPX(2);

  //Variables
  private int elevState = 0;

  @Override
  public void robotInit() {
    //Initialize Drive Train
    PWMVictorSPX m_frontLeft = new PWMVictorSPX(0);
    PWMVictorSPX m_rearLeft = new PWMVictorSPX(1);
    SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

    PWMVictorSPX m_frontRight = new PWMVictorSPX(8);
    PWMVictorSPX m_rearRight = new PWMVictorSPX(9);
    SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);
    m_drive = new DifferentialDrive(m_left, m_right);   

    //Initialize Camera
    CameraServer.getInstance().startAutomaticCapture();
  }

  @Override
  public void teleopPeriodic() {
    System.out.println(accel.getX() + ", " + accel.getY() + ", " + accel.getZ());
    //Elevator
    System.out.println(LimitSwitch_bottom.get());
    /*
      //Level 3
      if(elevState == 1){
        m_timer.reset();
        m_timer.start();
        elevLeft.set(-1);
        elevRight.set(.95);
        //Should go up
        if(m_timer.get() >= 6.0){
         elevLeft.set(0);
         elevRight.set(0);
        }
      }
      
      //Level 2
      else if(elevState == 2){
        m_timer.reset();
        m_timer.start();
        //Should go up
        if(m_timer.get() < 4){
          elevLeft.set(-1);
          elevRight.set(.95);
        }
        else{
         elevLeft.set(0);
          elevRight.set(0);
        }
      }

      //Level 1
      else if(elevState == 3){
        m_timer.reset();
        m_timer.start();
        //Should go up
        if(m_timer.get() > 2){
          elevLeft.set(0);
          elevRight.set(0);
      }
        else{
          elevLeft.set(-1);
          elevRight.set(0.95);
        }
      }
      
      //All the way down-trigger pressed
      else if(elevState == 4 && LimitSwitch_bottom.get()){
        if(m_timer.get() < 8){
          elevLeft.set(1);
          elevRight.set(-1);
        }
        else{
          elevLeft.set(0);
          elevRight.set(0);
        }
      }
      
        
      
    //Limit Switch
    else if(!LimitSwitch_bottom.get()){
      elevLeft.set(0);
      elevRight.set(0);
    } */
    /*

      if(logitechJoystick.getRawButton(8) == true){ 
        elevState = 1;
      }
      else if(logitechJoystick.getRawButton(10) == true){
         elevState = 2;
      }
      else if (logitechJoystick.getRawButton(12)== true){
        elevState = 3;
      }
      else if(logitechJoystick.getRawButton(1) == true){
        elevState = 4;
      } */
      
      //main elevator backup/manual code
      if(logitechJoystick.getRawAxis(1) > 0.5){
        elevLeft.set(-1);
        elevRight.set(.95);
        }
      else if(logitechJoystick.getRawAxis(1) < -0.5){
        elevLeft.set(1);
        elevRight.set(-1);
      }
      else{
        elevLeft.set(0);
        elevRight.set(0);
      }

      

     //Intake
     if(logitechJoystick.getRawButton(6)){ intake.set(1); }
     else if(logitechJoystick.getRawButton(5)){ intake.set(-1); }
     else{ intake.set(0); }

    //Inverse sine drive
     double logSpeed1;
     double logSpeed2;
     double rawSpeed1 = logitechController.getRawAxis(1);
     double rawSpeed2 = logitechController.getRawAxis(5);
    
     //logSpeed1 = Math.asin(rawSpeed1 * rawSpeed1);
     //logSpeed2 = Math.asin(rawSpeed2 * rawSpeed2);
     //logSpeed1 = Math.asin(rawSpeed1);
     //logSpeed2 = Math.asin(rawSpeed2);

      //Setting up Logarithmic Drive 
      if (rawSpeed1 >= 0){ 
        logSpeed1 = (rawSpeed1 * rawSpeed1 * .8); } 
      else { 
        logSpeed1 = -(rawSpeed1 * rawSpeed1 *.8); } 
      if (rawSpeed2 >= 0){ 
        logSpeed2 = (rawSpeed2 * rawSpeed2 * .8); } 
      else { 
        logSpeed2 = -(rawSpeed2 * rawSpeed2 * .8); }

     System.out.println(logSpeed1);
     System.out.println(logSpeed2);
     m_drive.tankDrive(-logSpeed1, -logSpeed2);
  }
}