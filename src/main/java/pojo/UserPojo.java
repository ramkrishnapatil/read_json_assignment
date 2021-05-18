/*
 * Copyright (C) 2021 Thales ATM all rights reserved. This software is
 * the property of Thales ATM and may not be used, copied or disclosed
 * in any manner except under a licence agreement signed with Thales ATM.
 */
package pojo;

import java.time.OffsetDateTime;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPojo {

    public String getDataName() {
        return "UserPojo";
    }

    public int _id;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss XXXXX")
    public OffsetDateTime created_at;

    @JsonProperty("last_login_at")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss XXXXX")
    public OffsetDateTime last_login_at;

    public Map<String, Object> properties = new LinkedHashMap<>();

    @JsonAnySetter
    public void setProperties(String key, Object value){
        properties.put(key, value);
    }

    @Override
    public String toString() {
        return "DeserializeDemoBean{" +
                "User _id=" + _id +
                ", activedDate=" + created_at +
                ", lastLoginAt=" + last_login_at +
                ", lastLoginAt=" + last_login_at.getOffset() + " Local " + last_login_at.toLocalTime() + " Offset " + last_login_at.toOffsetTime() +
                ", properties=" + Arrays.toString(properties.entrySet().toArray()) +
                '}';
    }

}
