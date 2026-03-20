package YummyList;

public interface Manageable {
    boolean addMenu(Menu menu);
    boolean editMenu(int id, String name, double price, int cookTime);
    boolean deleteMenu(int id);
    String getMenuDisplay();
}
