// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.challenges;

import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.collector.StartCollect;
import frc.robot.commands.collector.StopCollect;
import frc.robot.commands.drive.DrivePathHelpers;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Spindexer;


/** Add your docs here. */
public class GalacticSearch{

    public static Command MakeGalaticPath(final Drive drive, final Collector collector, final Spindexer spindexer, Command path) {
        return new SequentialCommandGroup(
            new StartCollect(collector),
            path,
            new StopCollect(collector)
        );
    }

    public static Command PathARed(final Drive drive, final Collector collector, final Spindexer spindexer) {
        // m_drive, m_collector, m_spindexer
        // TODO start collector upon startup - apply to all paths
        return MakeGalaticPath(drive, collector, spindexer,
            DrivePathHelpers.createOnBoardDrivePathCommand(
            drive,
            new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
            List.of(
                new Translation2d(0.0725384835139452, 1.03259030511446E-4),
                new Translation2d(0.25433387033441235, -6.958310803346422E-4),
                new Translation2d(0.513060543525278, -0.0021519752470937265),
                new Translation2d(0.7814830805838815, -0.0019625583255552716),
                new Translation2d(1.0672328501934238, -0.004264726602745073),
                new Translation2d(1.3443822556142389, -0.0048041032432397405),
                new Translation2d(1.6411923832128297, -0.011063968906852545),
                new Translation2d(1.9414413163204916, -0.050737841047596086),
                new Translation2d(2.234696784878626, -0.14247332199054435),
                new Translation2d(2.5446886944876423, -0.26982994736575344),
                new Translation2d(2.8087359181080314, -0.39811027547149525),
                new Translation2d(3.077054931723919, -0.5509529751694502),
                new Translation2d(3.337384772940955, -0.7048407589854229),
                new Translation2d(3.59260553318915, -0.7811656844013084),
                new Translation2d(3.8735411723719184, -0.7021143615952075),
                new Translation2d(4.10784417510324, -0.423304757535307),
                new Translation2d(4.2025404766513645, -0.0870201282614997),
                new Translation2d(4.223205451005419, 0.2758366272160677),
                new Translation2d(4.235253329363011, 0.6442642780118022),
                new Translation2d(4.27980753858684, 0.9944404643522318),
                new Translation2d(4.408312184282826, 1.3076211204812451),
                new Translation2d(4.64749320279103, 1.5500457234777436),
                new Translation2d(5.0210105146944395, 1.6922249667179872),
                new Translation2d(5.427044912910197, 1.7206451612045317),
                new Translation2d(5.868037738907312, 1.7098510046547377),
                new Translation2d(6.30271383878278, 1.693335306210491),
                new Translation2d(6.759057709310892, 1.6747474382702192),
                new Translation2d(7.206477143477314, 1.659231577596491),
                new Translation2d(7.633368340238061, 1.6507895818914813),
                new Translation2d(8.026129890278295, 1.6465236362661366),
                new Translation2d(8.440423610060357, 1.631417725488327),
                new Translation2d(8.71473372559263, 1.613993961690942)
            ),
            new Pose2d(8.844771563863814, 1.6053098651759086, Rotation2d.fromDegrees(-3.9940032958984384)),
            false           
        ));
    }

    /*
    */

    public static Command PathABlue(final Drive drive, final Collector collector, final Spindexer spindexer) {
        return MakeGalaticPath(
            drive,
            collector,
            spindexer,
            DrivePathHelpers.createOnBoardDrivePathCommand(
                drive,
                new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
                List.of(
                    new Translation2d(0.0418977471670079, 5.0762956503218074E-5),
                    new Translation2d(0.3298150252875784, -0.005978276236087719),
                    new Translation2d(0.8475831006278447, -0.020741608935572732),
                    new Translation2d(1.4561892577849038, -0.06619477451405716),
                    new Translation2d(2.063299270568079, -0.13177668669982484),
                    new Translation2d(2.694869688199307, -0.1899198771833284),
                    new Translation2d(3.295437210394988, -0.23905929853717434),
                    new Translation2d(3.8553029928944005, -0.21750326179289703),
                    new Translation2d(4.329338730176148, -0.06239382442624919),
                    new Translation2d(4.6084948095465235, 0.2466014797977163),
                    new Translation2d(4.653948487142915, 0.6385925843499406),
                    new Translation2d(4.4330993684986515, 0.9782337425738756),
                    new Translation2d(4.088009024997715, 1.1919046637611337),
                    new Translation2d(3.7280189087487146, 1.447041656822998),
                    new Translation2d(3.4475325435195514, 1.8424287721980384),
                    new Translation2d(3.447887332944347, 2.251282492920474),
                    new Translation2d(3.686310225550403, 2.5090016374468482),
                    new Translation2d(4.027071909858797, 2.6012218803184055),
                    new Translation2d(4.502003227873482, 2.5938033166622074),
                    new Translation2d(5.05477685965374, 2.4971583967449837),
                    new Translation2d(5.6228907664216745, 2.362270443861871),
                    new Translation2d(6.217316670120138, 2.273307604858079),
                    new Translation2d(6.852775599200702, 2.293947620867465),
                    new Translation2d(7.451207831152527, 2.401080904529521),
                    new Translation2d(7.982038245314398, 2.529808369372587),
                    new Translation2d(8.360885544194138, 2.641382930735979),
                    new Translation2d(8.53528190890722, 2.7064419362762604)
                ),
                new Pose2d(8.561927174681387, 2.7186607286241085, Rotation2d.fromDegrees(26.78499984741211)),
                false
            )
        );
    }
    
