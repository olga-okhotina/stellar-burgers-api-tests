package stellarburgers.user;

import java.util.UUID;

public class User {
    private final String email;
    private final String password;
    private final String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User random() {
        String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        return new User(
                "test_" + suffix + "@test.com",
                "Pass_" + suffix,
                "User_" + suffix
        );
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
}
