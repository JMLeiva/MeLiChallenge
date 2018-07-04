package com.jml.melichallenge.view.gallery;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.jml.melichallenge.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 *	Fragment responsible for showing a single zoomable - paneable picture
 * */
public class GalleryZoomPictureFragment extends GalleryPictureFragment
{
	@BindView(R.id.iv_picture)
	BigImageView iv_picture;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.gallery_zoom_picture_item_layout, container, false);
		ButterKnife.bind(this, v);

		iv_picture.getSSIV().setMaxScale(10);

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