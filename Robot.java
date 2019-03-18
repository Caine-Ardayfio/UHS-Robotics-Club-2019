/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.PWMVictorSPX;
//import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.AnalogInput;
//import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.cameraserver.CameraServer;
//import edu.wpi.first.hal.AccumulatorResult;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;
//import java.lang.Math;

public class Robot extends TimedRobot {
  //Controllers
  private Joystick logitechController = new Joystick(1);
  private Joystick logitechJoystick = new Joystick(0);

  //Auxiliary
  //private BuiltInAccelerometer accel = new BuiltInAccelerometer();
  private DifferentialDrive m_drive;
  private final Timer m_timer = new Timer();
  DigitalInput LimitSwitch_bottom = new DigitalInput(2);

  //Motor Controllers
  private PWMVictorSPX elevLeft = new PWMVictorSPX(3);
  private PWMVictorSPX elevRight = new PWMVictorSPX(4);
  private PWMVictorSPX intake = new PWMVictorSPX(2);
  private PWMVictorSPX dartOneMotor = new PWMVictorSPX(0);
  private PWMVictorSPX dartTwoMotor = new PWMVictorSPX(1);
  private PWMVictorSPX dartThreeMotor = new PWMVictorSPX(8);
  private PWMVictorSPX dartFourMotor = new PWMVictorSPX(9);

  @Override
  public void robotInit() {
    //Initialize Drive Train
    //PWMVictorSPX m_rearLeft = new PWMVictorSPX(1);
    //SpeedControllerGroup m_left = new SpeedControllerGroup( m_rearLeft);

   // PWMVictorSPX m_frontRight = new PWMVictorSPX(8);
    //PWMVictorSPX m_rearRight = new PWMVictorSPX(9);
    //SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);
   // m_drive = new DifferentialDrive(m_left, m_right);   

    //Initialize Camera
    CameraServer.getInstance().startAutomaticCapture();
  }
  
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }
  @Override
  public void autonomousPeriodic(){
    if(m_timer.get()<2){
      m_drive.tankDrive(1,1);
    }
    else if(m_timer.get()<2.2){
      m_drive.tankDrive(-1,1);
    }
    else{
      m_drive.stopMotor();
    }
  }
  
  @Override
  public void teleopPeriodic() {
    //Accelerometer
    //System.out.println(accel.getX() + ", " + accel.getY() + ", " + accel.getZ());
    //Elevator
    //System.out.println(LimitSwitch_bottom.get());

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

      //Setting up Logarithmic Drive 
      if (rawSpeed1 >= 0.1){ 
        logSpeed1 = (rawSpeed1 * rawSpeed1 * .8); } 
      else { 
        logSpeed1 = -(rawSpeed1 * rawSpeed1 *.8); } 
      if (rawSpeed2 >= 0.1){ 
        logSpeed2 = (rawSpeed2 * rawSpeed2 * .8); } 
      else { 
        logSpeed2 = -(rawSpeed2 * rawSpeed2 * .8); }

      if ( rawSpeed1 > 0.1 && rawSpeed2 < -0.1 ){
        logSpeed1 = (rawSpeed1 * rawSpeed1 *.5);
        logSpeed2 = -(rawSpeed2 * rawSpeed2 *.5);
      }
      else if( rawSpeed1 < 0.1 && rawSpeed2 > -0.1 ){
        logSpeed1 = -(rawSpeed1 * rawSpeed1 *.5);
        logSpeed2 = (rawSpeed2 * rawSpeed2 *.5);
      }
      //if(drive)
     //m_drive.tankDrive(-logSpeed1, -logSpeed2);

     AnalogInput dartOne;
     AnalogInput dartTwo;
     AnalogInput dartThree;
     AnalogInput dartFour;
     dartOne = new AnalogInput(0);
     dartTwo = new AnalogInput(1);
     dartThree = new AnalogInput(2);
     dartFour = new AnalogInput(3);
    // dart system
    // getValue is used to read how far extended the dart is (range: 0-3500, we want 400-3000 so it doesn't break)
    int rawOne = dartOne.getValue();
    int rawTwo = dartTwo.getValue();
    int rawThree = dartThree.getValue();
    int rawFour = dartFour.getValue();
     //double volts = dartOne.getVoltage();
     //int averageRaw = dartOne.getAverageValue();
     //double averageVolts = dartOne.getAverageVoltage();
     System.out.println(rawOne);
     System.out.println(rawTwo);
     System.out.println(rawThree);
     System.out.println(rawFour);

     dartOne.close();
     dartTwo.close();
     dartThree.close();
     dartFour.close();
    // extend group 1
     if (logitechController.getRawButton(1) == true) {
      if (rawOne > 3000){
        dartOneMotor.set(0);
      }
      else if (rawOne < 3000){
        dartOneMotor.set(.5);
      }
      if (rawTwo > 3000){
        dartTwoMotor.set(0);
      }
      else if (rawTwo < 3000){
        dartTwoMotor.set(.5);
      }
     }
    // retract group 1
     else if (logitechController.getRawButton(2) == true){
       if (rawOne < 400){
         dartOneMotor.set(0);
       }
       else if (rawOne > 400){
         dartOneMotor.set(-.5);
       }
       if (rawTwo < 400){
         dartTwoMotor.set(0);
       }
       else if (rawTwo >400){
         dartTwoMotor.set(-.5);
       }
     }
     //extend group 2
     else if (logitechController.getRawButton(3) == true){
      if (rawThree > 3000){
        dartThreeMotor.set(0);
      }
      else if (rawThree < 3000){
        dartThreeMotor.set(.5);
      }
      if (rawFour > 3000){
        dartFourMotor.set(0);
      }
      else if (rawFour < 3000){
        dartFourMotor.set(.5);
       }
     }
     //retact group 2
     else if (logitechController.getRawButton(4) == true){
      if (rawThree < 400){
        dartThreeMotor.set(0);
      }
      else if (rawThree > 400){
        dartThreeMotor.set(-.5);
      }
      if (rawFour < 400){
        dartFourMotor.set(0);
      }
      else if (rawFour >400){
        dartFourMotor.set(-.5);
      }
     }
     else{
       dartOneMotor.set(0);
       dartTwoMotor.set(0);
       dartThreeMotor.set(0);
       dartFourMotor.set(0);

     }
  }

}




