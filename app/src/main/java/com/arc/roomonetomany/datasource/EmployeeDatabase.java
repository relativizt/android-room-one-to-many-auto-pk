package com.arc.roomonetomany.datasource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by user on 31.10.2018.
 */

@Database(entities = {CompanyEntity.class, EmployeeEntity.class}, version = 1, exportSchema = false)
public abstract class EmployeeDatabase extends RoomDatabase {

    private static final String DB_NAME = "Employees.db";

    private static volatile EmployeeDatabase INSTANCE;

    private static final Object sLock = new Object();

    public static EmployeeDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EmployeeDatabase.class, DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract EmployeeDao employeeDao();

}
