package com.example.bilalhamid.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.bilalhamid.model.ResultMusicModel

class DatabseHelper(context:Context):SQLiteOpenHelper(
    context,DATABASE_NAME,null,DATABAE_VERSION)
{
    companion object{
         private val DATABAE_VERSION=1
        private val DATABASE_NAME="BilalITunes"
        private val Table_name="ListOfSongs"
        private val Song_Type="SongType"
        private val KEY_ID="id"
        private val Artist_Name="ArtistName"
        private val Collection_Name="CollectionName"
        private val ArtWorkUrl="ArtWorkUrl60"
        private val Track_Price="trackPrice"
        private val PreviewUrl="PreviewUrl"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_SONG_DB = ("CREATE TABLE " + Table_name + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + Song_Type + " TEXT,"
                + Artist_Name + " TEXT,"
                + Collection_Name + " TEXT, "
                + ArtWorkUrl+ " TEXT ,"
                + Track_Price+" TEXT, "+
                PreviewUrl + " Text "+")")
        db?.execSQL(CREATE_SONG_DB)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
      db!!.execSQL("Drop table if exists"+ Table_name)
        onCreate(db)
    }
    fun insertAll(type:String,artist:String,collection:String,artWork:String,trackPrice:String,preview:String){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Song_Type, type)
        contentValues.put( Artist_Name,artist)
        contentValues.put( Collection_Name,collection )
        contentValues.put( ArtWorkUrl,artWork)
        contentValues.put(Track_Price, trackPrice )
        contentValues.put(PreviewUrl,preview)
        // Inserting Row
          db.insertWithOnConflict(Table_name, null, contentValues,SQLiteDatabase.CONFLICT_IGNORE)
        //2nd argument is String containing nullColumnHack
        //db.close() // Closing database connection

    }
    fun retrivePopMusic(typeMusic:String):List<ResultMusicModel>{
        val popList:ArrayList<ResultMusicModel> = ArrayList()
        val query="select * from "+ Table_name+" where SongType = '"+ typeMusic+"'"
        val db=this.readableDatabase
        val cursor:Cursor

             cursor =db.rawQuery(query,null)
        if(cursor.moveToFirst()){
            do{
                var type:String=cursor.getString(1)
                var artist:String=cursor.getString(2)
                var collection:String=cursor.getString(3)
                var artWork:String=cursor.getString(4)
                var trackPrice1:Double=cursor.getDouble(5)
                var preview:String=cursor.getString(6)
                val result=ResultMusicModel(trackName=type,artistName=artist,collectionName=collection,artworkUrl100 =artWork,trackPrice=trackPrice1 ,previewUrl=preview)
                popList.add(result)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return popList
    }

    fun deleteAll(typeMusic:String){
        val db=this.writableDatabase
        val query="delete  from "+ Table_name+" where SongType = '"+ typeMusic+"'"
        val cursor:Cursor
        cursor =db.rawQuery(query,null)
                if(cursor.moveToFirst()) {
                    do {
                        db.execSQL(query)
                    } while (cursor.moveToNext())
                }

    }


}