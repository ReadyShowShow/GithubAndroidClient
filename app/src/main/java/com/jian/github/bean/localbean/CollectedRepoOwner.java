package com.jian.github.bean.localbean;

import com.jian.github.bean.apibean.User;

/**
 * Description：
 */
public class CollectedRepoOwner {

    private Long id;
    private String avatarUrl;
    private String login;

    public void convert(User owner) {
        this.id = owner.getId().longValue();
        this.login = owner.getAuthorName();
        this.avatarUrl = owner.getAvatarUrl();
    }

    @Override
    public String toString() {
        return "CollectedRepoOwner{" +
                "id=" + id +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", login='" + login + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
