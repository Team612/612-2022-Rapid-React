// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  // defines both Talons and Solenoids
  private Servo rightServo;
  private Servo leftServo;
  private final DoubleSolenoid piston1;
  private final DoubleSolenoid piston2;

  // Pushes the piston out
  private DutyCycleEncoder climbEncoder;
  private Compressor compressor = new Compressor(Constants.PCM_2, PneumaticsModuleType.CTREPCM);

  public Climb() {
    rightServo = new Servo(Constants.climb_servo[1]);
    leftServo = new Servo(Constants.climb_servo[0]);
    piston2 = new DoubleSolenoid(Constants.PCM_2, Constants.solenoidType, Constants.secondSolenoid[1],
        Constants.secondSolenoid[0]);
    piston1 = new DoubleSolenoid(Constants.PCM_2, Constants.solenoidType, Constants.firstSolenoid[1],
        Constants.firstSolenoid[0]);
    climbEncoder = new DutyCycleEncoder(Constants.climbEncoder);
    Shuffleboard.getTab("compressor")
    .add("compressor state", compressor);

  }

  public void extendArm() {
    System.out.println("Climb.extendArm() start");
    piston1.set(Value.kForward);
    piston2.set(Value.kForward);
    System.out.println("Climb.extendArm() end");
  }

  // Brings the piston in
  public void retractArm() {
    System.out.println("Climb.retractArm() start");
    piston1.set(Value.kReverse);
    piston2.set(Value.kReverse);
    System.out.println("Climb.retractArm() end");
  }

  public void setServosNeutral() {
    System.out.println("Climb.setServosNeutral() start");
    leftServo.setDisabled();
    rightServo.setDisabled();
    System.out.println("Climb.setServosNeutral() end");
  }

  // Opens servos
  public void servoClose() {
    System.out.println("Climb.servoClose() start");
    leftServo.setAngle(180);
    rightServo.setAngle(0);
    System.out.println("Climb.servoClose() end");
  }

  // Closes servos
  public void servoOpen() {
    System.out.println("Climb.servoOpen() start");
    leftServo.setAngle(90);
    rightServo.setAngle(90);
    System.out.println("Climb.servoOpen() end");
  }
  public void compressorOn(){
    compressor.enableDigital();
  }

  public void compressorOff(){
    compressor.disable();
  }

  public void getBoreEncoder() {
    // System.out.println("encoder dist: " + boreEncoder.getDistance());
    /*
     * With the rev logo facing towards the user:
     * clockwise is negative, counterclockwise is positive
     * distance of 1 indicates one full rotation. there is no upper bound
     * (i.e. if there's more than one rotation, the distance will return a value
     * greater than 1)
     * therefore, should take mod of distance and 1.0
     * when rio is reset, it'll remove the whole number and do just the decimal
     * negative acts weird: if it's at -1.56 and resets, it'll return to 0.4.
     */
    // System.out.println("encoder freq: " + boreEncoder.getFrequency());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
