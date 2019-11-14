package com.company;

import java.util.ArrayList;

public class Main {

    static final String GOAL = "to be or not to be that is a question";
    static final int CHROMOSOME_LENGTH = GOAL.length();

    static final int POPULATION_SIZE = 4000;
    static final double MUTATION_CHANCE = 0.02;

    static String[] population = new String[POPULATION_SIZE];
    static int[] fittest = new int[POPULATION_SIZE];

    private static char getRandomChar(){
        char c;
        int number = (int)(Math.random() * 27);
        if (number == 0) c= ' ';
        else {
            c = (char) (96 + number);
        }

        return c;
    }

    private static String getRandomString(){
        StringBuilder s = new StringBuilder(CHROMOSOME_LENGTH);
        for (int i = 0; i < CHROMOSOME_LENGTH; i++){
            int number = (int)(Math.random() * 27);
            if (number == 0) s.append(" ");
            else {
                s.append((char) (96 + number));
            }
        }
        return s.toString();
    }

    public static void initPopulation(){
        for(int ch = 0; ch < POPULATION_SIZE; ch++){
            population[ch] = getRandomString();
        }
    }

    public static void calcFittest(){
        int count;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            count = 1;
            for (int j = 0; j < population[i].length(); j++) {
                if (population[i].charAt(j) == GOAL.charAt(j)){
                    count++;
                }
            }

            fittest[i] = count;
        }
    }

    public static String[] crossover(String parent1, String parent2){
        String[] children = new String[2];
        int number = (int)(Math.random() * (CHROMOSOME_LENGTH-1));
        children[0] = parent1.substring(0, number).concat(parent2.substring(number));
        children[1] = parent2.substring(0, number).concat(parent1.substring(number));
        return children;
    }

    private static String getBestFittedFromRandom(int number){
        ArrayList<Integer> randoms = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            randoms.add((int) (Math.random() * POPULATION_SIZE));
        }

        int max = fittest[randoms.get(0)];
        for (int i = 1; i < randoms.size(); i++) {
            if( max < fittest[randoms.get(i)]) max = fittest[randoms.get(i)];
       }

       return population[max];
    }

    private static String getFromWheelSelection(int fittestSum){
        int number = (int)(Math.random() * fittestSum);
        int sum = 0;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            sum += fittest[i];
            if(sum >= number) return population[i];
        }

        return population[POPULATION_SIZE-1];
    }

    public static void select(){
        //tournamentSelection();
        wheelSelection();
    }

    private static void wheelSelection(){
        String[] newPopulation = new String[POPULATION_SIZE];
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

        newPopulation[0] = newPopulation[1] = newPopulation[2] = newPopulation[3]  = population[index];

        for (int i = 4; i < POPULATION_SIZE; i+=2) {
            String parent1 = getFromWheelSelection(fittestSum);
            String parent2 = getFromWheelSelection(fittestSum);
            String[] children = crossover(parent1, parent2);

            newPopulation[i] = children[0];
            newPopulation[i+1] = children[1];
        }

        population = newPopulation;
    }

    private static void tournamentSelection(){
        String[] newPopulation = new String[POPULATION_SIZE];

        int max = fittest[0];
        int index = 0;
        for (int i = 0; i < POPULATION_SIZE; i++){
            if(max < fittest[i]){
                max = fittest[i];
                index = i;
            }
        }

        newPopulation[0] = newPopulation[1] = newPopulation[2] = newPopulation[3]  = population[index];

        for (int i = 4; i < POPULATION_SIZE; i+=2) {
            String parent1 = getBestFittedFromRandom(30);
            String parent2 = getBestFittedFromRandom(30);
            String[] children = crossover(parent1, parent2);

            newPopulation[i] = children[0];
            newPopulation[i+1] = children[1];
        }

        population = newPopulation;
    }

    public static void mutate(){
        for (int i = 0; i < POPULATION_SIZE; i++) {
            for (int ch = 0; ch < CHROMOSOME_LENGTH; ch++) {
                if(Math.random() < MUTATION_CHANCE){
                    population[i] = population[i].substring(0, ch) + getRandomChar() + (ch < CHROMOSOME_LENGTH - 1 ? population[i].substring(ch + 1) : "");
                }
            }
        }
    }

    public static void print(int iteration){
       int max = fittest[0];
       int index = 0;
       for (int i = 0; i < POPULATION_SIZE; i++){
           if(max < fittest[i]){
               max = fittest[i];
               index = i;
           }
       }

       System.out.printf("%d: %s (%d),", iteration, population[index], fittest[index]);
       for(int i = 0; i < POPULATION_SIZE; i++){
           System.out.printf("%s (%d),", population[i], fittest[i]);
       }
       System.out.println();
    }

    public static void main(String[] args) {
	    initPopulation();
	    calcFittest();
        //print(0);
	    for (int i = 0; i < 600; i++){
            select();
            mutate();
            calcFittest();
            print(i);
        }

    }
}
