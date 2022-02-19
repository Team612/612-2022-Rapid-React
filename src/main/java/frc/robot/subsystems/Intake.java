// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  private final Servo topLeft = new Servo(0);
  private final Servo topRight = new Servo(1);
  private final Servo bottomLeft = new Servo(2);
  private final Servo bottomRight = new Servo(3);
  private final Servo wrist = new Servo(4);
  private final Talon arm = new Talon(Constants.Talon);
  private final Ultrasonic echo = new Ultrasonic(0, 1);
  private double DIST_THRESH = 10;

  public Intake() {}
  public void TopServoOpen() {
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
  }
  public void TalonFlex(double speed) {
    //moves on a fixed point
    arm.set(speed);
  }
  public void WristOpen() {
    //dynamically moves the wrist for the second claw
    wrist.setAngle(120);
  }
  public void WristClose() {
    //dynamically moves the wrist for the second claw
    wrist.setAngle(0);
  }

  public void closeByDefault() {
    if (echo.getRangeInches() <= DIST_THRESH) {
      BottomServoClose();
      TopServoClose();
    }
    echo.setAutomaticMode(true);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
