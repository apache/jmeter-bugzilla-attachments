package org.apache.jorphan.math;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestStatCalculatorPercentile {

    private StatCalculatorLong calc;

    @Before
    public void setUp() {
        calc = new StatCalculatorLong();
    }

    // https://en.wikipedia.org/wiki/Percentile#The_Nearest_Rank_method
    @Test
    public void testPercentileExample1() {
        calc.addValue(15);
        calc.addValue(20);
        calc.addValue(35);
        calc.addValue(40);
        calc.addValue(50);
        assertEquals(15, calc.getPercentPoint(0.05).intValue());
        assertEquals(20, calc.getPercentPoint(0.30).intValue());
        assertEquals(20, calc.getPercentPoint(0.40).intValue());
        assertEquals(35, calc.getPercentPoint(0.50).intValue());
        assertEquals(50, calc.getPercentPoint(1.00).intValue());
    }
    @Test
    public void testPercentileExample2() {
        calc.addValue(3);
        calc.addValue(6);
        calc.addValue(7);
        calc.addValue(8);
        calc.addValue(8);
        calc.addValue(10);
        calc.addValue(13);
        calc.addValue(15);
        calc.addValue(16);
        calc.addValue(20);
        assertEquals(7, calc.getPercentPoint(0.25).intValue());
        assertEquals(8, calc.getPercentPoint(0.50).intValue());
        assertEquals(15, calc.getPercentPoint(0.75).intValue());
        assertEquals(20, calc.getPercentPoint(1.00).intValue());
    }
    @Test
    public void testPercentileExample3() {
        calc.addValue(3);
        calc.addValue(6);
        calc.addValue(7);
        calc.addValue(8);
        calc.addValue(8);
        calc.addValue(9);
        calc.addValue(10);
        calc.addValue(13);
        calc.addValue(15);
        calc.addValue(16);
        calc.addValue(20);
        assertEquals(7, calc.getPercentPoint(0.25).intValue());
        assertEquals(9, calc.getPercentPoint(0.50).intValue());
        assertEquals(15, calc.getPercentPoint(0.75).intValue());
        assertEquals(20, calc.getPercentPoint(1.00).intValue());
    }

}
