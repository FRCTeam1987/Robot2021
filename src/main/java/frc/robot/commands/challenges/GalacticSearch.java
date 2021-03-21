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
import frc.robot.Constants;
import frc.robot.commands.drive.DrivePathHelpers;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Spindexer;


/** Add your docs here. */
public class GalacticSearch{


    public static Command PathARed(final Drive drive, final Collector collector, final Spindexer spindexer) {
        // m_drive, m_collector, m_spindexer
        // TODO start collector upon startup - apply to all paths
        return DrivePathHelpers.createOnBoardDrivePathCommand(
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
        );
    }
    
    public static Command PathABlue(final Drive drive) {
        return DrivePathHelpers.createOnBoardDrivePathCommand(
            drive,
            new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
            List.of(
                    new Translation2d(0.1811707637373186, 0.09698883270486855),
                    new Translation2d(-0.08589884889336677, 0.09340654980653501),
                    new Translation2d(-0.2466870154311417, 0.09317699515826018)
            ),
            new Pose2d(-0.26600959754485126, 0.0933696420449522, Rotation2d.fromDegrees(179.38800048828128)),
            false
        );
    }
    
    public static Command PathBBlue(final Drive drive) {
        return DrivePathHelpers.createOnBoardDrivePathCommand(
            drive,
            new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
            List.of(
                new Translation2d(2.412286453576407, -0.08424905134894),
                new Translation2d(2.013107724582363, 0.16934915644072576),
                new Translation2d(1.7083708220440263, 0.46558548303087227),
                new Translation2d(1.4530701120060703, 0.8100206716974084),
                new Translation2d(1.1880589330097144, 1.1421228615417391)
            ),
            new Pose2d(-0.10017699598217582, 1.88433649398515839, Rotation2d.fromDegrees(-178.4120025634766)),
            false
        );
    }
    
    public static Command PathBRed(final Drive drive) {
        return DrivePathHelpers.createOnBoardDrivePathCommand(
            drive,
            new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
            List.of(
                new Translation2d(2.412286453576407, -0.08424905134894),
                new Translation2d(2.013107724582363, 0.16934915644072576),
                new Translation2d(1.7083708220440263, 0.46558548303087227),
                new Translation2d(1.4530701120060703, 0.8100206716974084),
                new Translation2d(1.1880589330097144, 1.1421228615417391)
            ),
            new Pose2d(-0.10017699598217582, 1.88433649398515839, Rotation2d.fromDegrees(-178.4120025634766)),
            false
        );
    }
}
