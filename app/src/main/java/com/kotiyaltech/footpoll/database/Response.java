package com.kotiyaltech.footpoll.database;

import com.google.firebase.database.DatabaseError;

/**
 * Created by hp pc on 02-04-2018.
 */

public class Response {
    private boolean success;
    private DatabaseError databaseError;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DatabaseError getDatabaseError() {
        return databaseError;
    }

    public void setDatabaseError(DatabaseError databaseError) {
        this.databaseError = databaseError;
    }
}
