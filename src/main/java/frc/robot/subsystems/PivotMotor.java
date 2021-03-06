// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PivotMotor extends SubsystemBase {
  /** Creates a new PivotMotor. */
  private CANSparkMax pivotMotor;
  DutyCycleEncoder boreEncoderPivot;

  private double currRotate = 0.0;
  static PivotMotor instance = null;

  private PIDController pid = new PIDController(10, 1.2, 0.8);

  private PivotMotor() {
    boreEncoderPivot = new DutyCycleEncoder(Constants.boreEncoderPivot);
    //pivotMotor = new WPI_TalonSRX(Constants.talon_pivot);
    pivotMotor = new CANSparkMax(Constants.talon_pivot, MotorType.kBrushless);
    //pivotMotor.setNeutralMode(NeutralMode.Brake);
    pivotMotor.setIdleMode(IdleMode.kBrake);
  }

  public static PivotMotor getInstance() {
    if (instance == null) {
      instance = new PivotMotor();
    }

    return instance;
  }


  public void pivot(double rotate) {
    if (rotate != currRotate) {
      // System.out.println("PivotMotor.pivot() start : " + rotate);
    }
    //add brake mode again?
    pivotMotor.set(rotate);
    
    if (rotate != currRotate) {
      // System.out.println("PivotMotor.pivot() end : " + rotate);
    }

    currRotate = rotate;
  }

  public boolean moveToPosition(double target, double kP, double range){
    //pivotMotor.set(ControlMode.PercentOutput, kP*(target-getBoreEncoder()));
    // System.out.println("is running");
    pivotMotor.set(kP*(target-getBoreEncoder()));
    return (getBoreEncoder() > target - range && getBoreEncoder() < target + range);
  }

  public void moveToPositionPID(double target){
    pivotMotor.set(pid.calculate(getBoreEncoder(), target));
  }

  public double getBoreEncoder() {
    return boreEncoderPivot.getDistance();
  }

  //.702 hits the limit switch


  @Override
  public void periodic() {

  }
}
