// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  /*private final Servo topLeft = new Servo(1);
  private final Servo topRight = new Servo(1);
  private final Servo bottomLeft = new Servo(1);
  private final Servo bottomRight = new Servo(1); 
  private final Servo wrist = new Servo(2);*/


  private final int TOP_POSITION = 0;
  
  private final int BOTTOM_POSITION = 1;
  private final WPI_TalonSRX shoulder = new WPI_TalonSRX(Constants.Talon_arm);
  
  
  public Intake() {
    shoulder.getSensorCollection().setQuadraturePosition(0, 10);
  }

  public int getEncoder(){
    return shoulder.getSensorCollection().getQuadraturePosition();
  }

  public void setEncoder(){ 
    if (shoulder.getSensorCollection().isFwdLimitSwitchClosed()){ //forward goes to the bottom
      shoulder.getSensorCollection().setQuadraturePosition(TOP_POSITION, 10);
    }
    if (shoulder.getSensorCollection().isRevLimitSwitchClosed()){ //backwards goes to the top
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
    // moves on a fixed point
    if(isLimitTriggered()){
      shoulder.set(0);
    }
    else{
      shoulder.set(speed);
    }
  }

  /*public void TopServoOpen() {
    //opens the top claw
    topLeft.setAngle(90);
    topRight.setAngle(-90);
  }
  public void BottomServoOpen() {
    //opens the bottom claw
    bottomLeft.setAngle(90);
    bottomRight.setAngle(-90);
  }
  public void TopServoClose() {
    //closes the top claw
    topLeft.setAngle(270);
    topRight.setAngle(-270);
  }
  public void BottomServoClose() {
    //closes the bottom claw
    bottomLeft.setAngle(270);
    bottomRight.setAngle(-270);
  }*/
  
  /*public void WristOpen() {
    //dynamically moves the wrist for the second claw
    wrist.setAngle(120);
  }
  public void WristClose() {
    //dynamically moves the wrist for the second claw
    wrist.setAngle(0);
  }*/

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
