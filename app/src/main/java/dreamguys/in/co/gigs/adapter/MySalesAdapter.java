package dreamguys.in.co.gigs.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dreamguys.in.co.gigs.Model.POSTLanguageModel;
import dreamguys.in.co.gigs.Model.POSTMyActivity;
import dreamguys.in.co.gigs.Model.POSTPurchaseSeeFedBck;
import dreamguys.in.co.gigs.R;
import dreamguys.in.co.gigs.dialog.SaleStatusDialog;
import dreamguys.in.co.gigs.dialog.SalesSeeFeedbackDialog;
import dreamguys.in.co.gigs.network.ApiClient;
import dreamguys.in.co.gigs.network.ApiInterface;
import dreamguys.in.co.gigs.receiver.NetworkChangeReceiver;
import dreamguys.in.co.gigs.utils.AppConstants;
import dreamguys.in.co.gigs.utils.CustomProgressDialog;
import dreamguys.in.co.gigs.utils.CustomTextView;
import dreamguys.in.co.gigs.utils.SessionHandler;
import dreamguys.in.co.gigs.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prasad on 10/25/2017.
 */

public class MySalesAdapter extends RecyclerView.Adapter<MySalesAdapter.MyViewHolder> {
    private Context mContext;
    private List<POSTMyActivity.My_sale> data;
    private FragmentManager fm;
    private SalesSeeFeedbackDialog mSalesSeeFeedbackDialog;
    private HashMap<String, String> postGigsdetails;
    private CustomProgressDialog mCustomProgressDialog;
    private SaleStatusDialog mSaleStatusDialog;
    private int VIEW_NO = 0;
    public POSTLanguageModel.Common_strings commonStrings = new POSTLanguageModel().new Common_strings();
    public POSTLanguageModel.My_activity_screen my_activity_screen = new POSTLanguageModel().new My_activity_screen();

