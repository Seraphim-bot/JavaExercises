package calc;

import calc.cpu.Processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Java. Calculator - test task from cngroup.dk
 *
 * @author Sergey Iryupin
 * @version 0.1 dated Jan 8, 2019
 */

public class Calculator {

    private final static String FILE_NAME = "calculations.txt";

    List<String> lines;
    Processor cpu;

    public Calculator(File file) {
        lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Calculator execute() {
        cpu = new Processor();
        for (String line : lines) {
            cpu.processLine(line);
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        List<Double> results = cpu.getResults();
        for (Double number : results) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            if (Math.floor(number) == number) {
                sb.append(String.format("%.0f", number));
            } else {
                sb.append(number);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Calculator(new File(FILE_NAME)).execute());
    }
}
