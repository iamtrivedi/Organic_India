package com.organic.india.singletone;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import com.organic.india.common.SharedPreferenceUtils;
import com.organic.india.pojo.logged_in_user.Data;

public class Organic_india extends MultiDexApplication {

    private Data me;
    private static Organic_india app_instance;

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(base);
        MultiDex.install(this);
        SharedPreferenceUtils.init(this);
        app_instance = this;
        this.me = new Data();
    }

    public static synchronized Organic_india getInstance(){
        return app_instance;
    }

    public Data getMe(){
        return me;
    }

    public void setMe(Data me) {
        this.me = me;
    }
}
