// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  // defines both Talons and Solenoids
  private Servo rightServo;
  private Servo leftServo;
  private final DoubleSolenoid piston3;
  private final DoubleSolenoid piston2;
  static Climb instance = null;
  private boolean climbOpened = false;

  private Compressor compressor;
  private boolean toggle_compressor = false;

  private Climb() {
    rightServo = new Servo(Constants.climb_servo[1]);
    leftServo = new Servo(Constants.climb_servo[0]);
    compressor = new Compressor(Constants.PCM_2, PneumaticsModuleType.CTREPCM);
    compressorOff();
    piston2 = new DoubleSolenoid(Constants.PCM_2, Constants.solenoidType, Constants.secondSolenoid[1],Constants.secondSolenoid[0]);

    piston3 = new DoubleSolenoid(Constants.PCM_2, Constants.solenoidType, Constants.thirdSolenoid[1],Constants.thirdSolenoid[0]);
    
    // piston1 = new DoubleSolenoid(Constants.PCM_2, Constants.solenoidType, Constants.firstSolenoid[1],
    //     Constants.firstSolenoid[0]);
  }

  public static Climb getInstance(){
    if(instance == null){
      instance = new Climb();
    }
    return instance;
  }

  public void extendArm() {
    // System.out.println("Climb.extendArm() start");
    //piston1.set(Value.kForward);
    piston3.set(Value.kForward);
    piston2.set(Value.kForward);
    // System.out.println("Climb.extendArm() end");
  }

  public double getCompressorVoltage(){
    return compressor.getAnalogVoltage();
  }

  public double getCompressorCurrent(){
    return compressor.getCurrent();
  }

  // Brings the piston in
  public void retractArm() {
    // System.out.println("Climb.retractArm() start");
    //
    //piston1.set(Value.kReverse);
    piston3.set(Value.kReverse);
    piston2.set(Value.kReverse);
    // System.out.println("Climb.retractArm() end");
  }

  public void setServosNeutral() {
    // System.out.println("Climb.setServosNeutral() start");
    leftServo.setDisabled();
    rightServo.setDisabled();
    // System.out.println("Climb.setServosNeutral() end");
  }


  // Opens servos
  public void servoClose() {
    // System.out.println("Climb.servoClose() start");
    leftServo.setAngle(180);
    rightServo.setAngle(0);
    climbOpened = false;
    // System.out.println("Climb.servoClose() end");
  }

  // Closes servos
  public void servoOpen() {
    // System.out.println("Climb.servoOpen() start");
    leftServo.setAngle(90);
    rightServo.setAngle(90);
    climbOpened = true;
    // System.out.println("Climb.servoOpen() end");
  }

  public boolean getClimbOpened(){
    return climbOpened;
  }

  
  public boolean getCompressorState(){
      return compressor.enabled();
  }
  
  public void compressorOn(){
      compressor.enableDigital();
      // System.out.println("compressor on");
      toggle_compressor = true;
  }

  public void compressorOff(){
      compressor.disable();
      // System.out.println("compressor off");

      toggle_compressor = false;
  }

  public boolean toggleCompressor(){
    return toggle_compressor;
  }


  public double getRightServoAngle(){
    return rightServo.getAngle();
  }

  public double getLeftServoAngle(){
    return leftServo.getAngle();
  }
 
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
