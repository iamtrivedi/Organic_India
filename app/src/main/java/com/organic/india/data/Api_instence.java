package com.organic.india.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api_instence {

 // static String BASE_URL="http://hrm.like2gift.com/api/";
  static String BASE_URL="https://sampark.organicindia.com/api/";

    private static Retrofit retrofit;

    public static Api_Interfaces getRetrofitInstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(Api_Interfaces.class);
    }

}
