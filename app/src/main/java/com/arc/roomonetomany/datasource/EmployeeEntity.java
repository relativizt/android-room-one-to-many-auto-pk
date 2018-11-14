package com.arc.roomonetomany.datasource;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by user on 31.10.2018.
 */

@Entity(tableName = "employee_list",
        foreignKeys = @ForeignKey(
                entity = CompanyEntity.class,
                parentColumns = "id",
                childColumns = "company_id",
                onDelete = CASCADE),
        indices = @Index("company_id"))
public class EmployeeEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "company_id")
    private long mCompanyId;
    @NonNull
    @ColumnInfo(name = "name")
    private final String mName;

    public EmployeeEntity(@NonNull String name) {
        mName = name;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public long getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(long companyId) {
        mCompanyId = companyId;
    }

}
