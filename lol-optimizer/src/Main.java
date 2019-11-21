public class Main {

    static int budget = 6400;
    static Item[] items = Item.getArrayOfItems();

    public static void main(String[] args) {
        GeneticAlgorithm algorithm = new GeneticAlgorithm();

        for (int i = 0; i < 10; i++) {
            algorithm.iterate(i);
        }
    }
}
