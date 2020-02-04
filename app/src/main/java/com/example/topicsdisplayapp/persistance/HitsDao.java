package com.example.topicsdisplayapp.persistance;

import com.example.topicsdisplayapp.models.Hits;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface HitsDao {

    @Insert(onConflict = IGNORE)
    long[] insertHits(Hits... hit);

    @Query("UPDATE hits SET title = :title, created_at = :created_at, isSelected = :isSelected WHERE objectID = :objectID")
    void update(String objectID, String title, String created_at, boolean isSelected);

    @Query("SELECT * FROM hits LIMIT(:page * 20)")
    LiveData<List<Hits>> getList(int page);

}
