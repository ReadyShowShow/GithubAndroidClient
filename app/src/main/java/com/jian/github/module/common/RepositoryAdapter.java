package com.jian.github.module.common;

import android.content.Context;
import android.widget.ImageView;

import com.jian.github.bean.apibean.Repository;
import com.jian.github.utils.ImageLoader;
import com.jian.github.view.CornerMarkText;
import com.ouyangzn.github.R;
import com.ouyangzn.recyclerview.BaseRecyclerViewAdapter;
import com.ouyangzn.recyclerview.BaseViewHolder;

import java.util.List;

public class RepositoryAdapter extends BaseRecyclerViewAdapter<Repository> {

    private Context mContext;
    private boolean mLanguageVisible = false;

    public RepositoryAdapter(Context context, List<Repository> data) {
        super(R.layout.item_repo, data);
        this.mContext = context;
    }

    public void setLanguageVisible(boolean languageVisible) {
        mLanguageVisible = languageVisible;
    }

    @Override
    protected void convert(BaseViewHolder holder, Repository item) {
        holder.setText(R.id.tv_project_name, item.getFullName());
        holder.setText(R.id.tv_project_desc, item.getDescription());
        holder.setText(R.id.tv_author, item.getOwner().getAuthorName());
        holder.setText(R.id.tv_stars, mContext.getString(R.string.stars, item.getStargazersCount()));
        ImageView photo = (ImageView) holder.getConvertView().findViewById(R.id.img_author_photo);
        ImageLoader.load(photo, item.getOwner().getAvatarUrl());
        holder.setVisible(R.id.tv_language, mLanguageVisible);
        if (mLanguageVisible) {
            CornerMarkText text = (CornerMarkText) holder.getConvertView().findViewById(R.id.tv_language);
            text.setText(item.getLanguage());
        }
    }
}
