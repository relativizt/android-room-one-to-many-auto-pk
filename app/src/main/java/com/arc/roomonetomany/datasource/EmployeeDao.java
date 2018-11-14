package com.arc.roomonetomany.datasource;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by user on 31.10.2018.
 */

@Dao
public abstract class EmployeeDao {

    @Query("SELECT * FROM companies")
    public abstract List<CompanyEntity> selectAllCompanies();

    @Transaction
    @Query("SELECT * FROM companies WHERE name LIKE :companyName")
    public abstract List<CompanyEmployees> getEmployeesByCompanyName(String companyName);

    @Transaction
    public void insert(CompanyEntity companyEntity, List<EmployeeEntity> employeeEntities) {

        // Save rowId of inserted CompanyEntity as companyId
        final long companyId = insert(companyEntity);

        // Set companyId for all related employeeEntities
        for (EmployeeEntity employeeEntity : employeeEntities) {
            employeeEntity.setCompanyId(companyId);
            insert(employeeEntity);
        }

    }

    // If the @Insert method receives only 1 parameter, it can return a long,
    // which is the new rowId for the inserted item.
    // https://developer.android.com/training/data-storage/room/accessing-data
    @Insert(onConflict = REPLACE)
    public abstract long insert(CompanyEntity company);

    @Insert
    public abstract void insert(EmployeeEntity employee);

}
