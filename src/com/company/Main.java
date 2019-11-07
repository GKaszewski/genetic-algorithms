package com.company;

public class Main {

    static final String GOAL = "to be or not to be";
    static final int CHROMOSOME_LENGTH = GOAL.length();

    static final int POPULATION_SIZE = 300;
    static final double MUTATION_CHANCE = 0.02;

    static String[] population = new String[POPULATION_SIZE];
    static int[] fittest = new int[POPULATION_SIZE];

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
            count = 0;
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

    public static void select(){
        
    }

    public static void mutate(){

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
        print(0);
	    for (int i = 0; i < 300; i++){
            select();
            mutate();
            calcFittest();
            print(i);
        }
    }
}
