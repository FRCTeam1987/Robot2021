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
public class EightBallTrench extends SequentialCommandGroup {
  /** Creates a new FarAuto. */
  public EightBallTrench(
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
            new Translation2d(0.029796706778515186, 2.291077931001687E-4),
            new Translation2d(0.29711462377731046, 0.03413753739394231),
            new Translation2d(0.6691125601254789, 0.21456184252750102),
            new Translation2d(0.9807766357281605, 0.5526634022228909),
            new Translation2d(1.2993862400901712, 0.9941944159917337),
            new Translation2d(1.7151809464895424, 1.357177219632547),
            new Translation2d(2.254765032546359, 1.5473492063272716),
            new Translation2d(2.8038006078937183, 1.5826455788902507),
            new Translation2d(3.384634639630791, 1.5999305176252192),
            new Translation2d(3.9426898229721523, 1.6194071009037871),
            new Translation2d(4.543052452175487, 1.624371412970865),
            new Translation2d(5.094486088553332, 1.618025482337135),
            new Translation2d(5.5949867384757574, 1.6125379008360425),
            new Translation2d(5.901028751993499, 1.6115278674307456)
          ),
          new Pose2d(6.002516162845521, 1.6103755340989137, Rotation2d.fromDegrees(-0.11299896240234376)),
          false
        )
      ),
      new WaitCommand(0.25),
      new StopCollect(collector),
      new ParallelRaceGroup(
        new Agitate(spindexer),
        DrivePathHelpers.createOnBoardDrivePathCommand(
          drive,
          new Pose2d(6.001252012369706, 1.6103800763117835, Rotation2d.fromDegrees(-0.14299774169921875)),
          List.of(
            new Translation2d(5.958993215762906, 1.6105036966231345),
            new Translation2d(5.718864112467584, 1.6109086929857859),
            new Translation2d(5.289728381135451, 1.6144276095813828),
            new Translation2d(4.750384829901705, 1.6311241478443361),
            new Translation2d(4.159251178830143, 1.5954113493742959),
            new Translation2d(3.570256859321768, 1.396499295370707),
            new Translation2d(3.0203412724242864, 1.0910708813310275),
            new Translation2d(2.534942321995636, 0.8337761526013393),
            new Translation2d(2.0971065192614784, 0.703592823340136),
            new Translation2d(1.843196290206042, 0.6704281440501002)
          ),
          new Pose2d(1.7590644060620944, 0.6630167025912853, Rotation2d.fromDegrees(5.076988220214843)),
          true,
          false
        )
      ),
      new ParallelRaceGroup(
        new WaitCommand(0.75),
        new Agitate(spindexer)
        ), // agitate before shooting
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
          new Pose2d(1.7590644060620944, 0.6630167025912853, Rotation2d.fromDegrees(5.027000427246094)),
          List.of(
            new Translation2d(1.817785961804108, 0.6686709776604945),
            new Translation2d(2.0550388166585165, 0.7090543890763209),
            new Translation2d(2.464960912413297, 0.8555813885192247),
            new Translation2d(2.913943677486986, 1.0935019188962105),
            new Translation2d(3.383034969551812, 1.3572493325830846),
            new Translation2d(3.8576929308657726, 1.5207182218046917),
            new Translation2d(4.325044953527426, 1.5773014674754395),
            new Translation2d(4.775487679145907, 1.5920596880824152),
            new Translation2d(5.16027067941907, 1.6056828169675368),
            new Translation2d(5.448681642650842, 1.617407721245849),
            new Translation2d(5.706744723940226, 1.6253943357045428)
          ),
          new Pose2d(5.93173739320445, 1.6283158309261418, Rotation2d.fromDegrees(0.6469955444335938)),
          false,
          false
        )
      ),
      new StopAll(collector, spindexer, shooter)
    );
   }
 }
