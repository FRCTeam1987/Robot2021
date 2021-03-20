// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class DrivePathHelpers {

  public static CentripetalAccelerationConstraint getCentripetalAccelerationConstraint() {
    return new CentripetalAccelerationConstraint(Constants.Drive.Controls.maxCentripetalMetersPerSecondSquared);
  }

  public static DifferentialDriveKinematics getKinematics() {
    return new DifferentialDriveKinematics(Constants.Drive.Values.trackWidth);
  }

  public static SimpleMotorFeedforward getFeedForward() {
    return new SimpleMotorFeedforward(
      Constants.Drive.Controls.sVolts,
      Constants.Drive.Controls.vVoltsSecondsPerMeter,
      Constants.Drive.Controls.aVoltsSecondsSquaredPerMeter
    );
  }

  public static DifferentialDriveVoltageConstraint getVoltageConstraint() {
    return new DifferentialDriveVoltageConstraint(
      getFeedForward(),
      getKinematics(),
      Constants.Drive.Controls.maxVolts
    );
  }

  public static TrajectoryConfig getTrajectoryConfig(final Boolean reversed) {
    return new TrajectoryConfig(
      Constants.Drive.Controls.maxSpeedMetersPerSecond,
      Constants.Drive.Controls.maxAccelerationMetersPerSecondSquared
    ).setKinematics(getKinematics())
    .addConstraint(getVoltageConstraint())
    // .addConstraint(getCentripetalAccelerationConstraint())
    .setReversed(reversed);
  }

  public static Command createOnBoardDrivePathCommand(final Drive drive,
    final Pose2d startPose, final List<Translation2d> middle, final Pose2d endPose,final Boolean reversed) {
      final Trajectory trajectory = TrajectoryGenerator.generateTrajectory(startPose, middle, endPose, getTrajectoryConfig(reversed));
      return createDrivePathCommand(drive, trajectory);
  }

  public static Command createOnBoardDrivePathCommand(final Drive drive,
    final Pose2d startPose, final List<Translation2d> middle, final Pose2d endPose,
    final Boolean reversed, final Boolean shouldResetOdometry) {
      final Trajectory trajectory = TrajectoryGenerator.generateTrajectory(startPose, middle, endPose, getTrajectoryConfig(reversed));
      return createDrivePathCommand(drive, trajectory, shouldResetOdometry);
    }

  public static Trajectory createTrajectoryFromPathWeaverJsonFile(final String path) {
    Trajectory trajectory = new Trajectory();
    try {
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(path);
      trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory: " + path, ex.getStackTrace());
    }

    return trajectory;
  }

  public static Command createDrivePathCommand(final Drive drive, final Trajectory trajectory) {
    return createDrivePathCommand(drive, trajectory, true);
  }
  public static Command createDrivePathCommand(final Drive drive, final Trajectory trajectory, final Boolean shouldResetOdometry) {
    final RamseteController ramseteController = new RamseteController(Constants.Drive.Controls.ramseteB, Constants.Drive.Controls.ramseteZeta);
    final RamseteController disabledRamseteController = new RamseteController() {
      @Override
      public ChassisSpeeds calculate(Pose2d currentPose, Pose2d poseRef, double linearVelocityRefMeters, double angularVelocityRefRadiansPerSecond) {
        return new ChassisSpeeds(linearVelocityRefMeters, 0.0, angularVelocityRefRadiansPerSecond);
      }
    };
    var table = NetworkTableInstance.getDefault().getTable("troubleshooting");
    var leftReference = table.getEntry("left_reference");
    leftReference.setNumber(0);
    var leftMeasurement = table.getEntry("left_measurement");
    leftMeasurement.setNumber(0);
    var rightReference = table.getEntry("right_reference");
    rightReference.setNumber(0);
    var rightMeasurement = table.getEntry("right_measurement");
    rightMeasurement.setNumber(0);
    final SimpleMotorFeedforward feedForward = getFeedForward();
    final DifferentialDriveKinematics kinematics = getKinematics();
    final PIDController leftController = new PIDController(Constants.Drive.Controls.pVelocity, 0, 0);
    final PIDController rightController = new PIDController(Constants.Drive.Controls.pVelocity, 0, 0);
    double startTime;
    return new InstantCommand(
      () -> {
        if (shouldResetOdometry) {
          drive.resetOdometry(trajectory.getInitialPose());
        }
        drive.setBrake();
      },
      drive
    ).andThen(
      new RamseteCommand(
        trajectory,
        drive::getPose,
        // disabledRamseteController,
        ramseteController,
        feedForward,
        kinematics,
        drive::getWheelSpeeds,
        leftController,
        rightController,
        (leftVolts, rightVolts) -> {
          drive.driveTankVolts(leftVolts, rightVolts);
          leftReference.setNumber(drive.getWheelSpeeds().leftMetersPerSecond);
          leftMeasurement.setNumber(leftController.getSetpoint());
          rightReference.setNumber(drive.getWheelSpeeds().rightMetersPerSecond);
          rightMeasurement.setNumber(rightController.getSetpoint());
        },
        drive
      )
    ).andThen(
      () -> {
        drive.driveTankVolts(0, 0);
        drive.setCoast();
      },
      drive
    );
  }

  public static Command driveStraightCommand(final Drive drive, final double distance) {
    return createOnBoardDrivePathCommand(
      drive,
      new Pose2d(0.0, 0.0, new Rotation2d(0)),
      List.of(
        new Translation2d(distance / 2, 0.0)
      ),
      new Pose2d(distance, 0.0, new Rotation2d(0)),
      distance < 0
    );
  }
}
