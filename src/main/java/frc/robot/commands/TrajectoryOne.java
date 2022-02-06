// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import java.util.List;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveMotorVoltages;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.MecanumControllerCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class TrajectoryOne {
    Drivetrain m_drivetrain;
    public Command generateTrajectory(Drivetrain drivetrain){
        TrajectoryConfig config = new TrajectoryConfig(Constants.kMaxVelocityMetersPerSecond, Constants.maxAccelerationMetersPerSecondSq)
        .setKinematics(Constants.kDriveKinematics);
    
        Trajectory testTrajectory = TrajectoryGenerator.generateTrajectory(
          new Pose2d(0, 0, new Rotation2d(0)), 
          List.of(new Translation2d(2,0)),
          new Pose2d(2,0, new Rotation2d(0)), 
          config);
        
         MecanumControllerCommand mecanumControllerCommand =
          new MecanumControllerCommand(
            testTrajectory,
            drivetrain::getPose,
            Constants.kFeedforward,
            Constants.kDriveKinematics,
    
            //Position controllers 
            new PIDController(Constants.kPXController, 0, 0),
            new PIDController(Constants.kPYController, 0, 0),
            new ProfiledPIDController(Constants.kPThetaController, 0, 0, Constants.kThetaControllerConstraints),
    
            Constants.kMaxVelocityMetersPerSecond,
    
            //Velocity PID's
            new PIDController(Constants.kPFrontLeftVel, 0, 0),
            new PIDController(Constants.kPRearLeftVel, 0, 0),
            new PIDController(Constants.kPFrontRightVel, 0, 0),
            new PIDController(Constants.kPRearRightVel, 0, 0),
            drivetrain::getCurrentWheelSpeeds,
            drivetrain::mecanumVolts,
            drivetrain);

            //setting up sequence of commands
            //resetting the drivetrain odometry
            return new InstantCommand(() -> drivetrain.resetOdometry(testTrajectory.getInitialPose()), drivetrain)
              //run the actual MecanumControllor
              .andThen(mecanumControllerCommand)
              //Make sure that the robot stops
              .andThen(new InstantCommand (() -> drivetrain.mecanumVolts(new MecanumDriveMotorVoltages(0,0,0,0)), drivetrain));
      }
}
