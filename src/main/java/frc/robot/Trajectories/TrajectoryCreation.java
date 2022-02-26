// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Trajectories;

import java.util.List;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import frc.robot.Constants;

/** Add your docs here. */
public class TrajectoryCreation {

    public TrajectoryConfig config = new TrajectoryConfig(Constants.kMaxVelocityMetersPerSecond, Constants.maxAccelerationMetersPerSecondSq)
        .setKinematics(Constants.kDriveKinematics);
    
    public Trajectory testTrajectory = TrajectoryGenerator.generateTrajectory(
        new Pose2d(0, 0, new Rotation2d(0)), 
        List.of(new Translation2d(1,0)),
        new Pose2d(2,0, new Rotation2d(0)), 
        config);

    public Trajectory testTrajectory2 = TrajectoryGenerator.generateTrajectory(
        new Pose2d(0, 0, new Rotation2d(0)), 
        List.of(new Translation2d(2,0), new Translation2d(2,-2), new Translation2d(0, -2)),
        new Pose2d(0,0, new Rotation2d(0)), 
        config);

    public PathPlannerTrajectory examplePath = PathPlanner.loadPath("New New Path", 3, 1);
}
