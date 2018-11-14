package com.arc.roomonetomany.onetomany;

import android.support.annotation.NonNull;

import com.arc.roomonetomany.datasource.EmployeeEntity;

import java.util.List;

/**
 * Created by user on 02.11.2018.
 */

public interface OneToManyContract {
    interface View {

        void setPresenter(Presenter presenter);

        void showCompanyName(@NonNull String companyName);

        void showCompanyEmployeesList(@NonNull String employeesList);

        void showCompanyNotFoundToast(@NonNull String companyName);

        void showInsertCompanyToast(@NonNull String companyName);

        void showEmptyDataToast();

    }

    interface Presenter {

        void start();

        void insertCompanyEmployeesList(@NonNull String companyName, @NonNull String employeesList);

        void searchCompanyEmployeesList(@NonNull String companyName);

        void setCurrentCompanyName(@NonNull String companyName);

        String getCurrentCompanyName();

    }
}
