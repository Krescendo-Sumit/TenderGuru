package tender.guru.suvidha.util;

import org.json.JSONObject;

import java.util.List;

import tender.guru.suvidha.model.BhartiModel;
import tender.guru.suvidha.model.ChatModel;
import tender.guru.suvidha.model.ContentModel;
import tender.guru.suvidha.model.FeesModel;
import tender.guru.suvidha.model.FileModel;
import tender.guru.suvidha.model.PriceModel;
import tender.guru.suvidha.model.SignInModel;
import tender.guru.suvidha.model.SubCourseModel;
import tender.guru.suvidha.model.SwarankurModel;
import tender.guru.suvidha.model.TenderModel;
import tender.guru.suvidha.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {


    @POST(Constants.COURSE_URL)
    Call<List<BhartiModel>> getCourseList(@Body JSONObject jsonObject);
    @POST(Constants.SUB_COURSE_URL)
    Call<List<SubCourseModel>> getSubCourseList(@Query("mobile") String mobile, @Query("id")String id);

     @POST(Constants.CONTENT_LIST)
    Call<List<ContentModel>> getContentList(@Query("mobile") String mobile, @Query("id")String id);

    @POST(Constants.GET_USER_PROFILE)
    Call<List<UserModel>> getUserDetails(@Query("mobile") String mobile, @Query("id")String id, @Query("deviceid")String deviceid);

    @POST(Constants.GET_MATCH_PROFILE)
    Call<List<UserModel>> getMatches(@Query("mobile") String mobile, @Query("id")String id, @Query("deviceid")String deviceid);

    @POST(Constants.GET_TENDER_DETAILS)
    Call<List<TenderModel>> getTenders(@Query("mobile") String mobile, @Query("id")String id, @Query("deviceid")String deviceid, @Query("srcString")String srcString);


    @POST(Constants.CHECK_LOGIN)
    Call<List<SignInModel>> checkLogin(@Query("mobile") String mobile, @Query("pass")String pass, @Query("deviceid")String deviceid);

    @POST(Constants.GET_USER_FEES)
    Call<List<FeesModel>> getFeesDetails(@Query("mobile") String mobile, @Query("id")String id);

    @POST(Constants.GET_USER_FILES)
    Call<List<FileModel>> getFileDetails(@Query("mobile") String mobile, @Query("id")String id);

    @POST(Constants.INSERTUSER)
    Call<String> addUser(@Query("name") String name,@Query("email")  String email,@Query("mobile")  String mobile,@Query("course")  String course,@Query("password")  String password,@Query("confirmpassword")  String confirmpassword,@Query("address")  String address);

    @POST(Constants.VALIDATEFEES)
    Call<String> validateFees(@Query("sid") String uid);

    @POST(Constants.GETSWARANKUR)
    Call<List<SwarankurModel>> getSwarankur(@Query("mobile")String mobile,@Query("id") String userid);



    @POST(Constants.INSERT_REGISTRATION)
    Call<String> insertUser(@Query("id") String id, @Query("usercategory") String usercategory, @Query("fullname") String fullname, @Query("dob") String dob, @Query("age") String age, @Query("email") String email,@Query("mobile1") String mobile1,@Query("mobile2")  String mobile2,@Query("isJanmpatrikaAvailable")  String isJanmpatrikaAvailable,@Query("height")  String height,@Query("color")  String color,@Query("gotra")  String gotra,@Query("isChashma")  String isChashma,@Query("qualification")  String qualification,@Query("jobdetails")  String jobdetails,@Query("joblocation")  String joblocation,@Query("monthlyIncome")  String monthlyIncome,@Query("local_address")  String local_address,@Query("permanent_Address")  String permanent_Address,@Query("isWishToSeeJamnpatrika")  String isWishToSeeJamnpatrika,@Query("isMangal")  String isMangal,@Query("hobbies")  String hobbies,@Query("partnerRequirment")  String partnerRequirment,@Query("fathername")  String fathername,@Query("fatherocupation")  String fatherocupation,@Query("mothersname")  String mothersname,@Query("totalbrothers")  String totalbrothers,@Query("bothersmaritialStatas")  String bothersmaritialStatas,@Query("totalsister")  String totalsister,@Query("sistermaritialStatus")  String sistermaritialStatus,@Query("MamacheKul")  String mamacheKul,@Query("photopath")  String photopath,@Query("createdDate")  String createdDate, @Query("password") String password);

    @POST(Constants.UPDATE_REGISTRATION)
    Call<String> updateUser(@Query("id") String id, @Query("usercategory") String usercategory, @Query("fullname") String fullname, @Query("dob") String dob, @Query("age") String age, @Query("email") String email,@Query("mobile1") String mobile1,@Query("mobile2")  String mobile2,@Query("isJanmpatrikaAvailable")  String isJanmpatrikaAvailable,@Query("height")  String height,@Query("color")  String color,@Query("gotra")  String gotra,@Query("isChashma")  String isChashma,@Query("qualification")  String qualification,@Query("jobdetails")  String jobdetails,@Query("joblocation")  String joblocation,@Query("monthlyIncome")  String monthlyIncome,@Query("local_address")  String local_address,@Query("permanent_Address")  String permanent_Address,@Query("isWishToSeeJamnpatrika")  String isWishToSeeJamnpatrika,@Query("isMangal")  String isMangal,@Query("hobbies")  String hobbies,@Query("partnerRequirment")  String partnerRequirment,@Query("fathername")  String fathername,@Query("fatherocupation")  String fatherocupation,@Query("mothersname")  String mothersname,@Query("totalbrothers")  String totalbrothers,@Query("bothersmaritialStatas")  String bothersmaritialStatas,@Query("totalsister")  String totalsister,@Query("sistermaritialStatus")  String sistermaritialStatus,@Query("MamacheKul")  String mamacheKul,@Query("photopath")  String photopath,@Query("createdDate")  String createdDate, @Query("password") String password);

    @POST(Constants.INSERT_INTEREST)
    Call<String> addInterest(@Query("userid") String userid, @Query("interestedid") String interestid);

    @POST(Constants.INSERT_INTEREST_ACCEPT)
    Call<String> addInterest_Accept(@Query("userid") String userid, @Query("interestedid") String interestid, @Query("type") int type);

    @POST(Constants.INSERT_CHAT)
    Call<String> addChat(@Query("userid") String userid, @Query("interestedid") String interestid, @Query("message") String message);

    @POST(Constants.GET_CHAT)
    Call<List<ChatModel>> getChat(@Query("sender") String sender,@Query("receiver")  String receiver);

    @POST(Constants.GET_MATECHES_REQUEST)
    Call<List<UserModel>> getMatchesRequest(@Query("mobile") String mobile,@Query("userid")  String userid,@Query("deviceid")  String deviceid,@Query("type")  String type);

    @POST(Constants.GET_TENDER_SINGLE)
    Call<List<TenderModel>> getTenderDetails(@Query("userid") String userid,@Query("tenderid")  String tenderid);

    @POST(Constants.GET_TYPES)
    Call<String> getTypes(@Query("mobile") String mobile,@Query("userid")  String userid,@Query("type")  String type);

    @POST(Constants.GET_PREFERENCE)
    Call<String> getPreference(@Query("mobile") String mobile,@Query("userid")  String userid,@Query("type")  String type);


    @POST(Constants.INSERT_SUGGESTIONS)
    Call<String> addSuggestion(@Query("userid") String userid, @Query("message") String message);


    @POST(Constants.GET_NOTIFICATION)
    Call<List<ChatModel>> getNotifications(@Query("sender") String sender);


    @POST(Constants.GET_TENDER_DETAILS_OFFLINE)
    Call<List<TenderModel>> getTenders_Offline(@Query("mobile") String mobile, @Query("id")String id, @Query("deviceid")String deviceid, @Query("srcString")String srcString);

    @POST(Constants.GET_RESULT)
    Call<List<TenderModel>> getTendersResult(@Query("mobile") String mobile, @Query("id")String id, @Query("deviceid")String deviceid, @Query("srcString")String srcString);

    @POST(Constants.GET_NOTIFICATIONCOUNT)
    Call<String> getNotificatioCount(@Query("mobile") String mobile, @Query("id")String id);

    @POST(Constants.GET_PRIZINGDETAILS)
    Call<List<PriceModel>> getPrizingDetails(@Query("mobile") String mobile, @Query("id")String id);

  /*  @POST(Constants.GET_CITIES)
    Call<String> getCity(@Query("mobile") String mobile,@Query("userid")  String userid,@Query("type")  String type);

    @POST(Constants.GET_KEYWORD)
    Call<String> getCity(@Query("mobile") String mobile,@Query("userid")  String userid,@Query("type")  String type);

    @POST(Constants.GET_CATEGORY)
    Call<String> getCity(@Query("mobile") String mobile,@Query("userid")  String userid,@Query("type")  String type);
*/

/*
    @POST(police.bharti.katta.util.Constants.BHARTI_URL_INDIVISUAL)
    Call<List<BhartiModel>> getBhartiMenuDetails(@Query("mobile") String mobile,@Query("id")String id,@Query("type")String type);

    @POST(police.bharti.katta.util.Constants.SARAV_MENU_URL)
    Call<List<SaravMenuModel>> getSaravMenu(@Body JSONObject jsonObject);

    @POST(police.bharti.katta.util.Constants.CHALUGHADAMODI_MENU_URL)
    Call<List<ChaluGhadamodiModel>> getChalughadaModiMenu(@Body JSONObject jsonObject);

    @POST(police.bharti.katta.util.Constants.GET_BOOKS_LIST)
    Call<List<BookModel>> getBookList(@Body JSONObject jsonObject);

    */

}
