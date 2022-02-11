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
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.sensors.RomiGyro;

public class RomiDrivetrain extends SubsystemBase {
  private static final double kCountsPerRevolution = 1440.0;
  private static final double kWheelDiameterInch = 2.75591; // 70 mm


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

  public static DifferentialDriveKinematics m_kinematics = new DifferentialDriveKinematics(Constants.kTrackwidth);


  /** Creates a new RomiDrivetrain. */
  public RomiDrivetrain() {
    // Use inches as unit for encoder distances
    m_leftEncoder.setDistancePerPulse((Math.PI * kWheelDiameterInch) / kCountsPerRevolution);
    m_rightEncoder.setDistancePerPulse((Math.PI * kWheelDiameterInch) / kCountsPerRevolution);
    resetEncoders();
    m_gyro  = new RomiGyro();
    m_integral_odometry = new IntegralOdometry(m_gyro.getRotation2d(), new Pose2d());
    // Invert right side since motor is flipped
    m_rightMotor.setInverted(true);
    m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());

    m_kinematics = new DifferentialDriveKinematics(Constants.kTrackwidth);


  }


  public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
    m_diffDrive.arcadeDrive(xaxisSpeed, zaxisRotate);
  }

  public void resetEncoders() {
    m_leftEncoder.reset();
    m_rightEncoder.reset();
  }

  public double getLeftDistanceInch() {
    return m_leftEncoder.getDistance();
  }

  public double getRightDistanceInch() {
    return m_rightEncoder.getDistance();
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

  public Encoder getEncoderLeft(){
    return m_leftEncoder;
  }

  public Encoder getEncoderRight(){
    return m_rightEncoder;
  }

  public IntegralOdometry getIntegral(){
    return m_integral_odometry;
  }

  @Override
  public void periodic() {
    //First find the calibration value over an interval of 10 seconds
    //System.out.println(m_integral_odometry.calibrateAccelerometerWithTime(20, getAccelX()));

    //Next thing would be finding the chassis velocity
    //System.out.println("Time: " + WPIUtilJNI.now() * 1.0e-6);
    //System.out.println("Veloctiy: " + m_integral_odometry.getChassisVelocityWithTime(getAccelX(), m_leftEncoder, m_rightEncoder));
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
