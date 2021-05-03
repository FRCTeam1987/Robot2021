// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LimeLight;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AimBot3 extends ProfiledPIDCommand {

  private static final double kTurnP = 0.09;
  private static final double kTurnI = 0.0;
  private static final double kTurnD = 0.0;
  private static final double kMaxTurnRateDegPerS = 7.5;
  private static final double kMaxTurnAccelerationDegPerSSquared = 7.5;
  private static final double kTurnToleranceDeg = 1.0;
  private static final double kTurnRateToleranceDegPerS = 2.0;

  /** Creates a new AimBot3. */
  public AimBot3(final Drive drive, final LimeLight limeLight) {
    super(
      // The ProfiledPIDController used by the command
      new ProfiledPIDController(
        // The PID gains
        kTurnP,
        kTurnI,
        kTurnD,
        // The motion profile constraints
        new TrapezoidProfile.Constraints(kMaxTurnRateDegPerS, kMaxTurnAccelerationDegPerSSquared)),
      // This should return the measurement
      limeLight::getXAxis,
      // This should return the goal (can also be a constant)
      0.0,
      // This uses the output
      (output, setpoint) -> {
        drive.driveArcade(0, output, false);
      },
      drive
    );
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().enableContinuousInput(-180, 180);
    getController().setTolerance(kTurnToleranceDeg, kTurnRateToleranceDegPerS);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atGoal();
  }
}
