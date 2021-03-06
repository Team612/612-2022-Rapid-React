// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.simulation.SimDeviceDataJNI;
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

public class Drivetrain extends SubsystemBase {
  private final CANSparkMax spark_fl;
  private final CANSparkMax spark_fr;
  private final CANSparkMax spark_bl;
  private final CANSparkMax spark_br;

  private final double DEADZONE = 0.1;

  static Drivetrain instance = null;

  private MecanumDrive drivetrain;
  public double vel = Constants.kEncoderDistancePerPulse / 60;

  private static AHRS navx;
  MecanumDriveOdometry m_odometry;
  private Field2d m_field;


  private Drivetrain() {
    m_field = new Field2d();
    SmartDashboard.putData("Field", m_field);

    spark_fl = new CANSparkMax(Constants.SPARK_FL, MotorType.kBrushless);
    spark_fr = new CANSparkMax(Constants.SPARK_FR, MotorType.kBrushless);
    spark_bl = new CANSparkMax(Constants.SPARK_BL, MotorType.kBrushless);
    spark_br = new CANSparkMax(Constants.SPARK_BR, MotorType.kBrushless);
    navx = new AHRS(I2C.Port.kMXP);
    m_odometry = new MecanumDriveOdometry(Constants.kDriveKinematics, navx.getRotation2d());

    spark_fr.setInverted(true);
    spark_br.setInverted(true);

    spark_fl.setInverted(false);
    spark_bl.setInverted(false);

    spark_fl.setIdleMode(IdleMode.kBrake);
    spark_fr.setIdleMode(IdleMode.kBrake);
    spark_bl.setIdleMode(IdleMode.kBrake);
    spark_br.setIdleMode(IdleMode.kBrake);

    resetEncoders();
    spark_fr.getEncoder().setPositionConversionFactor(Constants.kEncoderDistancePerPulse);
    spark_fl.getEncoder().setPositionConversionFactor(Constants.kEncoderDistancePerPulse);
    spark_br.getEncoder().setPositionConversionFactor(Constants.kEncoderDistancePerPulse);
    spark_bl.getEncoder().setPositionConversionFactor(Constants.kEncoderDistancePerPulse);
    spark_fr.getEncoder().setVelocityConversionFactor(vel);
    spark_fl.getEncoder().setVelocityConversionFactor(vel);
    spark_br.getEncoder().setVelocityConversionFactor(vel);
    spark_bl.getEncoder().setVelocityConversionFactor(vel);

    drivetrain = new MecanumDrive(spark_fl, spark_bl, spark_fr, spark_br);
  }

  public double getFLEncoderVelocity(){
    return spark_fl.getEncoder().getVelocity();
  }
  public double getFREncoderVelocity(){
    return spark_fr.getEncoder().getVelocity();
  }
  public double getBLEncoderVelocity(){
    return spark_bl.getEncoder().getVelocity();
  }
  public double getBREncoderVelocity(){
    return spark_br.getEncoder().getVelocity();
  }

  public static Drivetrain getInstance(){
    if(instance == null){
      instance = new Drivetrain();
    }
    return instance;
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
  
  @Override
  public void periodic() {
    //update the odometry in the periodic block
    m_odometry.update(getNavxAngle(), 
      new MecanumDriveWheelSpeeds(
        spark_fl.getEncoder().getVelocity(), 
        spark_fr.getEncoder().getVelocity(), 
        spark_bl.getEncoder().getVelocity(),
        spark_br.getEncoder().getVelocity()
      ));
      m_field.setRobotPose(m_odometry.getPoseMeters());
      //System.out.println("fl: " + getWheelVoltages()[0] + "fr: " + getWheelVoltages()[1] + "bl: " + getWheelVoltages()[2] + "br: " + getWheelVoltages()[3]);
    // System.out.println("Navx angle: " + getNavxAngle().getDegrees());
  }
 
  
  public void simulationPeriodic(){
    int dev = SimDeviceDataJNI.getSimDeviceHandle("navX-Sensor[0]");
    SimDouble sim_yaw = new SimDouble(SimDeviceDataJNI.getSimValueHandle(dev, "Yaw"));
    sim_yaw.set(-navx.getRotation2d().getDegrees());
    //sim_yaw.set(1000);
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

  public double[] getWheelVoltages(){
    return new double[]{spark_fl.getBusVoltage(), spark_fr.getBusVoltage(), spark_bl.getBusVoltage(), spark_br.getBusVoltage()};
  }

  //getYaw = Returns the current yaw value (in degrees, from -180 to 180) 
  //reported by the sensor. Yaw is a measure of rotation around the Z Axis 
  //(which is perpendicular to the earth).

  public static void zeroYaw(){
    navx.zeroYaw();
    System.out.println("******************resetted yaw*********");
  }

  //Returns the total accumulated yaw angle (Z Axis, in degrees) reported by the sensor.
  public Rotation2d getNavxAngle(){
    return Rotation2d.fromDegrees(-navx.getAngle());
  }

  public double getYaw(){
    return navx.getYaw();
  }

  public double linearAccelX(){
    return navx.getWorldLinearAccelX();
  }

  public double linearAccelY(){
    return navx.getWorldLinearAccelY();
  }

  public double linearAccelZ(){
    return navx.getWorldLinearAccelZ();
  }

  public double getAngularVel(){
    return navx.getRate();
  }

  public boolean isCalibrating(){
    return navx.isCalibrating();
  }

}
