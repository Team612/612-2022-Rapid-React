// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class RollerIntake extends SubsystemBase {
  private WPI_TalonSRX intakeMotor = new WPI_TalonSRX(Constants.intakeWheelTalon);
  private DigitalInput m_button = new DigitalInput(Constants.buttonTrigger);
  private Debouncer m_debouncer = new Debouncer(0.1, Debouncer.DebounceType.kBoth);
  public RollerIntake() {}

  public void intakeOutake(double movement){
    intakeMotor.set(ControlMode.PercentOutput, movement);
  }

  public boolean getDebouncerVals(){
    System.out.println("Button: " + m_button.getChannel());
    return m_debouncer.calculate(m_button.get());
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}