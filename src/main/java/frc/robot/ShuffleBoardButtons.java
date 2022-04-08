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
    //Tabs
    ShuffleboardTab m_encoderTab;
    ShuffleboardTab m_ultraSonicTab;
    ShuffleboardTab m_compressorTab;
    ShuffleboardTab m_smartdashboard;
    

    //NetworkTables
    NetworkTableEntry pivotEntry;
    NetworkTableEntry intakeEntry;
    NetworkTableEntry ultrasonicOutake;
    NetworkTableEntry compressor_state;
    NetworkTableEntry compressor_button;
    NetworkTableEntry searchingInput;
    NetworkTableEntry servoOpened;
    NetworkTableEntry autoState;

    NetworkTableEntry isClimbOpened;
    NetworkTableEntry intakeButton;
    NetworkTableEntry isServoNeutral;


    public void initButtons(){
        m_encoderTab = Shuffleboard.getTab("encoder");
        m_ultraSonicTab = Shuffleboard.getTab("ultrasonic");
        m_compressorTab = Shuffleboard.getTab("compressor");
        m_smartdashboard = Shuffleboard.getTab("SmartDashboard");

        pivotEntry = m_encoderTab.add("Pivot Encoder", 0.0).getEntry();
        intakeEntry = m_encoderTab.add("Intake Encoder", 0.0).getEntry();
        ultrasonicOutake = m_ultraSonicTab.add("Ultrasonic Outake", 0.0).getEntry();

        //what driver wants to see
        compressor_state = m_smartdashboard.add("Compressor State", false).getEntry();
        compressor_button = m_smartdashboard.add("Compressor Button", false).getEntry();
        searchingInput = m_smartdashboard.add("searchingInputButton", false).getEntry();
        servoOpened = m_smartdashboard.add("servo Opened", false).getEntry();
        autoState = m_smartdashboard.add("autoState", false).getEntry();
        isClimbOpened = m_smartdashboard.add("climb opened", false).getEntry();
        intakeButton = m_smartdashboard.add("intake button", false).getEntry();
        isServoNeutral = m_smartdashboard.add("Is Servo Neutral?", false).getEntry();
    }

    public void updateButtons(){
        pivotEntry.setDouble(PivotMotor.getInstance().getBoreEncoder());
        intakeEntry.setDouble(Intake.getInstance().getBoreEncoder());
        ultrasonicOutake.setDouble(Intake.getInstance().getUltrasonicOutakeInches());
        compressor_state.setBoolean(Climb.getInstance().getCompressorState());
        compressor_button.setBoolean(Climb.getInstance().toggleCompressor());
        searchingInput.setBoolean(Intake.getInstance().getSearchingInput());
        servoOpened.setBoolean(Intake.getInstance().isServoOpen());
        autoState.setBoolean(Intake.getInstance().getInputState());
        isClimbOpened.setBoolean(Climb.getInstance().getClimbOpened());
        intakeButton.setBoolean(Intake.getInstance().getButtonVal());
        isServoNeutral.setBoolean(Climb.getInstance().getServoNeutral());
    }
    
}
