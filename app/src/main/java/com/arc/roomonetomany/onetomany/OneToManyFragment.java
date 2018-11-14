package com.arc.roomonetomany.onetomany;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arc.roomonetomany.R;

/**
 * Created by user on 02.11.2018.
 */

public class OneToManyFragment extends Fragment implements OneToManyContract.View {

    private OneToManyContract.Presenter mPresenter;

    private EditText mCompanyName;
    private EditText mCompanyEmployeesList;
    private Button mSearchButton;
    private Button mInsertButton;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.onetomany_frag, container, false);

        // Set-up UI elements
        mCompanyName = rootView.findViewById(R.id.company_name);
        mCompanyEmployeesList = rootView.findViewById(R.id.company_employees_list);
        mSearchButton = rootView.findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.searchCompanyEmployeesList(getStringFromEditable(mCompanyName.getText()));
            }
        });
        mInsertButton = rootView.findViewById(R.id.insert_button);
        mInsertButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.insertCompanyEmployeesList(
                        getStringFromEditable(mCompanyName.getText()),
                        getStringFromEditable(mCompanyEmployeesList.getText())
                );
            }
        });

        return rootView;
    }

    @Override
    public void setPresenter(@NonNull OneToManyContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showCompanyName(@NonNull String companyName) {
        mCompanyName.setText(companyName);
    }

    @Override
    public void showCompanyEmployeesList(@NonNull String employeesList) {
        mCompanyEmployeesList.setText(employeesList);
    }

    @Override
    public void showCompanyNotFoundToast(@NonNull String companyName) {
        String companyNotFoundText = getString(R.string.company_not_found, companyName);
        showToast(companyNotFoundText);
    }

    @Override
    public void showInsertCompanyToast(@NonNull String companyName) {
        String insertionCompanyText = getString(R.string.insert_company_text, companyName);
        showToast(insertionCompanyText);
    }

    @Override
    public void showEmptyDataToast() {
        showToast(getString(R.string.empty_data));
    }

    private String getStringFromEditable(Editable editable) {
        return editable.toString();
    }

    private void showToast(@NonNull String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
