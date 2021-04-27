// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.hangargames;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.StopAll;
import frc.robot.commands.drive.DrivePathHelpers;
import frc.robot.commands.shooter.ShootLimeLight;
import frc.robot.commands.spindexer.FeedShooter;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spindexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class NoCollector extends SequentialCommandGroup {
  /** Creates a new NoCollector. */
  public NoCollector(
    final Drive drive,
    final Collector collector,
    final Spindexer spindexer,
    final Shooter shooter,
    final LimeLight limeLight
  ) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new InstantCommand(() -> limeLight.setPipeline(9.0)),
      new ShootLimeLight(shooter, limeLight),
      new ParallelCommandGroup(
        new FeedShooter(spindexer),
        new WaitCommand(1)
      ),
      new StopAll(collector, spindexer, shooter),
      DrivePathHelpers.driveStraightCommand(drive, -1.0)
    );
  }
}
