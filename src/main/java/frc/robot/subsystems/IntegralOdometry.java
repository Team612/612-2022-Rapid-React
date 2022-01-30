// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
/** Add your docs here. */

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;

public class IntegralOdometry {
    private BuiltInAccelerometer m_accelerometer;
    private Pose2d m_currentPosition;
    private double m_previousTime = -1;

    private Rotation2d m_gyroOffset;
    private Rotation2d m_previousAngle;

    public IntegralOdometry(BuiltInAccelerometer accelerometer, Rotation2d gyroAngle, Pose2d initialPosition){
        m_accelerometer = accelerometer;
        m_currentPosition = initialPosition;
        m_gyroOffset = m_currentPosition.getRotation().minus(gyroAngle);
        m_previousAngle = initialPosition.getRotation();
    }

    public void resetPosition(Pose2d currentPosition, Rotation2d gyroAngle){
        m_currentPosition = currentPosition;
        m_previousAngle = currentPosition.getRotation();
        m_gyroOffset = currentPosition.getRotation().minus(gyroAngle);
    }

    public Pose2d getPoseMeters(){
        return m_currentPosition;
    }

    public Pose2d getVelocity(double currentTime, Rotation2d currentAngle, double getAccelX, double getAccelY, double getAccelZ){
        double delta_t = m_previousTime >= 0? currentTime - m_previousTime : 0.0;
        m_previousTime = currentTime;

        var angle = currentAngle.plus(m_gyroOffset);
        Pose2d accel_vector = new Pose2d(getAccelX, getAccelY, new Rotation2d(getAccelZ));

        var newPose = 
            m_currentPosition.exp(
                new Twist2d(
                    accel_vector.getX() * delta_t,
                    accel_vector.getY() * delta_t,
                    currentAngle.minus(m_previousAngle).getRadians()
                )
            );
        m_previousAngle = currentAngle;
        m_currentPosition = new Pose2d(newPose.getTranslation(), currentAngle);

        return newPose;
    }

    public Pose2d getVelocityWithTime(Rotation2d gyroAngle, double getAccelX, double getAccelY, double getAccelZ){
        return getVelocity(WPIUtilJNI.now() * 1.0e-6, gyroAngle, getAccelX, getAccelY, getAccelZ);
    }
}
