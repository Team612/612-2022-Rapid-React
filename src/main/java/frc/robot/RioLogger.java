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

public class RioLogger {
    private DoubleLogEntry pivotBoreEncoder;
    private DoubleLogEntry intakeBoreEncoder;
    private DoubleLogEntry EncoderFL;
    private DoubleLogEntry EncoderFR;
    private DoubleLogEntry EncoderBL;
    private DoubleLogEntry EncoderBR;
    private DoubleLogEntry climbServoRight;
    private DoubleLogEntry climbServoLeft;
    private DoubleLogEntry grabberServoRight;
    private DoubleLogEntry grabberServoLeft;
    private DoubleLogEntry ultrasonicOutake;
    private DoubleLogEntry ultrasonicIntake;
    private DoubleLogEntry getYaw;
    private DoubleLogEntry linearAccelX;
    private DoubleLogEntry linearAccelY;
    private DoubleLogEntry linearAccelZ;
    private DoubleLogEntry getAngularVel;
    private BooleanLogEntry compressorState;
    private BooleanLogEntry isCalibrating;

    public void initLog(){
        DataLogManager.start();
        DataLog log = DataLogManager.getLog();
        pivotBoreEncoder = new DoubleLogEntry(log, "/my/piovtBoreEncoder");
        intakeBoreEncoder = new DoubleLogEntry(log, "/my/intakeBoreEncoder");
        EncoderFL = new DoubleLogEntry(log, "/my/EncoderFL");
        EncoderFR = new DoubleLogEntry(log, "/my/EncoderFR");
        EncoderBL = new DoubleLogEntry(log, "/my/EncoderBL");
        EncoderBR = new DoubleLogEntry(log, "/my/EncoderBR");
        climbServoRight = new DoubleLogEntry(log, "/my/climbServoRight");
        climbServoLeft = new DoubleLogEntry(log, "/my/climbServoLeft");
        grabberServoRight = new DoubleLogEntry(log, "/my/grabberServoRight");
        grabberServoLeft = new DoubleLogEntry(log, "/my/grabberServoLeft");
        ultrasonicOutake = new DoubleLogEntry(log, "/my/ultrasonicOutake");
        ultrasonicIntake = new DoubleLogEntry(log, "/my/ultrasonicIntake");     
        getYaw = new DoubleLogEntry(log, "/my/getYaw");
        linearAccelX = new DoubleLogEntry(log, "/my/linearAccelX");
        linearAccelY = new DoubleLogEntry(log, "/my/linearAccelY");
        linearAccelZ = new DoubleLogEntry(log, "/my/linearAccelZ");
        getAngularVel = new DoubleLogEntry(log, "/my/getAngularVel");
        isCalibrating = new BooleanLogEntry(log, "/my/isCalibrating");
        compressorState = new BooleanLogEntry(log, "/my/compressorState");
    }

    public void executeLogger(){
        pivotBoreEncoder.append(PivotMotor.getInstance().getBoreEncoder());
        intakeBoreEncoder.append(Intake.getInstance().getBoreEncoder());
        EncoderFL.append(Drivetrain.getInstance().getFLEncoderVelocity());
        EncoderFR.append(Drivetrain.getInstance().getFREncoderVelocity());
        EncoderBL.append(Drivetrain.getInstance().getBLEncoderVelocity());
        EncoderBR.append(Drivetrain.getInstance().getBREncoderVelocity());
        climbServoRight.append(Climb.getInstance().getRightServoAngle());
        climbServoLeft.append(Climb.getInstance().getLeftServoAngle());
        grabberServoRight.append(Intake.getInstance().getBottomRightAngle());
        grabberServoLeft.append(Intake.getInstance().getBottomLeftAngle());
        compressorState.append(Climb.getInstance().getCompressorState());
        ultrasonicOutake.append(Intake.getInstance().getUltrasonicOutakeInches());
        ultrasonicIntake.append(Intake.getInstance().getUltrasonicIntakeInches());
        getYaw.append(Drivetrain.getInstance().getYaw());
        linearAccelX.append(Drivetrain.getInstance().linearAccelX());
        linearAccelY.append(Drivetrain.getInstance().linearAccelY());
        linearAccelZ.append(Drivetrain.getInstance().linearAccelZ());
        getAngularVel.append(Drivetrain.getInstance().getAngularVel());
        isCalibrating.append(Drivetrain.getInstance().isCalibrating());
    }
}
