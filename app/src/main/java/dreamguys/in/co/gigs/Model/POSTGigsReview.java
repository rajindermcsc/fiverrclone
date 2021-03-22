package dreamguys.in.co.gigs.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user5 on 15-12-2017.
 */

public class POSTGigsReview {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("gig_id")
        @Expose
        private String gig_id;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("rating")
        @Expose
        private String rating;
        @SerializedName("time_zone")
        @Expose
        private String time_zone;
        @SerializedName("created_date")
        @Expose
        private String created_date;
        @SerializedName("to_user_id")
        @Expose
        private String to_user_id;
        @SerializedName("from_user_id")
        @Expose
        private String from_user_id;
        @SerializedName("sellername")
        @Expose
        private String sellername;
        @SerializedName("buyername")
        @Expose
        private String buyername;

        @SerializedName("profile_img")
        @Expose
        private String profile_img;

        public String getGig_id() {
            return gig_id;
        }

        public void setGig_id(String gig_id) {
            this.gig_id = gig_id;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getTime_zone() {
            return time_zone;
        }

        public void setTime_zone(String time_zone) {
            this.time_zone = time_zone;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        public String getTo_user_id() {
            return to_user_id;
        }

        public void setTo_user_id(String to_user_id) {
            this.to_user_id = to_user_id;
        }

        public String getFrom_user_id() {
            return from_user_id;
        }

        public void setFrom_user_id(String from_user_id) {
            this.from_user_id = from_user_id;
        }

        public String getSellername() {
            return sellername;
        }

        public void setSellername(String sellername) {
            this.sellername = sellername;
        }

        public String getBuyername() {
            return buyername;
        }

        public void setBuyername(String buyername) {
            this.buyername = buyername;
        }

        public String getProfile_img() {
            return profile_img;
        }

        public void setProfile_img(String profile_img) {
            this.profile_img = profile_img;
        }

    }
}
