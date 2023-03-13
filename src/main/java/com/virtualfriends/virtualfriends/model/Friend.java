package com.virtualfriends.virtualfriends.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "characters")
public class Friend {

    @Id
    private String id;

    private String name;
    private String adjective;
    private String avatar_url;
    private String icon_url;

    public Friend() {

    }

    public Friend(String name, String adjective) {
        this.name = name;
        this.adjective = adjective;

        String combined = adjective + "%20" + name;
        this.avatar_url = "https://robohash.org/" + combined + ".png";
        this.icon_url = "https://api.dicebear.com/5.x/fun-emoji/svg?seed=" + combined;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdjective() {
        return adjective;
    }

    public void setAdjective(String adjective) {
        this.adjective = adjective;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }

    public void setAvatarUrl(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getIconUrl() {
        return icon_url;
    }

    public void setIconUrl(String icon_url) {
        this.icon_url = icon_url;
    }

    @Override
    public String toString() {
        return "Friend {" +
                "id='" + id + '\'' +
                ", name='" + name + '\''   +
                ", adjective='" + adjective + '\'' +
                ", avatar='" + avatar_url + '\'' +
                ", icon='" + icon_url + '\'' +
                '}';
    }
}
