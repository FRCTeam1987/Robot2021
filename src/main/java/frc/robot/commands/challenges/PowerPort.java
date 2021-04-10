// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.challenges;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.AimBot;
import frc.robot.commands.StopAll;
import frc.robot.commands.drive.DrivePathHelpers;
import frc.robot.commands.shooter.ShootLimeLight;
import frc.robot.commands.shooter.ShootRPM;
import frc.robot.commands.spindexer.FeedShooter;
import frc.robot.commands.spindexer.PrepShoot;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spindexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PowerPort {
  public static SequentialCommandGroup cycle(Drive drive, Spindexer spindexer, Shooter shooter, Collector collector, LimeLight limelight, double distance) {
    return new SequentialCommandGroup(
      new ParallelCommandGroup(
        new SequentialCommandGroup(
          // new Agitate(spindexer, 0.5, 0.4).withTimeout(0.75),
          new PrepShoot(spindexer)
        ),
        new ShootRPM(shooter, 3700),
        DrivePathHelpers.driveStraightCommand(drive, -distance)
      ),
      // TODO: Add AimBot here
      // TODO ShootLimelight
      new AimBot(drive, limelight),
      new ShootLimeLight(shooter, limelight),
      new FeedShooter(spindexer),
      new WaitCommand(Constants.Challenges.PowerPortShootDuration),  // TODO: Test this timing and add to Constants
      new StopAll(collector, spindexer, shooter),
      DrivePathHelpers.driveStraightCommand(drive, distance)
    );
  }

  public static SequentialCommandGroup cycleFirst(Drive drive, Spindexer spindexer, Shooter shooter, Collector collector, LimeLight limelight, double distance) {

    return new SequentialCommandGroup(
      // TODO: Add AimBot here
      // TODO ShootLimelight
      // new ShootRPM(shooter, 3500),
      // new WaitCommand(0.75),  // Give the shooter wheels a chance to speeed up
      new AimBot(drive, limelight),
      new ShootLimeLight(shooter, limelight),
      new FeedShooter(spindexer),
      new WaitCommand(Constants.Challenges.PowerPortShootDuration),  // TODO: Test this timing and add to Constants
      new StopAll(collector, spindexer, shooter),
      DrivePathHelpers.driveStraightCommand(drive, distance)
    );
  } 

  public static SequentialCommandGroup cycle(Drive drive, Spindexer spindexer, Shooter shooter, Collector collector, LimeLight limelight) {
    return cycle(drive, spindexer, shooter, collector, limelight, Constants.Challenges.PowerPortDistance);
  }

  public static SequentialCommandGroup cycleFirst(Drive drive, Spindexer spindexer, Shooter shooter, Collector collector, LimeLight limelight) {
    return cycleFirst(drive, spindexer, shooter, collector, limelight, Constants.Challenges.PowerPortDistance);
  }
}
