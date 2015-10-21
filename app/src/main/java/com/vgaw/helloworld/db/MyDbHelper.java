package com.vgaw.helloworld.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.vgaw.helloworld.bean.Dir;
import com.vgaw.helloworld.bean.Memo;
import com.vgaw.helloworld.bean.MemoDir;

import java.util.ArrayList;

/**
 * Created by caojin on 15-10-12.
 */
public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "memo_clip.db";

    private static final int DB_VERSION = 1;

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create memo table
        db.execSQL("CREATE TABLE " + MemoTable.TABLE_NAME + "("
            + MemoTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + MemoTable.CONTENT + " TEXT,"
            + MemoTable.CREATION_DATA + " TEXT,"
            + MemoTable.HAS_DIR + " INTEGER"
        + ");");

        // create dir table
        db.execSQL("CREATE TABLE " + DirTable.TABLE_NAME + "("
            + DirTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DirTable.NAME + " TEXT,"
            + DirTable.COUNT + " INTEGER"
        + ");");

        // create memodir table
        db.execSQL("CREATE TABLE " + MemoDirTable.TABLE_NAME + "("
                + MemoDirTable.MEMO_ID + " INTEGER,"
                + MemoDirTable.DIR_ID + " INTEGER"
                + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*************************the CRUD of memo table*************************/
    // select * from tablename where id<nowId and has_dir!=1 order by id desc limit 0 1;
    /**
     * query the right one memo of the given id memo.
     * @param nowId    the current memo id or the max value.
     * @return default memo if got nothing.
     */
    public Memo getRightMemo(int nowId){
        SQLiteDatabase sd = getReadableDatabase();

        Cursor c = sd.query(MemoTable.TABLE_NAME, null,
                MemoTable.ID + "<" + nowId,
                null, null, null, MemoTable.ID + " desc", "1");
        Memo memo = new Memo();
        if (c != null && c.getCount() > 0){
            c.moveToFirst();
            memo.setId(c.getInt(c.getColumnIndex(MemoTable.ID)));
            memo.setContent(c.getString(c.getColumnIndex(MemoTable.CONTENT)));
            memo.setCreate_date(c.getString(c.getColumnIndex(MemoTable.CREATION_DATA)));
        }
        return memo;
    }

    // select * from tablename where id>nowId and has_dir!=1 order by id asc limit 0 1;
    /**
     * query the left one memo of the given id memo.
     * @param nowId    the current memo id or the max value.
     * @return default memo if got nothing.
     */
    public Memo getLeftMemo(int nowId){
        SQLiteDatabase sd = getReadableDatabase();

        Cursor c = sd.query(MemoTable.TABLE_NAME, null,
                MemoTable.ID + ">" + nowId,
                null, null, null, MemoTable.ID + " asc", "1");
        Memo memo = new Memo();
        if (c != null && c.getCount() > 0){
            c.moveToFirst();
            memo.setId(c.getInt(c.getColumnIndex(MemoTable.ID)));
            memo.setContent(c.getString(c.getColumnIndex(MemoTable.CONTENT)));
            memo.setCreate_date(c.getString(c.getColumnIndex(MemoTable.CREATION_DATA)));
        }
        return memo;
    }

    /**
     * save memo.
     * @param memo
     */
    public void saveMemo(Memo memo){
        SQLiteDatabase sd = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(MemoTable.CONTENT, memo.getContent());
        cv.put(MemoTable.CREATION_DATA, memo.getCreate_date());
        cv.put(MemoTable.HAS_DIR, 0);

        sd.insert(MemoTable.TABLE_NAME, MemoTable.CONTENT, cv);
    }

    /**
     * update the memo.
     * @param memo
     */
    public void updateMemo(Memo memo){
        SQLiteDatabase sd = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(MemoTable.CONTENT, memo.getContent());

        sd.update(MemoTable.TABLE_NAME, cv, MemoTable.ID + "=" + memo.getId(), null);
    }

    /**
     * return the memos that do not has a dir.
     * select * from memo_table where has_dir != 1;
     */
    public ArrayList<Memo> queryUndirMemo(){
        SQLiteDatabase sd = getReadableDatabase();

        ArrayList<Memo> memoList = new ArrayList<>();
        Cursor c = sd.query(MemoTable.TABLE_NAME, null, MemoTable.HAS_DIR + "!=1", null, null, null, MemoTable.ID + " desc");
        if (c == null || c.getCount() < 1){
            return null;
        }
        while (c.moveToNext()){
            Memo memo = new Memo();
            memo.setId(c.getInt(c.getColumnIndex(MemoTable.ID)));
            memo.setContent(c.getString(c.getColumnIndex(MemoTable.CONTENT)));
            memo.setCreate_date(c.getString(c.getColumnIndex(MemoTable.CREATION_DATA)));
            memoList.add(memo);
        }
        return memoList;
    }

    /**
     * set the column "has_file" to 1.
     * @param memo
     */
    public void hasFile(Memo memo){
        SQLiteDatabase sd = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(MemoTable.HAS_DIR, 1);

        sd.update(MemoTable.TABLE_NAME, cv, MemoTable.ID + "=" + memo.getId(), null);
    }

    /*************************the CRUD of dir table**************************/
    public ArrayList<Dir> queryDir(){
        SQLiteDatabase sd = getReadableDatabase();

        ArrayList<Dir> dirList = new ArrayList<>();
        Cursor c = sd.query(DirTable.TABLE_NAME, null, null, null, null, null, DirTable.ID + " desc");
        if (c == null || c.getCount() < 1){
            return null;
        }
        while (c.moveToNext()){
            Dir dir = new Dir();
            dir.setId(c.getInt(c.getColumnIndex(DirTable.ID)));
            dir.setName(c.getString(c.getColumnIndex(DirTable.NAME)));
            dir.setCount(c.getInt(c.getColumnIndex(DirTable.COUNT)));
            dirList.add(dir);
        }
        return dirList;
    }

    /**
     * return the dir list filtered by keyWord.
     * @param keyWord    if "", return all.
     * @return
     */
    public ArrayList<Dir> queryDirByKeyWord(String keyWord){
        if ("".equals(keyWord)){
            return queryDir();
        }
        SQLiteDatabase sd = getReadableDatabase();

        ArrayList<Dir> dirList = new ArrayList<>();
        Cursor c = sd.query(DirTable.TABLE_NAME, null, DirTable.NAME + " like '%" + keyWord + "%'", null, null, null, DirTable.ID + " desc");
        if (c == null || c.getCount() < 1){
            return null;
        }
        while (c.moveToNext()){
            Dir dir = new Dir();
            dir.setId(c.getInt(c.getColumnIndex(DirTable.ID)));
            dir.setName(c.getString(c.getColumnIndex(DirTable.NAME)));
            dir.setCount(c.getInt(c.getColumnIndex(DirTable.COUNT)));
            dirList.add(dir);
        }
        return dirList;
    }

    /**
     * judge whether the dir of name "keyWord" exists.
     * @param name
     * @return
     */
    public Dir queryDirByName(String name){
        Dir dir = null;
        SQLiteDatabase sd = getReadableDatabase();
        Cursor c = sd.query(DirTable.TABLE_NAME, null, DirTable.NAME + " = '" + name + "'", null, null, null, DirTable.ID + " desc");
        if (c.getCount() == 1){
            c.moveToFirst();
            dir = new Dir();
            dir.setId(c.getInt(c.getColumnIndex(DirTable.ID)));
            dir.setName(c.getString(c.getColumnIndex(DirTable.NAME)));
            dir.setCount(c.getInt(c.getColumnIndex(DirTable.COUNT)));
        }
        return dir;
    }

    /**
     * add dir to dir_table with the column "count" 0.
     * @param dir
     */
    public int addDir(Dir dir){
        SQLiteDatabase sd = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DirTable.NAME, dir.getName());
        cv.put(DirTable.COUNT, 0);

        return (int) sd.insert(DirTable.TABLE_NAME, DirTable.NAME, cv);
    }

    /**
     * plus the dir count.
     * @param dir
     */
    public void dirCountPlus(Dir dir){
        SQLiteDatabase sd = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DirTable.COUNT, dir.getCount()+1);

        sd.update(DirTable.TABLE_NAME, cv, DirTable.ID + "=" + dir.getId(), null);
    }

    /*************************the CRUD of memodir table**********************/
    public void addMemoDir(MemoDir memoDir){
        SQLiteDatabase sd = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(MemoDirTable.MEMO_ID, memoDir.getMemo_id());
        cv.put(MemoDirTable.DIR_ID, memoDir.getDir_id());

        sd.insert(MemoDirTable.TABLE_NAME, MemoDirTable.DIR_ID, cv);
    }
}
