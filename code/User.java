public abstract class User {
    private String userID;
    private String name;
    private String password;

    // constructor when registering a new user
    public User(String id, String name, String pass) {
        userID = id;
        this.name = name;
        password = pass;
    }

    // constructor when loading from file
    public User(String id, String name) {
        userID = id;
        this.name = name;
        password = "password";
    }

    // getters
    public String getID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    // setters
    public void setName(String newname) {
        name = newname;
    }

    public void setID(String newID) {
        userID = newID;
    }

    public void changePassword(String pass) {
        password = pass;
    }

}