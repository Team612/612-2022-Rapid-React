// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.sim.Intake.SimRightServo;

public class SimRightServoClaw extends SubsystemBase {
  private final SimRightServo m_simServo;
  private final ShuffleboardTab tab;
  private final NetworkTableEntry servoEntry;
  /** Creates a new SimClaw. */
  public SimRightServoClaw() {
    m_simServo = new SimRightServo(0);
    tab = Shuffleboard.getTab("Intake");
    servoEntry = tab.add("RightServoPosition", 0.0).getEntry();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public double getServoAngle(){
    return m_simServo.getAngle();
  }

  public void setServoAngle(double angle){
    m_simServo.setAngle(angle);
  }

  @Override
  public void simulationPeriodic() {
      servoEntry.setDouble(getServoAngle());
  }


}
