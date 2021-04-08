// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.spindexer;

// import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Spindexer;

// Runs the spindexer to line the balls up next to each other to speed up shooting
public class PrepShoot extends SetSpindexer {
  
  // private final Spindexer m_spindexer;

  public PrepShoot(final Spindexer spindexer) {
    super(spindexer, 0.0, 0.0, 0.25);
    // m_spindexer = spindexer;
    // addRequirements(m_spindexer);
    // this.withTimeout(1);
  }

  // // Called when the command is initially scheduled.
  // @Override
  // public void initialize() {
  //   m_spindexer.setBigBlock(0.3);
  // }

  // // Called every time the scheduler runs while the command is scheduled.
  // @Override
  // public void execute() {}

  // // Called once the command ends or is interrupted.
  // @Override
  // public void end(boolean interrupted) {}

  // // Returns true when the command should end.
  // @Override
  // public boolean isFinished() {
  //   return true;
  // }
}
