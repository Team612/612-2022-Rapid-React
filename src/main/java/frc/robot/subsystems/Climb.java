// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  /** Creates a new Climb. */
  //private final TalonSRX m_talon = new TalonSRX(Constants.Talon);
  private final CANSparkMax m_spark = new CANSparkMax(Constants.Talon, null);
  private final Servo leftServo = new Servo(0);
  private final Servo rightServo = new Servo(0);
  public Climb() {}

  public void setSpeed(double speed) {
    m_spark.set(speed);
  }
  public void TopServoOpen() {
    //opens the top claw
    leftServo.setAngle(0);
    rightServo.setAngle(180);
  }
  public void TopServoClose() {
    //closes the top claw
    leftServo.setAngle(180);
    rightServo.setAngle(0);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
