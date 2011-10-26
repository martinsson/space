package space;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestMergeObjects {
    @Test
    public void mergeWithoutSpeed() throws Exception {
        StellarBody one = new StellarBody(1, 1, 0, 0, 0, 1, null);
        StellarBody other = new StellarBody(1, 0, 1, 0, 0, 1, null);
        StellarBody merge = one.absorb(other);
        assertEquals(0.5, merge.x, 0.00001);
        assertEquals(0.5, merge.y, 0.00001);
        assertEquals(0.0, merge.vx, 0.00001);
        assertEquals(0.0, merge.vy, 0.00001);
    }

    @Test
    public void mergeWithSpeed() throws Exception {
        StellarBody one = new StellarBody(1, 1, 0, 1, 0, 1, null);
        StellarBody other = new StellarBody(1, 0, 1, 0, 1, 1, null);
        StellarBody merge = one.absorb(other);
        assertEquals(0.5, merge.x, 0.00001);
        assertEquals(0.5, merge.y, 0.00001);
        assertEquals(0.5, merge.vx, 0.00001);
        assertEquals(0.5, merge.vy, 0.00001);
        assertEquals(2, merge.mass, 0.00001);
    }

    @Test
    public void mergeWithSpeedAndDifferentMasses() throws Exception {
        StellarBody one = new StellarBody(1, 1, 1, 1, 0, 1, null);
        StellarBody other = new StellarBody(4, 0, 0, 0, 1, 1, null);
        StellarBody merge = one.absorb(other);
        assertEquals(0.2, merge.x, 0.00001);
        assertEquals(0.2, merge.y, 0.00001);
        assertEquals(0.2, merge.vx, 0.00001);
        assertEquals(0.8, merge.vy, 0.00001);
        assertEquals(5, merge.mass, 0.00001);
    }

    @Test
    public void headsOnMergeConservesZeroSumMomentum() throws Exception {
        StellarBody one = new StellarBody(10, 0, 0, 100, 100, 1, null);
        StellarBody other = new StellarBody(100, 0, 0, -10, -10, 1, null);
        StellarBody merge = one.absorb(other);
        assertEquals(0, merge.x, 0.00001);
        assertEquals(0, merge.y, 0.00001);
        assertEquals(0, merge.vx, 0.00001);
        assertEquals(0, merge.vy, 0.00001);
        assertEquals(110, merge.mass, 0.00001);
    }

    @Test
    public void headsOnMergeConservesMomentum() throws Exception {
        StellarBody one = new StellarBody(10, 0, 0, 10, 10, 1, null);
        StellarBody other = new StellarBody(100, 0, 0, 0, 0, 1, null);
        StellarBody merge = one.absorb(other);
        assertEquals(0, merge.x, 0.00001);
        assertEquals(0, merge.y, 0.00001);
        assertEquals(100 / 110.0, merge.vx, 0.00001);
        assertEquals(100 / 110.0, merge.vy, 0.00001);
        assertEquals(110, merge.mass, 0.00001);
    }

}
