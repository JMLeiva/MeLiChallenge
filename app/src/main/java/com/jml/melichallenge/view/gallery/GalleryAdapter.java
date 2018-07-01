package com.jml.melichallenge.view.gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.jml.melichallenge.model.Picture;

import java.util.List;

public class GalleryAdapter extends FragmentStatePagerAdapter
{

	public interface OnItemClickListener
	{
		void onItemClick();
	}

	private OnItemClickListener listener;
	private List<Picture> pictures;

	public GalleryAdapter(FragmentManager fm, List<Picture> pictures)
	{
		this(fm, pictures, null);
	}

	public GalleryAdapter(FragmentManager fm, List<Picture> pictures, OnItemClickListener listener)
	{
		super(fm);
		this.listener = listener;
		this.pictures = pictures;
	}

	@Override
	public int getCount()
	{
		if (!hasPictures())
		{
			return 1;
		}

		return pictures.size();
	}

	@Override
	public Fragment getItem(int position)
	{
		if(hasPictures())
		{
			return getImageFragment(pictures.get(position));
		}
		else
		{
			return getImageFragment(null);
		}
	}

	private boolean hasPictures()
	{
		return pictures != null && !pictures.isEmpty();
	}

	private Fragment getImageFragment(Picture picture)
	{
		Bundle b = new Bundle();
		b.putParcelable(GalleryPictureFragment.PICTURE_EXTRA, picture);


		GalleryPictureFragment fragment = getPictureFragment();

		fragment.setArguments(b);
		fragment.setOnItemClickListener(listener);

		return fragment;
	}

	protected GalleryPictureFragment getPictureFragment()
	{
		return new GalleryZoomPictureFragment();
	}

}
