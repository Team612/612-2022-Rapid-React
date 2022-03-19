// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  
  private final Servo bottomLeft;
  private final Servo bottomRight;
 
  private final int TOP_POSITION = 0; // --> imo not necessaryx
  private final double DEADZONE = 0.1;

  public boolean servoOpen = false;
  public boolean servoClose = false;
  
  private final int BOTTOM_POSITION = 0; // --> imo not necessary
  private final WPI_TalonSRX shoulder;

  private final int ANGLE_DEADZONE = 100;

  /*
  - figure out angle
  - figure out encoder pos based off it 
  - same method, but after encoder val = ___, open servos 

  */

  private int calculate_target(double theta){
    return (int)(2048 * theta * 5/360);
  }
  
  public void autoOpen(double theta){
    System.out.println("autopen!");
    System.out.println("encoder: " + getEncoder());
    if(Math.abs(getEncoder() - calculate_target(theta)) <= ANGLE_DEADZONE){
      BottomServoOpen();
      System.out.println("grabber open!");
    }
  }

  public Intake() {
    shoulder = new WPI_TalonSRX(Constants.Talon_arm);
    shoulder.getSensorCollection().setQuadraturePosition(0, 10);
    shoulder.setNeutralMode(NeutralMode.Brake);
    bottomLeft = new Servo(Constants.bottom_servos[0]);
    bottomRight = new Servo(Constants.bottom_servos[1]);
  }

  public int getEncoder(){
    return shoulder.getSensorCollection().getQuadraturePosition();
  }

  

  public void setEncoder(){ 
    if (shoulder.getSensorCollection().isFwdLimitSwitchClosed()){ //forward goes to the bottom
      shoulder.getSensorCollection().setQuadraturePosition(TOP_POSITION, 10);
    }
    else if (shoulder.getSensorCollection().isRevLimitSwitchClosed()){ //backwards goes to the top
      shoulder.getSensorCollection().setQuadraturePosition(BOTTOM_POSITION, 10);
    }
  }

  public boolean isLimitTriggered(){
    if(shoulder.getSensorCollection().isFwdLimitSwitchClosed() || shoulder.getSensorCollection().isRevLimitSwitchClosed()){
      return true;
    }
    return false;
  }
  
  public void TalonFlex(double speed) {
    if(Math.abs(speed) < DEADZONE) speed = 0;
    shoulder.set(speed);
  }
  
  public boolean bottomlimitGoesOff() {
    return shoulder.getSensorCollection().isFwdLimitSwitchClosed();
  }

  public boolean upperLimitGoesOff(){
    return shoulder.getSensorCollection().isRevLimitSwitchClosed();
  }

  public void BottomServoOpen() {
    bottomLeft.setAngle(90);
    bottomRight.setAngle(90);
    servoOpen = true;
    servoClose = false;
  }

  public void BottomServoClose(){
    bottomLeft.setAngle(180);
    bottomRight.setAngle(0); 
    servoOpen = false;
    servoClose = true;
  }

  
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    setEncoder();
  }
}
