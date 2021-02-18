// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class TeleopDrive extends CommandBase {

  private final Drive m_drive;
  private final XboxController m_xbox;

  /** Creates a new TeleopDrive. */
  public TeleopDrive(final Drive drive, final XboxController xbox) {
    m_drive = drive;
    m_xbox = xbox;
    addRequirements(m_drive);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double move = m_xbox.getTriggerAxis(Hand.kRight) - m_xbox.getTriggerAxis(Hand.kLeft);
    double rotate = -m_xbox.getX(Hand.kLeft); //invert turning

    m_drive.driveArcade(-move, rotate);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
