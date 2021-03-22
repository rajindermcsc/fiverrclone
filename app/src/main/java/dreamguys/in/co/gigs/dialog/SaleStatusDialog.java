package dreamguys.in.co.gigs.dialog;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;

import dreamguys.in.co.gigs.EditGigs;
import dreamguys.in.co.gigs.Model.POSTAcceptBuyRequest;
import dreamguys.in.co.gigs.Model.POSTLanguageModel;
import dreamguys.in.co.gigs.Model.POSTMyActivity;
import dreamguys.in.co.gigs.R;
import dreamguys.in.co.gigs.fragment.MySalesFragment;
import dreamguys.in.co.gigs.network.ApiClient;
import dreamguys.in.co.gigs.network.ApiInterface;
import dreamguys.in.co.gigs.receiver.NetworkChangeReceiver;
import dreamguys.in.co.gigs.utils.AppConstants;
import dreamguys.in.co.gigs.utils.CustomProgressDialog;
import dreamguys.in.co.gigs.utils.NetworkAlertDialog;
import dreamguys.in.co.gigs.utils.SessionHandler;
import dreamguys.in.co.gigs.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prasad on 11/10/2017.
 */

public class SaleStatusDialog extends DialogFragment {

    View saleStatusDialg;
    Spinner mSpinner;
    TextView textStatus;
    Button btnOrdrStus, btnAcceptStus;
    Context mContext;
    POSTMyActivity.My_sale order_status;
    String status_id;
    int VIEW_NO;
    HashMap<String, String> postData = new HashMap<String, String>();
    CustomProgressDialog mCustomProgressDialog;
    public static MySalesFragment callbacks;
    public POSTLanguageModel.Common_strings commonStrings = new POSTLanguageModel().new Common_strings();
    public POSTLanguageModel.My_activity_screen my_activity_screen = new POSTLanguageModel().new My_activity_screen();

    public SaleStatusDialog() {

    }

    public SaleStatusDialog(Context mContext) {
        this.mContext = mContext;
        mCustomProgressDialog = new CustomProgressDialog(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setLanguageValues();
        if (VIEW_NO == 1) {
            saleStatusDialg = inflater.inflate(R.layout.dialog_sale_cancel, null);

            textStatus = (TextView) saleStatusDialg.findViewById(R.id.tv_reason);
            btnAcceptStus = (Button) saleStatusDialg.findViewById(R.id.btn_accept_status);
            btnAcceptStus.setText(my_activity_screen.getTxt_fld_update().getName());
            textStatus.setText(my_activity_screen.getLbl_fld_select_status().getName());
            textStatus.setText(order_status.getCancel_reason());

            if (order_status.getCancel_accept().equalsIgnoreCase("0")) {
                btnAcceptStus.setVisibility(View.VISIBLE);
            } else {
                btnAcceptStus.setVisibility(View.GONE);
            }

            btnAcceptStus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    postData.put("order_id", order_status.getOrder_id());
                    postData.put("time_zone", SessionHandler.getInstance().get(mContext, AppConstants.TIMEZONE_ID));

                    if (NetworkChangeReceiver.isConnected()) {
                        mCustomProgressDialog.showDialog();
                        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        apiInterface.postacceptbuyrequest(postData, SessionHandler.getInstance().get(mContext, AppConstants.TOKEN_ID), SessionHandler.getInstance().get(mContext, AppConstants.Language)).enqueue(new Callback<POSTAcceptBuyRequest>() {
                            @Override
                            public void onResponse(Call<POSTAcceptBuyRequest> call, Response<POSTAcceptBuyRequest> response) {
                                if (response.body().getCode().equals(200)) {
                                    Utils.toastMessage(mContext, response.body().getMessage());
                                    callbacks.updateRequestData();
                                } else {
                                    Utils.toastMessage(mContext, response.body().getMessage());
                                }
                                mCustomProgressDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<POSTAcceptBuyRequest> call, Throwable t) {
                                Log.i("TAG", t.getMessage());
                                mCustomProgressDialog.dismiss();
                            }
                        });

                    } else {
                        Utils.toastMessage(mContext, getString(R.string.err_internet_connection));
                    }


                    getDialog().dismiss();

                    getDialog().dismiss();
                }
            });
        } else {
            saleStatusDialg = inflater.inflate(R.layout.dialog_sale_status, null);
            mSpinner = (Spinner) saleStatusDialg.findViewById(R.id.spin_order_status);
            btnOrdrStus = (Button) saleStatusDialg.findViewById(R.id.btn_select_ordr_status);
//            final String[] orderStatusArr = getResources().getStringArray(R.array.order_status);
            final String[] orderStatusArr = new String[]{my_activity_screen.getLbl_fld_pending().getName(), my_activity_screen.getLbl_fld_processing().getName(), my_activity_screen.getLbl_fld_completed().getName(), my_activity_screen.getLbl_fld_declined().getName()};

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String value = orderStatusArr[position];
                    if (value.equalsIgnoreCase(my_activity_screen.getLbl_fld_pending().getName())) {
                        status_id = "2";
                    } else if (value.equalsIgnoreCase(my_activity_screen.getLbl_fld_processing().getName())) {
                        status_id = "3";
                    } else if (value.equalsIgnoreCase(my_activity_screen.getLbl_fld_declined().getName())) {
                        status_id = "5";
                    }  /*else if (value.equalsIgnoreCase("Completed Accept")) {
                        status_id = "8";
                    }*/ else {
                        status_id = "6";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            btnOrdrStus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderStatusCall(status_id);
                }
            });

        }


        return saleStatusDialg;
    }


    public void orderStatusCall(String status_id) {
        postData.put("order_id", order_status.getOrder_id());
        postData.put("order_status", status_id);
        postData.put("time_zone", SessionHandler.getInstance().get(mContext, AppConstants.TIMEZONE_ID));

        if (NetworkChangeReceiver.isConnected()) {
            mCustomProgressDialog.showDialog();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.postorderStatus(postData, SessionHandler.getInstance().get(mContext, AppConstants.TOKEN_ID), SessionHandler.getInstance().get(mContext, AppConstants.Language)).enqueue(new Callback<POSTAcceptBuyRequest>() {
                @Override
                public void onResponse(Call<POSTAcceptBuyRequest> call, Response<POSTAcceptBuyRequest> response) {
                    if (response.body().getCode().equals(200)) {
                        Utils.toastMessage(mContext, response.body().getMessage());
                        callbacks.updateRequestData();
                    } else if (response.body().getCode().equals(AppConstants.INVALID_RESPONSE_CODE)) {
//                    mCustomProgressDialog.dismiss();
                        NetworkAlertDialog.networkAlertDialog(mContext, "", response.body().getMessage(), null, null);
                    } else {
                        Utils.toastMessage(mContext, response.body().getMessage());
                    }
                    mCustomProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<POSTAcceptBuyRequest> call, Throwable t) {
                    Log.i("TAG", t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });

        } else {
            Utils.toastMessage(mContext, getString(R.string.err_internet_connection));
        }


        getDialog().dismiss();

    }

    public void passOrderStatusData(POSTMyActivity.My_sale order_status, int VIEW_NO) {
        this.order_status = order_status;
        this.VIEW_NO = VIEW_NO;
    }

    public static void callDialgFrag(MySalesFragment mMySalesFragment) {
        callbacks = mMySalesFragment;
    }


    public void setLanguageValues() {
        commonStrings = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.COMMONSTRINGS), POSTLanguageModel.Common_strings.class);
        my_activity_screen = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.MYACTIVITY), POSTLanguageModel.My_activity_screen.class);
    }

}
