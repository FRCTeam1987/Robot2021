// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.DigitalDebouncer;
import frc.robot.subsystems.Shooter;

  

public class ShooterMonitor extends CommandBase {
  private final Shooter m_shooter;
  private final DigitalDebouncer m_shotBall;
  
  public ShooterMonitor(final Shooter shooter) {
    m_shooter = shooter;
    addRequirements(m_shooter);
    m_shotBall = new DigitalDebouncer(.25);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shotBall.periodic(false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean isBallShooting = m_shooter.isBallShooting();
    m_shotBall.periodic(isBallShooting);
    if (!m_shotBall.get()) {
      // A ball is shooting
      m_shooter.shotBall();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_shooter.hasShotAllBalls(); 
  }
}
