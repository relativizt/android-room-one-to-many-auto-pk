package com.arc.roomonetomany.datasource;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by user on 31.10.2018.
 */

@Entity(tableName = "companies", indices = @Index(value = "name", unique = true))
public class CompanyEntity {
    @PrimaryKey (autoGenerate = true)
    public int id;
    @NonNull
    @ColumnInfo(name = "name")
    private final String mCompanyName;

    public CompanyEntity(@NonNull String companyName) {
        mCompanyName = companyName;
    }

    @NonNull
    public String getCompanyName() {
        return mCompanyName;
    }

}

