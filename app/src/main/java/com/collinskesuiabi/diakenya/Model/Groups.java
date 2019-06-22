package com.collinskesuiabi.diakenya.Model;

public class Groups {
    String groupImage ,name , groupId;

    public Groups(String groupImage, String name, String groupId) {
        this.groupImage = groupImage;
        this.name = name;
        this.groupId = groupId;
    }

    public Groups() {
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
