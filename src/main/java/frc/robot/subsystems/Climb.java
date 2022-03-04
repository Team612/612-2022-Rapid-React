// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  //defines both Talons and Solenoids
  private WPI_TalonSRX pivotMotor = new WPI_TalonSRX(Constants.talon_pivot);

  private Servo rightServo = new Servo(Constants.right_servo);
  private Servo leftServo = new Servo(Constants.left_servo);
  private final DoubleSolenoid piston1 = new DoubleSolenoid(Constants.PCM_2, Constants.solenoidType, Constants.firstSolenoid[1], Constants.firstSolenoid[0]);
  private final DoubleSolenoid piston2 = new DoubleSolenoid(Constants.PCM_2, Constants.solenoidType, Constants.secondSolenoid[1] ,Constants.secondSolenoid[0]);
  //Pushes the piston out
  DutyCycleEncoder boreEncoder = new DutyCycleEncoder(0);
  public Climb(){
    pivotMotor.setNeutralMode(NeutralMode.Brake);
  }
  
  public void extendArm(){
      piston1.set(Value.kForward);
      piston2.set(Value.kForward);
      System.out.println("extend");
  }
  
  //Brings the piston in
  public void retractArm(){
      piston1.set(Value.kReverse);
      piston2.set(Value.kReverse);
      System.out.println("retract");

  }
  public void retractArmHang(){
    piston1.set(Value.kReverse);
    piston2.set(Value.kReverse);
    pivotMotor.setNeutralMode(NeutralMode.Brake);
    System.out.println("retract hang");

}


  //Pivots the pivot arms
  public void pivot(double rotate){
      //pivotMotor.set(TalonFXControlMode.PercentOutput, rotate);
      pivotMotor.set(ControlMode.PercentOutput, rotate);
  }
  //Opens servos
  public void ServoClose(){
      leftServo.setAngle(180);
      rightServo.setAngle(0);
      System.out.println("close");

  }
  //Closes servos
  public void ServoOpen(){
      leftServo.setAngle(90);
      rightServo.setAngle(90);
      System.out.println("open");


  }
  public void getBoreEncoder() {
    System.out.println("encoder dist: " + boreEncoder.getDistance());
    /*
    With the rev logo facing towards the user:
    clockwise is negative, counterclockwise is positive
    distance of 1 indicates one full rotation. there is no upper bound
    (i.e. if there's more than one rotation, the distance will return a value greater than 1)
    therefore, should take mod of distance and 1.0
    when rio is reset, it'll remove the whole number and do just the decimal
    negative acts weird: if it's at -1.56 and resets, it'll return to 0.4.
    */
    System.out.println("encoder freq: " + boreEncoder.getFrequency());
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
