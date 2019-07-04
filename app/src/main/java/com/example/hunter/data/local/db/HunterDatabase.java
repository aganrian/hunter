package com.example.hunter.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.hunter.data.local.db.dao.UserDao;
import com.example.hunter.data.local.db.entity.UserEntity;

@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class HunterDatabase extends RoomDatabase {

    public abstract  UserDao userDao();

}
