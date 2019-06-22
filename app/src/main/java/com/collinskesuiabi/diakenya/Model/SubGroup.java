package com.collinskesuiabi.diakenya.Model;

public class SubGroup {
    String images ,groupId,subGroupId,name;

    public SubGroup(String images, String groupId, String subGroupId, String name) {
        this.images = images;
        this.groupId = groupId;
        this.subGroupId = subGroupId;
        this.name = name;
    }

    public SubGroup() {
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSubGroupId() {
        return subGroupId;
    }

    public void setSubGroupId(String subGroupId) {
        this.subGroupId = subGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
