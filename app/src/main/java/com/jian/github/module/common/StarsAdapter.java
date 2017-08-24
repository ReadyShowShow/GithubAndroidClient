package com.jian.github.module.common;

import android.widget.ImageView;

import com.jian.github.bean.apibean.Repository;
import com.jian.github.bean.apibean.User;
import com.jian.github.utils.ImageLoader;
import com.jian.github.view.CornerMarkText;
import com.ouyangzn.github.R;
import com.ouyangzn.recyclerview.BaseRecyclerViewAdapter;
import com.ouyangzn.recyclerview.BaseViewHolder;

import java.util.List;

public class StarsAdapter extends BaseRecyclerViewAdapter<Repository> {

    public StarsAdapter(List<Repository> data) {
        super(R.layout.item_repo, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Repository repo) {
        holder.setText(R.id.tv_project_name, repo.getFullName());
        holder.setText(R.id.tv_project_desc, repo.getDescription());
        User owner = repo.getOwner();
        holder.setText(R.id.tv_author, owner.getAuthorName());
        holder.setText(R.id.tv_stars, mContext.getString(R.string.stars, repo.getStargazersCount()));
        ImageView photo = (ImageView) holder.getConvertView().findViewById(R.id.img_author_photo);
        ImageLoader.load(photo, owner.getAvatarUrl());
        holder.setVisible(R.id.tv_language, true);
        CornerMarkText text = (CornerMarkText) holder.getConvertView().findViewById(R.id.tv_language);
        text.setText(repo.getLanguage());
    }
}
