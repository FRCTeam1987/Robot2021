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
import frc.robot.commands.drive.DrivePathHelpers;
import frc.robot.subsystems.Drive;

/** Add your docs here. */
public class AutoNav {

    public static Command barrelRun(final Drive drive) {
        return DrivePathHelpers.createOnBoardDrivePathCommand(
            drive,
            new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
            List.of(
                new Translation2d(0.038045110665827914, -4.408134614195819E-5),
                new Translation2d(0.44732284363298014, -0.0026177346917426775),
                new Translation2d(1.058181876469342, -0.008668959860559418),
                new Translation2d(1.7549639144750901, -0.023828679223830154),
                new Translation2d(2.4548908838649908, -0.054454209994092596),
                new Translation2d(3.046060499824672, -0.17098742315389795),
                new Translation2d(3.43341600714823, -0.5213284672082028),
                new Translation2d(3.4370956843329608, -1.027681996721807),
                new Translation2d(3.1091565882664822, -1.3834809414555416),
                new Translation2d(2.6310406564886786, -1.5022569480146255),
                new Translation2d(2.212262989339347, -1.2649420835850471),
                new Translation2d(2.078518530591007, -0.7904093265106173),
                new Translation2d(2.3291078829090646, -0.3678849355545697),
                new Translation2d(2.7864751422073115, -0.1501457445409086),
                new Translation2d(3.40448914715758, 0.05230047544716585),
                new Translation2d(4.044503869978855, 0.30105949872205845),
                new Translation2d(4.633083580128359, 0.5376796049356626),
                new Translation2d(5.181037212427339, 0.7982877587545503),
                new Translation2d(5.592541483445849, 1.1653552172624841),
                new Translation2d(5.653973449879445, 1.5952515427510099),
                new Translation2d(5.423514240060196, 1.9482691639688265),
                new Translation2d(5.013429978662835, 2.09578907038552),
                new Translation2d(4.629274846015721, 1.9202602528326131),
                new Translation2d(4.43569659550818, 1.4981405478551988),
                new Translation2d(4.53040574517909, 0.9934267560242458),
                new Translation2d(4.814014697065211, 0.43279079496188305),
                new Translation2d(5.158473651807161, -0.12450537321123013),
                new Translation2d(5.521092565733292, -0.6546140229569472),
                new Translation2d(5.9015962073817, -1.1609647730031438),
                new Translation2d(6.330402388242626, -1.4787856691279324),
                new Translation2d(6.773825833808366, -1.4580667110121535),
                new Translation2d(7.091858383569286, -1.166140348378934),
                new Translation2d(7.170488325163601, -0.7160828179246969),
                new Translation2d(6.955835468993446, -0.3429111256426944),
                new Translation2d(6.488767626024246, -0.14586543578824154),
                new Translation2d(5.878614434485247, -0.10639943952636606),
                new Translation2d(5.13203504917302, -0.09920965865138476),
                new Translation2d(4.315936321972694, -0.08440406713494505),
                new Translation2d(3.426121767240799, -0.06272725186441695),
                new Translation2d(2.545322623329741, -0.05150024550474493),
                new Translation2d(1.711107692447394, -0.050034363320119485),
                new Translation2d(0.8989996924430305, -0.02953800914320651),
                new Translation2d(0.2870319314710845, -0.017453728545820973)
            ),
            new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(180)),
            false
        );
    }

    public static Command bounce(final Drive drive) {
        return new SequentialCommandGroup(
            DrivePathHelpers.createOnBoardDrivePathCommand(
                drive,
                new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
                List.of(
                    new Translation2d(0.058391491820767444, -2.0479002385131943E-4),
                    new Translation2d(0.22210589968938416, 0.0015198740397760846),
                    new Translation2d(0.42151521964041205, 0.020492457133701937),
                    new Translation2d(0.6347645900765844, 0.07982215907392784),
                    new Translation2d(0.8538198474712686, 0.21643055698505229),
                    new Translation2d(1.0468535520427487, 0.43972832063384926),
                    new Translation2d(1.1739132175996174, 0.6910652157897977),
                    new Translation2d(1.2395892731660891, 0.9134833613302474)
                ),
                new Pose2d(1.2603546997351478, 1.0902571423989518, Rotation2d.fromDegrees(86.33999633789062)),
                false
            ),
            DrivePathHelpers.createOnBoardDrivePathCommand(
                drive,
                new Pose2d(1.2603546997351478, 1.0902571423989518, Rotation2d.fromDegrees(86.33001708984375)),
                List.of(
                    new Translation2d(1.2591279523429788, 1.0592216803091492),
                    new Translation2d(1.2709620790258183, 0.9004894282942953),
                    new Translation2d(1.3414360020931455, 0.6158100211913548),
                    new Translation2d(1.4612734450206906, 0.2912879574464766),
                    new Translation2d(1.6404780707774587, -0.07695778753409387),
                    new Translation2d(1.832055784034648, -0.46681567411289576),
                    new Translation2d(2.0294728289584767, -0.8515346893816332),
                    new Translation2d(2.240235649150509, -1.1777189844293168),
                    new Translation2d(2.492025714892668, -1.3776260886807006),
                    new Translation2d(2.763197151571996, -1.4502098486488055),
                    new Translation2d(3.037208805017259, -1.39728471273226),
                    new Translation2d(3.273236535875764, -1.2312974752512655),
                    new Translation2d(3.459022904834929, -0.9647802515260695),
                    new Translation2d(3.57729837734783, -0.6152083166520252),
                    new Translation2d(3.613842926742303, -0.23214100980599345),
                    new Translation2d(3.617252390135804, 0.1796908336119611),
                    new Translation2d(3.617308174265321, 0.5623684608202211),
                    new Translation2d(3.618158932840656, 0.867811382585109)
                ),
                new Pose2d(3.619423349811967, 1.048458065703151, Rotation2d.fromDegrees(-91.19000244140629)),
                true,
                false
            ),
                
            DrivePathHelpers.createOnBoardDrivePathCommand(
                drive,
                new Pose2d(3.619423349811967, 1.048458065703151, Rotation2d.fromDegrees(-91.19999694824222)),
                List.of(
                    new Translation2d(3.6182423374507517, 0.996821704640602),
                    new Translation2d(3.612696296908961, 0.7835521276436167),
                    new Translation2d(3.60519718216466, 0.48374028369544975),
                    new Translation2d(3.602579419877751, 0.1347948338495897),
                    new Translation2d(3.607317888962266, -0.22810773606614249),
                    new Translation2d(3.6158605176845238, -0.5562947020247545),
                    new Translation2d(3.6517111376338947, -0.8846716528411088),
                    new Translation2d(3.756640931496245, -1.171728366871094),
                    new Translation2d(3.9638568257103377, -1.4325887707012064),
                    new Translation2d(4.216243675608638, -1.6039194375268155),
                    new Translation2d(4.543877618376758, -1.7159278943626652),
                    new Translation2d(4.872722578572865, -1.7337798531754676),
                    new Translation2d(5.208234693418255, -1.6519807797932253),
                    new Translation2d(5.493432758544351, -1.4637199092900617),
                    new Translation2d(5.687482945804584, -1.1985894609904744),
                    new Translation2d(5.818331274408759, -0.8566244266967803),
                    new Translation2d(5.873148240404001, -0.507285961209374),
                    new Translation2d(5.89045636971693, -0.14762379475324078),
                    new Translation2d(5.8977485766123525, 0.21859804518292894),
                    new Translation2d(5.903397013899592, 0.53133451162537),
                    new Translation2d(5.9078781409983865, 0.7567881368499115)
                ),
                new Pose2d(5.909863579063351, 0.893301494058405, Rotation2d.fromDegrees(88.70001220703122)),
                false,
                false
            ),
            DrivePathHelpers.createOnBoardDrivePathCommand(
                drive,
                new Pose2d(5.909863579063351, 0.893301494058405, Rotation2d.fromDegrees(88.69000244140626)),
                List.of(
                    new Translation2d(5.908074241033608, 0.7908617990786607),
                    new Translation2d(5.917250598007816, 0.6056282859836697),
                    new Translation2d(5.9757001837757855, 0.3635128861161598),
                    new Translation2d(6.116204587513549, 0.09529955885395501),
                    new Translation2d(6.314350536667523, -0.078599047345735),
                    new Translation2d(6.571975765807698, -0.1731150077672621),
                    new Translation2d(6.842504806885297, -0.19843409558754732),
                    new Translation2d(7.055288566534806, -0.19288341932088882)
                ),
                new Pose2d(7.157738150095549, -0.19188252200380027, Rotation2d.fromDegrees(179.8399963378906)),
                true,
                false
            )
        );
    }
}
