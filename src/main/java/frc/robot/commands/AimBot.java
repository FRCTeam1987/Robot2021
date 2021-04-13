// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Util;
import frc.robot.lib.DigitalDebouncer;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LimeLight;

public class AimBot extends CommandBase {

  private final Drive m_drive;
  private final LimeLight m_limelight;
  private final DigitalDebouncer m_isOnTarget;
  private final DigitalDebouncer m_cannotSeeTarget;

  /** Creates a new AimBot. */
  public AimBot(final Drive drive, final LimeLight limelight) {
    m_drive = drive;
    m_limelight = limelight;
    m_isOnTarget = new DigitalDebouncer(0.15);
    m_cannotSeeTarget = new DigitalDebouncer(0.25);
    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_limelight.turnOnLEDs();
    m_isOnTarget.periodic(false);
    m_cannotSeeTarget.periodic(false);
    // m_drive.setBrake();
    m_drive.driveTank(0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    final boolean isVisible = m_limelight.getVisible() > 0;
    m_cannotSeeTarget.periodic(!isVisible);
    if (m_cannotSeeTarget.get()) {
      DriverStation.reportWarning("AimBot: Cannot see target!", false);
      m_drive.driveTank(0, 0);
      return;
    }
    final double tx = m_limelight.getXAxis();

    final boolean isOnTarget = isOnTarget();
    m_isOnTarget.periodic(isOnTarget);
    if (isOnTarget) {
      m_drive.driveTank(0, 0);
      return;
    }
    final double steering_adjust = 0.05 * tx;
    final double aimAssist = Math.copySign(
      Math.min(
        Math.max(
          Math.abs(steering_adjust),
          Constants.Drive.Values.minMovePercent // TODO Make this dynamic
        ),
        Constants.Drive.Values.maxMovePercent
      ),
      steering_adjust
    );
    m_drive.driveTank(-aimAssist, aimAssist, true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // m_drive.setCoast();
    m_drive.driveTank(0, 0);
    m_isOnTarget.periodic(true);
    m_cannotSeeTarget.periodic(true);
    m_drive.setBrake();
    m_limelight.turnOffLEDs();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_cannotSeeTarget.get()) {
      DriverStation.reportWarning("Aim Bot - Target Not Visible - Stopping...", false);
      return true;
    }
    return m_isOnTarget.get() && isOnTarget();
  }

  private boolean isOnTarget() {
    return Util.isWithinTolerance(m_limelight.getXAxis(), 0, 1.0);  //=~ 1
  }
}
