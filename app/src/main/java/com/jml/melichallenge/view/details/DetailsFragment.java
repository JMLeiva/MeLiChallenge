package com.jml.melichallenge.view.details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayout;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jml.melichallenge.R;
import com.jml.melichallenge.model.Attribute;
import com.jml.melichallenge.model.Description;
import com.jml.melichallenge.model.Item;
import com.jml.melichallenge.model.SellerAddress;
import com.jml.melichallenge.repository.ErrorWrapper;
import com.jml.melichallenge.view.common.BaseFragment;
import com.jml.melichallenge.view.description.DescriptionFragment;
import com.jml.melichallenge.view.gallery.GalleryAdapter;
import com.jml.melichallenge.view.gallery.GalleryFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static com.jml.melichallenge.view.details.DetailsActivity.ITEM_ID_EXTRA;

public class DetailsFragment extends BaseFragment
{
	@BindView(R.id.vp_gallery)
	ViewPager vp_gallery;

	@BindView(R.id.pb_gallery)
	ProgressBar pb_gallery;

	@BindView(R.id.tv_condition)
	TextView tv_condition;

	@BindView(R.id.tv_title)
	TextView tv_title;

	@BindView(R.id.tv_price)
	TextView tv_price;

	@BindView(R.id.tv_originalPrice)
	TextView tv_originalPrice;

	@BindView(R.id.tv_discount)
	TextView tv_discount;

	@BindView(R.id.rb_rating)
	RatingBar rb_rating;

	@BindView(R.id.tv_ratingCount)
	TextView tv_ratingCount;

	@BindView(R.id.tv_paymentLink)
	TextView tv_paymentLink;

	@BindView(R.id.tv_sellerLocationText)
	TextView tv_sellerLocationText;

	@BindView(R.id.include_warranty)
	View include_warranty;

	@BindView(R.id.ll_w1)
	View ll_w1;

	@BindView(R.id.ll_w2)
	View ll_w2;

	@BindView(R.id.tv_w2Description)
	TextView tv_w2Description;

	@BindView(R.id.divider_underWarranty)
	View divider_underWarranty;

	@BindView(R.id.gl_attributes)
	GridLayout gl_attributes;

	@BindView(R.id.tv_descriptionBody)
	TextView tv_descriptionBody;

	@BindView(R.id.pb_description)
	ProgressBar pb_description;

	@BindView(R.id.include_description)
	View include_description;

