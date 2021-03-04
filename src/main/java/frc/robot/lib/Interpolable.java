package frc.robot.lib;

// From Team 254
// https://github.com/Team254/FRC-2019-Public/blob/f99f09a15a2c8e7b5df868ec3a4a81105dc88112/src/main/java/com/team254/lib/util/Interpolable.java

/**
 * Interpolable is an interface used by an Interpolating Tree as the Value type. Given two end points and an
 * interpolation parameter on [0, 1], it calculates a new Interpolable representing the interpolated value.
 *
 * @param <T> The Type of Interpolable
 * @see InterpolatingTreeMap
 */
public interface Interpolable<T> {
    /**
     * Interpolates between this value and an other value according to a given parameter. If x is 0, the method should
     * return this value. If x is 1, the method should return the other value. If 0 < x < 1, the return value should be
     * interpolated proportionally between the two.
     *
     * @param other The value of the upper bound
     * @param x     The requested value. Should be between 0 and 1.
     * @return Interpolable<T> The estimated average between the surrounding data
     */
    T interpolate(T other, double x);
}
