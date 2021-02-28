// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Spindexer;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootTest extends InstantCommand {

  private final Spindexer m_spindexer;
  private final Shooter m_shooter;
  private final double m_speed;

  public ShootTest(final Spindexer spindexer, final Shooter shooter, final double speed) {
    m_spindexer = spindexer;
    m_shooter = shooter;
    m_speed = speed;
    addRequirements(m_spindexer);
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setPercent(m_speed);
    m_spindexer.setCompliant(0.95);
    m_spindexer.setOmni(0.95);
  }
}
