package com.example.mywordlibrary.models;

public class Word {

    private int id;
    private String word;
    private String meaning;
    private String spelling;
    private String example;
    private int isLearning;



    public Word(String word, String meaning, String spelling, String example,int isLearning) {

        this.setWord(word);
        this.setMeaning(meaning);
        this.setSpelling(spelling);
        this.setExample(example);
        this.setLearning(isLearning);
    }
    public Word(int id,String word, String meaning, String spelling, String example,int isLearning) {
        this.id = id;
        this.setWord(word);
        this.setMeaning(meaning);
        this.setSpelling(spelling);
        this.setExample(example);
        this.setLearning(isLearning);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public int isLearning() {
        return isLearning;
    }

    public void setLearning(int learning) {
        isLearning = learning;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
