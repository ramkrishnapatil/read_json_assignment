package model;

import java.util.List;
import java.util.Map;

import util.PrintDataUtil;

public class Organization extends Data {

    private static final String entityName = "Organization";

    public Organization(String resource) {
        super(resource);
    }

    @Override
    public String getDataName() {
        return entityName;
    }

}