    public static Command PathBRed(final Drive drive, final Collector collector, final Spindexer spindexer) {
        return MakeGalaticPath(
            drive,
            collector,
            spindexer,
            DrivePathHelpers.createOnBoardDrivePathCommand(
                drive,
                new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
                List.of(
                    new Translation2d(0.05140861777406116, -1.2527027522758118E-4),
                    new Translation2d(0.2902987775049788, -0.004187570484562408),
                    new Translation2d(0.6699508727029123, -0.0076336660492259435),
                    new Translation2d(1.1208503278989383, -0.017270411966701132),
                    new Translation2d(1.600397747332607, -0.03671808408311061),
                    new Translation2d(2.090627260498001, -0.19037127851330649),
                    new Translation2d(2.476689296066947, -0.5325438958901533),
                    new Translation2d(2.831220283353694, -0.9279206186511024),
                    new Translation2d(3.2331395683696393, -1.274294461114809),
                    new Translation2d(3.671924701358698, -1.3932986512076662),
                    new Translation2d(4.011150640156833, -1.1929436879635922),
                    new Translation2d(4.243905440006696, -0.8458523778501402),
                    new Translation2d(4.565265288824997, -0.479647467628378),
                    new Translation2d(5.017269083769095, -0.29113501928149754),
                    new Translation2d(5.541347628299142, -0.26181155867487593),
                    new Translation2d(6.174955894074069, -0.25083988608213914),
                    new Translation2d(6.847181166804145, -0.2256066405505233),
                    new Translation2d(7.451832031086812, -0.20863791267363171),
                    new Translation2d(7.994059789213928, -0.19932062998618152),
                    new Translation2d(8.401773031394772, -0.17303458178433176),
                    new Translation2d(8.678152723430621, -0.14006729351416242)
                ),
                new Pose2d(8.772280640686846, -0.12684250051754273, Rotation2d.fromDegrees(7.566999912261964)),
                false
            )
        );
    }

    public static Command PathBBlue(final Drive drive, final Collector collector, final Spindexer spindexer) {
        return MakeGalaticPath(
            drive,
            collector,
            spindexer,
            DrivePathHelpers.createOnBoardDrivePathCommand(
                drive,
                new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
                List.of(
                    new Translation2d(0.036178981346884805, 2.076405241603068E-5),
                    new Translation2d(0.3387208335432156, -0.006129149412471196),
                    new Translation2d(0.8494984991324188, -0.003762159972262039),
                    new Translation2d(1.470324268883303, 0.02022379592936572),
                    new Translation2d(2.1867969452064333, 0.02922895466837258),
                    new Translation2d(2.9606234004969534, 0.04022236407849605),
                    new Translation2d(3.6264706176765324, 0.06900579616736609),
                    new Translation2d(4.174647629368991, 0.1465571926874382),
                    new Translation2d(4.630702642147005, 0.4144325617921917),
                    new Translation2d(5.042204992321449, 0.8363549336135957),
                    new Translation2d(5.457661659618569, 1.2415287938019632),
                    new Translation2d(5.870157924019754, 1.4855771862563218),
                    new Translation2d(6.251005637033227, 1.3962274741483551),
                    new Translation2d(6.5710196693629115, 1.0844854151753394),
                    new Translation2d(6.900015617507868, 0.6533928608099563),
                    new Translation2d(7.280207832151789, 0.2422906245020373),
                    new Translation2d(7.80675829060019, 0.07755748835666731),
                    new Translation2d(8.304494368935858, 0.07710624886687127),
                    new Translation2d(8.794614473129299, 0.07468036969819115),
                    new Translation2d(9.106555895063055, 0.07391099398340278)
                ),
                new Pose2d(9.15513183533171, 0.07451060153135668, Rotation2d.fromDegrees(1.0029999017715456)),
                false
            )
        );
    }
}
