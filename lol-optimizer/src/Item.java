public class Item {
    private String name;
    private int ap;
    private int cost;
    private double bonus;

    public Item(String name, int ap, int cost, double bonus) {
        this.name = name;
        this.ap = ap;
        this.cost = cost;
        this.bonus = bonus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", ap=" + ap +
                ", cost=" + cost +
                ", bonus=" + bonus +
                '}'+"\n";
    }

    public static Item[] getArrayOfItems() {
        Item[] items = new Item[7];
        items[0] = new Item("Rabadon's Deathcap", 120, 3600, 0.3);
        items[1] = new Item("Luden's Echo", 90, 3200, 0);
        items[2] = new Item("Zhonya's Hourglass", 75, 2900, 0);
        items[3] = new Item("Void Staff", 70, 2650, 0);
        items[4] = new Item("Needlessly Large Rod", 60, 1250, 0);
        items[5] = new Item("Blasting Wand", 40, 850, 0);
        items[6] = new Item("Amplifying Tome", 20, 435, 0);

        return items;
    }
}
