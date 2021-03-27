// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.challenges;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.StopAll;
import frc.robot.commands.drive.DrivePathHelpers;
import frc.robot.commands.shooter.ShootRPM;
import frc.robot.commands.spindexer.Agitate;
import frc.robot.commands.spindexer.FeedShooter;
import frc.robot.commands.spindexer.PrepShoot;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spindexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PowerPort {
  public static SequentialCommandGroup cycle(Drive drive, Spindexer spindexer, Shooter shooter, Collector collector) {
    return new SequentialCommandGroup(
      new ParallelCommandGroup(
        new SequentialCommandGroup(
          new Agitate(spindexer, 0.5, 0.4).withTimeout(0.75),
          new PrepShoot(spindexer)
        ),
        new ShootRPM(shooter, 2000),
        DrivePathHelpers.driveStraightCommand(drive, -Constants.Challenges.PowerPortDistance)
      ),
      // TODO: Add AimBot here
      new FeedShooter(spindexer),
      new WaitCommand(1.0),  // TODO: Test this timing and add to Constants
      new StopAll(collector, spindexer, shooter),
      DrivePathHelpers.driveStraightCommand(drive, Constants.Challenges.PowerPortDistance)
    );
  }
}
