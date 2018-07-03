package com.jml.melichallenge.view.gallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jml.melichallenge.GlideApp;
import com.jml.melichallenge.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryNoZoomPictureFragment extends GalleryPictureFragment
{
	@BindView(R.id.iv_picture)
	ImageView iv_picture;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.gallery_no_zoom_picture_item_layout, container, false);
		ButterKnife.bind(this, v);


		if(this.picture == null)
		{
			loadNoPhoto();
		}
		else
		{
			loadImage();
		}

		iv_picture.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(onItemClickListener != null)
				{
					onItemClickListener.onItemClick();
				}
			}
		});

		return v;
	}

	private void loadImage()
	{
		if(getContext() != null)
		{
			GlideApp.with(getContext()).load(picture.getUrl())
					.centerInside()
					.placeholder(R.drawable.ic_photo_96dp)
					.error(R.drawable.ic_broken_image_96dp)
					.listener(this)
					.into(iv_picture);
		}
	}

	private void loadNoPhoto()
	{
		if(getContext() != null)
		{
			GlideApp.with(getContext()).load(R.drawable.ic_photo_96dp).centerInside().into(iv_picture);
		}
	}
}