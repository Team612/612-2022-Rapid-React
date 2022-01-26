// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  private final Servo one = new Servo(1);
  private final Servo two = new Servo(1);
  private final Servo three = new Servo(1);
  private final Servo four = new Servo(1);
  private final Servo five = new Servo(2);
  private final WPI_TalonSRX shoulder = new WPI_TalonSRX(Constants.Talon);

  
  public Intake() {
    
  }
  public static void ServoOpen(Servo one, Servo two) {
    //opens the claw servo
    one.setAngle(90);
    two.setAngle(90);
  }
  
  public static void ServoClose(Servo one, Servo two) {
    //closes the claw servo
    one.setAngle(270);
    two.setAngle(270);
  }
  public static void TalonFlex() {
    //moves the claw on a fixed point
    .joystick();
  }

  public static void Wrist() {
    //dynamically moves the wrist for the second claw
    
  }

  
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
