package org.encuestame.test.business.utils;

import java.text.DecimalFormat;

import junit.framework.TestCase;

public class TestCalculation extends TestCase {


    public void testPercent(){
        double total = 200;
        double actual = 123;
        double result = ((actual/total));
        System.out.println(result);
        DecimalFormat percent2 = new DecimalFormat("#0.00%");
        String st2 = percent2.format(result);
        System.out.println(st2.toString());
    }
}