    public MySalesAdapter(Context mContext, List<POSTMyActivity.My_sale> data, FragmentManager fm) {
        this.mContext = mContext;
        this.data = data;
        postGigsdetails = new HashMap<String, String>();
        this.fm = fm;
        mCustomProgressDialog = new CustomProgressDialog(mContext);
        mSalesSeeFeedbackDialog = new SalesSeeFeedbackDialog(mContext);
        mSaleStatusDialog = new SaleStatusDialog(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_purchase_gigs, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        setLanguageValues();
        holder.ctvOrderStatus.setText(my_activity_screen.getLbl_order_status().getName());
        holder.ctvOrderCancel.setText(my_activity_screen.getLbl_order_cancel().getName());
        holder.ctvFeedback.setText(my_activity_screen.getLbl_feedback().getName());
        Picasso.with(mContext).load(AppConstants.BASE_URL + data.get(position).getGig_image_thumb()).placeholder(R.drawable.no_image).into(holder.gigsImages);
        //holder.gigsImages.setImageURI(Uri.parse(AppConstants.BASE_URL + data.get(position).getGig_image_thumb()));
        holder.sellerName.setText(my_activity_screen.getLbl_buyser_name().getName() + data.get(position).getBuyer_name());
        holder.gigsDDate.setText(my_activity_screen.getLbl_delivery_date().getName() + data.get(position).getDelivery());
        holder.gigsOrderId.setText(my_activity_screen.getLbl_order_id().getName() + data.get(position).getOrder_id());
        holder.gigsTitle.setText(data.get(position).getTitle());
        holder.postedDate.setText(data.get(position).getCreated_date());
        holder.gigsRate.setText(data.get(position).getCurrency_sign() + " " + data.get(position).getAmount());

        if (data.get(position).getFeedback_val().equals(0)) {
            holder.pending.setText(data.get(position).getFeedback());
        } else if (data.get(position).getFeedback_val().equals(1)) {
            holder.pending.setText(data.get(position).getFeedback());
            holder.pending.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSalesSeeFeedbackDialog != null) {
                        mSalesSeeFeedbackDialog.setData(data.get(position), 2, null);
                        mSalesSeeFeedbackDialog.show(fm, "see feedback");
                    }
                }
            });

        } else {
            holder.pending.setText(data.get(position).getFeedback());
            holder.pending.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callSeeFedBck(data.get(position));
                }
            });
        }

        if (data.get(position).getOrder_cancel_val().equals(0)) {
            holder.cancel.setText(data.get(position).getOrder_cancel());
        } else if (data.get(position).getOrder_cancel_val().equals(1)) {
            holder.cancel.setText(data.get(position).getOrder_cancel());
            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSaleStatusDialog != null) {
                        VIEW_NO = 1;
                        mSaleStatusDialog.passOrderStatusData(data.get(position), VIEW_NO);
                        mSaleStatusDialog.show(fm, "show_order_status");
                    }
                }
            });
        } else {
            holder.cancel.setText(data.get(position).getOrder_cancel());
            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!data.get(position).getOrder_cancel().equalsIgnoreCase("-")) {
                        if (mSaleStatusDialog != null) {
                            VIEW_NO = 1;
                            mSaleStatusDialog.passOrderStatusData(data.get(position), VIEW_NO);
                            mSaleStatusDialog.show(fm, "show_order_status");
                        }
                    } else {
//                        Toast.makeText(mContext, "Not available", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        if (data.get(position).getStatus_msg_val().equalsIgnoreCase("0")) {
            holder.newStatus.setText(data.get(position).getOrder_status());
        } else if (data.get(position).getStatus_msg_val().equalsIgnoreCase("1")) {
            holder.newStatus.setText(data.get(position).getOrder_status());

            holder.newStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSaleStatusDialog != null) {
                        VIEW_NO = 2;
                        mSaleStatusDialog.passOrderStatusData(data.get(position), VIEW_NO);
                        mSaleStatusDialog.show(fm, "show_order_status");
                    }
                }
            });

        } else if (data.get(position).getStatus_msg_val().equalsIgnoreCase("2")) {
            holder.newStatus.setText(data.get(position).getOrder_status());
        } else if (data.get(position).getStatus_msg_val().equalsIgnoreCase("3")) {
            holder.newStatus.setText(data.get(position).getOrder_status());
            holder.newStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSaleStatusDialog != null) {
                        VIEW_NO = 2;
                        mSaleStatusDialog.passOrderStatusData(data.get(position), VIEW_NO);
                        mSaleStatusDialog.show(fm, "show_order_status");
                    }
                }
            });
        } else if (data.get(position).getStatus_msg_val().equalsIgnoreCase("4")) {
            holder.newStatus.setText(data.get(position).getOrder_status());
            holder.newStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSaleStatusDialog != null) {
                        VIEW_NO = 2;
                        mSaleStatusDialog.passOrderStatusData(data.get(position), VIEW_NO);
                        mSaleStatusDialog.show(fm, "show_order_status");
                    }
                }
            });
            holder.newStatus.setText(data.get(position).getOrder_status());
        } else if (data.get(position).getStatus_msg_val().equalsIgnoreCase("5")) {
            holder.newStatus.setText(data.get(position).getOrder_status());
        } else if (data.get(position).getStatus_msg_val().equalsIgnoreCase("6")) {
            holder.newStatus.setText(data.get(position).getOrder_status());
        } else if (data.get(position).getStatus_msg_val().equalsIgnoreCase("7")) {
            holder.newStatus.setText(data.get(position).getOrder_status());
        } else if (data.get(position).getStatus_msg_val().equalsIgnoreCase("8")) {
            holder.newStatus.setText(data.get(position).getOrder_status());
            /*holder.newStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSaleStatusDialog != null) {
                        VIEW_NO = 2;
                        mSaleStatusDialog.passOrderStatusData(data.get(position), VIEW_NO);
                        mSaleStatusDialog.show(fm, "show_order_status");
                    }
                }
            });*/
        } else if (data.get(position).getStatus_msg_val().equalsIgnoreCase("9")) {
            holder.newStatus.setText(data.get(position).getOrder_status());
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView gigsTitle;
        final TextView postedDate;
        final TextView gigsOrderId;
        final TextView gigsDDate;
        final TextView sellerName;
        final TextView pending;
        final TextView cancel;
        final TextView newStatus;
        final TextView gigsRate;
        final CircleImageView gigsImages;
        CustomTextView ctvFeedback, ctvOrderCancel, ctvOrderStatus;

        MyViewHolder(View itemView) {
            super(itemView);
            gigsTitle = (TextView) itemView.findViewById(R.id.tv_gigs_title);
            postedDate = (TextView) itemView.findViewById(R.id.posted_dated);
            pending = (TextView) itemView.findViewById(R.id.tv_pending);
            cancel = (TextView) itemView.findViewById(R.id.tv_cancel);
            newStatus = (TextView) itemView.findViewById(R.id.tv_new_status);
            gigsImages = (CircleImageView) itemView.findViewById(R.id.iv_gigs_images);
            sellerName = (TextView) itemView.findViewById(R.id.sellerName);
            gigsOrderId = (TextView) itemView.findViewById(R.id.order_id);
            gigsDDate = (TextView) itemView.findViewById(R.id.delivery_date);
            gigsRate = (TextView) itemView.findViewById(R.id.tv_gigs_rate);
            ctvFeedback = (CustomTextView) itemView.findViewById(R.id.ctv_feedback);
            ctvOrderCancel = (CustomTextView) itemView.findViewById(R.id.ctv_order_cancel);
            ctvOrderStatus = (CustomTextView) itemView.findViewById(R.id.ctv_order_status);
        }
    }


    private void callSeeFedBck(final POSTMyActivity.My_sale my_purchase) {
        postGigsdetails.put("from_user_id", my_purchase.getUser_id());
        postGigsdetails.put("to_user_id", SessionHandler.getInstance().get(mContext, AppConstants.TOKEN_ID));
        postGigsdetails.put("gig_id", my_purchase.getGigs_id());
        postGigsdetails.put("order_id", my_purchase.getOrder_id());
        postGigsdetails.put("user_id", SessionHandler.getInstance().get(mContext, AppConstants.TOKEN_ID));

        if (NetworkChangeReceiver.isConnected()) {
            mCustomProgressDialog.showDialog();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.postpurchsseFedBck(postGigsdetails, SessionHandler.getInstance().get(mContext, AppConstants.TOKEN_ID), SessionHandler.getInstance().get(mContext, AppConstants.Language)).enqueue(new Callback<POSTPurchaseSeeFedBck>() {
                @Override
                public void onResponse(Call<POSTPurchaseSeeFedBck> call, Response<POSTPurchaseSeeFedBck> response) {

                    if (response.body().getCode().equals(200)) {
                        if (response.body().getData().getUser_feed().size() > 0) {
                            if (mSalesSeeFeedbackDialog != null) {
                                mSalesSeeFeedbackDialog.setData(my_purchase, 1, response.body().getData().getUser_feed());
                                mSalesSeeFeedbackDialog.show(fm, "see feedback");
                            }
                        } else {
                            Utils.toastMessage(mContext, commonStrings.getLbl_no_feedback_found().getName());
                        }

                    } else {
                        Utils.toastMessage(mContext, response.body().getMessage());
                    }

                    mCustomProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<POSTPurchaseSeeFedBck> call, Throwable t) {
                    mCustomProgressDialog.dismiss();
                    Log.i("TAG", t.getMessage());
                }
            });


        } else {
            Utils.toastMessage(mContext, commonStrings.getLbl_enable_internet().getName());
        }

    }

    public void setLanguageValues() {
        commonStrings = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.COMMONSTRINGS), POSTLanguageModel.Common_strings.class);
        my_activity_screen = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.MYACTIVITY), POSTLanguageModel.My_activity_screen.class);
    }
}
