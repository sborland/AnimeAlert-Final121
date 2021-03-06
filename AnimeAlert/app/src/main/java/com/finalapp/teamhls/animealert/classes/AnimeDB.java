package com.finalapp.teamhls.animealert.classes;

/**
 * AnimeDB allows us to perform actions on the CurrentChart.db database
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class AnimeDB {
    private DBHelper dbHelper;
    public static String LOG_TAG = "My Log Tag";

    public AnimeDB(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insert(AnimeChart chart) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AnimeChart.KEY_malNum, chart.malNum);
        values.put(AnimeChart.KEY_title,chart.title);
        values.put(AnimeChart.KEY_airDate, chart.airDate);
        values.put(AnimeChart.KEY_simulCast, chart.simulCast);
        values.put(AnimeChart.KEY_isShort, chart.isShort);
        values.put(AnimeChart.KEY_currEp, chart.currEp);
        values.put(AnimeChart.KEY_sum, chart.sum);
        values.put(AnimeChart.KEY_img, chart.img);


        // Inserting Row
        db.insert(AnimeChart.TABLE, null, values);
        db.close(); // Closing database connection
    }

    public void delete(int malNum) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(AnimeChart.TABLE, AnimeChart.KEY_malNum + "= ?", new String[] { String.valueOf(malNum) });
        db.close(); // Closing database connection
    }

    public void update(AnimeChart chart) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(AnimeChart.KEY_malNum, chart.malNum);
        values.put(AnimeChart.KEY_title,chart.title);
        values.put(AnimeChart.KEY_airDate, chart.airDate);
        values.put(AnimeChart.KEY_simulCast, chart.simulCast);
        values.put(AnimeChart.KEY_isShort, chart.isShort);
        values.put(AnimeChart.KEY_currEp, chart.currEp);
        values.put(AnimeChart.KEY_sum, chart.sum);
        values.put(AnimeChart.KEY_img, chart.img);


        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(AnimeChart.TABLE, values, AnimeChart.KEY_malNum + "= ?", new String[] { String.valueOf(chart.malNum) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>>  getAnimeChart() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                AnimeChart.KEY_title + "," +
                AnimeChart.KEY_malNum + "," +
                AnimeChart.KEY_airDate + "," +
                AnimeChart.KEY_simulCast + "," +
                AnimeChart.KEY_isShort + "," +
                AnimeChart.KEY_currEp + ","+
                AnimeChart.KEY_sum + ","+
                AnimeChart.KEY_img+
                " FROM " + AnimeChart.TABLE;
        ArrayList<HashMap<String, String>> animeList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> anime = new HashMap<String, String>();
                anime.put("title", cursor.getString(cursor.getColumnIndex(AnimeChart.KEY_title)));
                anime.put("malNum", cursor.getString(cursor.getColumnIndex(AnimeChart.KEY_malNum)));
                anime.put("airDate", cursor.getString(cursor.getColumnIndex(AnimeChart.KEY_airDate)));
                anime.put("simulCast", cursor.getString(cursor.getColumnIndex(AnimeChart.KEY_simulCast)));
                anime.put("isShort", cursor.getString(cursor.getColumnIndex(AnimeChart.KEY_isShort)));
                anime.put("currEp", cursor.getString(cursor.getColumnIndex(AnimeChart.KEY_currEp)));
                anime.put("sum", cursor.getString(cursor.getColumnIndex(AnimeChart.KEY_sum)));
                anime.put("img", cursor.getString(cursor.getColumnIndex(AnimeChart.KEY_img)));
                animeList.add(anime);

            } while (cursor.moveToNext());
        }
        //Log.i(LOG_TAG,animeList.size()+"");

        cursor.close();
        db.close();

        return animeList;

    }

    public AnimeChart getAnimeByMalNum(int malNum){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                AnimeChart.KEY_title + "," +
                AnimeChart.KEY_malNum + "," +
                AnimeChart.KEY_airDate + "," +
                AnimeChart.KEY_simulCast + "," +
                AnimeChart.KEY_isShort + "," +
                AnimeChart.KEY_currEp + ","+
                AnimeChart.KEY_sum + ","+
                AnimeChart.KEY_img +
                " FROM " + AnimeChart.TABLE
                + " WHERE " +
                AnimeChart.KEY_malNum + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        AnimeChart chart = new AnimeChart();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(malNum) } );

        if (cursor.moveToFirst()) {
            do {
                chart.title =cursor.getString(cursor.getColumnIndex(AnimeChart.KEY_title));
                chart.malNum =cursor.getInt(cursor.getColumnIndex(AnimeChart.KEY_malNum));
                chart.airDate  =cursor.getLong(cursor.getColumnIndex(AnimeChart.KEY_airDate));
                chart.simulCast =cursor.getString(cursor.getColumnIndex(AnimeChart.KEY_simulCast));
                chart.isShort  =cursor.getString(cursor.getColumnIndex(AnimeChart.KEY_isShort));
                chart.currEp =cursor.getInt(cursor.getColumnIndex(AnimeChart.KEY_currEp));
                chart.sum =cursor.getString(cursor.getColumnIndex(AnimeChart.KEY_sum));
                chart.img = cursor.getString(cursor.getColumnIndex(AnimeChart.KEY_img));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return chart;
    }

}