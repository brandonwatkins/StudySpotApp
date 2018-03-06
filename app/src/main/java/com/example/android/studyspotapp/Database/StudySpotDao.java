package com.example.android.studyspotapp.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Brandon Watkins on 27/2/18.
 */

/*
@Dao
public interface StudySpotDao {

    @Query("SELECT * FROM Debt ORDER BY time")
    LiveData<List<Debt>> getAllDebts();

    @Query("SELECT * FROM Debt WHERE theyOweMe!=0 ORDER BY time")
    LiveData<List<Debt>> getAllTheirDebts();

    @Query("SELECT * FROM Debt WHERE theyOweMe=0 ORDER BY time")
    LiveData<List<Debt>> getAllMyDebts();

    @Insert (onConflict = REPLACE)
    void addDebt(Debt debt);

    @Update
    void updateDebt(Debt debt);

    @Query ("SELECT * FROM Debt WHERE id = :id")
    Debt getDebtById(long id);

    @Delete
    void deleteDebt(Debt debt);

}
*/