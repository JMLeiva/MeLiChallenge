package com.jml.melichallenge.view.gallery;

import android.support.v4.app.FragmentManager;

import com.jml.melichallenge.model.Picture;

import java.util.List;

/*
 *	Fragment responsible for showing a single a gallery of zoomable pictures
 * */
public class GalleryZoomAdapter extends GalleryAdapter
{

	public GalleryZoomAdapter(FragmentManager fm, List<Picture> pictures)
	{
		super(fm, pictures);
	}

	public GalleryZoomAdapter(FragmentManager fm, List<Picture> pictures, OnItemClickListener listener)
	{
		super(fm, pictures, listener);
	}

	@Override
	protected GalleryPictureFragment getPictureFragment()
	{
		return new GalleryZoomPictureFragment();
	}

}
