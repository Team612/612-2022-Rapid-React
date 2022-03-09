// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Climb;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.sim.Climb.SimClimbTalon;

public class SimPivotMotor extends SubsystemBase {
  private final SimClimbTalon m_talonSim;
  private final ShuffleboardTab tab;
  private final NetworkTableEntry talonEntry;
  /** Creates a new SimPivotMotor. */
  public SimPivotMotor() {
    m_talonSim = new SimClimbTalon(6);
    tab = Shuffleboard.getTab("ClimbSolenoid");
    talonEntry = tab.add("Talons", 0.0).getEntry();

  }

  public double getSpeed(){
    return m_talonSim.get();
  }

  public void setSpeed(ControlMode mode, double speed){
    m_talonSim.set(mode, speed);
  }

  @Override
  public void simulationPeriodic(){
    talonEntry.setDouble(getSpeed());
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
