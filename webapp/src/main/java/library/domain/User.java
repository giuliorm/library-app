package library.domain;

import javax.persistence.*;

/**
 * Created by zotova on 27.07.2016.
 */
@Entity
@Table(name = "users")
@SuppressWarnings("JpaQlInspection")
@NamedQueries({
        @NamedQuery(query = "FROM User u WHERE u.userId = :id", name="User.findById")
})
public class User {

    @Id
    @GeneratedValue
    @Column(name="userid")
    private int userId;

    @Column(name = "username")
    private String userName;

    @Column(name = "userpassword")
    private String userPassword;

    public static final String FINDBYID = "User.findById";

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String password) {
        this.userPassword = password;
    }

}
