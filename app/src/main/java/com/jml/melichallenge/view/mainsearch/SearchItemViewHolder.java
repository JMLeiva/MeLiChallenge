package com.jml.melichallenge.view.mainsearch;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jml.melichallenge.GlideApp;
import com.jml.melichallenge.R;
import com.jml.melichallenge.model.Item;
import com.jml.melichallenge.view.common.RecyclerViewClickListener;
import com.jmleiva.pagedrecyclerview.PagedViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchItemViewHolder extends PagedViewHolder implements View.OnClickListener
{
	@BindView(R.id.tv_title)
	TextView tv_name;

	@BindView(R.id.tv_price)
	TextView tv_price;

	@BindView(R.id.tv_originalPrice)
	TextView tv_originalPrice;

	@BindView(R.id.tv_discount)
	TextView tv_discount;

	@BindView(R.id.iv_thumbnail)
	ImageView iv_thumbnail;

	@BindView(R.id.rb_rating)
	RatingBar rb_rating;

	@BindView(R.id.tv_ratingCount)
	TextView tv_ratingCount;

	private RecyclerViewClickListener listener;

	public SearchItemViewHolder(View itemView, RecyclerViewClickListener listener)
	{
		super(itemView);
		ButterKnife.bind(this, itemView);

		this.listener = listener;
		itemView.setOnClickListener(this);
	}

	public void setup(Context context, Item item)
	{
		this.tv_name.setText(item.getTitle());

		if(item.hasDiscount())
		{
			tv_originalPrice.setVisibility(View.VISIBLE);
			tv_originalPrice.setText(Item.FormatHelper.formatPrice(context, item.getCurrencyId(), item.getOriginalPrice()));
			tv_originalPrice.setPaintFlags(tv_originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

			tv_discount.setVisibility(View.VISIBLE);
			tv_discount.setText(Item.FormatHelper.formatDiscount(context, item.getDiscount()));
		}
		else
		{
			tv_originalPrice.setVisibility(View.GONE);
			tv_discount.setVisibility(View.GONE);
		}

		if(item.hasEnoughtReviews())
		{
			rb_rating.setVisibility(View.VISIBLE);
			rb_rating.setRating(item.getReviews().getAverage());

			tv_ratingCount.setVisibility(View.VISIBLE);
			tv_ratingCount.setText(Item.FormatHelper.formatReviewCount(context, item.getReviews().getTotal()));
		}
		else
		{
			rb_rating.setVisibility(View.GONE);
			tv_ratingCount.setVisibility(View.GONE);
		}

		this.tv_price.setText(Item.FormatHelper.formatPrice(context, item.getCurrencyId(), item.getPrice()));

		GlideApp.with(context)
				.load(item.getThumbnail())
				.centerCrop()
				.placeholder(R.drawable.ic_photo_96dp)
				.error(R.drawable.ic_broken_image_96dp)
				.into(iv_thumbnail);
	}

	@Override
	public void onClick(View v)
	{
		listener.onClick(v, getAdapterPosition());
	}
}
