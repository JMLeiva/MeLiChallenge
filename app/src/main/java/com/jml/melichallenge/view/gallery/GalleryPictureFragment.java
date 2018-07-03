package com.jml.melichallenge.view.gallery;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jml.melichallenge.R;
import com.jml.melichallenge.model.Picture;
import butterknife.BindView;

public abstract class GalleryPictureFragment extends Fragment implements RequestListener<Drawable>
{
	public final static String PICTURE_EXTRA = "PICTURE_EXTRA";

	@BindView(R.id.pb_loading)
	ProgressBar progressBar;


	Picture picture;

	GalleryAdapter.OnItemClickListener onItemClickListener;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if(getArguments() != null)
		{
			this.picture = getArguments().getParcelable(PICTURE_EXTRA);
		}
	}

	public void setOnItemClickListener(GalleryAdapter.OnItemClickListener listener)
	{
		this.onItemClickListener = listener;
	}

	@Override
	public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource)
	{
		progressBar.setVisibility(View.GONE);
		return false;
	}

	@Override
	public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource)
	{
		progressBar.setVisibility(View.GONE);
		return false;
	}
}