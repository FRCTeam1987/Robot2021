// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SetGalacticRedOrBlue extends InstantCommand {
  private final Drive m_drive;
  private final Constants.Drive.Galatic.RedOrBlue m_redOrBlue;

  public SetGalacticRedOrBlue(Drive drive, Constants.Drive.Galatic.RedOrBlue redOrBlue) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = drive;
    addRequirements(m_drive);
    m_redOrBlue = redOrBlue;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.setGalacticRedOrBlue(m_redOrBlue);
  }
}