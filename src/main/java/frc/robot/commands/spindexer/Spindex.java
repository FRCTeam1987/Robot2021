// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.spindexer;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Spindexer;

public class Spindex extends InstantCommand {

  private final double m_speed;
  private final Spindexer m_spindexer;

  /** Creates a new SpinTest. */
  public Spindex(final Spindexer spindexer, final double speed) {
    m_speed = speed;
    m_spindexer = spindexer;
    addRequirements(m_spindexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_spindexer.setBigBlock(m_speed);
  }
}
