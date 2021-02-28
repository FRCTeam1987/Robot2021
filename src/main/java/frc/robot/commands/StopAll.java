// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spindexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class StopAll extends InstantCommand {

  private final Collector m_collector;
  private final Spindexer m_spindexer;
  private final Shooter m_shooter;

  public StopAll(final Collector collector, final Spindexer spindexer, final Shooter shooter) {

    m_collector = collector;
    m_spindexer = spindexer;
    m_shooter = shooter;
    addRequirements(m_collector, m_spindexer, m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_collector.stop();
    m_spindexer.stop();
    m_shooter.stop();
  }
}
