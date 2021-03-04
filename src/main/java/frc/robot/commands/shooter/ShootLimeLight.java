// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.lib.InterpolatingDouble;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootLimeLight extends CommandBase {

  private final Shooter m_shooter;
  private final LimeLight m_limelight;

  public ShootLimeLight(final Shooter shooter, final LimeLight limelight) {
    m_shooter = shooter;
    m_limelight = limelight;
  
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    final double y = m_limelight.getYAxis();
    m_shooter.setRPM(Constants.Shooter.Targeting.kDistanceToShooterSpeedClose.getInterpolated(new InterpolatingDouble(y)).value);
  }

  @Override
  public boolean isFinished() {
    return m_shooter.isInRPMTolerance();
  }
}
