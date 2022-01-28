// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.simulation.SimDeviceDataJNI;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  public final CANSparkMax spark_fl = new CANSparkMax(Constants.SPARK_FL, MotorType.kBrushless);
  public final CANSparkMax spark_fr = new CANSparkMax(Constants.SPARK_FR, MotorType.kBrushless);
  public final CANSparkMax spark_bl = new CANSparkMax(Constants.SPARK_BL, MotorType.kBrushless);
  public final CANSparkMax spark_br = new CANSparkMax(Constants.SPARK_BR, MotorType.kBrushless);

  public final MotorControllerGroup m_leftMotor = new MotorControllerGroup(spark_fl, spark_bl);
  public final MotorControllerGroup m_rightMotor = new MotorControllerGroup(spark_fr, spark_br);

  private final double DEADZONE = 0.1;

  private MecanumDrive drivetrain;

  private DifferentialDrivetrainSim m_driveSim = new DifferentialDrivetrainSim(
    LinearSystemId.identifyDrivetrainSystem(Constants.kV, Constants.kA, Constants.kV_Angular, Constants.kA_Angular),
    DCMotor.getNEO(2),
    16.0,
    Constants.kTrackWidth,
    Units.inchesToMeters(6),
    null
  );

  private final AHRS navx = new AHRS(I2C.Port.kMXP);
  MecanumDriveOdometry m_odometry = new MecanumDriveOdometry(Constants.kDriveKinematics, navx.getRotation2d());

  //constructor
  public Drivetrain() {
    drivetrain = new MecanumDrive(spark_fl, spark_bl, spark_fr, spark_br);
    spark_fr.setInverted(true);
    spark_br.setInverted(true);

    REVPhysicsSim.getInstance().addSparkMax(spark_fl, (float) 3.28, 5820);
    REVPhysicsSim.getInstance().addSparkMax(spark_fr, (float) 3.28, 5820);
    REVPhysicsSim.getInstance().addSparkMax(spark_bl, (float) 3.28, 5820);
    REVPhysicsSim.getInstance().addSparkMax(spark_br, (float) 3.28, 5820);

    spark_fl.setIdleMode(IdleMode.kBrake);
    spark_fr.setIdleMode(IdleMode.kBrake);
    spark_bl.setIdleMode(IdleMode.kBrake);
    spark_br.setIdleMode(IdleMode.kBrake);
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
  }
 
  public void simulationPeriodic(){
    int dev = SimDeviceDataJNI.getSimDeviceHandle("navX-Sensor[0]");
    SimDouble sim_yaw = new SimDouble(SimDeviceDataJNI.getSimValueHandle(dev, "Yaw"));
    
    m_driveSim.setInputs(m_leftMotor.get() * RobotController.getInputVoltage(), m_rightMotor.get() * RobotController.getInputVoltage());
    m_driveSim.update(0.02);

    REVPhysicsSim.getInstance().run();
    sim_yaw.set(-m_driveSim.getHeading().getDegrees());
  }
  
}
