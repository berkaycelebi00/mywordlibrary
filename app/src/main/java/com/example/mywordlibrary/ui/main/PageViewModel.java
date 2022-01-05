package com.example.mywordlibrary.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mywordlibrary.database_helper.DatabaseHelper;
import com.example.mywordlibrary.models.Word;

import java.util.ArrayList;

public class PageViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    public int currentIndex = 1;
    private  ArrayList<Word> learningWords;
    private  ArrayList<Word> learnedWords;
    private LiveData<ArrayList<Word>> wordList = Transformations.map(mIndex, new Function<Integer, ArrayList<Word>>() {
        @Override
        public ArrayList<Word> apply(Integer input) {
            DatabaseHelper db = new DatabaseHelper(getApplication().getApplicationContext());
            if(input == 1){
                learningWords = db.getLearningWordList();
                 return learningWords;
            }else{
                learnedWords = db.getLearnedWordList();
                return learnedWords;
            }
        }
    });

    public PageViewModel(@NonNull Application application) {
        super(application);
    }

    public void setIndex(int index) {
        currentIndex = index;
        DatabaseHelper db = new DatabaseHelper(getApplication().getApplicationContext());
        learningWords = db.getLearningWordList();
        learnedWords = db.getLearningWordList();
        mIndex.setValue(index);
    }

    public LiveData<ArrayList<Word>> getText() {
        return wordList;
    }


}