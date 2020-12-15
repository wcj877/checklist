package net.fkm.drawermenutest.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.fkm.drawermenutest.db.DBOpenHelper;
import net.fkm.drawermenutest.model.ListInfo;
import net.fkm.drawermenutest.utils.Constants;

import java.util.ArrayList;

public class ListDao {
    private SQLiteOpenHelper helper;//一个帮助程序类，用于管理数据库创建和版本管理。
    private SQLiteDatabase db;//SQLiteDatabase具有创建，删除，执行SQL命令和执行其他常见数据库管理任务的方法。
    public ListDao(Context context){
        helper=new DBOpenHelper(context);
    }

    //获取id为userId的用户的指定状态的清单
    public ArrayList<ListInfo> queryAll(String userId){
        ArrayList<ListInfo> listInfoList = new ArrayList<>();

        db=helper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor;

        if (Constants.listStatus>0){
            if (Constants.showCompleted){
                if (Constants.sortBy != null){
                    cursor = db.rawQuery("select * from list where user_id='" + userId + "' and list_status='" + Constants.listStatus + "' ORDER BY " + Constants.sortBy + " DESC" , null);
                } else{
                    cursor = db.rawQuery("select * from list where user_id='" + userId + "' and list_status='" + Constants.listStatus + "'", null);
                }
            } else{
                if (Constants.sortBy != null){
                    cursor = db.rawQuery("select * from list where user_id='" + userId + "' and list_status='" + Constants.listStatus + "' and isPerfection =0  ORDER BY " + Constants.sortBy + " DESC" , null);
                } else{
                    cursor = db.rawQuery("select * from list where user_id='" + userId + "' and list_status='" + Constants.listStatus + "' and isPerfection =0", null);
                }
            }


        } else {

            if (Constants.showCompleted){
                if (Constants.sortBy != null){
                    cursor = db.rawQuery("select * from list where user_id='" + userId + "' ORDER BY " + Constants.sortBy + " DESC" , null);
                } else{
                    cursor = db.rawQuery("select * from list where user_id='" + userId + "'", null);
                }
            } else{
                if (Constants.sortBy != null){
                    cursor = db.rawQuery("select * from list where user_id='" + userId + "' and isPerfection =0  ORDER BY " + Constants.sortBy + " DESC" , null);
                } else{
                    cursor = db.rawQuery("select * from list where user_id='" + userId + "' and isPerfection =0", null);
                }
            }

//            cursor = db.rawQuery("select * from list where user_id='" + userId + "'", null);
        }

        while (cursor.moveToNext()){
            ListInfo list=new ListInfo();

            list.setUserId(cursor.getString(cursor.getColumnIndex("user_id")));
            list.setListId(cursor.getInt(cursor.getColumnIndex("list_id")));
            list.setListTitle(cursor.getString(cursor.getColumnIndex("list_title")));
            list.setDescribe(cursor.getString(cursor.getColumnIndex("describe")));
            list.setListStatus(cursor.getInt(cursor.getColumnIndex("list_status")));
            list.setPriority(cursor.getInt(cursor.getColumnIndex("priority")));
            list.setIsPerfection(cursor.getInt(cursor.getColumnIndex("isPerfection")));
            list.setTime(cursor.getString(cursor.getColumnIndex("time")));
            listInfoList.add(list);
        }

        return listInfoList;
    }
}
