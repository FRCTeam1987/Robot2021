// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.hangargames;

import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
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
      new StartCollect(collector),
      DrivePathHelpers.driveStraightCommand(drive, 2.0),
      // new WaitCommand(0.25),
      new StopCollect(collector),
      new ParallelRaceGroup(
        new Agitate(spindexer),
        DrivePathHelpers.createOnBoardDrivePathCommand(
          drive,
          new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
          List.of(
            new Translation2d(-0.035450471349925586, 5.944452561349999E-4),
            new Translation2d(-0.27842679045931, 0.012242149890922408),
            new Translation2d(-0.6240635965938064, 0.05564713677919099),
            new Translation2d(-0.9893544915883036, 0.1939284963326707),
            new Translation2d(-1.3116753796957028, 0.48950747934155486),
            new Translation2d(-1.5375234547010699, 0.930747284712866),
            new Translation2d(-1.6212379706156892, 1.434677069570948),
            new Translation2d(-1.6242475623711847, 1.9141477069394743),
            new Translation2d(-1.610355540167418, 2.4556824858547373),
            new Translation2d(-1.5940366359033196, 2.9468076076951206),
            new Translation2d(-1.5771392606980974, 3.3971930287781227),
            new Translation2d(-1.5687494792694983, 3.8148327198418506),
            new Translation2d(-1.6154566850867098, 4.2248313179129955),
            new Translation2d(-1.7445887942360472, 4.486742436197851),
            new Translation2d(-1.9248092504568042, 4.616490706356302),
            new Translation2d(-2.051853357892612, 4.652056996199755)
          ),
          new Pose2d(-2.118601988207358, 4.655475168206258, Rotation2d.fromDegrees(0.7400054931640624)),
          true
        )
      ),
      new PrepShoot(spindexer),
      new AimBot(drive, limeLight),
      new ShootLimeLight(shooter, limeLight),
      new FeedShooter(spindexer).withTimeout(2.0),
      new StopAll(collector, spindexer, shooter),
      new StartCollect(collector),
      new ParallelRaceGroup(
        new Agitate(spindexer),
        DrivePathHelpers.createOnBoardDrivePathCommand(
          drive,
          new Pose2d(-2.2282320655658556, 4.6778731367343465, Rotation2d.fromDegrees(4.379989624023438)),
          List.of(
            new Translation2d(-2.190117985295738, 4.680792663428301),
            new Translation2d(-1.900806146660265, 4.706484744059248),
            new Translation2d(-1.4792248384194795, 4.754002027055002),
            new Translation2d(-1.0172297925221654, 4.823436258418594),
            new Translation2d(-0.5454751578645527, 4.894808109776567),
            new Translation2d(-0.08090529594991519, 4.942459823763969),
            new Translation2d(0.30361495691902507, 4.889316827703687),
            new Translation2d(0.594620099122985, 4.706924808979446),
            new Translation2d(0.7337938833833635, 4.450635747147415),
            new Translation2d(0.7420200886350184, 4.175281598439769),
            new Translation2d(0.6633797099748981, 3.915465520491286),
            new Translation2d(0.499209712780859, 3.6488276304082032),
            new Translation2d(0.28739832160596895, 3.4279186234373946)
          ),
          new Pose2d(0.14952924853327357, 3.3071296470470877, Rotation2d.fromDegrees(-139.4200096130371)),
          false,
          false
        )
      ),
      new StopCollect(collector),
      new ParallelRaceGroup(
        new Agitate(spindexer),
        DrivePathHelpers.createOnBoardDrivePathCommand(
          drive,
          new Pose2d(0.14939269542114436, 3.307011458388224, Rotation2d.fromDegrees(-139.84000968933105)),
          List.of(
            new Translation2d(0.18407824875336606, 3.337842754798023),
            new Translation2d(0.3270913715975922, 3.4913856061366992),
            new Translation2d(0.4828835264752854, 3.779106114860245),
            new Translation2d(0.5199485823045806, 4.144517411672494),
            new Translation2d(0.3671381703187995, 4.532608790281743),
            new Translation2d(0.04205599275278059, 4.825219637995374),
            new Translation2d(-0.36655590031271135, 4.941779849143629),
            new Translation2d(-0.8188858267511132, 4.909585928688051),
            new Translation2d(-1.2133560363304683, 4.824187713649674),
            new Translation2d(-1.4479701786839725, 4.790483259769166)
          ),
          new Pose2d(-1.4646466894321337, 4.789085225326773, Rotation2d.fromDegrees(4.440002441406264)),
          true,
          false
        )
      ),
      new PrepShoot(spindexer),
      new AimBot(drive, limeLight),
      new ShootLimeLight(shooter, limeLight),
      new FeedShooter(spindexer).withTimeout(2.0),
      new StopAll(collector, spindexer, shooter)
    );
  }
}
