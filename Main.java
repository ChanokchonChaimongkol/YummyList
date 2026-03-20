package YummyList;

import javax.swing.*;

public class Main {

    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "1234";

    public static void main(String[] args) {

        MenuManager menuManager = new MenuManager();
        MemberManager memberManager = new MemberManager();

        while (true) {

            String mainMenu = """
                    ===== YUMMY LIST =====
                    1. Register
                    2. Member Login
                    3. Admin Login
                    4. Exit
                    """;

            String input = JOptionPane.showInputDialog(mainMenu);
            if (input == null) break;

            int choice = Integer.parseInt(input);

            // ================= REGISTER =================
            if (choice == 1) {

                String user = JOptionPane.showInputDialog("Enter Username:");
                String pass = JOptionPane.showInputDialog("Enter Password:");

                memberManager.register(user, pass);
                JOptionPane.showMessageDialog(null, "Register Success!");
            }

            // ================= MEMBER LOGIN =================
            else if (choice == 2) {

                String user = JOptionPane.showInputDialog("Username:");
                String pass = JOptionPane.showInputDialog("Password:");

                Member current = memberManager.login(user, pass);

                if (current != null) {

                    JOptionPane.showMessageDialog(null, "Login Success!");

                    Order order = new Order();
                    boolean ordering = true;

                    // ===== สั่งหลายเมนู =====
                    while (ordering) {

                        JOptionPane.showMessageDialog(null,
                                menuManager.getMenuDisplay());

                        String idStr = JOptionPane.showInputDialog("Enter Menu ID:");
                        if (idStr == null) break;

                        int id = Integer.parseInt(idStr);
                        Menu selected = menuManager.getMenuById(id);

                        if (selected != null) {

                            int qty = Integer.parseInt(
                                    JOptionPane.showInputDialog("Enter Quantity:")
                            );

                            order.addItem(selected, qty);

                            int confirm = JOptionPane.showConfirmDialog(
                                    null,
                                    "Add more order?"
                            );

                            if (confirm != JOptionPane.YES_OPTION) {
                                ordering = false;
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Menu not found!");
                        }
                    }

                    double total = order.calculateTotal();
                    int cookTime = order.calculateCookTime();

                    // ===== ใช้แต้มลดราคา (1 point = 1 บาท) =====
                    int currentPoints = current.getPoints();

                    if (currentPoints > 0 && total > 0) {

                        String usePointStr = JOptionPane.showInputDialog(
                                "You have " + currentPoints + " points.\n" +
                                        "Enter points to use (1 point = 1 Baht):"
                        );

                        if (usePointStr != null && !usePointStr.isEmpty()) {

                            int usePoint = Integer.parseInt(usePointStr);

                            // ป้องกันใช้เกิน
                            if (usePoint > currentPoints)
                                usePoint = currentPoints;

                            if (usePoint > total)
                                usePoint = (int) total;

                            total -= usePoint;
                            current.addPoints(-usePoint);
                        }
                    }

                    // ===== คำนวณแต้มใหม่ 5% =====
                    int earnedPoints = (int) (total * 0.05);
                    current.addPoints(earnedPoints);

                    memberManager.saveToFile();

                    String receipt =
                            "========== RECEIPT ==========\n" +
                                    order.getOrderSummary() + "\n\n" +
                                    "Total After Discount: " + total + "\n" +
                                    "Cook Time: " + cookTime + " minutes\n" +
                                    "Earned Points (5%): " + earnedPoints + "\n" +
                                    "Current Points: " + current.getPoints() + "\n" +
                                    "=============================";

                    JOptionPane.showMessageDialog(null, receipt);

                    MessageLogger.saveMessage(receipt);

                } else {
                    JOptionPane.showMessageDialog(null, "Login Failed!");
                }
            }

            // ================= ADMIN LOGIN =================
            else if (choice == 3) {

                String user = JOptionPane.showInputDialog("Admin Username:");
                String pass = JOptionPane.showInputDialog("Admin Password:");

                if (user.equals(ADMIN_USER) && pass.equals(ADMIN_PASS)) {

                    JOptionPane.showMessageDialog(null, "Admin Login Success!");

                    boolean adminLoop = true;

                    while (adminLoop) {

                        String adminMenu = """
                                ===== ADMIN MENU =====
                                1. View Menu
                                2. Add Menu
                                3. Edit Menu
                                4. Delete Menu
                                5. Logout
                                """;

                        String adminInput = JOptionPane.showInputDialog(adminMenu);
                        if (adminInput == null) break;

                        int adminChoice = Integer.parseInt(adminInput);

                        if (adminChoice == 1) {
                            JOptionPane.showMessageDialog(null,
                                    menuManager.getMenuDisplay());
                        }

                        else if (adminChoice == 2) {

                            int id = Integer.parseInt(
                                    JOptionPane.showInputDialog("Menu ID:")
                            );

                            String name = JOptionPane.showInputDialog("Menu Name:");
                            double price = Double.parseDouble(
                                    JOptionPane.showInputDialog("Price:")
                            );
                            int cookTime = Integer.parseInt(
                                    JOptionPane.showInputDialog("Cook Time (minutes):")
                            );

                            boolean added = menuManager.addMenu(
                                    new Menu(id, name, price, cookTime)
                            );

                            if (added)
                                JOptionPane.showMessageDialog(null, "Menu Added!");
                            else
                                JOptionPane.showMessageDialog(null, "ID already exists!");
                        }

                        else if (adminChoice == 3) {

                            int id = Integer.parseInt(
                                    JOptionPane.showInputDialog("Menu ID to Edit:")
                            );

                            String name = JOptionPane.showInputDialog("New Name:");
                            double price = Double.parseDouble(
                                    JOptionPane.showInputDialog("New Price:")
                            );
                            int cookTime = Integer.parseInt(
                                    JOptionPane.showInputDialog("New Cook Time:")
                            );

                            boolean edited = menuManager.editMenu(id, name, price, cookTime);

                            if (edited)
                                JOptionPane.showMessageDialog(null, "Menu Updated!");
                            else
                                JOptionPane.showMessageDialog(null, "Menu not found!");
                        }

                        else if (adminChoice == 4) {

                            int id = Integer.parseInt(
                                    JOptionPane.showInputDialog("Menu ID to Delete:")
                            );

                            boolean deleted = menuManager.deleteMenu(id);

                            if (deleted)
                                JOptionPane.showMessageDialog(null, "Menu Deleted!");
                            else
                                JOptionPane.showMessageDialog(null, "Menu not found!");
                        }

                        else {
                            adminLoop = false;
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Admin Login Failed!");
                }
            }

            else {
                break;
            }
        }
    }
}