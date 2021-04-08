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
                new Pose2d(-3.0098989543229606E-4, 2.9418163382145705E-7, Rotation2d.fromDegrees(-0.05999755859375001)),
                List.of(
                    new Translation2d(0.05706739890599653, 1.3028813826993104E-4),
                    new Translation2d(0.22368936197677017, 0.0015458840341139933),
                    new Translation2d(0.4361757072795013, 0.0020837665301113983),
                    new Translation2d(0.7110463362336332, -0.007611745179901801),
                    new Translation2d(1.003638810089259, -0.01869516292443259),
                    new Translation2d(1.3255957066851816, -0.022782744405835872),
                    new Translation2d(1.6344848613456542, -0.026749090808530843),
                    new Translation2d(1.9133525301602792, -0.045173896043082494),
                    new Translation2d(2.2215077481748557, -0.11675786967250028),
                    new Translation2d(2.5369010327389554, -0.2430593125201513),
                    new Translation2d(2.8135605719891905, -0.3795890486128907),
                    new Translation2d(3.088221797771069, -0.5286787095678442),
                    new Translation2d(3.3384739011154347, -0.6803641235793829),
                    new Translation2d(3.566017509046772, -0.748959120037878),
                    new Translation2d(3.800948631874846, -0.6994147410523184),
                    new Translation2d(4.020365677826242, -0.5293278357850912),
                    new Translation2d(4.161258772641697, -0.2694429975926186),
                    new Translation2d(4.226103070054235, 0.06343552094276778),
                    new Translation2d(4.256729382696813, 0.41511397418849805),
                    new Translation2d(4.281904561709107, 0.7834444614512492),
                    new Translation2d(4.335791257252175, 1.1031212724866417),
                    new Translation2d(4.474554725487208, 1.3482429070430964),
                    new Translation2d(4.744684762509489, 1.5448480779292884),
                    new Translation2d(5.117805302866488, 1.6443355147813605),
                    new Translation2d(5.577152285855159, 1.6800060501834142),
                    new Translation2d(6.094781530303204, 1.6928557721407727),
                    new Translation2d(6.699663820438634, 1.67319406455235),
                    new Translation2d(7.2644255011036, 1.6243631967011896),
                    new Translation2d(7.8073692613630294, 1.5582306468726008),
                    new Translation2d(8.28679356056795, 1.4943472811147858),
                    new Translation2d(8.685313323262287, 1.4318870537026887)
                ),
                new Pose2d(8.898978133147903, 1.399467484990454, Rotation2d.fromDegrees(-8.270019531250002)),
                                false
            ),
            "Path: Red A"
        );
    }

    // Starting position -10
    // 1. Center
    // 2. Zero Sensors
    // 3. Rotate to -10
    public static Command PathABlue(final Drive drive, final Collector collector, final Spindexer spindexer) {
        return MakeGalaticPath(
            drive,
            collector,
            spindexer,
            DrivePathHelpers.createOnBoardDrivePathCommand(
                drive,
                new Pose2d(0.00649892312381057, 6.754334884415848E-4, Rotation2d.fromDegrees(-10.760986328124998)),
                List.of(
                    new Translation2d(0.03861300050134733, -0.005420448450988996),
                    new Translation2d(0.3694716477179683, -0.0661425176000446),
                    new Translation2d(1.0046389894740877, -0.20419711031345003),
                    new Translation2d(1.7525382501067504, -0.4166014509360049),
                    new Translation2d(2.503254906474254, -0.6398376651647013),
                    new Translation2d(3.2711223649701884, -0.6517202079248873),
                    new Translation2d(3.9230839149608707, -0.3160700642526282),
                    new Translation2d(4.270548943990733, 0.311961976563013),
                    new Translation2d(4.2997373549670455, 0.9918161140836113),
                    new Translation2d(4.472350740256521, 1.588597705641238),
                    new Translation2d(4.952842103783127, 1.9935948556072232),
                    new Translation2d(5.661026273533598, 2.01315486244597),
                    new Translation2d(6.3094125482875905, 1.6157736321600513),
                    new Translation2d(7.03691248724974, 1.1222610842221528),
                    new Translation2d(7.921466278067403, 0.819731039145898),
                    new Translation2d(8.750967190153885, 0.7580288685165505),
                    new Translation2d(9.331514448795575, 0.7773053916972111),
                    new Translation2d(9.677738187868218, 0.7858959011492134)
                ),
                new Pose2d(9.742059027077357, 0.783986970131817, Rotation2d.fromDegrees(-2.6309814453125)),
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
                new Pose2d(-4.8158449594555786E-4, 5.149139304628081E-7, Rotation2d.fromDegrees(0.029998779296874997)),
                List.of(
                    new Translation2d(0.0771720067414636, 4.7091204188758106E-4),
                    new Translation2d(0.28093378941330444, -3.941985687820757E-4),
                    new Translation2d(0.5687915700733197, -0.0066213675376172905),
                    new Translation2d(0.9406383042957053, -0.04215260486928915),
                    new Translation2d(1.293396556612391, -0.18601181873461034),
                    new Translation2d(1.5948413604261096, -0.41868954689273674),
                    new Translation2d(1.885862727784107, -0.7034664973876339),
                    new Translation2d(2.166033056487656, -1.0276449089367217),
                    new Translation2d(2.496005936330105, -1.3875455383327484),
                    new Translation2d(2.8138144194606034, -1.7386210487138463),
                    new Translation2d(3.110762875647603, -2.0707568653424544),
                    new Translation2d(3.433573775980925, -2.3669205989152915),
                    new Translation2d(3.785715165903994, -2.483369987979495),
                    new Translation2d(4.106007791442945, -2.4133498052365585),
                    new Translation2d(4.362152237032797, -2.2102395379855753),
                    new Translation2d(4.560765293897973, -1.8877133511485573),
                    new Translation2d(4.751849731885227, -1.5028014729221733),
                    new Translation2d(4.989192922442236, -1.0966218900222342),
                    new Translation2d(5.2736213746589495, -0.7430087937363554),
                    new Translation2d(5.662899129645044, -0.4612934789478599),
                    new Translation2d(6.172668867978648, -0.30429387202155755),
                    new Translation2d(6.682269871255631, -0.2539741882833754),
                    new Translation2d(7.194028863463354, -0.25988646706110874),
                    new Translation2d(7.713867884904694, -0.2581189386264681),
                    new Translation2d(8.166554297371007, -0.2698773091895883),
                    new Translation2d(8.503304960132944, -0.2866524461161466),
                    new Translation2d(8.69531726105015, -0.29394311820237523)
                ),
                new Pose2d(8.713005986478636, -0.2945223254561093, Rotation2d.fromDegrees(-1.9800109863281248)),
                                false
            ),
            "Path: Red B"
        );
    }

    // Start straight ahead
    public static Command PathBBlue(final Drive drive, final Collector collector, final Spindexer spindexer) {
        return MakeGalaticPath(
            drive,
            collector,
            spindexer,
            DrivePathHelpers.createOnBoardDrivePathCommand(
                drive,
                new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(-156.99099731445312)),
                List.of(
                    new Translation2d(-0.03990267923744159, -0.016766968633778898),
                    new Translation2d(-0.47772749251877633, -0.20163247678547214),
                    new Translation2d(-1.1742956143058347, -0.5011896551957487),
                    new Translation2d(-2.0333975000029256, -0.8714400811437005),
                    new Translation2d(-2.8569442598666694, -1.3691457341169093),
                    new Translation2d(-3.421493451697229, -2.1476646724251385),
                    new Translation2d(-3.592794407901945, -3.0193117318087737),
                    new Translation2d(-3.8274330451564484, -3.8009810617452287),
                    new Translation2d(-4.486828973421792, -4.314104380970042),
                    new Translation2d(-5.359378763827062, -4.276050579551006),
                    new Translation2d(-6.041405709959522, -3.7976510481965944),
                    new Translation2d(-6.823674321528144, -3.4609678304455187),
                    new Translation2d(-7.676213146364177, -3.5658286515530486),
                    new Translation2d(-8.206663120238398, -3.8170215644629475),
                    new Translation2d(-8.46637218922331, -3.991073720105132)
                ),
                new Pose2d(-8.506348311405766, -4.020133982031494, Rotation2d.fromDegrees(-144.1009979248047)),
                false
            ),
            "Path: Blue B"
        );
    }
}
