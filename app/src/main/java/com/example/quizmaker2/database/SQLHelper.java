package com.example.quizmaker2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.quizmaker2.database.models.Answer;
import com.example.quizmaker2.database.models.Follow;
import com.example.quizmaker2.database.models.Question;
import com.example.quizmaker2.database.models.Quiz;
import com.example.quizmaker2.database.models.Record;
import com.example.quizmaker2.database.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="QuizMaker2.db";
    private static final int DATABASE_VERSION=1;

    public SQLHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
//        String Createsql="PRAGMA foreign_keys = ON;";
//        db.execSQL(Createsql);
        String Createsql="CREATE TABLE User(id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,password TEXT);";
        db.execSQL(Createsql);
        Createsql ="CREATE TABLE Quiz(id INTEGER PRIMARY KEY AUTOINCREMENT,time INTEGER,des TEXT, questpertest INTEGER, name TEXT, state TEXT,userid INTEGER,FOREIGN KEY (userid) REFERENCES User (id) ON DELETE CASCADE ON UPDATE NO ACTION);";
        db.execSQL(Createsql);
        Createsql ="CREATE TABLE Follow(followerid INTEGER,followingid INTEGER,PRIMARY KEY (followerid, followingid),FOREIGN KEY (followerid) REFERENCES User (id) ON DELETE CASCADE ON UPDATE NO ACTION,FOREIGN KEY (followingid) REFERENCES User (id) ON DELETE CASCADE ON UPDATE NO ACTION);";
        db.execSQL(Createsql);
        Createsql ="CREATE TABLE Question(id INTEGER PRIMARY KEY AUTOINCREMENT,context TEXT,quizID INTEGER,FOREIGN KEY (quizid) REFERENCES Quiz (id) ON DELETE CASCADE ON UPDATE NO ACTION);";
        db.execSQL(Createsql);
        Createsql ="CREATE TABLE Answer(id INTEGER PRIMARY KEY AUTOINCREMENT,isTrue INTEGER, context TEXT,qid INTEGER,FOREIGN KEY (qid) REFERENCES Question (id) ON DELETE CASCADE ON UPDATE NO ACTION);";
        db.execSQL(Createsql);
        Createsql ="CREATE TABLE Record(id INTEGER PRIMARY KEY AUTOINCREMENT,userid INTEGER,quizid INTEGER,timestamp TEXT,  percent double,FOREIGN KEY (userid) REFERENCES User (id) ON DELETE CASCADE ON UPDATE NO ACTION,FOREIGN KEY (quizid) REFERENCES Quiz (id) ON DELETE CASCADE ON UPDATE NO ACTION);";
        db.execSQL(Createsql);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<User> getAllUser(){
        List<User> list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor t=db.query("User",null,null,
                null,null,null,null);
        while(t!=null&&t.moveToNext()){
            int id=t.getInt(0);
            String username=t.getString(1);
            String password=t.getString(2);
            User user=new User(id,username,password);
            list.add(user);
        }
        return list;
    }

    public User checkLogin(String username,String password){
        List<User> list=getAllUser();
        for (int i=0;i<list.size();i++){
            if (list.get(i).getUsername().equals(username)&&list.get(i).getPassword().equals(password)){
                return list.get(i);
            }
        }
        return null;
    }


    public void register(String username,String password){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("username",username);
        values.put("password",password);
        db.insert("User",null,values);
    }

    public List<User> getAllUserExcept(User user){
        List<User> list=getAllUser();
        for (int i=0;i<list.size();i++){
            if (list.get(i).getId()==user.getId()){
                list.remove(i);
                break;
            }
        }
        return list;
    }

    public boolean isFollow(User a,User b){
        SQLiteDatabase db=getReadableDatabase();
        String where="followerid=? and followingid=?";
        String[] args={Integer.toString(a.getId()),Integer.toString(b.getId())};
        Cursor t=db.query("Follow",null,where,
                args,null,null,null);
        if (t!=null && t.moveToNext()){
            return true;
        }
        return false;
    }
    public void Follow(User a,User b){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("followerid",a.getId());
        values.put("followingid",b.getId());
        db.insert("Follow",null,values);
        System.out.println(a.getUsername()+" follow "+b.getUsername());
    }
    public void UnFollow(User a,User b){
        SQLiteDatabase db=getWritableDatabase();
        String where="followerid=? and followingid=?";
        String[] args={Integer.toString(a.getId()),Integer.toString(b.getId())};
        db.delete("Follow",where,args);
        System.out.println(a.getUsername()+" unfollow "+b.getUsername());
    }

    public int CountQuiz(User a){
        SQLiteDatabase db=getReadableDatabase();
        String where="userid=? ";
        String[] args={Integer.toString(a.getId())};
        Cursor t=db.query("Quiz",null,where,
                args,null,null,null);
        int count=0;
        while (t!=null && t.moveToNext()){
            count++;
        }
        return count;
    }

    public List<Quiz> getQuizByUser(User a){
        System.out.println("Get bt");
        List<Quiz> list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor t=db.rawQuery("SELECT distinct Quiz.* FROM Quiz,user,follow where quiz.userid=? or (user.id=follow.followerid and quiz.state='Public' and follow.followingid=quiz.userid and user.id=?)",
                new String[]{Integer.toString(a.getId()),Integer.toString(a.getId())});
        while (t!=null && t.moveToNext()){
            int id=t.getInt(0);
            int time=t.getInt(1);
            String des=t.getString(2);
            int questpertime=t.getInt(3);
            String name=t.getString(4);
            String state=t.getString(5);
            int userid=t.getInt(6);
            Quiz quiz=new Quiz(id,name,userid,des,time,questpertime,state);
            list.add(quiz);
        }
        return list;
    }
    public void createQuiz(Quiz quiz){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("insert into Quiz (time,des,questpertest,name,state,userid) values (?,?,?,?,?,?)",
                new Object[]{quiz.getTime(),quiz.getDescription(),quiz.getQuestionpertest(),quiz.getName(),"Private",quiz.getUserId()});
    }

    public String getUserNameByQuiz(Quiz quiz){
        SQLiteDatabase db=getReadableDatabase();
        Cursor t= db.rawQuery("select user.username from user,quiz where quiz.userid=user.id and quiz.id=?",new String[]{Integer.toString(quiz.getId())});
        if (t!=null && t.moveToNext()){
            String x=t.getString(0);
            return x;
        }
        return "";
    }

    public List<Question> getQuestionByQuiz(Quiz quiz){
        List<Question> list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor t= db.rawQuery("select * from question where quizid=?",new String[]{Integer.toString(quiz.getId())});
        while (t!=null && t.moveToNext()){
            int id=t.getInt(0);
            String context=t.getString(1);
            int quizid=t.getInt(2);
            Question question=new Question(id,context);
            list.add(question);
        }
        return list;
    }

    public int getQuestionCountByQuiz(Quiz quiz){
        return getQuestionByQuiz(quiz).size();
    }

    public void delQuestion(Question a){
        deleteAnswerbyQuestion(a);
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM QUESTION WHERE id=?",new Object[]{a.getId()});
    }

    public long addQuestion(Question a){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("context",a.getContent());
        values.put("quizid",a.getQuizid());
        return db.insert("Question",null,values);
    }
    public long addAnswer(Answer a){
        System.out.println("nho bo m nhe");
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("context",a.getContext());
        values.put("isTrue",a.isTrue());
        values.put("qid",a.getQuestionid());
        return db.insert("Answer",null,values);
    }
    public void editQuestion(Question a){
        deleteAnswerbyQuestion(a);
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("UPDATE QUESTION SET context=? where id=?",new Object[]{a.getContent(),a.getId()});
    }
    public void deleteAnswerbyQuestion(Question a){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM Answer WHERE qid=?",new Object[]{a.getId()});
    }

    public void deleteQuiz(Quiz a){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM quiz WHERE id=?",new Object[]{a.getId()});
    }

    public void editQuiz(Quiz a){
        SQLiteDatabase db=getWritableDatabase();

        db.execSQL("UPDATE quiz set name=?, des=?, time=?, questpertest=?, state=? WHERE id=?",
                new Object[]{a.getName(),a.getDescription(),a.getTime(),a.getQuestionpertest(),a.getState(),a.getId()});
    }

    public List<Answer> getAnswerByQuestion(Question a){
        List<Answer> list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor t=db.rawQuery("SELECT * FROM ANSWER WHERE qid=?",new String[]{Integer.toString(a.getId())});
        while(t!=null&&t.moveToNext()){
            int id=t.getInt(0);
            String context=t.getString(2);
            int IsTrue=t.getInt(1);
            Answer ans=new Answer(id,context,IsTrue==1?true:false);
            list.add(ans);
        }
        return list;
    }

    public void DeleteAnswerByQuestion(Question a){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM ANSWER WHERE qid=?",new Object[]{a.getId()});
    }

    public void addRecord(Record a){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("userid",a.getUserid());
        values.put("quizid",a.getQuizid());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        values.put("timestamp",sdf.format(a.getTimestamp()));
        values.put("percent",a.getPercent());
        db.insert("Record",null,values);
        System.out.println("added record");
    }

    public List<Record> getRecordByQuiz(Quiz q){
        SQLiteDatabase db=getReadableDatabase();
        List<Record> list=new ArrayList<>();
        Cursor t=db.rawQuery("SELECT * FROM RECORD WHERE Quizid=?",new String[]{Integer.toString(q.getId())});
        while(t!=null&&t.moveToNext()){
            int id=t.getInt(0);
            int userid=t.getInt(1);
            int quizid=t.getInt(2);
            String  time=t.getString(3);
            double percent=t.getDouble(4);
            SimpleDateFormat formatter5=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Record record= null;
            try {
                record = new Record(id,userid,quizid,formatter5.parse(time),percent);
            } catch (ParseException e) {
                System.out.println("dmmmmm");
                e.printStackTrace();
            }
            list.add(record);
        }
        return list;

    }

    public void deleteRecord(Record a){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM RECORD where id=?",new Object[]{a.getId()});
    }
    public String getUsernamebyId(int id){
        SQLiteDatabase db=getReadableDatabase();
        Cursor t=db.rawQuery("SELECT username from user where id=?",new String[]{Integer.toString(id)});
        if (t!=null&&t.moveToNext()){
            String name=t.getString(0);
            return name;
        }
        return "";
    }

    public List<Quiz> getQuizByUsername(String query,User a){
        SQLiteDatabase db=getReadableDatabase();
        System.out.println("Get vip");
        List<Quiz> list=new ArrayList<>();
        query="%"+query+"%";
        Cursor t= db.rawQuery("SELECT distinct Quiz.* FROM Quiz,user,follow where quiz.name like ?  and (quiz.userid=? or (user.id=follow.followerid and quiz.state='Public' and follow.followingid=quiz.userid and user.id=?))"
        ,new String[]{query,Integer.toString(a.getId()),Integer.toString(a.getId())});
        while (t!=null && t.moveToNext()){
            int id=t.getInt(0);
            int time=t.getInt(1);
            String des=t.getString(2);
            int questpertime=t.getInt(3);
            String name=t.getString(4);
            String state=t.getString(5);
            int userid=t.getInt(6);
            Quiz quiz=new Quiz(id,name,userid,des,time,questpertime,state);
            list.add(quiz);
        }
        return list;
    }
}
