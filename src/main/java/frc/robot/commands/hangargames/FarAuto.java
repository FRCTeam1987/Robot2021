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
public class FarAuto extends SequentialCommandGroup {
  /** Creates a new FarAuto. */
  public FarAuto(
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
      new StartCollect(collector),
      DrivePathHelpers.driveStraightCommand(drive, 2.6),
      // new WaitCommand(0.25),
      new StopCollect(collector),
      new ParallelRaceGroup(
        new Agitate(spindexer),
        DrivePathHelpers.createOnBoardDrivePathCommand(
          drive,
          new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
          List.of(
            new Translation2d(-0.05550115008478476, 1.8394408605086314E-4),
            new Translation2d(-0.2458827703012238, 0.010776621819167212),
            new Translation2d(-0.5464428110977673, 0.06051834753587001),
            new Translation2d(-0.9025490047524852, 0.22395987649317617),
            new Translation2d(-1.20818749259906, 0.5199441165934529),
            new Translation2d(-1.3866368641226983, 0.9232454535313881),
            new Translation2d(-1.4931383973040968, 1.4553686333444316),
            new Translation2d(-1.5896194880904537, 2.060348514268685),
            new Translation2d(-1.6475622575487603, 2.646350494565739),
            new Translation2d(-1.6633664136529807, 3.249034470521058),
            new Translation2d(-1.6650149605581859, 3.8147050907193463),
            new Translation2d(-1.6662488932573052, 4.3375222610477495),
            new Translation2d(-1.6800961213098373, 4.812531070715421),
            new Translation2d(-1.7914561394768453, 5.156403978379423),
            new Translation2d(-2.0151469281789147, 5.395622120179954),
            new Translation2d(-2.2575446214762613, 5.502268689388504)
          ),
new Pose2d(-2.3652379731062765, 5.513766053596661, Rotation2d.fromDegrees(6.2290029525756845)),
          true
        )
      ),
      new ParallelRaceGroup(
        new Agitate(spindexer),
        new AimBot(drive, limeLight)
      ),
      new ShootLimeLight(shooter, limeLight),
      new ParallelCommandGroup(
        new FeedShooter(spindexer),
        new WaitCommand(2.0)
      ),
      new StopAll(collector, spindexer, shooter),
      new StartCollect(collector),
      new ParallelRaceGroup(
        new Agitate(spindexer),
        DrivePathHelpers.createOnBoardDrivePathCommand(
          drive,
          new Pose2d(-2.3649985986820647, 5.513792143589033, Rotation2d.fromDegrees(6.219000816345214)),
            List.of(
              new Translation2d(-2.309505262914631, 5.521159048775289),
              new Translation2d(-2.0438094321346476, 5.551723140441807),
              new Translation2d(-1.6733743916668813, 5.6090745984451145),
              new Translation2d(-1.2385473588081422, 5.703835507522106),
              new Translation2d(-0.8024849096224365, 5.8113748504688045),
              new Translation2d(-0.3744164232611161, 5.874367884394817),
              new Translation2d(0.06677763121965183, 5.814858410433653),
              new Translation2d(0.43466642728232185, 5.59976742094826),
              new Translation2d(0.6436903026624545, 5.283306644930623),
              new Translation2d(0.673202301187954, 4.977271673092282),
              new Translation2d(0.5957795721784652, 4.72205576252892),
              new Translation2d(0.42687884942416265, 4.474076554600488),
              new Translation2d(0.2424564765784037, 4.305857678091895)
            ),
new Pose2d(0.1732638118538688, 4.257655330094851, Rotation2d.fromDegrees(-146.54100036621094)),
          false,
          false
        )
      ),
      new StopCollect(collector),
      new ParallelRaceGroup(
        new Agitate(spindexer),
        DrivePathHelpers.createOnBoardDrivePathCommand(
          drive,
          new Pose2d(0.17331403388233796, 4.257688519671991, Rotation2d.fromDegrees(-146.54100036621094)),
            List.of(
            new Translation2d(0.1952998507031909, 4.272988097037664),
            new Translation2d(0.36384362316291036, 4.444479326387576),
            new Translation2d(0.5224752771886959, 4.815322007128506),
            new Translation2d(0.466521605935213, 5.230373111924653),
            new Translation2d(0.17252771135707914, 5.567877837540999),
            new Translation2d(-0.1965888134921414, 5.693202752930731),
            new Translation2d(-0.4888647909382599, 5.686103098592013)
            ),
          new Pose2d(-0.6835851230386165, 5.660754023667665, Rotation2d.fromDegrees(5.998999595642091)),
          true,
          false
        )
      ),
      new PrepShoot(spindexer),
      new AimBot(drive, limeLight),
      new ShootLimeLight(shooter, limeLight),
      new ParallelCommandGroup(
        new FeedShooter(spindexer),
        new WaitCommand(2.0)
      ),
      new StopAll(collector, spindexer, shooter)
    );
  }
}
