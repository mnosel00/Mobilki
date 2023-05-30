package pl.wsei.mobilne.reminderapp;

import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CheckMatTest {

    private CheckMat check;

    @Before
    public void setUp() {
        check = new CheckMat();
    }

    @Test
    public void testAdd() {
        assertEquals(5, check.add(2, 3));
    }


    @Test
    public void testSubtract() {
        assertEquals(-1, check.subtract(2, 3));
    }

    @Test
    public void testMultiply() {
        assertEquals(6, check.multiply(2, 3));
    }

    @Test
    public void testDivide() {
        assertEquals(2.0, check.divide(6, 3), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivideByZero() {
        check.divide(6, 0);
    }
}