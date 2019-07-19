package com.example.hunter.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.hunter.data.local.db.entity.UserEntity;

import java.util.List;

import io.reactivex.Flowable;

/*Data akses object atau query untuk table dari user pada sqlite*/
@Dao
public interface UserDao {

    @Query("SELECT * FROM user_table ")
    Flowable<List<UserEntity>> loadUser();

    @Query("DELETE FROM user_table")
    void deleteUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserEntity entity);

    @Query("SELECT id_user FROM user_table ")
    Integer getUserId();

    @Query("SELECT nama_lengkap FROM user_table ")
    String getName();

    @Query("SELECT point FROM user_table ")
    Integer getPoint();

    @Query("SELECT no_hp FROM user_table ")
    String getNoHp();

    @Query("SELECT picture FROM user_table ")
    String getPicture();

    @Query("UPDATE user_table set point= :point   WHERE id_user =:id_user")
    void updateJumlahHasil(Integer point,Integer id_user);

    @Query("UPDATE user_table set picture= :profileImage   WHERE id_user =:id_user")
    void updateUrlImageProfile(String profileImage,Integer id_user);

    @Query("UPDATE user_table set ktp_picture= :ktpImage   WHERE id_user =:id_user")
    void updateUrlKtpProfile(String ktpImage,Integer id_user);

}
