// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.subsystems.PivotMotor;

/** Add your docs here. */
public class ShuffleBoardButtons {
    ShuffleboardTab m_encoderTab;
    NetworkTableEntry pivotEntry;
    NetworkTableEntry intakeEntry;

    public void initButtons(){
        pivotEntry = m_encoderTab.add("Pivot Encoder", 0.0).getEntry();
        intakeEntry = m_encoderTab.add("intake Encoder", 0.0).getEntry();
    }

    public void updateButtons(){
        pivotEntry.setDouble(PivotMotor.getInstance().getBoreEncoder());
        intakeEntry.setDouble(PivotMotor.getInstance().getBoreEncoder());
    }
    
}
