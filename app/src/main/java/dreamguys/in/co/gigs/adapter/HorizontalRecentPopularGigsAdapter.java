package dreamguys.in.co.gigs.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import java.util.HashMap;
import java.util.List;

import dreamguys.in.co.gigs.DetailGigs;
import dreamguys.in.co.gigs.FavouriteAnimationLib.LikeButton;
import dreamguys.in.co.gigs.FavouriteAnimationLib.OnLikeListener;
import dreamguys.in.co.gigs.Model.GETHomeGigs;
import dreamguys.in.co.gigs.Model.POSTAddFav;
import dreamguys.in.co.gigs.Model.POSTLanguageModel;
import dreamguys.in.co.gigs.Model.POSTRemoveFav;
import dreamguys.in.co.gigs.R;
import dreamguys.in.co.gigs.network.ApiClient;
import dreamguys.in.co.gigs.network.ApiInterface;
import dreamguys.in.co.gigs.receiver.NetworkChangeReceiver;
import dreamguys.in.co.gigs.utils.AppConstants;
import dreamguys.in.co.gigs.utils.SessionHandler;
import dreamguys.in.co.gigs.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prasad on 10/25/2017.
 */

public class HorizontalRecentPopularGigsAdapter extends RecyclerView.Adapter<HorizontalRecentPopularGigsAdapter.MyViewHolder> {
    private final Context mContext;
    private final List<GETHomeGigs.Recent_gigs_list> recent_popular_gigs_list;
    private HashMap<String, String> postDetails;
    public POSTLanguageModel.Common_strings commonStrings = new POSTLanguageModel().new Common_strings();

