package com.jian.github.data;

import com.jian.github.bean.localbean.CollectedRepo;

import java.util.List;

public interface ICollectDataSource {

    /**
     * 查询所有收藏
     *
     * @param page  页码，从0开始
     * @param limit 每页数量
     * @return List<CollectedRepo>
     */
    List<CollectedRepo> queryCollectRepo(int page, int limit);

    /**
     * 根据关键字搜索
     *
     * @param keyword 关键字
     * @return List<CollectedRepo>
     */
    List<CollectedRepo> queryByKeyword(String keyword, int page, int limit);

    boolean collectRepo(CollectedRepo repo);

    boolean cancelRepo(CollectedRepo repo);

    boolean cancelRepo(Long id);

}
