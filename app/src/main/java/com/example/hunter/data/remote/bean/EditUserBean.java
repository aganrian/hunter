package com.example.hunter.data.remote.bean;

import com.google.gson.annotations.SerializedName;

/*Variable response dari edit user*/
public class EditUserBean extends BaseBean{

    @SerializedName("data")
    private LoginBean loginBean;

    public LoginBean getLoginBean() {
        return loginBean;
    }
}
