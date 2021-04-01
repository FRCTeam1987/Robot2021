// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.challenges;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.commands.shooter.ConfigClose;
import frc.robot.commands.shooter.ConfigFar;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spindexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Accuracy extends SequentialCommandGroup {
  /** Creates a new Accuracy. */
  public Accuracy(Drive drive, Spindexer spindexer, Shooter shooter, Collector collector, XboxController xbox, LimeLight limelight) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      // TODO: Determine distances and cycles
      // TODO: Implement shooter hood for close shots
      // new ConfigClose(shooter),
      // PowerPort.cycle(drive, spindexer, shooter, collector, Constants.Challenges.AccuracyGreenDistance),
      // new WaitUntilCommand(() -> xbox.getStartButtonPressed()),
      new ConfigFar(shooter),
      // PowerPort.cycle(drive, spindexer, shooter, collector, Constants.Challenges.AccuracyYellowDistance),
      // new WaitUntilCommand(() -> xbox.getStartButtonPressed()),
      // PowerPort.cycle(drive, spindexer, shooter, collector, Constants.Challenges.AccuracyBlueDistance),
      // new WaitUntilCommand(() -> xbox.getStartButtonPressed()),
      PowerPort.cycle(drive, spindexer, shooter, collector, limelight, Constants.Challenges.AccuracyBlueDistance),
      new WaitUntilCommand(() -> xbox.getStartButtonPressed()),
      PowerPort.cycle(drive, spindexer, shooter, collector, limelight, Constants.Challenges.AccuracyRedDistance)
    );
  }
}
