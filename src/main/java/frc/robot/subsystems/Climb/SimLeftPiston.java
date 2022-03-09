// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Climb;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.sim.Climb.SimLeftSolenoid;

public class SimLeftPiston extends SubsystemBase {
  private final SimLeftSolenoid m_simSolenoid;
  private final ShuffleboardTab tab;
  private final NetworkTableEntry solenoidEntry;
  /** Creates a new SimPiston. */
  public SimLeftPiston() {
    m_simSolenoid = new SimLeftSolenoid(7, PneumaticsModuleType.CTREPCM, 1, 0);
    tab = Shuffleboard.getTab("ClimbSolenoid");
    solenoidEntry = tab.add("LeftSolenoid", "off").getEntry();
  }

  public Value getSolenoidValue(){
    return m_simSolenoid.get();
  }

  public void setSolenoidValue(Value val){
    m_simSolenoid.set(val);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public String returnState(){
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
  
  @Override
  public void simulationPeriodic() {
      solenoidEntry.setString(returnState());
  }
}
