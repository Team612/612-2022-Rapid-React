// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PivotMotor extends SubsystemBase {
  /** Creates a new PivotMotor. */
  //private WPI_TalonSRX pivotMotor;
  private CANSparkMax pivotMotor;
  DutyCycleEncoder boreEncoderPivot;

  private double currRotate = 0.0;
  static PivotMotor instance = null;

  ShuffleboardTab m_tab; // fix this in logging branch 
  NetworkTableEntry entry;


  private PivotMotor() {
    boreEncoderPivot = new DutyCycleEncoder(Constants.boreEncoderPivot);
    //pivotMotor = new WPI_TalonSRX(Constants.talon_pivot);
    pivotMotor = new CANSparkMax(Constants.talon_pivot, MotorType.kBrushless);
    //pivotMotor.setNeutralMode(NeutralMode.Brake);
    pivotMotor.setIdleMode(IdleMode.kBrake);
    m_tab = Shuffleboard.getTab("encoder");
    entry = m_tab.add("Pivot Encoder", 0.0).getEntry();
  }

  public static PivotMotor getInstance() {
    if (instance == null) {
      instance = new PivotMotor();
    }

    return instance;
  }


  public void pivot(double rotate) {
    if (rotate != currRotate) {
      System.out.println("PivotMotor.pivot() start : " + rotate);
    }
    //add brake mode again?
    pivotMotor.set(rotate);
    
    if (rotate != currRotate) {
      System.out.println("PivotMotor.pivot() end : " + rotate);
    }

    currRotate = rotate;
  }

  public boolean moveToPosition(double target, double kP){
    //pivotMotor.set(ControlMode.PercentOutput, kP*(target-getBoreEncoder()));
    pivotMotor.set(kP*(target-getBoreEncoder()));
    return (getBoreEncoder() > target - 0.05 && getBoreEncoder() < target + 0.05);
  }

  public double getBoreEncoder() {
    return boreEncoderPivot.getDistance();
  }

  //.702 hits the limit switch


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // System.out.println("pivot bore encoder: " + getBoreEncoder());
    entry.setDouble(getBoreEncoder());

  }
}
