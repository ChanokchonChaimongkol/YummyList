package YummyList;

public interface Orderable {
    void addItem(Menu menu, int quantity);
    double calculateTotal();
    int calculateCookTime();
    String getOrderSummary();
}