	@BindView(R.id.sv_main)
	View sv_main;

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	private ItemViewModel viewModel;
	private DescriptionViewModel descriptionViewModel;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		AndroidSupportInjection.inject(this);
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.details_fragment, container, false);
		ButterKnife.bind(this, view);

		setupUI();

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		viewModel = ViewModelProviders.of(this, viewModelFactory).get(ItemViewModel.class);
		descriptionViewModel = ViewModelProviders.of(this, viewModelFactory).get(DescriptionViewModel.class);
		setupObserver(viewModel);
		setupObserver(descriptionViewModel);

		if(getArguments() != null)
		{
			String itemIdExtra = getArguments().getString(ITEM_ID_EXTRA);
			viewModel.setItemId(itemIdExtra);
			descriptionViewModel.setItemId(itemIdExtra);
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

	private void setupObserver(ItemViewModel viewModel)
	{
		// Update the list when the data changes
		viewModel.getDataObservable().observe(this, new Observer<Item>()
		{
			@Override
			public void onChanged(@Nullable Item item)
			{
				if(item == null)
				{
					return;
				}

				setupUI(item);
			}
		});

		viewModel.getErrorObservable().observe(this, new Observer<ErrorWrapper>()
		{
			@Override
			public void onChanged(@Nullable ErrorWrapper errorWrapper)
			{
				if(errorWrapper == null)
				{
					return;
				}

				if(!connectionManager.isInternetConnected())
				{
					showNoConnection();
				}
				else
				{
					String errorMsg = errorWrapper.getMessage();

					if (errorMsg != null)
					{
						Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	private void setupObserver(DescriptionViewModel descriptionViewModel)
	{
		// Update the list when the data changesCannot find setter for field.
		descriptionViewModel.getDataObservable().observe(this, new Observer<Description>()
		{
			@Override
			public void onChanged(@Nullable Description description)
			{
				if(description == null)
				{
					return;
				}

				setupUI(description);
			}
		});

		descriptionViewModel.getErrorObservable().observe(this, new Observer<ErrorWrapper>()
		{
			@Override
			public void onChanged(@Nullable ErrorWrapper errorWrapper)
			{
				if(errorWrapper == null)
				{
					return;
				}

				String errorMsg = errorWrapper.getMessage();

				if(errorMsg != null)
				{
					Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	protected void onRetryNoConnection()
	{
		hideNoConnection();
		viewModel.retry();
		descriptionViewModel.retry();
	}

	@Override
	protected void showNoConnection()
	{
		super.showNoConnection();
		sv_main.setVisibility(View.GONE);
	}

	@Override
	protected void hideNoConnection()
	{
		super.hideNoConnection();
		sv_main.setVisibility(View.VISIBLE);
	}


	private void setupUI(Description description)
	{
		setupDescription(description);
	}

	private void setupUI(Item item)
	{
		setupGallery(item);
		setupMainData(item);
		setupPayment(item);
		setupSeller(item);
		setupWarranty(item);
		setupAttributes(item);
	}

	private void setupGallery(final Item item)
	{
		pb_gallery.setVisibility(View.GONE);

		GalleryAdapter.OnItemClickListener listener = new GalleryAdapter.OnItemClickListener()
		{
			@Override
			public void onItemClick()
			{
				FragmentManager fragmentManager = getFragmentManager();

				if(fragmentManager != null)
				{
					GalleryFragment galleryFragment = new GalleryFragment();
					Bundle args = new Bundle();
					args.putParcelableArrayList(GalleryFragment.PICTURES_EXTRA, new ArrayList<Parcelable>(item.getPictures()));
					args.putInt(GalleryFragment.INDEX_EXTRA, vp_gallery.getCurrentItem());
					galleryFragment.setArguments(args);

					getFragmentManager()
							.beginTransaction()
							.setReorderingAllowed(true)
							//.addSharedElement(imageView, imageView.getTransitionName())
							.replace(R.id.fragmentContainer, galleryFragment, GalleryFragment.class.getSimpleName()) //REVISAR CAMBIO DE CONFIGURACION CON ESTO ABIERTO
							.addToBackStack(null)
							.commit();
				}
			}
		};

		GalleryAdapter galleryAdapter = new GalleryAdapter(getChildFragmentManager(), item.getPictures(), listener);

		vp_gallery.setAdapter(galleryAdapter);
	}

	private void setupMainData(Item item)
	{
		tv_condition.setText(item.getCondition());
		tv_title.setText(item.getTitle());

		if (item.hasDiscount())
		{
			tv_originalPrice.setVisibility(View.VISIBLE);
			tv_originalPrice.setText(Item.FormatHelper.formatPrice(getContext(), item.getCurrencyId(), item.getOriginalPrice()));
			tv_originalPrice.setPaintFlags(tv_originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

			tv_discount.setVisibility(View.VISIBLE);

			if(getContext() != null)
			{
				tv_discount.setText(Item.FormatHelper.formatDiscount(getContext(), item.getDiscount()));
			}
		}
		else
		{
			tv_originalPrice.setVisibility(View.GONE);
			tv_discount.setVisibility(View.GONE);
		}

		if (item.hasEnoughtReviews())
		{
			rb_rating.setVisibility(View.VISIBLE);
			rb_rating.setRating(item.getReviews().getAverage());

			tv_ratingCount.setVisibility(View.VISIBLE);
			tv_ratingCount.setText(Item.FormatHelper.formatReviewCount(getContext(), item.getReviews().getTotal()));
		}
		else
		{
			rb_rating.setVisibility(View.GONE);
			tv_ratingCount.setVisibility(View.GONE);
		}
	}

	private void setupPayment(Item item)
	{
		this.tv_price.setText(Item.FormatHelper.formatPrice(getContext(), item.getCurrencyId(), item.getPrice()));

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
		{
			this.tv_paymentLink.setText(Html.fromHtml(getString(R.string.payment_learn_more), Html.FROM_HTML_MODE_COMPACT));
		}
		else
		{
			this.tv_paymentLink.setText(Html.fromHtml(getString(R.string.payment_learn_more)));
		}

		this.tv_paymentLink.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private void setupSeller(Item item)
	{
		if(getContext() != null)
		{
			tv_sellerLocationText.setText(SellerAddress.FormatHelper.format(getContext(), item.getSellerAddress()));
		}
	}

	private void setupWarranty(Item item)
	{
		if(item.hasAnyWarranty())
		{
			include_warranty.setVisibility(View.VISIBLE);
			divider_underWarranty.setVisibility(View.VISIBLE);

			if(item.acceptsMercadoPago())
			{
				ll_w1.setVisibility(View.VISIBLE);
			}
			else
			{
				ll_w1.setVisibility(View.GONE);
			}

			if(item.hasCustomWarranty())
			{
				ll_w2.setVisibility(View.VISIBLE);
				tv_w2Description.setText(item.getCustomWarranty());
			}
			else
			{
				ll_w2.setVisibility(View.GONE);
			}
		}
		else
		{
			include_warranty.setVisibility(View.GONE);
			divider_underWarranty.setVisibility(View.GONE);
		}
	}

	private void setupDescription(Description description)
	{
		pb_description.setVisibility(View.GONE);
		tv_descriptionBody.setText(description.getText());

		include_description.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LiveData<Item> itemliveData = viewModel.getDataObservable();

				if(itemliveData != null && itemliveData.getValue() != null)
				{
					FragmentManager fragmentManager = getFragmentManager();

					if (fragmentManager != null)
					{
						DescriptionFragment galleryFragment = new DescriptionFragment();
						Bundle args = new Bundle();
						args.putString(ITEM_ID_EXTRA, itemliveData.getValue().getId());
						galleryFragment.setArguments(args);

						getFragmentManager()
								.beginTransaction()
								.setReorderingAllowed(true)
								//.addSharedElement(imageView, imageView.getTransitionName())
								.replace(R.id.fragmentContainer, galleryFragment, DescriptionFragment.class.getSimpleName())
								.addToBackStack(null)
								.commit();
					}
				}
			}
		});
	}

	private void setupAttributes(Item item)
	{
		gl_attributes.removeAllViews();

		LayoutInflater layoutInflater = LayoutInflater.from(getContext());

		int total = item.getAttributes().size();
		int column = 2;
		int row = total / column;
		gl_attributes.setColumnCount(column);
		gl_attributes.setRowCount(row + 1);

		for(Attribute attribute : item.getAttributes())
		{
			View view = layoutInflater.inflate(R.layout.attribute_cell, gl_attributes, false);

			TextView attName = view.findViewById(R.id.tv_attributeName);
			TextView attValue = view.findViewById(R.id.tv_attributeValue);

			attName.setText(attribute.getName());
			attValue.setText(attribute.getValue());

			//GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(rowSpan, colspan);

			GridLayout.Spec spec = GridLayout.spec(GridLayout.UNDEFINED, 1.0f);
			GridLayout.LayoutParams lp = new GridLayout.LayoutParams(new ViewGroup.MarginLayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT));
			lp.columnSpec = spec;

			gl_attributes.addView(view, lp);
		}
	}
}
