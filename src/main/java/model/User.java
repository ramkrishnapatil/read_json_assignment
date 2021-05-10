package model;

public class User extends Data {

    private static final String entityName = "Users";

    public User(String resource) {
        super(resource);
    }

    @Override
    public String getDataName() {
        return entityName;
    }

}