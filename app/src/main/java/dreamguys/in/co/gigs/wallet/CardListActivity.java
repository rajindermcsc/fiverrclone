package dreamguys.in.co.gigs.wallet;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.stripe.android.BuildConfig;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import java.util.Calendar;

import dreamguys.in.co.gigs.Model.GETStripeConfig;
import dreamguys.in.co.gigs.R;
import dreamguys.in.co.gigs.network.ApiClient;
import dreamguys.in.co.gigs.network.ApiInterface;
import dreamguys.in.co.gigs.receiver.NetworkChangeReceiver;
import dreamguys.in.co.gigs.utils.AppConstants;
import dreamguys.in.co.gigs.utils.CustomProgressDialog;
import dreamguys.in.co.gigs.utils.SessionHandler;
import dreamguys.in.co.gigs.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static dreamguys.in.co.gigs.utils.AppConstants.isFromWhichCard;

public class CardListActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextCVV, editTextCardNumber, editTextMM;
    Button actionAddCashSecurely;
    String publicKey = "", secretKey = "", str_expiry_date = "";
    int previousLength;
    private static final String EXP_DATE_REGAX = "(0[1-9]|1[0-2])[0-9]{2}";
    CustomProgressDialog customProgressDialog;
    RadioGroup radioGroupAddNewCards;
    RadioButton radioButtonDebitCreditCard;
    LinearLayout linearLayoutAddNewCardPanel;
    private static final char space = ' ';

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_lists);
        customProgressDialog = new CustomProgressDialog(this);
        actionAddCashSecurely = findViewById(R.id.action_add_cash_securely);
        editTextMM = findViewById(R.id.edit_mm_yy);
        editTextCVV = findViewById(R.id.cet_cvv);
        editTextCardNumber = findViewById(R.id.cet_enter_card_number);
        radioGroupAddNewCards = findViewById(R.id.rg_card_details);
        radioButtonDebitCreditCard = findViewById(R.id.rb_debit_credit_cards);
        linearLayoutAddNewCardPanel = findViewById(R.id.ll_card_panel);

        if (SessionHandler.getInstance().get(this, AppConstants.TOKEN_ID) != null) {
            if (NetworkChangeReceiver.isConnected()) {
                customProgressDialog.showDialog();
                final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                apiInterface.getStripeConfiDetails(SessionHandler.getInstance().get(this, AppConstants.TOKEN_ID), SessionHandler.getInstance().get(this, AppConstants.Language))
                        .enqueue(new Callback<GETStripeConfig>() {
                            @Override
                            public void onResponse(Call<GETStripeConfig> call, Response<GETStripeConfig> response) {
                                customProgressDialog.dismiss();
                                if (response.body().getCode().equals(200)) {
                                    if (response.body().getData().getStripePublicKey() != null && response.body().getData().getStripeSecretKey() != null) {
                                        if (!response.body().getData().getStripeSecretKey().isEmpty() && !response.body().getData().getStripePublicKey().isEmpty()) {
                                            publicKey = response.body().getData().getStripePublicKey();
                                            secretKey = response.body().getData().getStripeSecretKey();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<GETStripeConfig> call, Throwable t) {
                                customProgressDialog.dismiss();
                            }
                        });
            } else {
                Toast.makeText(this, getString(R.string.err_internet_connection), Toast.LENGTH_SHORT).show();
            }
        }

        actionAddCashSecurely.setOnClickListener(this);

        radioGroupAddNewCards.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkId = radioGroupAddNewCards.getCheckedRadioButtonId();
                if (checkId == radioButtonDebitCreditCard.getId()) {
                    isFromWhichCard = 0;
                    linearLayoutAddNewCardPanel.setVisibility(View.VISIBLE);
                }
            }
        });

        editTextCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 18) {
                    editTextMM.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                cardNumberPattern(editable);
            }
        });

        editTextMM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                previousLength = editTextMM.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = editTextMM.getText().toString().trim().length();

                if (previousLength <= length && length < 3) {
                    int month = Integer.parseInt(editTextMM.getText().toString());
                    if (length == 1 && month >= 2) {
                        String autoFixStr = "0" + month + "/";
                        editTextMM.setText(autoFixStr);
                        editTextMM.setSelection(3);
                    } else if (length == 2 && month <= 12) {
                        String autoFixStr = editTextMM.getText().toString() + "/";
                        editTextMM.setText(autoFixStr);
                        editTextMM.setSelection(3);
                    } else if (length == 2 && month > 12) {
                        editTextMM.setText("1");
                        editTextMM.setSelection(1);
                    }
                } else if (length == 5 && before == 0) {
                    String enteredYear = s.toString().substring(3);
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100; //getting last 2 digits of current year i.e. 2018 % 100 = 18
                    if (Integer.parseInt(enteredYear) < currentYear) {
                        Toast.makeText(CardListActivity.this, "Invalid Expiry Date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    editTextCVV.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    void cardNumberPattern(Editable s) {
        int pos = 0;
        while (true) {
            if (pos >= s.length()) break;
            if (space == s.charAt(pos) && (((pos + 1) % 5) != 0 || pos + 1 == s.length())) {
                s.delete(pos, pos + 1);
            } else {
                pos++;
            }
        }

        // Insert char where needed.
        pos = 4;
        while (true) {
            if (pos >= s.length()) break;
            final char c = s.charAt(pos);
            // Only if its a digit where there should be a space we insert a space
            if ("0123456789".indexOf(c) >= 0) {
                s.insert(pos, "" + space);
            }
            pos += 5;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.action_add_cash_securely) {
            if (isFromWhichCard == -1) {
                Toast.makeText(this, "Please select one card or add new card to proceed.", Toast.LENGTH_SHORT).show();
                return;
            } else if (isFromWhichCard == 0) {

                if (SessionHandler.getInstance().get(this, AppConstants.TOKEN_ID) == null) {
                    Toast.makeText(this, "Please login to proceed", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editTextCardNumber.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter the card number to proceed", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editTextMM.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter the expiry month/year to proceed", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editTextCVV.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter the cvv number to proceed", Toast.LENGTH_SHORT).show();
                    return;
                }


                String[] spiltExpiryDateStr = editTextMM.getText().toString().split("/");

                int month = Integer.parseInt(spiltExpiryDateStr[0]);
                int year = Integer.parseInt(spiltExpiryDateStr[1]);

                Card card = new Card(editTextCardNumber.getText().toString(), month, year, editTextCVV.getText().toString());
                customProgressDialog.showDialog();

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (card != null) {
                            if (card.validateCard() && card.validateCVC() && card.validateExpiryDate()) {
                                try {
                                    if (publicKey.isEmpty()) {
                                        Stripe stripe = new Stripe(publicKey);
                                        stripe.createToken(card, new TokenCallback() {
                                            @Override
                                            public void onError(Exception error) {
                                                customProgressDialog.dismiss();
                                                Log.i("TAG_ERROR", error.getMessage());
                                            }

                                            @Override
                                            public void onSuccess(Token token) {
                                                customProgressDialog.dismiss();
                                                Log.i("TAG_TOKEN", token.getId());
                                            }
                                        });
                                    } else {
                                        Toast.makeText(CardListActivity.this, "please contact the system administrator...", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (AuthenticationException e) {
                                    e.printStackTrace();
                                    customProgressDialog.dismiss();
                                }

                            }
                        }
                    }
                });

            }
        }
    }

    public void setVisibilityNewCard() {
        try {
            editTextCardNumber.getText().clear();
            editTextMM.getText().clear();
            editTextCVV.getText().clear();

            LinearLayout linearLayout = (LinearLayout) radioGroupAddNewCards.getChildAt(1);
            RadioButton radioButton = (RadioButton) radioGroupAddNewCards.getChildAt(0);

            if (radioButton.isChecked()) {
                radioGroupAddNewCards.clearCheck();
                linearLayout.setVisibility(View.GONE);
            }

            linearLayoutAddNewCardPanel.invalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
