// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LimeLight;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AimBot2 extends PIDCommand {
  /** Creates a new AimBot2. */
  public AimBot2(final Drive drive, final LimeLight limeLight) {
    super(
      // The controller that the command will use
      new PIDController(0.3, 0, 0),
      // This should return the measurement
      limeLight::getXAxis,
      // This should return the setpoint (can also be a constant)
      0,
      // This uses the output
      output -> {
        drive.driveTank(-output, output);
      },
      drive
    );
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().enableContinuousInput(-180, 180);
    getController().setTolerance(0.5, 2.5);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