    public HorizontalRecentPopularGigsAdapter(Context mContext, List<GETHomeGigs.Recent_gigs_list> recent_popular_gigs_list) {
        this.mContext = mContext;
        this.recent_popular_gigs_list = recent_popular_gigs_list;
        postDetails = new HashMap<String, String>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_popular_gigs_item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        setLanguageValues();
        holder.gigsTitle.setText(recent_popular_gigs_list.get(position).getTitle());
        holder.gigsAuthor.setText(recent_popular_gigs_list.get(position).getFullname());
        holder.gigsRate.setText(AppConstants.DOLLAR_SIGN + recent_popular_gigs_list.get(position).getGig_price());
        holder.gigsLocation.setText(recent_popular_gigs_list.get(position).getCountry() + "," + recent_popular_gigs_list.get(position).getState_name());
        Picasso.with(mContext).load(AppConstants.BASE_URL + recent_popular_gigs_list.get(position).getImage()).placeholder(R.drawable.no_image).into(holder.gigsImages);
        //holder.gigsImages.setImageURI(Uri.parse(AppConstants.BASE_URL + recent_popular_gigs_list.get(position).getImage()));
        holder.gigsRating.setText(recent_popular_gigs_list.get(position).getGig_rating());
        holder.gigsReviewCount.setText("(" + recent_popular_gigs_list.get(position).getGig_usercount() + ")");
        if (SessionHandler.getInstance().get(mContext, AppConstants.TOKEN_ID) != null) {
            if (recent_popular_gigs_list.get(position).getFavourite().equalsIgnoreCase("1")) {
                holder.isSelected = true;
//                holder.favGigsIcons.setVisibility(View.VISIBLE);
//                holder.favGigsIcons.setImageTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.colorPrimary)));
//                holder.favGigsIcons.setImageResource(R.drawable.ic_favorite_filled_24dp);

            } else {
                holder.isSelected = false;
//                holder.favGigsIcons.setVisibility(View.VISIBLE);
//                holder.favGigsIcons.setImageTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.colorPrimary)));
//                holder.favGigsIcons.setImageResource(R.drawable.ic_favorite_border_purple_24dp);
            }


            holder.favGigsIcons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkChangeReceiver.isConnected()) {
                        postDetails.put("gig_id", recent_popular_gigs_list.get(position).getId());
                        postDetails.put("user_id", SessionHandler.getInstance().get(mContext, AppConstants.TOKEN_ID));
                        if (holder.isSelected) {
                            holder.isSelected = false;
                            removeFavAPI(holder, position);
                        } else {
                            holder.isSelected = true;
                            addFavAPI(holder, position);
                        }
                    } else {
                        Utils.toastMessage(mContext, commonStrings.getLbl_enable_internet().getName());
                    }
                }
            });


            holder.likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    postDetails.put("gig_id", recent_popular_gigs_list.get(position).getId());
                    postDetails.put("user_id", SessionHandler.getInstance().get(mContext, AppConstants.TOKEN_ID));
                    holder.isSelected = true;
                    addFavAPI(holder, position);
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    postDetails.put("gig_id", recent_popular_gigs_list.get(position).getId());
                    postDetails.put("user_id", SessionHandler.getInstance().get(mContext, AppConstants.TOKEN_ID));
                    holder.isSelected = false;
                    removeFavAPI(holder, position);
                }
            });


        } else {
            holder.favGigsIcons.setVisibility(View.GONE);
        }
    }

    private void removeFavAPI(final MyViewHolder holder, int position) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.removeFav(postDetails,SessionHandler.getInstance().get(mContext, AppConstants.TOKEN_ID), SessionHandler.getInstance().get(mContext, AppConstants.Language)).enqueue(new Callback<POSTRemoveFav>() {
            @Override
            public void onResponse(Call<POSTRemoveFav> call, Response<POSTRemoveFav> response) {
                if (response.body().getCode().equals(200)) {
//                    Utils.toastMessage(mContext, response.body().getMessage());
                    holder.favGigsIcons.setImageTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.colorPrimary)));
                    holder.favGigsIcons.setImageResource(R.drawable.ic_favorite_border_purple_24dp);
                } else {
                    Utils.toastMessage(mContext, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<POSTRemoveFav> call, Throwable t) {
                Log.i("TAG", t.getMessage());
            }
        });
    }

    private void addFavAPI(final MyViewHolder holder, final int position) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.addFav(postDetails,SessionHandler.getInstance().get(mContext, AppConstants.TOKEN_ID), SessionHandler.getInstance().get(mContext, AppConstants.Language)).enqueue(new Callback<POSTAddFav>() {
            @Override
            public void onResponse(Call<POSTAddFav> call, Response<POSTAddFav> response) {
                if (response.body().getCode().equals(200)) {
//                    Utils.toastMessage(mContext, response.body().getMessage());
                    holder.favGigsIcons.setImageTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.colorPrimary)));
                    holder.favGigsIcons.setImageResource(R.drawable.ic_favorite_filled_24dp);
                } else {
                    Utils.toastMessage(mContext, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<POSTAddFav> call, Throwable t) {
                Log.i("TAG", t.getMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return recent_popular_gigs_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView gigsTitle;
        final TextView gigsAuthor;
        final TextView gigsLocation;
        final TextView gigsReviewCount;
        final TextView gigsRate;
        final ImageView gigsImages;
        TextView gigsRating;
        final ImageView favGigsIcons;
        Boolean isSelected;
        LikeButton likeButton;

        MyViewHolder(View itemView) {
            super(itemView);
            gigsTitle = (TextView) itemView.findViewById(R.id.tv_gigs_title);
            gigsAuthor = (TextView) itemView.findViewById(R.id.author);
            gigsReviewCount = (TextView) itemView.findViewById(R.id.rating_count);
            gigsImages = (ImageView) itemView.findViewById(R.id.iv_gigs_images);
            gigsRate = (TextView) itemView.findViewById(R.id.tv_gigs_rate);
            gigsLocation = (TextView) itemView.findViewById(R.id.tv_loc);
            gigsRating = (TextView) itemView.findViewById(R.id.rating_value);
            favGigsIcons = (ImageView) itemView.findViewById(R.id.iv_fav_gigs);
            likeButton = (LikeButton) itemView.findViewById(R.id.heart_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callGigsDetails = new Intent(mContext, DetailGigs.class);
                    callGigsDetails.putExtra(AppConstants.GIGS_ID, recent_popular_gigs_list.get(getAdapterPosition()).getId());
                    callGigsDetails.putExtra(AppConstants.GIGS_TITLE, recent_popular_gigs_list.get(getAdapterPosition()).getTitle());
                    mContext.startActivity(callGigsDetails);
                }
            });
        }
    }

    public void setLanguageValues() {
        commonStrings = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.COMMONSTRINGS), POSTLanguageModel.Common_strings.class);
    }
}
