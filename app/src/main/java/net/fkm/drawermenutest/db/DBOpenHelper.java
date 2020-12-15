package net.fkm.drawermenutest.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    final static String DBNAME="experss_db.db";
    final static int VERSION=1;

    public DBOpenHelper(@Nullable Context context) {//创建数据库
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建用户表
        db.execSQL("create table 'user'(" +
                "'user_id' varvhar(20) not null ," +
                "'password' varchar(20) not null," +
                "'user_status' int not null," +
                "'teme' int not null," +
                "'user_con' int not null,"+
                "'user_total' int not null," +
                "'user_date' varchar(20)  ," +
                "primary key('user_id'));");

        db.execSQL("insert into user values('u_101','123456','0','0',0,'0','');");
        db.execSQL("insert into user values('u_102','123456','0','0',0,'0','');");

        //创建清单表
        db.execSQL("create table 'list'("+
                "`list_id` INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "`user_id` varchar(20) not null,"+
                "`list_title` varchar(40) not null,"+
                "`describe` varchar(255) ,"+
                "`list_status` int ,"+
                "`priority` int,"+
                "`isPerfection` int,"+
                "`time` varchar(20) );");
        db.execSQL("insert into `list` values ('1','u_101','收集箱','511321989800201150','0','0','0','')");
        db.execSQL("insert into `list` values ('2','u_101','今天','511321989800201150','1','1','0','')");
        db.execSQL("insert into `list` values ('3','u_101','学习','511321989800201150','2','2','0','')");
        db.execSQL("insert into `list` values ('4','u_101','锻炼','511321989800201150','3','3','0','')");
        db.execSQL("insert into `list` values ('5','u_101','工作','511321989800201150','4','2','0','')");


        //创建习惯表
        db.execSQL("create table 'habit'("+
                "`habit_id` INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "`list_title` varchar(40) not null,"+
                "`habit_status` int ,"+
                "`habit_con` int ,"+
                "`habit_total` int,"+
                "`user_id` varchar(20) not null,"+
                "`habit_date` varchar(20));"
        );

        //预存数据
        db.execSQL("insert into `habit` values ('1','afaawefw','0','0','0','u_101','')");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
