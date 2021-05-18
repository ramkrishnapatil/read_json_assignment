package pojo;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import datastore.Data;
import datastore.DataStore;

public class OrganizationPojo extends DataStore {

    public OrganizationPojo(String fileName) {
        super(fileName);
    }

    @Override
    public String getDataName() {
        return "OrganizationPojo";
    }

    @Override
    public List<Data> visit(Data result) {
        return null;
    }

    private int _id;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    private String created_at;
    private String details;
    private String domain_names;
    private String external_id;
    private String name;
    private String shared_tickets;
    private String tags;
    private String url;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDomain_names() {
        return domain_names;
    }

    public void setDomain_names(String domain_names) {
        this.domain_names = domain_names;
    }

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShared_tickets() {
        return shared_tickets;
    }

    public void setShared_tickets(String shared_tickets) {
        this.shared_tickets = shared_tickets;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "DeserializeDemoBean{" +
                "Organization_id=" + _id +
                ", name=" + name +
                ", created_at=" + created_at +
                '}';
    }
}
