// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.spindexer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AgitateTest extends CommandBase {
  private static boolean m_clockwise;

  /** Creates a new AgitateTest. */
  public AgitateTest(boolean clockwise) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_clockwise = clockwise;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println(
      (m_clockwise) ?
      "clockwise" :
      "counter clockwise"
    );
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
