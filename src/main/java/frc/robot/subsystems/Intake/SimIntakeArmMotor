// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.sim.Intake.SimIntakeTalon;

public class SimIntakeArmMotor extends SubsystemBase {
  private final SimIntakeTalon m_talonSim;
  private final ShuffleboardTab tab;
  private final NetworkTableEntry intakeThingy;
  /** Creates a new SimPivotMotor. */
  public SimIntakeArmMotor() {
    m_talonSim = new SimIntakeTalon(9);
    tab = Shuffleboard.getTab("Intake");
    intakeThingy = tab.add("Intake Talons", 0.0).getEntry();
  }

  public double getSpeed(){
    return m_talonSim.get();
  }

  public void setSpeed(ControlMode mode, double speed){
    m_talonSim.set(mode, speed);
  }

  @Override
  public void simulationPeriodic(){
    intakeThingy.setDouble(getSpeed());
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
