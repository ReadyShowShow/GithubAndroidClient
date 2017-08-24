package com.jian.github.bean.apibean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：搜索Repository返回的结果
 */
public class RepoSearchResult {

    @Expose
    @SerializedName("total_count")
    Integer totalCount;
    @Expose
    @SerializedName("incomplete_results")
    Boolean incompleteResults;
    @Expose
    @SerializedName("items")
    List<Repository> repositories = new ArrayList();

    public Boolean getIncompleteResults() {
        return this.incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<Repository> getRepositories() {
        return this.repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "RepoSearchResult{" +
                "totalCount=" + totalCount +
                ", incompleteResults=" + incompleteResults +
                ", repositories=" + repositories +
                '}';
    }
}
