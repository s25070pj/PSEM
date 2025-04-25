package com.example.lab2.ui.bmi;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class BMIFragmentTest extends TestCase {

    BMIFragment bmiFragment;

    @Before
    public void setUp(){
        bmiFragment = new BMIFragment();
    }

    @Test
    public void testCheckInputMethod(){
        String input = "70";

        boolean checkedInput = bmiFragment.checkInput(input);

        assertTrue(checkedInput);
    }

}