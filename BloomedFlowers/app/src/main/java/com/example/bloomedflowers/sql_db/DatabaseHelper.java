package com.example.bloomedflowers.sql_db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bloomedflowers.model.Favorites;
import com.example.bloomedflowers.model.Flowers;
import com.example.bloomedflowers.model.Users;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Bloomed_Flowers";
    private static final String TABLE_USERS = "users_table";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";

    //Flowers table
    private static final String TABLE_FLOWERS = "flowers";
    private static final String FLOWERS_ID = "flowers_id";
    private static final String FLOWERS_IMAGE = "flowers_image";
    private static final String FLOWERS_NAME = "flowers_name";
    private static final String FLOWERS_TYPE = "flowers_type";
    private static final String FLOWERS_SEASON = "flowers_seasons";
    private static final String FLOWERS_GROWTH = "flowers_growth";
    private static final String FLOWERS_METHOD = "flowers_method";

    //Favorites table
    private static final String TABLE_FAVORITES = "favorites";
    private static final String FAVORITES_ID = "favorites_id";
    private static final String FAVORITES_FLOWERS_ID = "favorites_flower_id";
    private static final String FAVORITES_USER_ID = "favorites_user_id";
    private static final String FAVORITES_IMAGE = "favorites_image";
    private static final String FAVORITES_NAME = "favorites_name";
    private static final String FAVORITES_TYPE = "favorites_type";
    private static final String FAVORITES_SEASON = "favorites_seasons";
    private static final String FAVORITES_GROWTH = "favorites_growth";
    private static final String FAVORITES_METHOD = "favorites_method";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        //UsersTable
        db.execSQL(" CREATE TABLE " + TABLE_USERS + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT NOT NULL, " +
                KEY_EMAIL + " TEXT NOT NULL, " +
                KEY_PHONE + " TEXT NOT NULL, " +
                KEY_PASSWORD + " TEXT NOT NULL);"
        );

        db.execSQL(" CREATE TABLE " + TABLE_FLOWERS + " (" +
                FLOWERS_ID + " INTEGER PRIMARY KEY, " +
                FLOWERS_IMAGE + " TEXT NOT NULL, " +
                FLOWERS_NAME + " TEXT NOT NULL, " +
                FLOWERS_TYPE + " TEXT NOT NULL, " +
                FLOWERS_SEASON + " TEXT NOT NULL, " +
                FLOWERS_GROWTH + " TEXT NOT NULL, " +
                FLOWERS_METHOD + " TEXT NOT NULL);"
        );

        db.execSQL(" CREATE TABLE " + TABLE_FAVORITES + " (" +
                FAVORITES_ID + " INTEGER PRIMARY KEY, " +
                FAVORITES_FLOWERS_ID + " TEXT NOT NULL, " +
                FAVORITES_USER_ID + " TEXT NOT NULL, " +
                FAVORITES_IMAGE + " TEXT NOT NULL, " +
                FAVORITES_NAME + " TEXT NOT NULL, " +
                FAVORITES_TYPE + " TEXT NOT NULL, " +
                FAVORITES_SEASON + " TEXT NOT NULL, " +
                FAVORITES_GROWTH + " TEXT NOT NULL, " +
                FAVORITES_METHOD + " TEXT NOT NULL);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLOWERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);

        onCreate(db);
    }

    public void register(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, users.getUserName());
        values.put(KEY_EMAIL, users.getEmail());
        values.put(KEY_PHONE, users.getPhone());
        values.put(KEY_PASSWORD, users.getPassword());

        db.insert(TABLE_USERS, null, values);
        db.close();
    }


    public List<Users> getAllUsers() {
        List<Users> usersList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Users users = new Users();
                users.setId(Integer.parseInt(cursor.getString(0)));
                users.setUserName(cursor.getString(1));
                users.setEmail(cursor.getString(2));
                users.setPhone(cursor.getString(3));
                users.setPassword(cursor.getString(4));

                usersList.add(users);
            } while (cursor.moveToNext());
        }

        return usersList;
    }


    public int updatePassword(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, user.getPassword());

        // updating row
        return db.update(TABLE_USERS, values, KEY_EMAIL + " = ?",
                new String[]{String.valueOf(user.getEmail())});
    }


    public void deleteUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }


    //ADD Flower
    public void addFlower(Flowers flowers) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FLOWERS_IMAGE, flowers.getImage());
        values.put(FLOWERS_NAME, flowers.getName());
        values.put(FLOWERS_TYPE, flowers.getType());
        values.put(FLOWERS_SEASON, flowers.getSeason());
        values.put(FLOWERS_GROWTH, flowers.getGrowthRate());
        values.put(FLOWERS_METHOD, flowers.getMethodOfCare());

        // Inserting Row
        db.insert(TABLE_FLOWERS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    public void addFavorite(Favorites favorites) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FAVORITES_FLOWERS_ID, favorites.getFlowerId());
        values.put(FAVORITES_USER_ID, favorites.getUserId());
        values.put(FAVORITES_IMAGE, favorites.getImage());
        values.put(FAVORITES_NAME, favorites.getName());
        values.put(FAVORITES_TYPE, favorites.getType());
        values.put(FAVORITES_SEASON, favorites.getSeason());
        values.put(FAVORITES_GROWTH, favorites.getGrowthRate());
        values.put(FAVORITES_METHOD, favorites.getMethodOfCare());

        // Inserting Row
        db.insert(TABLE_FAVORITES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public void updateFlower(Flowers flowers) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FLOWERS_ID, flowers.getId());
        values.put(FLOWERS_IMAGE, flowers.getImage());
        values.put(FLOWERS_NAME, flowers.getName());
        values.put(FLOWERS_TYPE, flowers.getType());
        values.put(FLOWERS_SEASON, flowers.getSeason());
        values.put(FLOWERS_GROWTH, flowers.getGrowthRate());
        values.put(FLOWERS_METHOD, flowers.getMethodOfCare());

        // Inserting Row
        db.update(TABLE_FLOWERS, values, FLOWERS_ID+"= ?", new String[]{String.valueOf(flowers.getId())});
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    //GET All Flowers
    public List<Flowers> getAllFlowers() {
        List<Flowers> taskList = new ArrayList<>();
        // Select All Query

        String selectQuery = "SELECT  * FROM " + TABLE_FLOWERS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Flowers flowers = new Flowers();
                flowers.setId(Integer.parseInt(cursor.getString(0)));
                flowers.setImage(cursor.getString(1));
                flowers.setName(cursor.getString(2));
                flowers.setType(cursor.getString(3));
                flowers.setSeason(cursor.getString(4));
                flowers.setGrowthRate(cursor.getString(5));
                flowers.setMethodOfCare(cursor.getString(6));


                // Adding contact to list
                taskList.add(flowers);
            } while (cursor.moveToNext());
        }

        // return contact list
        return taskList;
    }
    public List<Favorites> getAllFavorites() {
        List<Favorites> taskList = new ArrayList<>();
        // Select All Query

        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITES;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Favorites favorites = new Favorites();
                favorites.setId(Integer.parseInt(cursor.getString(0)));
                favorites.setFlowerId(Integer.parseInt(cursor.getString(1)));
                favorites.setUserId(Integer.parseInt(cursor.getString(2)));
                favorites.setImage(cursor.getString(3));
                favorites.setName(cursor.getString(4));
                favorites.setType(cursor.getString(5));
                favorites.setSeason(cursor.getString(6));
                favorites.setGrowthRate(cursor.getString(7));
                favorites.setMethodOfCare(cursor.getString(8));


                // Adding contact to list
                taskList.add(favorites);
            } while (cursor.moveToNext());
        }

        // return contact list
        return taskList;
    }

    //Delete Flower
    public void deleteFlower(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FLOWERS, FLOWERS_ID + " = ?",new String[]{String.valueOf(id)} );
        db.close();
    }

    //Delete Favorite
    public void deleteFavorites(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, FAVORITES_ID + " = ?",new String[]{String.valueOf(id)} );
        db.close();
    }

