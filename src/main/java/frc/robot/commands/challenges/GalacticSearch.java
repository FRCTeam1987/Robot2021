// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.challenges;

import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.collector.StartCollect;
import frc.robot.commands.collector.StopCollect;
import frc.robot.commands.drive.DrivePathHelpers;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Spindexer;


/** Add your docs here. */
public class GalacticSearch{

    public static Command MakeGalaticPath(final Drive drive, final Collector collector, final Spindexer spindexer, Command path, String pathName) {
        return new SequentialCommandGroup(
            new PrintCommand(pathName),
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
            ),
            "Path: Red A"
        );
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
                new Translation2d(0.08553977823365241, -2.3725570256476428E-4),
                new Translation2d(0.2634464560008724, -0.0038980799831749274),
                new Translation2d(0.552877430296957, -0.01553763938506635),
                new Translation2d(0.9083086067222123, -0.034710373722494504),
                new Translation2d(1.2873045652877384, -0.05624617567718834),
                new Translation2d(1.6792635115182226, -0.07113961502610128),
                new Translation2d(2.109678075086995, -0.09362536151630181),
                new Translation2d(2.5050553757692584, -0.12803633733355413),
                new Translation2d(2.8392921462976686, -0.1547319377828312),
                new Translation2d(3.0410692204016083, -0.14574818117763236),
                new Translation2d(3.2102222609238695, -0.08862788673882205),
                new Translation2d(3.3516740844947703, 0.04260816384107652),
                new Translation2d(3.4126172504095833, 0.2222437247163227),
                new Translation2d(3.3919670755665314, 0.4468793802441149),
                new Translation2d(3.2775922909895545, 0.7167718170947118),
                new Translation2d(3.1205248958158656, 1.044805391684715),
                new Translation2d(3.0563495308942126, 1.3338913319884593),
                new Translation2d(3.0942991473978525, 1.507745231275667),
                new Translation2d(3.1674869086591015, 1.6365601219046577),
                new Translation2d(3.2573852565430945, 1.7420555204438317),
                new Translation2d(3.3982490943178956, 1.842896077149576),
                new Translation2d(3.626819396995722, 1.909153191307349),
                new Translation2d(3.9371248190305024, 1.927726218850841),
                new Translation2d(4.326031531530631, 1.9123950601567268),
                new Translation2d(4.726320309242517, 1.891036238817072),
                new Translation2d(5.037975852795851, 1.8762538471459664),
                new Translation2d(5.4419861627782735, 1.8548869274316502),
                new Translation2d(5.9700438182602324, 1.8035599506595899),
                new Translation2d(6.6292961768529075, 1.720508009265502),
                new Translation2d(7.232069236803221, 1.6512896126402943),
                new Translation2d(7.660089927661695, 1.60482655381399)
                ),
                new Pose2d(7.833675589764719, 1.5868532239798034, Rotation2d.fromDegrees(-5.460002899169922)),
                false
            ),
            "Path: Blue A"
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
                new Translation2d(0.047676498977024556, -1.6646676790914636E-4),
                new Translation2d(0.3182816303042301, 0.003401837945174933),
                new Translation2d(0.6802217454292178, -0.0140996152257938),
                new Translation2d(1.0402142478316645, -0.23393856658973708),
                new Translation2d(1.3335090652697525, -0.6969463441856605),
                new Translation2d(1.5436176596812647, -1.2189494409068344),
                new Translation2d(1.742167685318593, -1.7137861966095833),
                new Translation2d(2.1148036902106013, -2.027477654174676),
                new Translation2d(2.5769559622925207, -1.9399455297605477),
                new Translation2d(3.0166636579954655, -1.6720881109352415),
                new Translation2d(3.5775712579469716, -1.4678257861884223),
                new Translation2d(4.208034955143003, -1.4488334683555022),
                new Translation2d(4.920096148841525, -1.516953330945214),
                new Translation2d(5.610700032819805, -1.585737507681651),
                new Translation2d(6.278833577430584, -1.64600081605963),
                new Translation2d(6.788600053201849, -1.6871442553967078),
                new Translation2d(7.131508232380163, -1.706009534042982)
                ),
                new Pose2d(7.264, -1.715, Rotation2d.fromDegrees(-4.442)),
                false
            ),
            "Path: Red B"
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
                new Translation2d(0.045148097834746125, -1.2868213717370962E-4),
                new Translation2d(0.39238546432202465, -0.01060623136010963),
                new Translation2d(0.9839696250265589, -0.03594304736454039),
                new Translation2d(1.7162865365478785, -0.0881236267385017),
                new Translation2d(2.4356713555148395, -0.10082382990205001),
                new Translation2d(3.0428766056712906, -0.0813951868661622),
                new Translation2d(3.571468798648243, 0.15133697230146848),
                new Translation2d(3.913101640991227, 0.6079187717174853),
                new Translation2d(4.256874971732432, 1.1381877727042937),
                new Translation2d(4.61988066680102, 1.5394763840658074),
                new Translation2d(5.114092775273758, 1.616981094118748),
                new Translation2d(5.589674447848363, 1.380064734357222),
                new Translation2d(6.004780077485973, 0.9968271237678902),
                new Translation2d(6.494110502622936, 0.568188284853488),
                new Translation2d(7.0215079020123845, 0.16354930577156543),
                new Translation2d(7.511661059284099, -0.20053253196429915),
                new Translation2d(7.900644726844025, -0.4703937565775807)
                ),
                new Pose2d(8.125431079139743, -0.6159482266150152, Rotation2d.fromDegrees(-30.638999938964844)),

                /*
                new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
                List.of(
                new Translation2d(0.049663235254611075, -9.718406931619881E-5),
                new Translation2d(0.382876499648161, -0.009128282422868427),
                new Translation2d(0.9129464187466605, -0.0484492771895125),
                new Translation2d(1.5318206447679485, -0.07822899236509374),
                new Translation2d(2.215009193306457, -0.08580880491197689),
                new Translation2d(2.8798989443450185, -0.09813389728095397),
                new Translation2d(3.443603804209747, 0.0013877404943058655),
                new Translation2d(3.884858722348541, 0.3340381694610148),
                new Translation2d(4.266198425989816, 0.8430700656488341),
                new Translation2d(4.569358271389571, 1.3221894294298007),
                new Translation2d(4.994042042154741, 1.5746648218601418),
                new Translation2d(5.424948655935944, 1.4408894315146281),
                new Translation2d(5.748282267996697, 1.0659590275321833),
                new Translation2d(6.141952387063065, 0.5828221419521067),
                new Translation2d(6.588463534772158, 0.12082406380777336),
                new Translation2d(6.9955632310296725, -0.3602832168262343),
                new Translation2d(7.364226005354923, -0.5853184856619476),
                new Translation2d(7.633510972068677, -0.8124429353823176)
                ),
                new Pose2d(7.701341383969663, -0.8693662221562104, Rotation2d.fromDegrees(-39.34199523925782)),
                */
                false
            ),
            "Path: Blue B"
        );
    }
}
