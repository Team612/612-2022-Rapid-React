// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.sim.SimServo;
import frc.robot.sim.SimTalon;

public class SimIntake extends SubsystemBase {
  public final SimServo m_leftServoClaw;
  public final SimServo m_rightServoClaw;
  public final SimTalon m_armTalon;
  private final ShuffleboardTab tab;
  private final NetworkTableEntry m_leftServoEntry;
  private final NetworkTableEntry m_rightServoEntry;
  private final NetworkTableEntry m_talonEntry;
  /** Creates a new SimIntake. */
  public SimIntake() {
    m_leftServoClaw = new SimServo(1);
    m_rightServoClaw = new SimServo(0);
    m_armTalon = new SimTalon(9);
    tab = Shuffleboard.getTab("Intake");
    m_leftServoEntry = tab.add("Left Servo Angle", 0.0).getEntry();
    m_rightServoEntry = tab.add("Right Servo Angle", 0.0).getEntry();
    m_talonEntry = tab.add("Talon Speed", 0.0).getEntry();
  }

  //talon methods
  public double getSpeed(SimTalon talon){
    return talon.get();
  }

  public void setSpeed(ControlMode mode, double speed, SimTalon talon){
    talon.set(mode, speed);
  }

  //servo methods
  public double getServoAngle(SimServo servo){
    return servo.getAngle();
  }

  public void setServoAngle(double angle, SimServo servo){
    servo.setAngle(angle);
  }

  @Override
  public void simulationPeriodic(){
    m_leftServoEntry.setDouble(getServoAngle(m_leftServoClaw));
    m_rightServoEntry.setDouble(getServoAngle(m_rightServoClaw));
    m_talonEntry.setDouble(getSpeed(m_armTalon));
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
