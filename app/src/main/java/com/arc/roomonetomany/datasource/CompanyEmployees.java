package com.arc.roomonetomany.datasource;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 31.10.2018.
 */

public class CompanyEmployees {
    @Embedded
    public CompanyEntity mCompany;

    @Relation (parentColumn = "id", entityColumn = "company_id", entity = EmployeeEntity.class)
    public List<EmployeeEntity> mEmployees;

}

