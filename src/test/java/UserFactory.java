public final class UserFactory {

    public static User of(int id, String name, String password, Role role) {
        final User user = new User();
        user.id = id;
        user.name = name;
        user.password = password;
        user.role = role;

        return user;
    }
}
