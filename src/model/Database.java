package model;

import gui.MainFrame;
import java.awt.Color;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

public class Database {
    private ArrayList<String> topTen;
    private LinkedList<String> words;
    private int count;
    private String text1 = null;

    public Database(){
        words = new LinkedList<>();
        topTen = new ArrayList<String>();
    }

    public ArrayList<String> getTopTen() {
        return this.topTen;
    }
    
    class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter{
        public MyHighlightPainter(Color c) {
            super(c);
        }
    }
    int counter = 0;
    Highlighter.HighlightPainter myPainter = new MyHighlightPainter(Color.green);
    public void removeHighlights(JTextArea textArea)
    {
        Highlighter hilits = textArea.getHighlighter();
        hilits.removeAllHighlights();
    }
    
    public void highlighter(JTextComponent textArea,ArrayList<String> patterns,String text2)
    {
        //removeHighlights((JTextArea) textArea);
        try {
            textArea.setText(text2);
            Highlighter hilits = textArea.getHighlighter();
            for(int i = 0;i<patterns.size();i++)
            {
            Document doc1;
            /*if(counter == 0)
            {
                doc1 = textArea.getDocument();
                text1 = doc1.getText(0, doc1.getLength());
                textArea.setText(text1);
            }*/
            //counter++;
            String pattern = patterns.get(i);
            //hilits.getHighlights();
            Document doc = textArea.getDocument();
            String text = doc.getText(0, doc.getLength());
            String pattern1 = pattern.trim();
            pattern1 += " ";
            int pos = 0;
            //textArea.setHighlighter(null);
            while((pos = text.indexOf(pattern1,pos)) >= 0)
            {
                hilits.addHighlight(pos, pos + pattern1.length() - 1, myPainter);
                pos += pattern1.length();
            }
            }
        } catch (Exception e) {
        }
    }
    
    public boolean loadFromFile(File file, JFrame parent) throws IOException {
        try {
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            words.clear();
            for (String line : lines){
                String[] values = line.split(" ",-1);
                for (int i = 0; i < values.length; i++) {
                    if (!values[i].equals(""))
                        words.add(values[i]);
                    else
                        continue;
                }
            }
            this.count=words.size();
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent,"Error while reading file.\nPlease provide a text file.");
            return false;
        }
    }

    public int getCount() {
        return count;
    }

    public void displayPage(String filePath, JTextArea textArea) throws IOException {
        try {
            FileReader fread = new FileReader(filePath);
            BufferedReader buffRead = new BufferedReader(fread);
            textArea.read(buffRead, null);
            buffRead.close();
            textArea.requestFocus();
        }
        catch(Exception exp){
            JOptionPane.showMessageDialog(textArea,"Error while reading file.");
        }
    }

    public String byPriorityQueue() {
        LinkedList<String> list = words;
        HashMap<String , Integer> map = new HashMap<>();
        for(String s : list) {
            if(map.containsKey(s))
            {
                int count = map.get(s);
                map.replace(s,count + 1);
            }
            else
            {
                map.put(s,1);
            }
        }

        PriorityQueue<Map.Entry<String,Integer>> pq=new PriorityQueue(10,new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o1).getValue()
                        -((Map.Entry<String, Integer>) o2).getValue();
            }
        });

        Iterator<Map.Entry<String,Integer>> itr=map.entrySet().iterator();
        int count=0;

        while(count<10&&itr.hasNext()) {
            Map.Entry<String,Integer> entry=itr.next();
            pq.add(entry);
            count++;
        }
        while(itr.hasNext()) {
            Map.Entry<String,Integer> entry=itr.next();
            Map.Entry<String,Integer> entry1=pq.peek();
            if(entry.getValue()>entry1.getValue()) {
                pq.poll();
                pq.add(entry);
            }
        }
        PriorityQueue<Map.Entry<String,Integer>> pq1=new PriorityQueue(10,new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        -((Map.Entry<String, Integer>) o1).getValue();
            }
        });
        Iterator it=pq.iterator();
        while(it.hasNext()) {
            Map.Entry<String,Integer> entry=(Map.Entry<String,Integer>)it.next();
            pq1.add(entry);
        }

        String str = "Top 10 most frequent words are :\n";
        int i=0;
        topTen.clear();
        while(!pq1.isEmpty())
        {
            Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>)pq1.poll();
            topTen.add(entry.getKey());
            str+=((i+1)+".  "+entry.getKey()+" : "+entry.getValue()+"\n");
            i++;
        }
        str+="Best Case Complexity = O(n)\nAverage Case Complexity = O(n)\nWorst Case Complexity = O(n^2)\n";
        return str;
    }


    public String byArrayList(){
        LinkedList<String> l = words;
        HashMap<String,Integer> hmp = new HashMap<String,Integer>();
        for(int i=0;i<l.size();i++){
            if(hmp.containsKey(l.get(i))){
                int cnt = hmp.get(l.get(i));
                cnt++;
                hmp.put((String) l.get(i), cnt);
            }
            else{
                hmp.put((String)l.get(i),1);
            }


        }
        ArrayList<Map.Entry<String,Integer>> word_list=new ArrayList<Map.Entry<String,Integer>>(hmp.entrySet());

        Collections.sort(word_list, new Comparator<Map.Entry<String,Integer>>(){
            public int compare(Map.Entry<String,Integer> a,Map.Entry<String,Integer> b)
            {
                return b.getValue()-a.getValue();
            }
        });

        String str = "Top 10 most frequent words are :\n";

        this.topTen.clear();
        for(int i=0;i<word_list.size()&&i<10;i++) {
            if (!word_list.isEmpty()){
                this.topTen.add(word_list.get(i).getKey());
                str+=((i+1)+".  "+word_list.get(i).getKey()+" : "+word_list.get(i).getValue()+"\n");
            }
        }

        str+="Best Case Complexity = O( n*log(n) )\nAverage Case Complexity = O( n*log(n) )\nWorst Case Complexity = O(n^2)\n";
        return str;
    }

    public String byTreeMap(){
        LinkedList<String> l = words;
        TreeMap<String,Integer> hmp = new TreeMap<String,Integer>();
        for(int i=0;i<l.size();i++){
            if(hmp.containsKey(l.get(i))){
                int cnt = hmp.get(l.get(i));
                cnt++;
                hmp.put((String) l.get(i), cnt);
            }
            else{
                hmp.put((String)l.get(i),1);
            }


        }

        String str = "Top 10 most frequent words are :\n";

        this.topTen.clear();
        Set abc = hmp.keySet();
        String value;
        int c=0;
        for (Iterator i = abc.iterator();i.hasNext()&&c<10;c++){
            if (!hmp.isEmpty()){
                Integer key = (Integer)i.next();
                value = String.valueOf(hmp.get(key));
                this.topTen.add(value);
                str+=((c+1)+".  "+value+" : "+key+"\n");
            }
        }

        str+="Best Case Complexity = O( n*log(n) )\nAverage Case Complexity = O( n*log(n) )\nWorst Case Complexity = O( n*log(n) )\n";
        return str;
    }

}