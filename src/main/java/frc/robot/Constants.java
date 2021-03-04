// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.lib.InterpolatingDouble;
import frc.robot.lib.InterpolatingTreeMap;

// TODO Update these constants!!!

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static double milliPerMin = 600;
  public static double falconTicksPerRevolution = 2048;

  public static final class Collector {
    public static final class Can {
      public static final int motorMaster = 10;
      public static final int motorSlave = 11;
    }
    public static final class Solenoid {
      public static final int extend = 0;
      public static final int retract = 7;


    }
    public static final class Values {
      public static final DoubleSolenoid.Value cylinderExtend = DoubleSolenoid.Value.kForward;
      public static final DoubleSolenoid.Value cylinderRetract = DoubleSolenoid.Value.kReverse;
      public static final double motorIn = 0.5;
      public static final double motorOut = -0.5;
      public static final double motorStop = 0.0;
    }

  }

  public static final class Drive {
    public static final class Can {
      public static final int leftMaster = 3;
      public static final int leftSlave = 5;
      public static final int rightMaster = 2;
      public static final int rightSlave = 4;
    }
    public static final class Controls {
      public static final double sVolts = 0.672;  // kS
      public static final double vVoltsSecondsPerMeter = 1.99;  // kV
      public static final double aVoltsSecondsSquaredPerMeter = 0.226;  // kA
      public static final double maxVolts = 10;
      public static final double maxSpeedMetersPerSecond = 2.25;
      public static final double maxAccelerationMetersPerSecondSquared = 2.25;
      public static final double ramseteB = 2.0;
      public static final double ramseteZeta = 0.15;
      public static final double pVelocity = 0.0;  // 0.15;
      public static final double maxCentripetalMetersPerSecondSquared = 1.0;
    }
    public static final class Values {
      public static final int encoderTicksPerRevolution = 2048;
      public static final double postEncoderGearing = 9.091;
      public static final double trackWidth = 0.72; // 12.9798;
      public static final double wheelDiameter = 0.156972; // 0.1524;  // 6 in wheel in m
      public static final double wheelCircumference = Math.PI * wheelDiameter;
      public static final double minMovePercent = 0.275;
      public static final double maxMovePercent = 0.35;
    }
  }

  public static final class OI {
    public static final class Xbox {
      public static final int driverID = 0;
      public static final double deadBandThreshold = 0.25;
    }

    public static final class Buttons {
      public static final class Driver {
        public static final int collectorBtnId = XboxController.Button.kY.value;
        public static final int shooterBtnId = XboxController.Button.kX.value;
      }

      public static final class CoDriver {

      }
    }
  }

  public static final class Spindexer {
    public static final class Can {
      public static final int bigBlock = 1;
      public static final int omni = 9;
      public static final int compliant = 8;
    }
    public static final double compliantSpeed = 0.95;
    public static final double bigBlockJamCurrent = 40; // TODO test
    public static final double agitateSpeed = 0.4;
    public static final double agitateDuration = 1;
    public static final double shootSpeed = 0.65;
  }

  public static final class Shooter {
    public static final class Can {
      public static final int flywheelMaster = 6;
      public static final int flywheelSlave = 7;
    }

    public static final class Targeting {

      public static class TreeMapValues{
        public static class Close {
            // public final static double ty1 = 15.09;
            // public final static double ty2 = 12.7;
            // public final static double ty3 = 12.0;
            // public final static double ty4 = 10.63;
            // public final static double ty5 = 8.75;
            // public final static double ty6 = 6.30;
            // public final static double ty7 = 4.83;
            // public final static double ty8 = 4.08;;
            public final static double ty1 = 16.47;
            public final static double ty2 = 14.76;
            public final static double ty3 = 12.23;
            public final static double ty4 = 10.28;
            public final static double ty5 = 8.26;
            // public final static double ty9 = -0.98;
            // public final static double ty10 = -0.83;
            // public final static double ty11 = 0.78;
            // public final static double ty12 = 1.57;
            // public final static double ty13 = 3.21;
            // public final static double ty14 = 5.19;
            // public final static double ty15 = 8.08;
            // public final static double ty16 = 12.55;
            // public final static double ty17 = 16.74;
            // public final static double ty18 = 18.88;

            public final static double rpm1 = 3400;
            public final static double rpm2 = 3400;
            public final static double rpm3 = 3375;
            public final static double rpm4 = 3425;
            public final static double rpm5 = 3500;
            // public final static double rpm9 = 3325;
            // public final static double rpm10 = 3325;
            // public final static double rpm11 = 3200;
            // public final static double rpm12 = 3200;
            // public final static double rpm13 = 3100;
            // public final static double rpm14 = 3040;
            // public final static double rpm15 = 3040;
            // public final static double rpm16 = 3040;
            // public final static double rpm17 = 3035;
            // public final static double rpm18 = 3025;
        }
    }

      public static InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> kDistanceToShooterSpeedClose = new InterpolatingTreeMap<>();
      static {
        kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty1), new InterpolatingDouble(TreeMapValues.Close.rpm1));
        kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty3), new InterpolatingDouble(TreeMapValues.Close.rpm3));
        kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty4), new InterpolatingDouble(TreeMapValues.Close.rpm4));
        kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty2), new InterpolatingDouble(TreeMapValues.Close.rpm2));
        kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty5), new InterpolatingDouble(TreeMapValues.Close.rpm5));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty6), new InterpolatingDouble(TreeMapValues.Close.rpm6));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty7), new InterpolatingDouble(TreeMapValues.Close.rpm7));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty8), new InterpolatingDouble(TreeMapValues.Close.rpm8));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty9), new InterpolatingDouble(TreeMapValues.Close.rpm9));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty10), new InterpolatingDouble(TreeMapValues.Close.rpm10));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty11), new InterpolatingDouble(TreeMapValues.Close.rpm11));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty12), new InterpolatingDouble(TreeMapValues.Close.rpm12));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty13), new InterpolatingDouble(TreeMapValues.Close.rpm13));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty14), new InterpolatingDouble(TreeMapValues.Close.rpm14));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty15), new InterpolatingDouble(TreeMapValues.Close.rpm15));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty16), new InterpolatingDouble(TreeMapValues.Close.rpm16));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty17), new InterpolatingDouble(TreeMapValues.Close.rpm17));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty18), new InterpolatingDouble(TreeMapValues.Close.rpm18));
      }
    }


    public final static double angleTolerance = 1.0;
    public final static double angleErrorToSpinUp = 5.0;
    public final static double rpmTolerance = 100.0;
    public final static double ticksPerRotation = 2048; //for falcons
    public final static double milliPerMin = 600;
    public final static double reduction = 1.0;
  }

}