//    public void addFriends(Friend friend) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(USER_ID, friend.getUserId());
//        values.put(NAME, friend.getName());
//        values.put(USERNAME, friend.getUserName());
//        values.put(PHONENUMBER, friend.getNumber());
//        values.put(IMAGE_URI, friend.getImageUri());
//
//
//        // Inserting Row
//        db.insert(FRIEND, null, values);
//        //2nd argument is String containing nullColumnHack
//        db.close(); // Closing database connection
//    }

//    public void updateFriend(Friend friend) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(USER_ID, friend.getUserId());
//        values.put(NAME, friend.getName());
//        values.put(USERNAME, friend.getUserName());
//        values.put(PHONENUMBER, friend.getNumber());
//        values.put(IMAGE_URI, friend.getImageUri());
//
//
//        // Inserting Row
//        db.update(FRIEND, values, FRIEND_ID+"= ?", new String[]{String.valueOf(friend.getId())});
//        //2nd argument is String containing nullColumnHack
//        db.close(); // Closing database connection
//    }


//    public List<Friend> getAllFriends() {
//        List<Friend> friendList = new ArrayList<>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + FRIEND;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Friend friend = new Friend();
//                friend.setId(Integer.parseInt(cursor.getString(0)));
//                friend.setUserId(Integer.parseInt(cursor.getString(1)));
//                friend.setName(cursor.getString(2));
//                friend.setUserName(cursor.getString(3));
//                friend.setNumber(cursor.getString(4));
//                friend.setImageUri(cursor.getString(5));
//
//
//                // Adding contact to list
//                friendList.add(friend);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return friendList;
//    }

//    public void deleteFriend(int id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(FRIEND, FRIEND_ID + " = ?",new String[]{String.valueOf(id)} );
//        db.close();
//    }
}