package YummyList;

public interface Authenticatable {
    void register(String user, String pass);
    Member login(String user, String pass);
    void saveToFile();
    void loadFromFile();
}
