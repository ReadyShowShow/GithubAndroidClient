package com.jian.github.db.convert;

import com.jian.github.bean.localbean.CollectedRepoOwner;
import com.jian.github.App;

import org.greenrobot.greendao.converter.PropertyConverter;

public class CollectedRepoOwnerConvert implements PropertyConverter<CollectedRepoOwner, String> {
    @Override
    public CollectedRepoOwner convertToEntityProperty(String databaseValue) {
        return App.getApp().getGson().fromJson(databaseValue, CollectedRepoOwner.class);
    }

    @Override
    public String convertToDatabaseValue(CollectedRepoOwner entityProperty) {
        return App.getApp().getGson().toJson(entityProperty);
    }
}
