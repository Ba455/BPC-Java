package clinic;

public abstract class Person {

    private int id;
    private String fullName;
    private String address;
    private String telephone;

    public Person(int id, String fullName, String address, String telephone) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.telephone = telephone;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + fullName;
    }
}
