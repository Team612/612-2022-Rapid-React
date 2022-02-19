// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  //defines both Talons and Solenoids
  private WPI_TalonSRX pivotMotor = new WPI_TalonSRX(Constants.Talon);
  private Servo Servo1 = new Servo(Constants.Servos);
  private Servo Servo2 = new Servo(Constants.Servos);
  private final DoubleSolenoid piston = new DoubleSolenoid(Constants.PCM_2, Constants.solenoidType, Constants.firstSolenoid[0], Constants.firstSolenoid[1]);

  //Pushes the piston out
  public void extendArm(){
      piston.set(Value.kForward);
  }
  
  //Brings the piston in
  public void retractArm(){
      piston.set(Value.kReverse);
  }
  public void retractArmHang(){
    piston.set(Value.kReverse);
    pivotMotor.setNeutralMode(NeutralMode.Brake);
}


  //Pivots the pivot arms
  public void pivot(double rotate){
      //pivotMotor.set(TalonFXControlMode.PercentOutput, rotate);
      pivotMotor.set(ControlMode.PercentOutput, rotate);
  }
  //Opens servos
  public void ServoClose(){
      Servo1.setAngle(45);
      Servo2.setAngle(45);
      
  }
  //Closes servos
  public void ServoOpen(){
      Servo1.setAngle(0);
      Servo2.setAngle(0);
      

  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
