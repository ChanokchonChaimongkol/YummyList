package YummyList;

public class Menu {
    private int id;
    private String name;
    private double price;
    private int cookTime; // นาที

    public Menu(int id, String name, double price, int cookTime) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.cookTime = cookTime;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getCookTime() { return cookTime; }

    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setCookTime(int cookTime) { this.cookTime = cookTime; }

    @Override
    public String toString() {
        return id + "," + name + "," + price + "," + cookTime;
    }
}