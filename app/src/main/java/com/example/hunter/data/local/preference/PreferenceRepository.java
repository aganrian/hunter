package com.example.hunter.data.local.preference;

/*interface untuk preference repository supaya bisa di akses dimana saja*/
public interface PreferenceRepository {


    boolean isUserLogged();

    void setUserLogged(boolean userLogged);

}
