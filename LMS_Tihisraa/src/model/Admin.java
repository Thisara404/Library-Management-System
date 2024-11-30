package model;

public class Admin extends User {
//    private Password  password;
    public Admin( String name, String contact, String address, String memberNo, String expireDate, String password) {
        super(name,  contact,  address);
        
//        this.password = new Password(password);
    }

    @Override
    public void signIn() {

    }
}
