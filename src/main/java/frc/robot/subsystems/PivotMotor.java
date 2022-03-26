// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PivotMotor extends SubsystemBase {
  /** Creates a new PivotMotor. */
  private CANSparkMax pivotMotor;
  private double currRotate = 0.0;

  public PivotMotor() {
    pivotMotor = new CANSparkMax(Constants.talon_pivot, MotorType.kBrushless);
    pivotMotor.setIdleMode(IdleMode.kBrake);
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

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
