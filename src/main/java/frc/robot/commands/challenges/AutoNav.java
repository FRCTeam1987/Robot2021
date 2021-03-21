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
    
    public static Command barrelRunTest(final Drive drive) {
        return DrivePathHelpers.createOnBoardDrivePathCommand(
            drive,
            new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
            List.of(
                    new Translation2d(0.051644975120826414, 6.35268938503286E-4),
                    new Translation2d(0.3094278267261832, 0.007993126911073872),
                    new Translation2d(0.6952706561150733, 0.01862765311913122),
                    new Translation2d(1.1574632199390185, 0.03373620409620022),
                    new Translation2d(1.6607454968197617, 0.04306583828442545),
                    new Translation2d(2.1237241388588695, 0.037380021682404146),
                    new Translation2d(2.5202461241439384, 0.022378964231927093),
                    new Translation2d(2.9127967260321546, -0.02244466651393028),
                    new Translation2d(3.2550389162473037, -0.13298657158469587),
                    new Translation2d(3.5173055432907505, -0.3306252924114993),
                    new Translation2d(3.6484166266642712, -0.6088048450087562),
                    new Translation2d(3.638458674668457, -0.9270200645321465),
                    new Translation2d(3.5079944480053284, -1.2093672639987327),
                    new Translation2d(3.290294820868817, -1.410354090624796),
                    new Translation2d(2.9955593242754506, -1.5128131202710395),
                    new Translation2d(2.6952234861432456, -1.480217928664895),
                    new Translation2d(2.413468667677065, -1.2972274705966063),
                    new Translation2d(2.2375103978161563, -1.0088841630158611),
                    new Translation2d(2.2062150903020847, -0.6784722570987264),
                    new Translation2d(2.3210511240956073, -0.3881927530151892),
                    new Translation2d(2.5750975635538884, -0.15705031250207446),
                    new Translation2d(2.93612453096618, -0.014599153470513618),
                    new Translation2d(3.3419316226674596, 0.013305217753733441),
                    new Translation2d(3.7793152487510295, -0.01832919339900304),
                    new Translation2d(4.225287357226764, -0.04747717668391682),
                    new Translation2d(4.677119012124791, -0.024951593258152476),
                    new Translation2d(5.102796653169257, 0.06237015632678845),
                    new Translation2d(5.48568768493625, 0.24169323211078175),
                    new Translation2d(5.754547384509853, 0.49430183406283673),
                    new Translation2d(5.897307776754014, 0.7943281286952198),
                    new Translation2d(5.91030324190909, 1.0990349744517292),
                    new Translation2d(5.805740500074755, 1.3518625352941656),
                    new Translation2d(5.588834619458377, 1.5345970532472197),
                    new Translation2d(5.277088208132786, 1.6141027285923564),
                    new Translation2d(4.961786819759981, 1.5610798053386865),
                    new Translation2d(4.696902038193073, 1.3835776250658314),
                    new Translation2d(4.5110555648933675, 1.1008872367932214),
                    new Translation2d(4.461262129371483, 0.7901730192802676),
                    new Translation2d(4.52398774053265, 0.44160577575807397),
                    new Translation2d(4.6910469438792, 0.05339902722086111),
                    new Translation2d(4.944896031683028, -0.3259528989891053),
                    new Translation2d(5.23246583025742, -0.6396313051364876),
                    new Translation2d(5.569599517025356, -0.9222003376497881),
                    new Translation2d(5.905544581625172, -1.1315513594397961),
                    new Translation2d(6.297327646167174, -1.2801202215399308),
                    new Translation2d(6.68228214205683, -1.3348094051914339),
                    new Translation2d(7.026091374201307, -1.2985979748339078),
                    new Translation2d(7.341960083173854, -1.1418450078242386),
                    new Translation2d(7.549121938307113, -0.886361067189035),
                    new Translation2d(7.626592798917766, -0.5676014357713843),
                    new Translation2d(7.533290054730405, -0.2409577474614042),
                    new Translation2d(7.328237670028775, -0.019963259058474982),
                    new Translation2d(7.0430552077438575, 0.11596466591130955),
                    new Translation2d(6.7143074876284405, 0.1605853713258163),
                    new Translation2d(6.32411569212708, 0.15974736585552207),
                    new Translation2d(5.918744449565311, 0.1609329685649958),
                    new Translation2d(5.501008314616585, 0.1557897622029405),
                    new Translation2d(5.102241205391346, 0.14992654491241786),
                    new Translation2d(4.758158210489359, 0.15046760915152885),
                    new Translation2d(4.418300586273098, 0.15386968587283273),
                    new Translation2d(4.081598043655696, 0.15007545671185968),
                    new Translation2d(3.7259195534624014, 0.1404475252785839),
                    new Translation2d(3.3376790706942367, 0.13549015581398538),
                    new Translation2d(2.9330998376025263, 0.12790845010525992),
                    new Translation2d(2.522468439215288, 0.12240281551077385),
                    new Translation2d(2.087904856074594, 0.12383773651025694),
                    new Translation2d(1.6608670039153477, 0.12602671332117196),
                    new Translation2d(1.2708141763591432, 0.11852300153246023),
                    new Translation2d(0.8793177245424104, 0.1079433233838411),
                    new Translation2d(0.5379862571402247, 0.10207152724112703),
                    new Translation2d(0.1811707637373186, 0.09698883270486855),
                    new Translation2d(-0.08589884889336677, 0.09340654980653501),
                    new Translation2d(-0.2466870154311417, 0.09317699515826018)
            ),
            new Pose2d(-0.26600959754485126, 0.0933696420449522, Rotation2d.fromDegrees(179.38800048828128)),
            false
        );
    }
    
    public static Command slalom(final Drive drive) {
        return DrivePathHelpers.createOnBoardDrivePathCommand(
            drive,
            new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
            List.of(
                new Translation2d(0.05917432225616568, -1.4064145374268828E-4),
                new Translation2d(0.28160035966562963, 0.004788452628312101),
                new Translation2d(0.5571316239523917, 0.056909080571109646),
                new Translation2d(0.85480355258064, 0.18210379387147757),
                new Translation2d(1.1145316708997863, 0.3974928834814362),
                new Translation2d(1.3298279373867368, 0.6966309738086139),
                new Translation2d(1.551940835942089, 1.0146862301148476),
                new Translation2d(1.8759865279574417, 1.337988455292644),
                new Translation2d(2.299976300551897, 1.5907333384495128),
                new Translation2d(2.802231391293089, 1.7488559075647252),
                new Translation2d(3.3336795912617774, 1.8253981809315518),
                new Translation2d(3.909843487363356, 1.838746029602323),
                new Translation2d(4.4724526611983055, 1.7770463957323206),
                new Translation2d(4.991878199413891, 1.6328346778410863),
                new Translation2d(5.425789327194551, 1.3971937869077637),
                new Translation2d(5.7434691555278725, 1.0836682720057027),
                new Translation2d(5.961447008331677, 0.7553449042892226),
                new Translation2d(6.1354860799636315, 0.43004233860506513),
                new Translation2d(6.35601085988795, 0.13373001221498554),
                new Translation2d(6.662604573483366, -0.060477328047542044),
                new Translation2d(7.0195764654246835, -0.10658753622318638),
                new Translation2d(7.346145396692203, -0.00387282142109506),
                new Translation2d(7.601717346051441, 0.2411259671246079),
                new Translation2d(7.710831809260163, 0.5873291512433346),
                new Translation2d(7.661698058522395, 0.9589636100088907),
                new Translation2d(7.472539633258195, 1.246555724215154),
                new Translation2d(7.16723141049953, 1.4280189146221054),
                new Translation2d(6.811915681394942, 1.4656431747869776),
                new Translation2d(6.46409421267033, 1.3272929016891344),
                new Translation2d(6.210935054623662, 1.066987051395692),
                new Translation2d(6.0043745774240795, 0.7150619492969572),
                new Translation2d(5.775279066739707, 0.36016562859591705),
                new Translation2d(5.416427059549551, 0.05559460174504886),
                new Translation2d(4.96295058570551, -0.15312020918620123),
                new Translation2d(4.468321030578619, -0.2783208831242119),
                new Translation2d(3.905649409588958, -0.3371063314473092),
                new Translation2d(3.368374828436083, -0.33025255517897345),
                new Translation2d(2.8535716357976404, -0.24735609827865854),
                new Translation2d(2.412286453576407, -0.08424905134894),
                new Translation2d(2.013107724582363, 0.16934915644072576),
                new Translation2d(1.7083708220440263, 0.46558548303087227),
                new Translation2d(1.4530701120060703, 0.8100206716974084),
                new Translation2d(1.1880589330097144, 1.1421228615417391)
                // new Translation2d(0.8139614445207696, 1.417151124457935)
                // new Translation2d(0.4120606593416466, 1.5391617673260314),
                // new Translation2d(0.08635713145439827, 1.5488131849673743)
            ),
            new Pose2d(-0.10017699598217582, 1.88433649398515839, Rotation2d.fromDegrees(-178.4120025634766)),
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
