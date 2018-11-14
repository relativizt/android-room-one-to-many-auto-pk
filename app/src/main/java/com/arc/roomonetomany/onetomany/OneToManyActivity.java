package com.arc.roomonetomany.onetomany;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.arc.roomonetomany.R;
import com.arc.roomonetomany.datasource.EmployeeDatasource;
import com.arc.roomonetomany.datasource.EmployeeEntity;
import com.arc.roomonetomany.datasource.EmployeeRepository;

import java.util.Arrays;
import java.util.List;

public class OneToManyActivity extends AppCompatActivity {
    private static final String TAG = "OneToManyActivity";
    private static final String CURRENT_COMPANY_NAME_KEY = "CURRENT_COMPANY_KEY";

    private OneToManyContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onetomany_act);

        FragmentManager fragmentManager = getSupportFragmentManager();
        OneToManyFragment oneToManyFragment =
                (OneToManyFragment) fragmentManager.findFragmentById(R.id.one_to_many_frame);

        if (oneToManyFragment == null) {
            oneToManyFragment = new OneToManyFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.one_to_many_frame, oneToManyFragment)
                    .commit();
        }

        mPresenter = new OneToManyPresenter(
                EmployeeRepository.getInstance(getApplicationContext()), oneToManyFragment);

        if (savedInstanceState != null) {
            String currentCompanyName = savedInstanceState.getString(CURRENT_COMPANY_NAME_KEY);
            if (currentCompanyName != null) {
                mPresenter.setCurrentCompanyName(currentCompanyName);
            } else {
                Log.d(TAG, "null is saved in Bundle");
                throw new IllegalStateException("null is saved in Bundle");
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(CURRENT_COMPANY_NAME_KEY, mPresenter.getCurrentCompanyName());
        super.onSaveInstanceState(outState);
    }
}
