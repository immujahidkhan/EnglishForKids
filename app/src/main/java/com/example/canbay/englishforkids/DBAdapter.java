package com.example.canbay.englishforkids;

/**
 * Created by canbay on 24.12.2016.
 */
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ImageView;


/**
 * Created by alfiroj on 5/13/16.
 */
public class DBAdapter extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;

    // Database Name
    private static final String DATABASE_NAME = "onlineicttutorQuiz";

    // Table name
    private static final String TABLE_QUESTION = "question";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_QUESION = "question";
    private static final String KEY_QUESION_Imageid = "imageid";
    private static final String KEY_ANSWER = "answer"; //correct option
    private static final String KEY_OPTA= "opta"; //option a
    private static final String KEY_OPTB= "optb"; //option b
    private static final String KEY_OPTC= "optc"; //option c
    private static final String KEY_OPTD= "optd"; //option d


    private SQLiteDatabase myDatabase;

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        myDatabase=db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +KEY_QUESION_Imageid + " INTEGER ,"+ KEY_QUESION
                + " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
                +KEY_OPTB +" TEXT, "+KEY_OPTC +" TEXT, "+KEY_OPTD+" TEXT)";

        db.execSQL(sql);

        addQuestionsWithImage();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);

        // Create tables again
        onCreate(db);
    }

    public int rowCount()
    {
        int row=0;
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        row=cursor.getCount();
        return row;
    }

    public List<Question> getAllQuestions() {

        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION;
        myDatabase=this.getReadableDatabase();

        Cursor cursor = myDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setId(cursor.getInt(0));
                quest.setQUESTIONImageId(cursor.getInt(1));
                quest.setQUESTION(cursor.getString(2));
                quest.setANSWER(cursor.getString(3));
                quest.setOptionA(cursor.getString(4));
                quest.setOptionB(cursor.getString(5));
                quest.setOptionC(cursor.getString(6));
                quest.setOptionD(cursor.getString(7));

                quesList.add(quest);

            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }


    private void addQuestionsWithImage()
    {
        //format is question-option1-option2-option3-option4-answer

        Question q1=new Question(R.drawable.apple,"Soru1","Şık1", "Şık2", "Şık3", "Şık4","Şık2");
        this.addQuestionWithImage(q1);

        Question q2=new Question(0,"Soru2","1", "2", "3", "4","1");
        this.addQuestionWithImage(q2);

        Question q3=new Question(0,"Soru3","1", "2", "3", "4","1");
        this.addQuestionWithImage(q3);

        Question q4=new Question(0,"Soru4","1", "2", "3", "4","5");
        this.addQuestionWithImage(q4);

        Question q5=new Question(0,"LAN is ","Local Area Connection", "Landline Area Network", "Land Area Networking", "Link Access Network","Local Area Connection");
        this.addQuestionWithImage(q5);

    }

    // Adding new question

    public void addQuestionWithImage(Question quest) {

        ContentValues values = new ContentValues();
        values.put(KEY_QUESION_Imageid,quest.getQUESTIONImageId());
        values.put(KEY_QUESION, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        values.put(KEY_OPTA, quest.getOptionA());
        values.put(KEY_OPTB, quest.getOptionB());
        values.put(KEY_OPTC, quest.getOptionC());
        values.put(KEY_OPTD, quest.getOptionD());

        // Inserting Row
        myDatabase.insert(TABLE_QUESTION, null, values);
    }


}
