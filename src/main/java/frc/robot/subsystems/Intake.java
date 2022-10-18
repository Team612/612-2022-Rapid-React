// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  private final Servo bottomLeft;
  private final Servo bottomRight;
 
  private final double DEADZONE = 0.1;

  private boolean servoOpen = false;
  private boolean isSearchingInput = false;

  private final WPI_TalonSRX shoulder;
  DutyCycleEncoder boreEncoderIntake;
  
  private final DoubleSolenoid piston1;

  private final Ultrasonic m_ultrasonicOutake; 
  // private final Ultrasonic m_ultrasonicIntake;

  private final DigitalInput m_intakeButton;
  static Intake instance = null;
  private boolean buttonstate = false;
  
  private Intake() {
    boreEncoderIntake = new DutyCycleEncoder(Constants.boreEncoderIntake);
    shoulder = new WPI_TalonSRX(Constants.Talon_arm);
    shoulder.setNeutralMode(NeutralMode.Brake);
    bottomLeft = new Servo(Constants.bottom_servos[0]);
    bottomRight = new Servo(Constants.bottom_servos[1]);

    // m_ultrasonicIntake = new Ultrasonic(Constants.ULTRASONIC_INTAKE[0], Constants.ULTRASONIC_INTAKE[1]);
    m_ultrasonicOutake = new Ultrasonic(Constants.ULTRASONIC_OUTAKE[0], Constants.ULTRASONIC_OUTAKE[1]);
    m_intakeButton = new DigitalInput(Constants.IntakeButton);
    piston1 = new DoubleSolenoid(Constants.PCM_2, Constants.solenoidType, Constants.firstSolenoid[1],
    Constants.firstSolenoid[0]);
  }
  // public double getUltrasonicIntakeInches(){
  //   return m_ultrasonicIntake.getRangeInches();
  // }

  public void setSearchingInput(boolean state){
    isSearchingInput = state;
  }
  
    
  public boolean getSearchingInput(){
    return isSearchingInput;
  }

  public double getUltrasonicOutakeInches(){
    return m_ultrasonicOutake.getRangeInches();
  }

  public static Intake getInstance() {
    if (instance == null) {
      instance = new Intake();
    }
    return instance;
  }

  public double getBoreEncoder() {
    return boreEncoderIntake.getDistance();
  }

  public boolean isLimitTriggered(){
    if(shoulder.getSensorCollection().isFwdLimitSwitchClosed() || shoulder.getSensorCollection().isRevLimitSwitchClosed()){
      return true;
    }
    return false;
  }
  
  public void setArm(double speed) {
    if(Math.abs(speed) < DEADZONE) speed = 0;
    shoulder.set(speed);
  }
  
  public boolean bottomlimitGoesOff() {
    return shoulder.getSensorCollection().isFwdLimitSwitchClosed();
  }

  public boolean upperLimitGoesOff(){
    return shoulder.getSensorCollection().isRevLimitSwitchClosed();
  }

  public void BottomServoOpen() {
    bottomLeft.setAngle(90);
    bottomRight.setAngle(90);
    servoOpen = true;
  }

  public void BottomServoClose(){
    bottomLeft.setAngle(180);
    bottomRight.setAngle(0); 
    servoOpen = false;
  }

  public boolean isServoOpen(){
    return servoOpen;
  }

  public double getBottomLeftAngle(){
    return bottomLeft.getAngle();
  }

  public double getBottomRightAngle(){
    return bottomRight.getAngle();
  }
  
  public boolean getButtonVal(){
    return !m_intakeButton.get();
  }
  public void setInputState(boolean b){
    buttonstate = b;
  }
  public boolean getInputState(){
    return buttonstate;
  }

  public void open(){
    piston1.set(Value.kForward);
  }

  public void close(){
    piston1.set(Value.kReverse);
  }

  @Override
  public void periodic() {
  }
}
