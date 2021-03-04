// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.spindexer;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Spindexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SetSpindexer extends InstantCommand {

  private final Spindexer m_spindexer;
  private final double m_compliantSpeed;
  private final double m_omniSpeed;
  private final double m_bigBlockSpeed;

  public SetSpindexer(final Spindexer spindexer, final double compliantSpeed, final double omniSpeed, final double bigBlockSpeed) {
    m_spindexer = spindexer;
    m_compliantSpeed = compliantSpeed;
    m_omniSpeed = omniSpeed;
    m_bigBlockSpeed = bigBlockSpeed;
    addRequirements(m_spindexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_spindexer.setCompliant(m_compliantSpeed);
    m_spindexer.setOmni(m_omniSpeed);
    m_spindexer.setBigBlock(m_bigBlockSpeed);
  }
}
