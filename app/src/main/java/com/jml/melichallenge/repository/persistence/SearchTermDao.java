package com.jml.melichallenge.repository.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SearchTermDao
{
	@Insert
	void insert(SearchTerm term);

	@Query("SELECT * from search_terms WHERE term LIKE :qStr ORDER BY term ASC")
	LiveData<List<SearchTerm>> findLike(String qStr);

	//TESTING PUPOSES
	@Query("SELECT * from search_terms ORDER BY term ASC")
	LiveData<List<SearchTerm>> getAll();
}
