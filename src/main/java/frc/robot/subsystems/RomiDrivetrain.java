// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.sensors.RomiGyro;

public class RomiDrivetrain extends SubsystemBase {

  private final Spark m_leftMotor = new Spark(0);
  private final Spark m_rightMotor = new Spark(1);

  private final Encoder m_leftEncoder = new Encoder(4, 5);
  private final Encoder m_rightEncoder = new Encoder(6, 7);

  private final BuiltInAccelerometer accelerometer = new BuiltInAccelerometer();
  private final RomiGyro m_gyro;
  private final IntegralOdometry m_integral_odometry;

  // Set up the differential drive controller
  private final DifferentialDrive m_diffDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);
  private final DifferentialDriveOdometry m_odometry;

  /** Creates a new RomiDrivetrain. */
  public RomiDrivetrain() {
    // Use inches as unit for encoder distances
    m_leftEncoder.setDistancePerPulse(Constants.encoder_ratio);
    m_rightEncoder.setDistancePerPulse(Constants.encoder_ratio);
    resetEncoders();
    m_gyro  = new RomiGyro();
    m_integral_odometry = new IntegralOdometry(m_gyro.getRotation2d(), new Pose2d());
    // Invert right side since motor is flipped
    m_rightMotor.setInverted(true);
    m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());



  }


  public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
    m_diffDrive.arcadeDrive(xaxisSpeed, zaxisRotate);
  }

  public void resetEncoders() {
    m_leftEncoder.reset();
    m_rightEncoder.reset();
  }

  public double getGyroAngle(){
    return Math.abs(m_gyro.getRate() * 1000) / 1000;
  }

  public double getLeftDistanceInch() {
    return m_leftEncoder.getDistance();
  }

  public double getRightDistanceInch() {
    return m_rightEncoder.getDistance();
  }

  public double getLeftEncoderRate(){
    return m_leftEncoder.getRate();
  }

  public double getRightEncoderRate(){
    return m_rightEncoder.getRate();
  }

  //getting average inch distance
  public double getAverageEncoderRate(){
    return (getLeftEncoderRate() + getRightEncoderRate()) / 2.0;
  }

  public double getAccelX(){
    return accelerometer.getX();
  }

  public double getAccelY(){
    return accelerometer.getY();
  }

  public double getAccelZ(){ 
    return accelerometer.getZ();

  }


  @Override
  public void periodic() {
    double velocityX = Math.floor(m_integral_odometry.getChassisVelocityWithTime(getAccelX(),getAverageEncoderRate()) * 100000)/100000;
    var test = m_integral_odometry.getWheelVelocity(velocityX, getGyroAngle());

    System.out.println("left_velocity: " + test.leftMetersPerSecond  + " left_encoder: " + m_leftEncoder.getRate());
    //m_integral_odometry.getPositionWithTime(m_gyro.getRotation2d(), test.leftMetersPerSecond);

    //System.out.println(m_integral_odometry.getPoseMeters().getX());;
    //System.out.println(m_integral_odometry.calibrateAccelerometerWithTime(10, getAccelX()));
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
