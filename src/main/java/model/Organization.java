package model;

public class Organization extends Data {

    private static final String ENTITY_NAME = "Organization";

    public Organization(String resource) {
        super(resource);
    }

    @Override
    public String getDataName() {
        return ENTITY_NAME;
    }

}