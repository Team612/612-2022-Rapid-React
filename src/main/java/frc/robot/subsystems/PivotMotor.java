// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PivotMotor extends SubsystemBase {
  /** Creates a new PivotMotor. */
  private WPI_TalonSRX pivotMotor;
  private double currRotate = 0.0;

  public PivotMotor() {
    pivotMotor = new WPI_TalonSRX(Constants.talon_pivot);
    pivotMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void pivot(double rotate) {
    if (rotate != currRotate) {
      System.out.println("PivotMotor.pivot() start : " + rotate);
    }

    // pivotMotor.set(TalonFXControlMode.PercentOutput, rotate);
    pivotMotor.set(ControlMode.PercentOutput, rotate);
    
    if (rotate != currRotate) {
      System.out.println("PivotMotor.pivot() end : " + rotate);
    }

    currRotate = rotate;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
