package controller;

import model.Database;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    Database db = new Database();

    public boolean loadFromFile(File file, JFrame parent) throws IOException {
        return db.loadFromFile(file,parent);
    }

    public int getCount(){
        return db.getCount();
    }
    
    public ArrayList<String> getTopTen()
    {
        return db.getTopTen();
    }

    public String byPriorityQueue(){
        return db.byPriorityQueue();
    }
    public String byArrayList(){
        return db.byArrayList();
    }
    public String byTreeMap(){
        return db.byTreeMap();
    }
    public void displayPage(String filePath, JTextArea textArea) throws IOException {
        db.displayPage(filePath,textArea);
    }
    public void highlight(JTextArea textArea,ArrayList<String> patterns,String text){
        db.highlighter(textArea, patterns,text);
    }
    public void removeHighlights(JTextArea textArea)
    {
        db.removeHighlights(textArea);
    }
    
}
