package com.arc.roomonetomany.onetomany;

import android.support.annotation.NonNull;

import com.arc.roomonetomany.datasource.EmployeeDatasource;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 02.11.2018.
 */

public class OneToManyPresenter implements OneToManyContract.Presenter {
    private static final String DATA_NOT_AVAILABLE_STRING = "data isn't available";

    private String mCurrentCompanyName;
    private OneToManyContract.View mView;
    private EmployeeDatasource mEmployeeRepository;

    public OneToManyPresenter(@NonNull EmployeeDatasource employeeRepository,
                              @NonNull OneToManyContract.View view) {
        mView = view;
        mEmployeeRepository = employeeRepository;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        // handle screen rotation
        if (mCurrentCompanyName == null) {
            return;
        }
        mView.showCompanyName(mCurrentCompanyName);
        searchCompanyEmployeesList(mCurrentCompanyName);
    }

    @Override
    public void setCurrentCompanyName(@NonNull String companyName) {
        setCompanyName(companyName);
    }

    @Override
    public String getCurrentCompanyName() {
        return mCurrentCompanyName;
    }

    @Override
    public void insertCompanyEmployeesList(@NonNull String companyName,
                                           @NonNull String employeesList) {
        // Don't handle empty Strings
        if (companyName.isEmpty() || employeesList.isEmpty()) {
            mView.showEmptyDataToast();
            return;
        }
        // Remove all whitespace from string
        String filteredEmployeesList = employeesList.replaceAll("\\s", "");
        // Assume names are separated by ',' char
        String[] employeeNames = filteredEmployeesList.split(",");
        mEmployeeRepository.saveEmployees(companyName, Arrays.asList(employeeNames));
        mView.showInsertCompanyToast(companyName);
        setCompanyName(companyName);

    }

    @Override
    public void searchCompanyEmployeesList(@NonNull String companyName) {

        // Skip empty company name
        if (companyName.isEmpty()) {
            return;
        }

        mEmployeeRepository.getEmployees(companyName,
                new EmployeeDatasource.LoadEmployeesCallback() {
            @Override
            public void onEmployeesLoaded(List<String> employees) {
                // Create formatted String with names from given List
                StringBuilder builder = new StringBuilder();
                int lastNameIndex = employees.size() - 1;
                for (int i = 0; i < lastNameIndex; i++) {
                    builder.append(employees.get(i)).append(",\n");
                }
                // Custom handler for last name in list
                builder.append(employees.get(lastNameIndex));

                mView.showCompanyEmployeesList(builder.toString());

            }

            @Override
            public void onNoCompanyFound(String companyName) {
                // Show message
                mView.showCompanyNotFoundToast(companyName);
                // Clear employees list
                mView.showCompanyEmployeesList("");
            }

        });
        setCompanyName(companyName);
    }

    private void setCompanyName(@NonNull String companyName) {
        mCurrentCompanyName = companyName;
    }
}
