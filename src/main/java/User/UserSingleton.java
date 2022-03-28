package User;

public class UserSingleton {
    private static UserSingleton instance;

    private String userName;
    private int userId;

    private UserSingleton() {
    }

    public String getUserName() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public static UserSingleton getInstance() {
        if (instance == null) {
            instance = new UserSingleton();
        }
        return instance;
    }
}
