// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.datalog.*;
import edu.wpi.first.wpilibj.DataLogManager;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PivotMotor;

/** Add your docs here. */
public class RioLogger {
    // BooleanLogEntry myBooleanLog;
    private DoubleLogEntry pivotBoreEncoder;
    private DoubleLogEntry intakeBoreEncoder;
    private DoubleLogEntry EncoderFL;
    private DoubleLogEntry EncoderFR;
    private DoubleLogEntry EncoderBL;
    private DoubleLogEntry EncoderBR;
    
    private DoubleLogEntry servoRight;
    private DoubleLogEntry servoLeft;

    public void initLog(){
        DataLogManager.start();
        DataLog log = DataLogManager.getLog();
        pivotBoreEncoder = new DoubleLogEntry(log, "/my/pivotBoreEncoder");
        intakeBoreEncoder = new DoubleLogEntry(log, "/my/intakeBoreEncoder");
        EncoderFL = new DoubleLogEntry(log, "/my/EncoderFL");
        EncoderFR = new DoubleLogEntry(log, "/my/EncoderFR");
        EncoderBL = new DoubleLogEntry(log, "/my/EncoderBL");
        EncoderBR = new DoubleLogEntry(log, "/my/EncoderBR");
        servoRight = new DoubleLogEntry(log, "/my/servoRight");
        servoLeft = new DoubleLogEntry(log, "/my/servoLeft");

    }

    public void executeLogger(){
        pivotBoreEncoder.append(PivotMotor.getInstance().getBoreEncoder());
        intakeBoreEncoder.append(Intake.getInstance().getBoreEncoder());
        EncoderFL.append(Drivetrain.getInstance().getFLEncoderVelocity());
        EncoderFR.append(Drivetrain.getInstance().getFREncoderVelocity());
        EncoderBL.append(Drivetrain.getInstance().getBLEncoderVelocity());
        EncoderBR.append(Drivetrain.getInstance().getBREncoderVelocity());
        servoRight.append(Climb.getInstance().getRightServoAngle());
        servoLeft.append(Climb.getInstance().getLeftServoAngle());
    }
    
}
