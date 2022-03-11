// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import java.util.List;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import frc.robot.Constants  ;
import frc.robot.subsystems.Drivetrain;

/** Add your docs here. */
public class TrajectoryCreation {

    private double forward_offset = .17;

    // public TrajectoryCreation(){
    //     Drivetrain.zeroYaw();
    // }

    public TrajectoryConfig config = new TrajectoryConfig(Constants.kMaxVelocityMetersPerSecond, Constants.maxAccelerationMetersPerSecondSq)
        .setKinematics(Constants.kDriveKinematics);
    
    
    public Trajectory getOutOfTarmac = TrajectoryGenerator.generateTrajectory(
        new Pose2d(0, 0, new Rotation2d(0)), 
        List.of(
            new Translation2d(1.5 * (0.5), new Rotation2d(0))            
        ),
        new Pose2d(1.5, 0, new Rotation2d(-Math.PI/2.0)), 
        config);

    public Trajectory moveToBall = TrajectoryGenerator.generateTrajectory(
        new Pose2d(0, 0, new Rotation2d(0)), 
        List.of(
            new Translation2d(1.5/2.0, new Rotation2d(0))
        ),
        new Pose2d(1.5, 0, new Rotation2d(-3.0 * Math.PI/8.0)), 
        config);

    public Trajectory testTrajectory2 = TrajectoryGenerator.generateTrajectory(
        new Pose2d(0, 0, new Rotation2d(0)), 
        List.of(new Translation2d(2,0), new Translation2d(2,-2), new Translation2d(0, -2)),
        new Pose2d(0,0, new Rotation2d(0)), 
        config);

    public PathPlannerTrajectory path1v1 = PathPlanner.loadPath("Path 1.1", 3, 1);
    public PathPlannerTrajectory path1v2 = PathPlanner.loadPath("Path 1.2", 3, 1);
    public PathPlannerTrajectory path1v3 = PathPlanner.loadPath("Path 1.3", 3, 1);

    public PathPlannerTrajectory path2v1 = PathPlanner.loadPath("Path 2.1", 3, 1);
    public PathPlannerTrajectory path2v2 = PathPlanner.loadPath("Path 2.2", 3, 1);
    public PathPlannerTrajectory path2v3 = PathPlanner.loadPath("Path 2.3", 3, 1);
    public PathPlannerTrajectory path2v4 = PathPlanner.loadPath("Path 2.4", 3, 1);

    public PathPlannerTrajectory path3v1 = PathPlanner.loadPath("Path 3.1", 3, 1);
    public PathPlannerTrajectory path3v2 = PathPlanner.loadPath("Path 3.2", 3, 1);
    public PathPlannerTrajectory path3v3 = PathPlanner.loadPath("Path 3.3", 3, 1);
    public PathPlannerTrajectory path3v4 = PathPlanner.loadPath("Path 3.4", 3, 1);

    public PathPlannerTrajectory testPath = PathPlanner.loadPath("New Path", 3, 1);
}
