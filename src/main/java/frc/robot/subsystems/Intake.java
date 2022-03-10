// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  
  private final Servo bottomLeft;
  private final Servo bottomRight;
 
  private final int TOP_POSITION = 0;
  private final double DEADZONE = 0.1;

  public boolean servoOpen = false;
  public boolean servoClose = false;
  
  private final int BOTTOM_POSITION = 1;
  private final WPI_TalonSRX shoulder;

  private final Ultrasonic your_mother;
 
  
  public Intake() {
    shoulder = new WPI_TalonSRX(Constants.Talon_arm);
    shoulder.getSensorCollection().setQuadraturePosition(0, 10);
    shoulder.setNeutralMode(NeutralMode.Brake);
    bottomLeft = new Servo(Constants.bottom_servos[0]);
    bottomRight = new Servo(Constants.bottom_servos[1]);
    your_mother = new Ultrasonic(1, 2);
    your_mother.setEnabled(true);

    //shoulder.setSafetyEnabled(true); 
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

  public boolean isBottomOpened(){
    if(bottomLeft.getAngle() == 90 && bottomRight.getAngle() == 90){
      return true;
    }
    return false;
  }

  public boolean isBottomClosed(){
    if(bottomLeft.getAngle() == 180 && bottomRight.getAngle() == 0){
      return true;
    }
    return false;
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    System.out.println("ultrasonic: " + your_mother.getRangeInches());
  }

}
