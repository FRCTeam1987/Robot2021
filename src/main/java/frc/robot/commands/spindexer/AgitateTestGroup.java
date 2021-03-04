// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.spindexer;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AgitateTestGroup extends SequentialCommandGroup {
  /** Creates a new AgitateTest. */
  public AgitateTestGroup() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      // true for clockwise and false for counter clockwise
      new AgitateTest(true),
      new WaitCommand(2.0),
      new AgitateTest(false),
      new WaitCommand(2.0)
    );
  }

  @Override
  public void end(boolean interrupted) {
    CommandScheduler.getInstance().schedule(this);
  }
}
