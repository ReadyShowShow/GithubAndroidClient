package com.jian.github.module.common;

import android.content.Context;
import android.widget.ImageView;

import com.jian.github.bean.localbean.CollectedRepo;
import com.jian.github.bean.localbean.CollectedRepoOwner;
import com.jian.github.utils.ImageLoader;
import com.jian.github.view.CornerMarkText;
import com.jian.github.R;
import com.ouyangzn.recyclerview.BaseRecyclerViewAdapter;
import com.ouyangzn.recyclerview.BaseViewHolder;

import java.util.List;

public class CollectAdapter extends BaseRecyclerViewAdapter<CollectedRepo> {

    private Context mContext;

    public CollectAdapter(Context context, List<CollectedRepo> data) {
        super(R.layout.item_repo, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, CollectedRepo repo) {
        holder.setText(R.id.tv_project_name, repo.getFullName());
        holder.setText(R.id.tv_project_desc, repo.getDescription());
        CollectedRepoOwner owner = repo.getOwner();
        holder.setText(R.id.tv_author, owner.getLogin());
        holder.setText(R.id.tv_stars, mContext.getString(R.string.stars, repo.getStargazersCount()));
        ImageView photo = (ImageView) holder.getConvertView().findViewById(R.id.img_author_photo);
        ImageLoader.load(photo, owner.getAvatarUrl());
        holder.setVisible(R.id.tv_language, true);
        CornerMarkText text = (CornerMarkText) holder.getConvertView().findViewById(R.id.tv_language);
        text.setText(repo.getLanguage());
    }
}
