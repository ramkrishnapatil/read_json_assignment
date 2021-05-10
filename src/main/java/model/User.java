package model;

public class User extends Data {

    private static final String ENTITY_NAME = "Users";

    public User(String resource) {
        super(resource);
    }

    @Override
    public String getDataName() {
        return ENTITY_NAME;
    }

}