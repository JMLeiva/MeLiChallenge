package com.jml.melichallenge.view.gallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jml.melichallenge.R;
import com.jml.melichallenge.model.Picture;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryFragment extends Fragment
{
	public final static String PICTURES_EXTRA = "PICTURES_EXTRA";
	public final static String INDEX_EXTRA = "INDEX_EXTRA";

	@BindView(R.id.vp_gallery)
	ViewPager vp_gallery;

	private List<Picture> pictures;
	private int initialIndex = 0;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);


		if(getArguments() != null)
		{
			pictures = getArguments().getParcelableArrayList(PICTURES_EXTRA);
			initialIndex = getArguments().getInt(INDEX_EXTRA, 0);
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View view = inflater.inflate(R.layout.gallery_fragment, container, false);


		ButterKnife.bind(this, view);

		GalleryAdapter adapter = new GalleryAdapter(getFragmentManager(), pictures);
		vp_gallery.setAdapter(adapter);

		if(initialIndex < adapter.getCount())
		{
			vp_gallery.setCurrentItem(initialIndex);
		}

		return view;
	}
}
