package YummyList;

import java.util.*;

public class Order implements Orderable {

    private static class OrderItem {
        Menu menu;
        int quantity;

        OrderItem(Menu menu, int quantity) {
            this.menu = menu;
            this.quantity = quantity;
        }
    }

    private ArrayList<OrderItem> orderItems = new ArrayList<>();

    @Override
    public void addItem(Menu menu, int quantity) {
        orderItems.add(new OrderItem(menu, quantity));
    }

    @Override
    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : orderItems) {
            total += item.menu.getPrice() * item.quantity;
        }
        return total;
    }

    @Override
    public int calculateCookTime() {
        int time = 0;
        for (OrderItem item : orderItems) {
            time += item.menu.getCookTime() * item.quantity;
        }
        return time;
    }

    @Override
    public String getOrderSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("========= ORDER LIST =========\n");

        for (OrderItem item : orderItems) {
            sb.append(item.menu.getName())
                    .append(" x ")
                    .append(item.quantity)
                    .append(" = ")
                    .append(item.menu.getPrice() * item.quantity)
                    .append("\n");
        }

        sb.append("===============================");
        return sb.toString();
    }
}
