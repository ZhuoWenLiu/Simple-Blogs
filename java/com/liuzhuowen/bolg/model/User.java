package com.liuzhuowen.bolg.model;

public class User {
    public Integer uid;
    public String username;
    public String nickname;
    public String password;
    public String avatar;
    public String git;
    public User(){}

    public User(Integer uid, String username, String nickname, String password, String avatar, String git) {
        this.uid = uid;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.avatar = avatar;
        this.git = git;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", git='" + git + '\'' +
                '}';
    }
}
