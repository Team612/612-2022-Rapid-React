// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  private final Servo topLeft = new Servo(Constants.topLeftChannel);
  private final Servo topRight = new Servo(Constants.topRightChannel);
  private final Servo bottomLeft = new Servo(Constants.bottomLeftChannel);
  private final Servo bottomRight = new Servo(Constants.bottomRightChannel);
  private final CANSparkMax wrist = new CANSparkMax(Constants.wristChannel, MotorType.kBrushless);
  private final Talon arm = new Talon(Constants.Talon);
  private final Ultrasonic topEcho = new Ultrasonic(Constants.echoTop[0], Constants.echoTop[1]);
  private final Ultrasonic bottomEcho = new Ultrasonic(Constants.echoBottom[0], Constants.echoBottom[1]);
  private double DIST_THRESH = Constants.distanceThresh;

  public Intake() {}
  public void TopServoOpen() {
    //opens the top claw
    topLeft.setAngle(0);
    topRight.setAngle(180);
  }
  public void BottomServoOpen() {
    //opens the bottom claw
    bottomLeft.setAngle(0);
    bottomRight.setAngle(180);
  }
  public void TopServoClose() {
    //closes the top claw
    topLeft.setAngle(180);
    topRight.setAngle(0);
  }
  public void BottomServoClose() {
    //closes the bottom claw
    bottomLeft.setAngle(180);
    bottomRight.setAngle(0);
  }
  public void TalonFlex(double speed) {
    //moves on a fixed point
    arm.set(speed);
  }
  public void wristFlex(double speed) {
    wrist.set(speed);
  }
  public double wristCheck() {
    return wrist.getEncoder().getPosition();
  }
  public void topCloseByDefault() {
    if (topEcho.getRangeInches() <= DIST_THRESH) {
      TopServoClose();
    }
    topEcho.setAutomaticMode(true);
  }
  public void bottomCloseByDefault() {
    if (bottomEcho.getRangeInches() <= DIST_THRESH) {
      BottomServoClose();
    }
    bottomEcho.setAutomaticMode(true);
  }
  public double servoCheck() {
    return topLeft.getAngle();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
