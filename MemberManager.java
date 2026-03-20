package YummyList;

import java.io.*;
import java.util.*;

public class MemberManager implements Authenticatable {

    private ArrayList<Member> memberList = new ArrayList<>();
    private final String FILE_NAME = "member.txt";

    public MemberManager() {
        loadFromFile();
    }

    @Override
    public void register(String user, String pass) {
        memberList.add(new Member(user, pass, 0));
        saveToFile();
    }

    @Override
    public Member login(String user, String pass) {
        for (Member m : memberList) {
            if (m.getUsername().equals(user) && m.checkPassword(pass)) {
                return m;
            }
        }
        return null;
    }

    @Override
    public void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Member m : memberList) pw.println(m.toString());
        } catch (IOException e) {
            System.out.println("Error saving member");
        }
    }

    @Override
    public void loadFromFile() {
        try (Scanner sc = new Scanner(new File(FILE_NAME))) {
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(",");
                memberList.add(new Member(data[0], data[1], Integer.parseInt(data[2])));
            }
        } catch (Exception e) {
            System.out.println("Member file not found");
        }
    }
}
