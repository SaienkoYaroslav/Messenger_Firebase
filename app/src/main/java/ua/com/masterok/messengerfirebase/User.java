package ua.com.masterok.messengerfirebase;

public class User {

    private String id;
    private String name;
    private String lastName;
    private int age;
    private boolean isOnline;

    public User(String id, String name, String lastName, int age, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.isOnline = isOnline;
    }

    // При роботі з РілТаймБД Файрбейс клас, ексземпляр якого ми хочемо записати до БД, повинен мати пустий конструктор
    public User() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public boolean isOnline() {
        return isOnline;
    }
}
