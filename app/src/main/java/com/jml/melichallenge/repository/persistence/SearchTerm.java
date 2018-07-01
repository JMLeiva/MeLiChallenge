package com.jml.melichallenge.repository.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "search_terms")
public class SearchTerm {

	public SearchTerm()
	{

	}

	@PrimaryKey
	@NonNull
	@ColumnInfo(name = "term")
	public String term;

	public SearchTerm(@NonNull String word) {this.term = word;}

	@NonNull
	public String getTerm(){return this.term;}
}
