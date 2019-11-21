import com.sun.deploy.util.ArrayUtil;

import java.util.Arrays;
import java.util.stream.Stream;

public class GeneticAlgorithm {

    static final int POPULATION_SIZE = 400;
    static final double MUTATION_CHANCE = 0.05;
    static final int CHROMOSOME_LENGTH = 4;

    static int[][] population = new int[POPULATION_SIZE][CHROMOSOME_LENGTH];
    static int[] fittest = new int[POPULATION_SIZE];

    public GeneticAlgorithm() {
        for (int ch = 0; ch < POPULATION_SIZE; ch++) {
            for (int item = 0; item < CHROMOSOME_LENGTH; item++) {
                population[ch][item] = getRandomItemNumber();
            }
        }

        calculateFittest();
    }

    private int getRandomItemNumber() {
        return (int)(Math.random() * 7);
    }

    public void calculateFittest() {
        boolean hasBonus = false;
        int ap = 0, cost = 0;
        for (int ch = 0; ch < POPULATION_SIZE; ch++) {
            ap = cost = 0;
            hasBonus = false;

            for (int items = 0; items < CHROMOSOME_LENGTH; items++) {
               ap +=  Main.items[population[ch][items]].getAp();
               cost += Main.items[population[ch][items]].getCost();

               if(Main.items[population[ch][items]].getBonus() > 0.0) {
                   hasBonus = true;
               }
            }

            if(cost > Main.budget) {
                fittest[ch] = 0;
            } else {
                fittest[ch] = ap;
                if(hasBonus) {
                    ap -= 120;
                    ap = (int) (ap * 1.3);
                    ap += 120;

                    fittest[ch]=ap;
                }
            }
        }
    }

    public int[][] crossover(int[] parent1, int[] parent2) {
        int[][] children = new int[2][4];
        int number = (int)(Math.random() * (CHROMOSOME_LENGTH-1));
        int[] child1FirstPart = Arrays.copyOfRange(parent1, 0, number);
        int[] child1SecondPart = Arrays.copyOfRange(parent2, number, parent2.length);
        int[] child1 = new int[4];
        int[] child2FirstPart = Arrays.copyOfRange(parent2, 0, number);
        int[] child2SecondPart = Arrays.copyOfRange(parent1, number, parent1.length);
        int[] child2 = new int[4];
        int position = 0;
        for (int element : child1FirstPart) {
            child1[position] = element;
            position++;
        }
        for (int element : child1SecondPart) {
            child1[position] = element;
            position++;
        }

        position = 0;

        for (int element : child2FirstPart) {
            child2[position] = element;
            position++;
        }
        for (int element : child2SecondPart) {
            child2[position] = element;
            position++;
        }
        children[0] = child1;
        children[1] = child2;

        return children;
    }

    public void mutate() {
        for (int ch = 0; ch < POPULATION_SIZE; ch++) {
            for (int items = 0; items < CHROMOSOME_LENGTH; items++) {
                if(Math.random() < MUTATION_CHANCE) {
                    population[ch][items] = getRandomItemNumber();
                }
            }
        }
    }

    private static int[] getFromWheelSelection(int fittestSum){
        int number = (int)(Math.random() * fittestSum);
        int sum = 0;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            sum += fittest[i];
            if(sum >= number) return population[i];
        }

        return population[POPULATION_SIZE-1];
    }

    public void select() {
        int[][] newPopulation = new int[POPULATION_SIZE][CHROMOSOME_LENGTH];
        int fittestSum = 0;
        int max = fittest[0];
        int index = 0;
        for (int i = 0; i < POPULATION_SIZE; i++){
            fittestSum += fittest[i];
            if(max < fittest[i]){
                max = fittest[i];
                index = i;
            }
        }

        //newPopulation[0] = newPopulation[1] = newPopulation[2] = newPopulation[3]  = population[index];
        for (int i = 0; i < POPULATION_SIZE; i+=2) {
            int[] parent1 = getFromWheelSelection(fittestSum);
            int[] parent2 = getFromWheelSelection(fittestSum);
            int[][] children = crossover(parent1, parent2);

            newPopulation[i] = children[0];
            newPopulation[i+1] = children[1];
        }

        population = newPopulation;

    }

    public void iterate(int i) {
        select();
        mutate();
        calculateFittest();
        print(i);
        //System.out.println(Arrays.toString(population[0]));
    }

    public void print(int iteration) {
        int max = fittest[0];
        int index = 0;
        for (int i = 0; i < POPULATION_SIZE; i++){
            if(max < fittest[i]){
                max = fittest[i];
                index = i;
            }
        }

        int[] slice = population[index];
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            info.append(Main.items[slice[i]].getName()).append(", ");
        }
        System.out.printf("%d: %s (%d), ", iteration, info.toString(), fittest[index]);
        for(int i = 0; i < POPULATION_SIZE; i++){
            slice = population[index];
            info = new StringBuilder();
            for (int j = 0; j < 4; j++) {
                info.append(Main.items[slice[j]].getName()).append(",");
            }
            System.out.printf("%d: %s Fittest: (%d), ", iteration, info.toString(), fittest[i]);
        }
        System.out.println();
    }
}
