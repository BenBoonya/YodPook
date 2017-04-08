package com.yodpook.android.models;

/**
 * Created by Boonya Kitpitak on 4/8/17.
 */

public class Member {
    private String profileUrl;
    private String uid;

    public Member() {
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
