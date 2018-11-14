package com.arc.roomonetomany.datasource;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by user on 31.10.2018.
 */

public class EmployeeRepository implements EmployeeDatasource {

    private static volatile EmployeeRepository INSTANCE;

    private EmployeeDao employeeDao;

    private Executor diskIOExecutor;

    private Executor mainThreadExecutor;

    private EmployeeRepository(@NonNull Context context) {
        employeeDao = EmployeeDatabase.getInstance(context).employeeDao();

        // Executor for database operations
        diskIOExecutor = Executors.newSingleThreadExecutor();

        // Executor for MainThread(UI update)
        mainThreadExecutor = new Executor() {
            private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(@NonNull Runnable runnable) {
                mainThreadHandler.post(runnable);
            }
        };
    }

    public static EmployeeRepository getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (EmployeeRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EmployeeRepository(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    public void getEmployees(@NonNull final String companyName,
                             @NonNull final LoadEmployeesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Gets List holding @Relation object
                List<CompanyEmployees> companyEmployeesList =
                        employeeDao.getEmployeesByCompanyName(companyName);

                final List<String> employees = new ArrayList<>();
                if (!companyEmployeesList.isEmpty()) {
                    // Room always returns List or Set when @Relation is used
                    // https://issuetracker.google.com/issues/62905145
                    // So we get first element from it
                    CompanyEmployees relationHolder = companyEmployeesList.get(0);
                    List<EmployeeEntity> employeeEntities =
                            relationHolder.mEmployees;
                    for (EmployeeEntity entity : employeeEntities) {
                        employees.add(entity.getName());
                    }
                }

                // update UI in main thread
                mainThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (employees.isEmpty()) {
                            callback.onNoCompanyFound(companyName);
                        } else {
                            callback.onEmployeesLoaded(employees);
                        }
                    }
                });
            }
        };
        diskIOExecutor.execute(runnable);

    }

    public void saveEmployees(@NonNull final String companyName,
                              @NonNull final List<String> employeesNames) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {

                List<EmployeeEntity> employeeEntities = new ArrayList<>(employeesNames.size());
                for (String name : employeesNames) {
                    employeeEntities.add(new EmployeeEntity(name));
                }

                employeeDao.insert(new CompanyEntity(companyName), employeeEntities);
            }
        };
        diskIOExecutor.execute(saveRunnable);
    }



}
