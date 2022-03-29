// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PivotMotor;

public class ShuffleBoardButtons {
    ShuffleboardTab m_encoderTab;
    ShuffleboardTab m_ultraSonicTab;
    ShuffleboardTab m_compressorTab;

    NetworkTableEntry pivotEntry;
    NetworkTableEntry intakeEntry;
    NetworkTableEntry ultrasonicIntake;
    NetworkTableEntry ultrasonicOutake;
    NetworkTableEntry compressor_state;
    NetworkTableEntry compressor_button;



    public void initButtons(){
        m_encoderTab = Shuffleboard.getTab("encoder");
        m_ultraSonicTab = Shuffleboard.getTab("ultrasonic");
        m_compressorTab = Shuffleboard.getTab("compressor");

        pivotEntry = m_encoderTab.add("Pivot Encoder", 0.0).getEntry();
        intakeEntry = m_encoderTab.add("Intake Encoder", 0.0).getEntry();
        ultrasonicIntake = m_ultraSonicTab.add("Ultrasonic Intake", 0.0).getEntry();
        ultrasonicOutake = m_ultraSonicTab.add("Ultrasonic Outake", 0.0).getEntry();
        compressor_state = m_compressorTab.add("Compressor State", false).getEntry();
        compressor_button = m_compressorTab.add("Compressor Button", false).getEntry();
    }

    public void updateButtons(){
        pivotEntry.setDouble(PivotMotor.getInstance().getBoreEncoder());
        intakeEntry.setDouble(Intake.getInstance().getBoreEncoder());
        ultrasonicIntake.setDouble(Intake.getInstance().getUltrasonicIntakeInches());
        ultrasonicOutake.setDouble(Intake.getInstance().getUltrasonicOutakeInches());
        compressor_state.setBoolean(Climb.getInstance().getCompressorState());
        compressor_button.setBoolean(Climb.getInstance().toggleCompressor());
    }
    
}
