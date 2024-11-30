package model;

public abstract class User {

    public abstract void signIn();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private int id;
    private String name;
    private String contact;
    private String address;

    public User(String name, String contact, String address) {
//        this.id = id;
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    User() {
        System.out.println("User created");
    }
}
