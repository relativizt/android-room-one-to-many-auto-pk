package com.arc.roomonetomany.datasource;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by user on 31.10.2018.
 */

public interface EmployeeDatasource {

    //interface callback for Name list
    interface LoadEmployeesCallback {

        void onEmployeesLoaded(List<String> employees);

        void onNoCompanyFound(String companyName);

    }

    void saveEmployees(@NonNull String companyName, @NonNull List<String> employeeNames);

    void getEmployees(@NonNull String companyName, @NonNull LoadEmployeesCallback callback);
}
