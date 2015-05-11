package sise.ui.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wojciech Szałapski
 */
public class TrainingDataGenerator {

    private final static double MINIMUM = 1;

    private final static double MAXIMUM = 100;

    private final static Random generator = new Random();

    public static void main(String[] args) {
        double span = MAXIMUM - MINIMUM;

//        Scanner scanner = new Scanner(System.in);
//        
//        System.out.print("Podaj ilość punktów: ");
//        int points = scanner.nextInt();
//        
//        System.out.print("Podaj stopień zaburzenia: ");
//        double noise = scanner.nextDouble();
        int[] pointsValue = new int[]{10, 50, 100, 200, 500, 1000};
        double[] noiseValue = new double[]{0, 0.01, 0.05, 0.1, 0.2, 0.25};

        for (int points : pointsValue) {
            for (double noise : noiseValue) {
                try (PrintWriter writer = new PrintWriter("points = " + points + " noise = " + noise + ".txt")) {
                    for (int i = 0; i < points; ++i) {
                        double x = Math.random() * span + MINIMUM;
                        double y = Math.sqrt(x);

                        double glitch = Math.random() * noise;
                        if (generator.nextBoolean()) {
                            y *= 1 - glitch;
                        } else {
                            y *= 1 + glitch;
                        }

                        writer.write(x + " " + y + "\n");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TrainingDataGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
