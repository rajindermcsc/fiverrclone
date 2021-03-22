package dreamguys.in.co.gigs.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dreamguys.in.co.gigs.Model.POSTSellerReviews;
import dreamguys.in.co.gigs.R;
import dreamguys.in.co.gigs.utils.AppConstants;

/**
 * Created by user5 on 07-11-2017.
 */

public class SellerReviewsGigsAdpater extends BaseAdapter {

    LayoutInflater mInflater;
    private Context mContext;
    private List<POSTSellerReviews.Review_detail> SellerReviews;
    String commentTime = "";
    SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");


    public SellerReviewsGigsAdpater(Context mContext, List<POSTSellerReviews.Review_detail> SellerReviews) {
        this.mContext = mContext;
        this.SellerReviews = SellerReviews;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return SellerReviews.size();
    }

    @Override
    public POSTSellerReviews.Review_detail getItem(int position) {
        return SellerReviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_review_gigs, null);
            mHolder.inputGigsBuyername = (TextView) convertView.findViewById(R.id.tv_gigs_username);
            mHolder.inputGigscomment = (TextView) convertView.findViewById(R.id.tv_gigs_comment);
            mHolder.inputGigsReviewRatingBar = (RatingBar) convertView.findViewById(R.id.rating_gigs);
            mHolder.inputUserimage = (ImageView) convertView.findViewById(R.id.iv_user_profile);
            try {
                Date oldDate = dateFormat.parse(SellerReviews.get(position).getCreated_date());
                Date currentDate = new Date();
                long diff = currentDate.getTime() - oldDate.getTime();
                long seconds = diff / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;
                if (oldDate.before(currentDate)) {

                    if (seconds < 60) {
                        mHolder.inputGigsBuyername.setText(SellerReviews.get(position).getBuyer_name() + " ("
                                + seconds + " seconds ago)");
                    } else if (minutes < 60) {
                        mHolder.inputGigsBuyername.setText(SellerReviews.get(position).getBuyer_name() + " ("
                                + minutes + " minutes ago)");
                    } else if (hours < 24) {
                        mHolder.inputGigsBuyername.setText(SellerReviews.get(position).getBuyer_name() + " ("
                                + hours + " hours ago)");
                    } else {
                        mHolder.inputGigsBuyername.setText(SellerReviews.get(position).getBuyer_name() + " ("
                                + days + " days ago)");
                    }

                    Log.e("oldDate", "is previous date");
                    Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
                            + " hours: " + hours + " days: " + days);

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }


        mHolder.inputGigscomment.setText(SellerReviews.get(position).getComment());
        mHolder.inputGigsReviewRatingBar.setRating(Float.parseFloat(SellerReviews.get(position).getRating()));

        if (SellerReviews.get(position).getProfile_img() != null && !SellerReviews.get(position).getProfile_img().isEmpty()) {
            Picasso.with(mContext)
                    .load(AppConstants.BASEURL + SellerReviews.get(position).getProfile_img())
                    .placeholder(R.drawable.ic_no_image_100)
                    .error(R.drawable.ic_no_image_100)
                    .into(mHolder.inputUserimage);
            //mHolder.inputUserimage.setImageURI(Uri.parse(AppConstants.BASEURL + SellerReviews.get(position).getProfile_img()));
        }


        return convertView;
    }


    private class ViewHolder {
        TextView inputGigsBuyername, inputGigscomment;
        RatingBar inputGigsReviewRatingBar;
        ImageView inputUserimage;
    }


}
