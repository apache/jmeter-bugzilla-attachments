package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DummyAnnotatedTest
{
    public String name;
    public int two = 1; //very wrong.
    
    public DummyAnnotatedTest() {
        name="NOT SET";
    }
    public DummyAnnotatedTest(String name) {
        this.name = name;
    }

    @Test(expected=RuntimeException.class)
    public void fail() {
        throw new RuntimeException();
    }
    
    @Before
    public void verifyTwo() {
        two = 2;
    }
    
    @After
    public void printDone() {
        System.out.println("done with an annotated test.");
    }
    
    @Test
    public void add() {
        int four = two+2;
        if(4!=four) {
            throw new RuntimeException("4 did not equal four.");
        }
        //or if you have assertions enabled
        assert 4 == four;
    }
}
