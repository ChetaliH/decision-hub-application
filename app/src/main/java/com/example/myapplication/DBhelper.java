package com.example.myapplication;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBhelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    public DBhelper(Context context){
        super(context,"DecHub.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase mydb) {
       mydb.execSQL("create Table users(username Text primary key,password Text)");
       mydb.execSQL("CREATE TABLE Rules(ruleid TEXT PRIMARY KEY, ruleName TEXT, ruleDescription TEXT, conditions TEXT)");
       mydb.execSQL("create Table CardTypes(rule_id Text,cardType Text,interestRate Double)");
       mydb.execSQL("create Table IncomeRange(cardType Text,Income long)");
       mydb.execSQL("create Table KYC(age Integer, Pan Long,CIBIL Integer,National String)");
        insertInitialData(mydb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase mydb, int oldVersion, int newVersion) {

        mydb.execSQL("drop Table if EXISTS "+"users");
        mydb.execSQL("drop Table if EXISTS "+"Rules");
        mydb.execSQL("drop Table if EXISTS "+"CardTypes");
        mydb.execSQL("drop Table if EXISTS "+"IncomeRange");
        mydb.execSQL("drop Table if EXISTS "+"KYC");
        onCreate(mydb);

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    public void insertInitialData(SQLiteDatabase mydb) {

        List<ContentValues> rulesValuesList = new ArrayList<>();
        List<ContentValues> cardValuesList = new ArrayList<>();
        List<ContentValues> incomeValuesList = new ArrayList<>();

        ContentValues values1 = new ContentValues();
        values1.put("ruleid","IR001");
        values1.put("ruleName", "CIBIL Rule");
        values1.put("ruleDescription", "Interest Rate is 9.35");
        values1.put("conditions", "CIBIL Score between 700 to 750");
        rulesValuesList.add(values1);

        ContentValues values3=new ContentValues();
        values3.put("ruleid","CT002");
        values3.put("ruleName","Interest Rate Calculation");
        values3.put("ruleDescription","Interest rate is assigned based on type of card the owner possesses");
        values3.put("conditions","Level of Card");
        rulesValuesList.add(values3);


        ContentValues values2=new ContentValues();
        values2.put("ruleid","CT003");
        values2.put("ruleName","Interest Rate Calculation");
        values2.put("ruleDescription","Interest rate is calculated using Final and outstanding amount");
        values2.put("conditions","duration of loan");
        rulesValuesList.add(values2);

        for (ContentValues ruleValues : rulesValuesList) {
           mydb.insert("Rules", null, ruleValues);
        }

        Log.d(TAG, "Data inserted into Rules table successfully");

        ContentValues values4=new ContentValues();
        values4.put("rule_id","CT002");
        values4.put("cardType","Regular");
        values4.put("interestRate",1.99);
        cardValuesList.add(values4);

        ContentValues values5=new ContentValues();
        values5.put("rule_id","CT002");
        values5.put("cardType","Super premium");
        values5.put("interestRate",3.6);
        cardValuesList.add(values5);


        ContentValues values7=new ContentValues();
        values7.put("rule_id","CT002");
        values7.put("cardType","Premium");
        values7.put("interestRate",3.6);
        cardValuesList.add(values7);

        ContentValues values8=new ContentValues();
        values8.put("rule_id","CT002");
        values8.put("cardType","Business");
        values8.put("interestRate",3.6);
        cardValuesList.add(values8);

        for (ContentValues cardValues : cardValuesList) {
            mydb.insert("CardTypes", null, cardValues);
        }

        ContentValues values9=new ContentValues();
        values9.put("cardType","Standard credit card");
        values9.put("Income",35000);
        incomeValuesList.add(values9);

        ContentValues values10=new ContentValues();
        values10.put("cardType","Rewards credit card");
        values10.put("Income",60000);
        incomeValuesList.add(values10);

        ContentValues values11=new ContentValues();
        values11.put("cardType","Platinum credit card");
        values11.put("Income",100000);
        incomeValuesList.add(values11);

        ContentValues values12=new ContentValues();
        values12.put("cardType","Signature credit card");
        values12.put("Income",200000);
        incomeValuesList.add(values12);

        for (ContentValues incomeValues : incomeValuesList) {
            mydb.insert("IncomeRange", null, incomeValues);
        }


    }



    public boolean insertData(String username,String password){
        SQLiteDatabase myDB=this.getWritableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put("username",username);
        contentvalues.put("password",password);

        long result= myDB.insert("users",null,contentvalues);

        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkUsername(String username){
        SQLiteDatabase myDB=this.getWritableDatabase();
        Cursor cursor= myDB.rawQuery("select * from users where username=?",new String[]{username});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkUsernamePassword(String username,String password){
        SQLiteDatabase myDB=this.getWritableDatabase();
        Cursor cursor= myDB.rawQuery("select * from users where username=? and password=?",new String[]{username,password});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    public void updateRule(String RuleId,String newRuleName, String newRuleDescription,String newRuleCondition) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ruleid",RuleId);
        values.put("ruleName", newRuleName);
        values.put("ruleDescription", newRuleDescription);
        values.put("conditions",newRuleCondition);

        myDB.update("Rules", values, "ruleid=?", new String[]{RuleId});
        myDB.close();
    }

    public ArrayList<RulesModal> fetchRules(String id){
        SQLiteDatabase myDB=this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(id)};

        String query = "SELECT * FROM Rules WHERE ruleid = ?";
        Cursor cursor = myDB.rawQuery(query, selectionArgs);
        ArrayList<RulesModal> rulearr=new ArrayList<>();

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        RulesModal modal=new RulesModal();
                        modal.conditions=cursor.getString(3);
                        rulearr.add(modal);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return  rulearr;
    }

    public void updateInterestRate(String ruleId, String cardType, double newInterestRate) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("interestRate", newInterestRate);

        myDB.update("CardTypes", values, "rule_id=? AND cardType=?", new String[]{ruleId, cardType});
        myDB.close();
    }

    public double fetchInterestRate(String ruleId, String cardType) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        String query = "SELECT interestRate FROM CardTypes WHERE rule_id = ? AND cardType = ?";
        Cursor cursor = myDB.rawQuery(query, new String[]{ruleId, cardType});
        double interestRate = 0.0;

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    interestRate = cursor.getDouble(0);
                }
            } finally {
                cursor.close();
            }
        }

        return interestRate;
    }

    public void updateCardType(String incomeType, String newCardType) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("cardType", newCardType);

            myDB.update("IncomeRange", values, "Income=?", new String[]{incomeType});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (myDB != null && myDB.isOpen()) {
                myDB.close();
            }
        }
    }

    public String fetchCardTypeForIncome(long income) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        String query = "SELECT cardType FROM IncomeRange WHERE Income = ?";
        Cursor cursor = myDB.rawQuery(query, new String[]{String.valueOf(income)});
        String cardType="";

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    cardType = cursor.getString(0);
                    Log.d("DBDebug", "Card Type for Income " + income + ": " + cardType);
                }
            } finally {
                cursor.close();
                Log.d("DBDebug","no data");
            }
        }

        return cardType;
    }

    public boolean insertKYCData(int age, long pan, int cibil, String nationality) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("age", age);
        values.put("Pan", pan);
        values.put("CIBIL", cibil);
        values.put("National", nationality);

        long result = myDB.insert("KYC", null, values);

        return result != -1;
    }
}

