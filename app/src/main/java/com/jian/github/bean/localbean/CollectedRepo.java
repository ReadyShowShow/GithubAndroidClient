package com.jian.github.bean.localbean;

import com.jian.github.bean.apibean.Repository;
import com.jian.github.bean.apibean.User;
import com.jian.github.db.convert.CollectedRepoOwnerConvert;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

@Entity(nameInDb = "COLLECTED_REPO", indexes = {
        @Index(value = "id", unique = true)
})
public class CollectedRepo {
    @Id(autoincrement = false)
    private Long id;
    private long collectTime;
    private String htmlUrl;
    private String fullName;
    private String language;
    private int stargazersCount;
    @Convert(converter = CollectedRepoOwnerConvert.class, columnType = String.class)
    private CollectedRepoOwner owner;
    private String description;

    @Generated(hash = 1441903010)
    public CollectedRepo(Long id, long collectTime, String htmlUrl, String fullName, String language,
                         int stargazersCount, CollectedRepoOwner owner, String description) {
        this.id = id;
        this.collectTime = collectTime;
        this.htmlUrl = htmlUrl;
        this.fullName = fullName;
        this.language = language;
        this.stargazersCount = stargazersCount;
        this.owner = owner;
        this.description = description;
    }

    @Generated(hash = 813661088)
    public CollectedRepo() {
    }

    public void convert(Repository repo) {
        this.id = repo.getId().longValue();
        this.htmlUrl = repo.getHtmlUrl();
        this.fullName = repo.getFullName();
        this.language = repo.getLanguage();
        this.stargazersCount = repo.getStargazersCount();
        this.description = repo.getDescription();
        this.owner = new CollectedRepoOwner();
        User owner = repo.getOwner();
        this.owner.convert(owner);
    }

    @Override
    public String toString() {
        return "CollectedRepo{"
                + "id="
                + id
                + ", collectTime="
                + collectTime
                + ", htmlUrl='"
                + htmlUrl
                + '\''
                + ", fullName='"
                + fullName
                + '\''
                + ", language='"
                + language
                + '\''
                + ", stargazersCount="
                + stargazersCount
                + ", owner="
                + owner
                + ", description='"
                + description
                + '\''
                + '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCollectTime() {
        return this.collectTime;
    }

    public void setCollectTime(long collectTime) {
        this.collectTime = collectTime;
    }

    public String getHtmlUrl() {
        return this.htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getStargazersCount() {
        return this.stargazersCount;
    }

    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CollectedRepoOwner getOwner() {
        return this.owner;
    }

    public void setOwner(CollectedRepoOwner owner) {
        this.owner = owner;
    }

}
