// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.sim.SimServo;
import frc.robot.sim.SimSolenoid;
import frc.robot.sim.SimTalon;

public class SimClimb extends SubsystemBase {
  public final SimSolenoid m_leftSolenoid;
  public final SimSolenoid m_rightSolenoid;
  public final SimServo m_leftStatic;
  public final SimServo m_rightStatic;
  public final SimTalon m_pivot;
  private final ShuffleboardTab tab;
  private final NetworkTableEntry m_leftPistonEntry;
  private final NetworkTableEntry m_rightPistonEntry;
  private final NetworkTableEntry m_leftStaticEntry;
  private final NetworkTableEntry m_rightStaticEntry;
  private final NetworkTableEntry m_talonPivotEntry;
  /** Creates a new SimClimb. */
  public SimClimb() {
    m_leftSolenoid = new SimSolenoid(7, PneumaticsModuleType.CTREPCM, 1, 0);
    m_rightSolenoid = new SimSolenoid(8, PneumaticsModuleType.CTREPCM, 2, 3);
    m_leftStatic = new SimServo(2);
    m_rightStatic = new SimServo(3);
    m_pivot = new SimTalon(6);
    tab = Shuffleboard.getTab("Climb");
    m_leftPistonEntry = tab.add("Left Piston", "off").getEntry();
    m_rightPistonEntry = tab.add("Right Piston", "off").getEntry();
    m_leftStaticEntry = tab.add("Left Static Angle", 0.0).getEntry();
    m_rightStaticEntry = tab.add("Right Static Angle", 0.0).getEntry();
    m_talonPivotEntry = tab.add("Pivot Speed", 0.0).getEntry();
  }

  //solenoid methods
  public Value getSolenoidValue(SimSolenoid solenoid){
    return solenoid.get();
  }

  public void setSolenoidValue(Value val, SimSolenoid solenoid){
    solenoid.set(val);
  }

  public String returnState(SimSolenoid m_simSolenoid){
    if(m_simSolenoid.get() == Value.kForward){
      return "forward";
    }
    else if(m_simSolenoid.get() == Value.kReverse){
      return "reverse";
    }
    else if(m_simSolenoid.get() == Value.kOff){
      return "off";
    }
    return "Solenoid not on";
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
    m_leftPistonEntry.setString(returnState(m_leftSolenoid));
    m_rightPistonEntry.setString(returnState(m_rightSolenoid));
    m_leftStaticEntry.setDouble(getServoAngle(m_leftStatic));
    m_rightStaticEntry.setDouble(getServoAngle(m_rightStatic));
    m_talonPivotEntry.setDouble(getSpeed(m_pivot));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
