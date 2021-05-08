// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.hangargames;

import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AimBot;
import frc.robot.commands.StopAll;
import frc.robot.commands.collector.StartCollect;
import frc.robot.commands.collector.StopCollect;
import frc.robot.commands.drive.DrivePathHelpers;
import frc.robot.commands.shooter.ShootLimeLight;
import frc.robot.commands.spindexer.Agitate;
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
public class SixBallTrench extends SequentialCommandGroup {
  /** Creates a new FarAuto. */
  public SixBallTrench(
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
      new StartCollect(collector),
      new ParallelRaceGroup(
        new Agitate(spindexer),
        DrivePathHelpers.createOnBoardDrivePathCommand(
          drive,
          new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
          List.of(
            new Translation2d(0.03467288657946717, 2.374940945609765E-4),
            new Translation2d(0.3749186273916362, 0.048341225566454954),
            new Translation2d(0.8211413260312612, 0.32710485496444924),
            new Translation2d(1.1421757285239116, 0.7926732238913893),
            new Translation2d(1.530158836112883, 1.2514571818179008),
            new Translation2d(2.10582354177961, 1.513220733217175),
            new Translation2d(2.7584534342336067, 1.575231133787987),
            new Translation2d(3.3522202961833703, 1.564091018371087),
            new Translation2d(3.9712455316836226, 1.5476515085368672),
            new Translation2d(4.4630466843924035, 1.547775028814714)
          ),
          new Pose2d(4.646943189558377, 1.5495210636957784, Rotation2d.fromDegrees(0.548004150390625)),
          false
        )
      ),
      // new WaitCommand(1),
      new StopCollect(collector),
      new ParallelRaceGroup(
        new Agitate(spindexer),
        DrivePathHelpers.createOnBoardDrivePathCommand(
          drive,
          new Pose2d(4.646702409049293, 1.5495187081476924, Rotation2d.fromDegrees(0.5780029296875)),
          List.of(
          new Translation2d(4.6022169235331445, 1.5496000214070202),
          new Translation2d(4.3075010304706725, 1.5502380278999133),
          new Translation2d(3.812519471362363, 1.5318144551939248),
          new Translation2d(3.177376083555862, 1.4698254221574645),
          new Translation2d(2.5416738597529496, 1.3605224061664916),
          new Translation2d(1.8890084563166023, 1.2186059386262225),
          new Translation2d(1.3888190794997302, 1.0910507755425736),
          new Translation2d(1.0468158171185433, 1.004358598401227)
          ),
          new Pose2d(0.8905948800987403, 0.9665612883235929, Rotation2d.fromDegrees(13.867996215820312)),
          true,
          false
        )
      ),
      new ParallelRaceGroup(
        new WaitCommand(0.75),
        new Agitate(spindexer)
        ),
      new ParallelCommandGroup(
        new PrepShoot(spindexer),
        new AimBot(drive, limeLight)
      ),
      new ShootLimeLight(shooter, limeLight),
      new ParallelCommandGroup(
        new FeedShooter(spindexer),
        new WaitCommand(2.0)
      ),
      new StopAll(collector, spindexer, shooter),
      new ParallelRaceGroup(
        new Agitate(spindexer),
        DrivePathHelpers.createOnBoardDrivePathCommand(
          drive,
          new Pose2d(0.8907117666329508, 0.9665901455378964, Rotation2d.fromDegrees(13.867996215820312)),
          List.of(
          new Translation2d(0.9284592155175223, 0.9759389128415531),
          new Translation2d(1.2387398887162226, 1.0646487333661705),
          new Translation2d(1.700166756466234, 1.2350250005670471),
          new Translation2d(2.249749072888093, 1.4221032632710386),
          new Translation2d(2.8320304017813176, 1.531420216113031),
          new Translation2d(3.4386072612797665, 1.545060629276549),
          new Translation2d(4.003164403189701, 1.5319025215954205),
          new Translation2d(4.4995440599507495, 1.5289451198017252),
          new Translation2d(4.881195936747261, 1.5299160171532353),
          new Translation2d(5.107778136366939, 1.5299346511289897)
          ),
          new Pose2d(5.204994501418402, 1.529152111756465, Rotation2d.fromDegrees(-0.6519927978515625)),
          false,
          false
        )
      ),
      new StopAll(collector, spindexer, shooter)
    );
   }
 }
