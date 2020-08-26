package retrofitfileupload;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Belal on 10/5/2017.
 */

public interface Api {

    //the base URL for our API
    //make sure you are not using localhost
    //find the ip usinc ipconfig command
    String BASE_URL = "http://xpertwebtech.in/gfood/api/";

    //this is our multipart request
    //we have two parameters on is name and other one is description

    @Headers("access-token: NDRX6tRYDfkxnqLYIwLUrf8HoKzTDNmqzZLtWJxGlUbnPVBjODBOWHkeT61KstkbjcPEFvtscuScDzgTOpbRPTWEB")
    @Multipart
    @POST("register-dilevery-boy")
    Call<Response> becomevendor(@Part MultipartBody.Part aadhar_card,
                                @Part("name") String user_name,
                                @Part("email") String user_emil,
                                @Part("password")  String password,
                                @Part("phone") String phone,
                                @Part("address") String address);


//    @Headers("access-token: NDRX6tRYDfkxnqLYIwLUrf8HoKzTDNmqzZLtWJxGlUbnPVBjODBOWHkeT61KstkbjcPEFvtscuScDzgTOpbRPTWEB")
//    @Multipart
//    @POST("upload-prescription")
//    Call<PrescriptionUploaded> uploadPrescription(@Part MultipartBody.Part image,
//                                                  @Part("user_id") com.squareup.okhttp.RequestBody user_id);
//


}
