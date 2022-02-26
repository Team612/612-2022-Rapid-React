// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.MecanumDriveMotorVoltages;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.IMU.BNO055;
import frc.robot.IMU.IntegralOdometry;


public class Drivetrain extends SubsystemBase {

  public final CANSparkMax spark_fl = new CANSparkMax(Constants.SPARK_FL, MotorType.kBrushless);
  public final CANSparkMax spark_fr = new CANSparkMax(Constants.SPARK_FR, MotorType.kBrushless);
  public final CANSparkMax spark_bl = new CANSparkMax(Constants.SPARK_BL, MotorType.kBrushless);
  public final CANSparkMax spark_br = new CANSparkMax(Constants.SPARK_BR, MotorType.kBrushless);


  private final IntegralOdometry m_integral_odometry1;
  private final IntegralOdometry m_integral_odometry2;

  private final BNO055 imu;
  
  private final double DEADZONE = 0.1;

  private MecanumDrive drivetrain;
  public double vel = Constants.kEncoderDistancePerPulse / 60;

  private final AHRS navx = new AHRS(I2C.Port.kMXP);
  MecanumDriveOdometry m_odometry = new MecanumDriveOdometry(Constants.kDriveKinematics, navx.getRotation2d());
  private Field2d m_field = new Field2d();

  

  public Drivetrain() {
    SmartDashboard.putData("Field", m_field);
    drivetrain = new MecanumDrive(spark_fl, spark_bl, spark_fr, spark_br);
    spark_fr.setInverted(true);
    spark_br.setInverted(true);
    resetEncoders();
    spark_fr.getEncoder().setPositionConversionFactor(Constants.kEncoderDistancePerPulse);
    spark_fl.getEncoder().setPositionConversionFactor(Constants.kEncoderDistancePerPulse);
    spark_br.getEncoder().setPositionConversionFactor(Constants.kEncoderDistancePerPulse);
    spark_bl.getEncoder().setPositionConversionFactor(Constants.kEncoderDistancePerPulse);

    spark_fr.getEncoder().setVelocityConversionFactor(vel);
    spark_fl.getEncoder().setVelocityConversionFactor(vel);
    spark_br.getEncoder().setVelocityConversionFactor(vel);
    spark_bl.getEncoder().setVelocityConversionFactor(vel);

    spark_fl.setIdleMode(IdleMode.kBrake);
    spark_fr.setIdleMode(IdleMode.kBrake);
    spark_bl.setIdleMode(IdleMode.kBrake);
    spark_br.setIdleMode(IdleMode.kBrake);

    m_integral_odometry1 = new IntegralOdometry(navx.getRotation2d(), new Pose2d());
    m_integral_odometry2 = new IntegralOdometry(navx.getRotation2d(), new Pose2d());

    imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS, BNO055.vector_type_t.VECTOR_ACCELEROMETER);

  }
  public void driveMecanum(double y, double x, double zRot){
    if(Math.abs(x) < DEADZONE) x = 0;
    if(Math.abs(y) < DEADZONE) y = 0;
    if(Math.abs(zRot) < DEADZONE) zRot = 0;
    drivetrain.driveCartesian(y, x, zRot);
  }

  public void driveMecanum(double fl, double bl, double fr, double br){
    spark_fl.set(fl);
    spark_bl.set(bl);
    spark_fr.set(fr);
    spark_br.set(br);
  }

  public void calibrate(){
    navx.calibrate();
  }


  public double getRawAccelerationX(){
    return navx.getRawAccelX();
  } 

  
  public double getLinearAccelerationX(){
    return navx.getWorldLinearAccelX();
  } 

  public double getChassisRawVelocityX() {
    return m_integral_odometry1.getChassisVelocityXWithTime(getRawAccelerationX(), spark_fl.getEncoder(), spark_fr.getEncoder());
  }

  public double getChassisLinearVelocityX() {
    return m_integral_odometry1.getChassisVelocityXWithTime(getLinearAccelerationX(), spark_fl.getEncoder(), spark_fr.getEncoder());
  }

  public boolean isCalibrateBosch(){
    return imu.isCalibrated();
  }

  public Rotation2d BoschRotation2d(){
    return new Rotation2d(imu.getHeading());
  }

  public double[] getAcceleration(){
    return (double[]) imu.getVector();
  }
  

  public double getEncoderVelocityX(){
    var chassisState = Constants.kDriveKinematics.toChassisSpeeds(
      new MecanumDriveWheelSpeeds(
        spark_fl.getEncoder().getVelocity(), 
        spark_fr.getEncoder().getVelocity(), 
        spark_bl.getEncoder().getVelocity(),
        spark_br.getEncoder().getVelocity()
      ));

    return chassisState.vxMetersPerSecond;
  }


  
  @Override
  public void periodic() {
    //update the odometry in the periodic block
    m_odometry.update(navx.getRotation2d(), 
      new MecanumDriveWheelSpeeds(
        spark_fl.getEncoder().getVelocity(), 
        spark_fr.getEncoder().getVelocity(), 
        spark_bl.getEncoder().getVelocity(),
        spark_br.getEncoder().getVelocity()
      ));
      m_field.setRobotPose(m_odometry.getPoseMeters());
  }
 
  public void simulationPeriodic(){
   
  }

  public Pose2d getPose(){
    return m_odometry.getPoseMeters();
  }

  public void resetOdometry(Pose2d pose){
    m_odometry.resetPosition(pose, navx.getRotation2d());
  }

  public void mecanumVolts(MecanumDriveMotorVoltages volts){
    spark_fl.setVoltage(volts.frontLeftVoltage);
    spark_fr.setVoltage(volts.frontRightVoltage);
    spark_bl.setVoltage(volts.rearLeftVoltage);
    spark_br.setVoltage(volts.rearRightVoltage);
  }

  public void resetEncoders(){
    spark_fl.getEncoder().setPosition(0);
    spark_bl.getEncoder().setPosition(0);
    spark_fr.getEncoder().setPosition(0);
    spark_br.getEncoder().setPosition(0);
  }

  public MecanumDriveWheelSpeeds getCurrentWheelSpeeds(){
    return new MecanumDriveWheelSpeeds(
      spark_fl.getEncoder().getVelocity(),
      spark_fr.getEncoder().getVelocity(),
      spark_bl.getEncoder().getVelocity(),
      spark_br.getEncoder().getVelocity());
  }

  public void zeroHeading(){
    navx.reset();
  }

  public double getHeading(){
    return navx.getRotation2d().getDegrees();
  }

  public double getTurnRate(){
    return -navx.getRate();
  }
}
