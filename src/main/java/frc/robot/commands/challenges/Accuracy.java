// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.challenges;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spindexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Accuracy extends SequentialCommandGroup {
  /** Creates a new Accuracy. */
  public Accuracy(Drive drive, Spindexer spindexer, Shooter shooter, Collector collector, XboxController xbox) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      // TODO: Determine distances and cycles
      // TODO: Implement shooter hood for close shots
      PowerPort.cycle(drive, spindexer, shooter, collector, 1.0),
      new WaitUntilCommand(() -> xbox.getXButtonPressed()),
      PowerPort.cycle(drive, spindexer, shooter, collector, 2.0),
      new WaitUntilCommand(() -> xbox.getXButtonPressed()),
      PowerPort.cycle(drive, spindexer, shooter, collector, 3.0)
    );
  }
}
