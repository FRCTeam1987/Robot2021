// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class WaitForShots extends CommandBase {

  private final Shooter m_shooter;
  private final int m_numberOfShots;
  private int m_currentShots;

  /** Creates a new WaitForShots. */
  public WaitForShots(final Shooter shooter, final int numberOfShots) {
    m_shooter = shooter;
    m_numberOfShots = numberOfShots;
    m_currentShots = m_numberOfShots;
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_currentShots = m_numberOfShots;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // TODO: detect RPM dip as a shot, decrement current shots for each dip
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_currentShots == 0;
  }
}
