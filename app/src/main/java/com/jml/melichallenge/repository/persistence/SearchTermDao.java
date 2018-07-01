package com.jml.melichallenge.repository.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SearchTermDao
{
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	void insert(SearchTerm term);

	@Query("SELECT * FROM search_terms WHERE term LIKE :qStr")
	LiveData<List<SearchTerm>> findLike(String qStr);

	//TESTING PUPOSES
	@Query("SELECT * FROM search_terms ORDER BY term ASC")
	LiveData<List<SearchTerm>> getAll();
}
