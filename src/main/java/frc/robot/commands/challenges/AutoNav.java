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
            new Translation2d(0.0373224982673935, -1.1905924203785199E-4),
            new Translation2d(0.4553488155696806, -0.0064464990280113),
            new Translation2d(1.1269564018753868, -0.011309841930436534),
            new Translation2d(1.858041859218519, -0.016371217593578277),
            new Translation2d(2.601024342651769, -0.03459924198518236),
            new Translation2d(3.2487773265246815, -0.29826945508837094),
            new Translation2d(3.511109546372117, -0.8859590511028447),
            new Translation2d(3.2332396570908424, -1.4145934907848765),
            new Translation2d(2.6156235249236617, -1.5343766997378745),
            new Translation2d(2.1093344934853304, -1.1687368481358988),
            new Translation2d(2.0555890405629844, -0.5187128959334689),
            new Translation2d(2.526590565363247, -0.0637176433719399),
            new Translation2d(3.1944647547769023, -4.060217211962788E-4),
            new Translation2d(3.9418066513020786, -0.017125510102776777),
            new Translation2d(4.659961512528385, -0.015932523353663748),
            new Translation2d(5.338927037396333, 0.19903370221639657),
            new Translation2d(5.686761937007213, 0.6955000348794612),
            new Translation2d(5.538602221356662, 1.2568066484153628),
            new Translation2d(5.005336125768298, 1.4789040941537466),
            new Translation2d(4.471301107775907, 1.23536646521209),
            new Translation2d(4.245681374912272, 0.6945161543750324),
            new Translation2d(4.447442942902034, 0.17885722668709783),
            new Translation2d(4.892205222541623, -0.2541167212678919),
            new Translation2d(5.406675043191983, -0.7099007487811931),
            new Translation2d(5.9703811903078075, -1.1828889485213925),
            new Translation2d(6.564124373832726, -1.4522998784506589),
            new Translation2d(7.081120924806124, -1.2511025203308488),
            new Translation2d(7.2623355277738755, -0.7177456769672277),
            new Translation2d(6.982208415989353, -0.23229910934532497),
            new Translation2d(6.450073723272211, -0.012050207863134297),
            new Translation2d(5.729706896992499, 0.03553839446298603),
            new Translation2d(4.868851622119875, 0.045646617157289765),
            new Translation2d(3.905934824745601, 0.07237285549709187),
            new Translation2d(2.9099437514472326, 0.11120923145494277),
            new Translation2d(1.939670477468655, 0.20629807937712854),
            new Translation2d(1.039238151708986, 0.1957641300230121),
            new Translation2d(0.2289619473973314, 0.20413530182640109),
            new Translation2d(-0.3543959342170186, 0.19775341034679597),
            new Translation2d(-0.7440914603630593, 0.17417442993307942)
            ),
            new Pose2d(-0.9444540991268693, 0.15160940999336605, Rotation2d.fromDegrees(-170.76000976562503)),    
            
            false
        );
    }
    
    public static Command slalom(final Drive drive) {
        return DrivePathHelpers.createOnBoardDrivePathCommand(
            drive,
            new Pose2d(0.001023443804814138, 1.4079643303564568E-5, Rotation2d.fromDegrees(0.0330020897090435)),
            List.of(
            new Translation2d(0.04731556459832366, -9.371926515368406E-5),
            new Translation2d(0.33133820284527893, 0.0031920069562600757),
            new Translation2d(0.6622541567733464, 0.04931842502126824),
            new Translation2d(1.0004665830695687, 0.211717382116655),
            new Translation2d(1.2248499821937051, 0.5329560820900139),
            new Translation2d(1.4131314923961131, 0.8809252143080433),
            new Translation2d(1.7648839066159014, 1.1690339792767768),
            new Translation2d(2.2770909204910654, 1.2964685087845993),
            new Translation2d(2.9261987776956397, 1.325045538194055),
            new Translation2d(3.5916392871442384, 1.3022703341662012),
            new Translation2d(4.233886036520429, 1.2806178616252668),
            new Translation2d(4.81181984374419, 1.240810202651671),
            new Translation2d(5.3073400944535996, 1.1060055160684674),
            new Translation2d(5.660448149116686, 0.8121057008315075),
            new Translation2d(5.896736245935978, 0.42555711090715675),
            new Translation2d(6.160237658977568, 0.04667021564442932),
            new Translation2d(6.558249179963056, -0.1704756283756578),
            new Translation2d(7.044372740678802, -0.0690799686945243),
            new Translation2d(7.322011108472809, 0.2913734271423267),
            new Translation2d(7.29584922020269, 0.7851973518255375),
            new Translation2d(6.980987080599001, 1.1205237358827345),
            new Translation2d(6.525081232883345, 1.1994696360321921),
            new Translation2d(6.09311500966381, 1.009240097782578),
            new Translation2d(5.7977074880769885, 0.6098369543431947),
            new Translation2d(5.604497124223823, 0.20005751389383403),
            new Translation2d(5.2724666097842015, -0.0788574766051527),
            new Translation2d(4.720335994191321, -0.1645196521661299),
            new Translation2d(4.060110067730019, -0.13974868041668806),
            new Translation2d(3.3852054307973947, -0.11684412341023545),
            new Translation2d(2.7306931455247283, -0.08909325820372731),
            new Translation2d(2.1397433426862684, -0.02768170448810168),
            new Translation2d(1.660789260988692, 0.22354990080690718),
            new Translation2d(1.326748163100732, 0.6450441801753573),
            new Translation2d(0.937187624009447, 1.0616474212749696),
            new Translation2d(0.4559908923756866, 1.4351692244876166),
            new Translation2d(-0.010219783113715223, 1.7701460515464582),
            new Translation2d(-0.338077274059851, 1.9731968245238771),
            new Translation2d(-0.5291594162895021, 2.0635244873162133)
            ),
            new Pose2d(-0.5728667370514167, 2.0771669578666248, Rotation2d.fromDegrees(178.74299621582034)),
            
            false
        );
    }

    public static Command slalomTest(final Drive drive) {
        return DrivePathHelpers.createOnBoardDrivePathCommand(
            drive,
            new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
            List.of(
                new Translation2d(0.033529481962064824, 1.8706848477474468E-4),
                new Translation2d(0.295092598992218, 0.015504501362308552),
                new Translation2d(0.5717160477956764, 0.07636721643788319),
                new Translation2d(0.9261039245366949, 0.3086147533387712),
                new Translation2d(1.21234172185815, 0.8158412071860914),
                new Translation2d(1.538435965935396, 1.3315313395909594),
                new Translation2d(2.102707122832353, 1.760293436654788),
                new Translation2d(2.8255070677859284, 1.9333667591081771),
                new Translation2d(3.5837935415796167, 1.9376271264726015),
                new Translation2d(4.35247659489068, 1.873898292531776),
                new Translation2d(5.118350593627804, 1.6929598842613363),
                new Translation2d(5.689445062596204, 1.2029649636967032),
                new Translation2d(5.967768077209793, 0.4621090481227858),
                new Translation2d(6.438608898250651, -0.19089353429881564),
                new Translation2d(7.1901853372787885, -0.31707734816402017),
                new Translation2d(7.829117426139421, 0.1775277317281255),
                new Translation2d(7.882081223233324, 0.9606984056563317),
                new Translation2d(7.30619045874609, 1.530592612209503),
                new Translation2d(6.527399860483366, 1.4165133719780585),
                new Translation2d(6.0178591679963676, 0.7104382033545491),
                new Translation2d(5.532365948518209, 0.04804160602472195),
                new Translation2d(4.768091563420258, -0.3755137392303103),
                new Translation2d(3.93504025080736, -0.5217930701348357),
                new Translation2d(3.072990339911477, -0.3999091422001952),
                new Translation2d(2.2745669086128193, -0.13987301356363943),
                new Translation2d(1.639017211207561, 0.4064697351302431),
                new Translation2d(1.2386900736018636, 1.1827612111598957),
                new Translation2d(0.6520555035830493, 1.7838988170910297),
                new Translation2d(0.11720409466776642, 1.9021564862913725)
            ),
            new Pose2d(0.12654555086290292, 1.9031442286387172, Rotation2d.fromDegrees(-174.92399597167972)),
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
                    new Translation2d(6.571975765807698, -0.1731150077672621)
                    // new Translation2d(6.842504806885297, -0.19843409558754732)
                    // new Translation2d(7.0, 0)
                ),
                new Pose2d(8.0, -0.6, Rotation2d.fromDegrees(165.8399963378906)),
                true,
                false
            )
        );
    }
}
