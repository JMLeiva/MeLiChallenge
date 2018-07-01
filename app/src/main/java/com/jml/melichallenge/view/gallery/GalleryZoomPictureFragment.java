package com.jml.melichallenge.view.gallery;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.jml.melichallenge.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryZoomPictureFragment extends GalleryPictureFragment
{
	public final static String PICTURE_EXTRA = "PICTURE_EXTRA";

	@BindView(R.id.iv_picture)
	BigImageView iv_picture;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.gallery_zoom_picture_item_layout, container, false);
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

	private void loadNoPhoto()
	{
		progressBar.setVisibility(View.GONE);
		iv_picture.showImage(Uri.parse(""));
	}

	private void loadImage()
	{
		iv_picture.setImageLoaderCallback(new ImageLoader.Callback()
		{
			@Override
			public void onCacheHit(File image)
			{
				//iv_placeHolder.setVisibility(View.GONE);
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onCacheMiss(File image)
			{

			}

			@Override
			public void onStart()
			{

			}

			@Override
			public void onProgress(int progress)
			{

			}

			@Override
			public void onFinish()
			{

			}

			@Override
			public void onSuccess(File image)
			{
				//iv_placeHolder.setVisibility(View.GONE);
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onFail(Exception error)
			{

			}
		});

		iv_picture.showImage(Uri.parse(picture.getUrl()));
	}
}