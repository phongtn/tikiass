package com.wind.tiki;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class TestSimpleExcel {

    SimpleExcel excelCalculator = new SimpleExcel();

    private PrintStream sysOut;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));

        excelCalculator.addCell("A1", "5");
        excelCalculator.addCell("A2", "A1 5 * B1 +");
        excelCalculator.addCell("B1", "6");
    }

    @After
    public void revertStreams() {
        System.setOut(sysOut);
    }

    @Test
    public void testExample() {
        excelCalculator.computeFormula();
        excelCalculator.printResult();
        assertThat(outContent.toString(), containsString("31"));
    }

    public void testOrder() {
        excelCalculator.addCell("D4", "1");
        excelCalculator.addCell("D2", "1");
        excelCalculator.addCell("A1", "5");
        excelCalculator.addCell("B1", "6");
        excelCalculator.addCell("A2", "A1 5 * B1 +");
    }

    @Test
    public void testCircularDependencies() {
        excelCalculator.addCell("A1", "A2 2 *");
        excelCalculator.addCell("A2", "A1 5 +");
        excelCalculator.validateCircular();
        assertThat(outContent.toString(), containsString("between A1 and A2 detected"));
    }
}
