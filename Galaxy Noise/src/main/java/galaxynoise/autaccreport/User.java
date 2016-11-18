package galaxynoise.autaccreport;

/**
 * Created by semjeromers on 11/12/2016.
 */

public class User {
    String userName;
    String password;
    int uid;

    User()
    {

    }

    public User(int uid, String userName, String password )
    {
        this.uid = uid;
        this.userName = userName;
        this.password = password;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
