package com.gios.airindex.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.gios.airindex.model.AirIndexStation;
import com.gios.airindex.model.AqIndex;
import com.gios.airindex.model.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Database handler.
 *
 * @author Dawid Popiołkiewicz
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String CLASS_TAG = "DatabaseHandler";
    private static final String DATABASE_NAME = "AirIndex";
    private static final String TABLE_NAME = "Station";
    private static final String KEY_ID = "id";
    private static final String KEY_STATION_ID = "stationId";
    private static final String KEY_NAME = "name";
    private static final String KEY_CITY = "city";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_CALC_DATE = "calcDate";
    private static final String KEY_INDEX_LEVEL_NAME = "indexLevelName";

    private static final String NO_DATA = "Brak danych";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_STATION_ID + " INTEGER not null, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_CITY + " TEXT NOT NULL, " +
            KEY_ADDRESS + " TEXT, " +
            KEY_CALC_DATE + " TEXT NOT NULL, " +
            KEY_INDEX_LEVEL_NAME + " TEXT NOT NULL)";


    private SQLiteDatabase database;

    /**
     * Instantiates a new Database handler.
     *
     * @param ctx the ctx
     */
    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("drop table " + DATABASE_NAME);
            this.onCreate(db);
        }
    }


    /**
     * Add station long.
     *
     * @param station the station
     * @param aqIndex the aq index
     * @return the long
     */
    public long saveOrUpdateStation(Station station, AqIndex aqIndex) {
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_STATION_ID, station.getId());
        contentValues.put(KEY_NAME, station.getName());
        contentValues.put(KEY_CITY, station.getCity().getName());
        if (station.getAddress() == null || station.getAddress().isEmpty()) {
            contentValues.put(KEY_ADDRESS, station.getName());

        } else {
            contentValues.put(KEY_ADDRESS, station.getAddress());
        }
        if (aqIndex.getCalcDate() == null || aqIndex.getCalcDate().isEmpty()) {
            contentValues.put(KEY_CALC_DATE, NO_DATA);
        } else {
            contentValues.put(KEY_CALC_DATE, aqIndex.getCalcDate());
        }

        if (aqIndex.getStIndexLevel() == null) {
            contentValues.put(KEY_INDEX_LEVEL_NAME, NO_DATA);
        } else {
            contentValues.put(KEY_INDEX_LEVEL_NAME, aqIndex.getStIndexLevel().getIndexLevelName());
        }
        String id = String.valueOf(station.getId());
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_STATION_ID + " = " + id, null);

        int count = cursor.getCount();
        Log.i(CLASS_TAG, "Value of count: " + count);

        if (count <= 0) {
            long insert = database.insert(TABLE_NAME, null, contentValues);
            Log.i(CLASS_TAG, "Value of insert: " + insert);
            cursor.close();
            return insert;
        }

        String where = " stationId = ?";
        String[] whereArgs = {id};
        int update = database.update(TABLE_NAME, contentValues, where, whereArgs);
        Log.i(CLASS_TAG, "Value of update: " + update);
        return update;
    }

    /**
     * Gets station list by city.
     *
     * @param city the city
     * @return the station list by city
     */
    public List<AirIndexStation> getStationListByCity(String city) {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_CITY + " = " + "\"" + city + "\"", null);
        return getStationsList(cursor);
    }

    /**
     * Gets station list by index.
     *
     * @param index the index
     * @return the station list by index
     */
    public List<AirIndexStation> getStationListByIndex(String index) {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_INDEX_LEVEL_NAME + " = " + index, null);
        return getStationsList(cursor);
    }


    public List<AirIndexStation> getStationListByCityAndIndex(String city, String index) {
        database = this.getReadableDatabase();
        String s = "SELECT * FROM Station WHERE city like '%Warszawa%' AND indexLevelName like '%Dobry%'";
        final String sql = "\"SELECT * FROM \" + TABLE_NAME + \" WHERE \" + KEY_INDEX_LEVEL_NAME + \" = \" + \"\\\"\" + index + \"\\\"\" + \" AND \" + KEY_CITY + \" = \" + \"\\\"\" + city + \"\\\"\"";
        Cursor cursor = database.rawQuery(s, null);
        return getStationsList(cursor);
    }

    /**
     * Gets all cities.
     *
     * @return the all cities
     */
    public List<String> getAllCities() {
        List<String> citiesList = new ArrayList<>();
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT DISTINCT " + KEY_CITY + " FROM " + TABLE_NAME + " ORDER BY " + KEY_CITY + " ASC", null);
        if (cursor.getCount() == 0) return new ArrayList<>(0);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            citiesList.add(cursor.getString(0));
        }
        cursor.close();
        return citiesList;
    }

    public List<String> getAllIndexes() {
        List<String> indexesList = new ArrayList<>();
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT DISTINCT " + KEY_INDEX_LEVEL_NAME + " FROM " + TABLE_NAME + " ORDER BY " + KEY_INDEX_LEVEL_NAME + " ASC", null);
        if (cursor.getCount() == 0) return new ArrayList<>(0);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            indexesList.add(cursor.getString(0));
        }
        cursor.close();
        return indexesList;
    }


    /**
     * Gets all.
     *
     * @return the all
     */
    public List<AirIndexStation> getAll() {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_CITY + " ASC", null);
        return getStationsList(cursor);
    }

    private List<AirIndexStation> getStationsList(Cursor cursor) {
        List<AirIndexStation> airIndexStationList = new ArrayList<>();
        if (cursor.getCount() == 0) return new ArrayList<>(0);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            AirIndexStation airIndexStation = new AirIndexStation();
            airIndexStation.setId(cursor.getLong(1));
            airIndexStation.setStationName(cursor.getString(2));
            airIndexStation.setCity(cursor.getString(3));
            airIndexStation.setAddress(cursor.getString(4));
            airIndexStation.setCalcDate(cursor.getString(5));
            airIndexStation.setIndexLevelName(cursor.getString(6));
            airIndexStationList.add(airIndexStation);
        }
        cursor.close();
        return airIndexStationList;
    }
}
