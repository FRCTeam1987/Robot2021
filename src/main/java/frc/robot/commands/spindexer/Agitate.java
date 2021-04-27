// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.spindexer;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Spindexer;

public class Agitate extends CommandBase {

  private final Spindexer m_spindexer;
  private final double m_speed;
  private final double m_duration;
  private double m_switchTimestamp;
  private int m_direction;

  public Agitate(final Spindexer spindexer, final double speed, final double duration) {
    m_spindexer = spindexer;
    m_speed = speed;
    m_duration = duration;
    addRequirements(m_spindexer);
  }

  public Agitate(final Spindexer spindexer) {
    this(spindexer, Constants.Spindexer.agitateSpeed, Constants.Spindexer.agitateDuration);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_direction = 1;
    m_switchTimestamp = Timer.getFPGATimestamp();
    m_spindexer.setBigBlock(m_speed * m_direction);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    final double currentTime = Timer.getFPGATimestamp();
    if (currentTime < m_switchTimestamp + m_duration) {
      return;
    }
    m_switchTimestamp = currentTime;
    m_direction *= -1;
    m_spindexer.setBigBlock(m_speed * m_direction);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_spindexer.setBigBlock(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
