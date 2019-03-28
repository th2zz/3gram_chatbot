
import java.util.*;
import java.io.*;

public class Chatbot{
    private static String filename = "./WARC201709_wid.txt";
    private static int vocabularySize = 0;
    private static ArrayList<Integer> readCorpus(){
        ArrayList<Integer> corpus = new ArrayList<Integer>();
        try{
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            while(sc.hasNext()){
                if(sc.hasNextInt()){
                    int i = sc.nextInt();
                    corpus.add(i);
                }
                else{
                    sc.next();
                }
            }
            f = new File("./vocabulary.txt");
            sc = new Scanner(f);
            while (sc.hasNext()) {
                vocabularySize++;
                sc.next();
            }
            vocabularySize++;
            //System.out.println(vocabularySize);
        }
        catch(FileNotFoundException ex){
            System.out.println("File Not Found.");
        }
        return corpus;
    }
    @SuppressWarnings("unchecked")
    static public void main(String[] args){
        ArrayList<Integer> corpus = readCorpus();
        int flag = Integer.valueOf(args[0]);
        int[] unigramStorage = new int[vocabularySize];
        double[] unigramProb = new double[vocabularySize];
        HashMap<Integer, Integer>[] bigramStorage = new HashMap[vocabularySize];
        HashMap<Integer, Double>[] bigramProb = new HashMap[vocabularySize];
        if(flag == 100){
            int w = Integer.valueOf(args[1]);
            int count = 0;
            //TODO count occurence of w
            for (int i: corpus) {
                if (i == w) {
                    count++;
                }
            }

            System.out.println(count);
            System.out.println(String.format("%.7f",count/(double)corpus.size()));
        }
        else if(flag == 200){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            //TODO generate
            for (int j: corpus) {
                unigramStorage[j]++;
            }
            int length = corpus.size();
            double p = 0;
            for (int i = 0; i < vocabularySize; i++) {
                p = p + unigramStorage[i] / (double)length;
                unigramProb[i] = p;
            }
            double prob = n1 / (double)n2;
            for (int i = 0; i < unigramStorage.length; i++) {
                if (prob <= unigramProb[0]) {
                    System.out.println(0);
                    System.out.println(String.format("%.7f",0.0));
                    System.out.println(String.format("%.7f",unigramProb[0]));
                    return;
                }
                if (prob <= unigramProb[i]) {
                    System.out.println(i);
                    System.out.println(String.format("%.7f",unigramProb[i - 1]));
                    System.out.println(String.format("%.7f",unigramProb[i]));
                    return;
                }
            }
        }
        else if(flag == 300){
            int h = Integer.valueOf(args[1]);
            int w = Integer.valueOf(args[2]);
            int count = 0;
            ArrayList<Integer> words_after_h = new ArrayList<Integer>();
            //TODO
            for (int i = 0; i < corpus.size() - 1; i++) {
                if (corpus.get(i) == h) {
                    words_after_h.add(corpus.get(i + 1));
                    if (corpus.get(i + 1) == w) {
                        count++;
                    }
                }
            }
            //output
            System.out.println(count);
            System.out.println(words_after_h.size());
            System.out.println(String.format("%.7f",count/(double)words_after_h.size()));
            /*Iterator<Integer> iterator = corpus.iterator();
            System.out.println(iterator.next());
            System.out.println(iterator.);*/
        }
        else if(flag == 400){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h = Integer.valueOf(args[3]);
            //TODO
            for (int i = 0; i < vocabularySize; i++) {
                bigramStorage[i] = new HashMap<Integer, Integer>();
                //bigramProb[i] = new HashMap<Integer, Double>();
            }
            for (int i = 0; i < corpus.size() - 1; i++) {
                int history = corpus.get(i);
                int word = corpus.get(i + 1);
                if (!bigramStorage[history].containsKey(word)) {
                    bigramStorage[history].put(word, 1);
                } else {
                    int count = bigramStorage[history].get(word);
                    bigramStorage[history].put(word, count + 1);
                }
            }
            double p = 0;
            double previous = p;
            double prob = n1 / (double)n2;
            int size = 0;
            for (int i: bigramStorage[h].values()) {
                size = size + i;
            }
            for (int i = 0; i < vocabularySize; i++) {
                if (bigramStorage[h].containsKey(i)) {
                    p = p + bigramStorage[h].get(i) / (double)size;
                    if (prob <= p) {
                        System.out.println(i);
                        System.out.println(String.format("%.7f",previous));
                        System.out.println(String.format("%.7f",p));
                        return;
                    }
                    previous = p;
                    //bigramProb[h].put(i, p);
                }
            }
            /*for (int i: bigramStorage[h].keySet()) {
                //System.out.println(i);
                p = p + bigramStorage[h].get(i) / (double)size;
                if (prob <= p) {
                    System.out.println(i);
                    System.out.println(String.format("%.7f",previous));
                    System.out.println(String.format("%.7f",p));
                    *//*HashMap<Integer, Integer> test = new HashMap<>();
                    test.put(0,0);
                    test.put(2,-1);
                    test.put(4193, 2);
                    test.put(2722,-3);
                    test.put(1893,-3);
                    for (int n : test.keySet()) {
                        System.out.println(n);
                    }*//*
                    return;
                }
                previous = p;
                bigramProb[h].put(i, p);
            }*/

            /*Iterator<Integer> iterator = bigramProb[h].keySet().iterator();
            if (iterator.hasNext()) {
                int first = iterator.next();
                if (prob <= bigramProb[h].get(first)) {
                    System.out.println(first);
                    System.out.println(String.format("%.7f",0.0));
                    System.out.println(String.format("%.7f",bigramProb[h].get(first)));
                    return;
                }
                while (iterator.hasNext()) {

                }
            }*/
        }
        else if(flag == 500){
            int h1 = Integer.valueOf(args[1]);
            int h2 = Integer.valueOf(args[2]);
            int w = Integer.valueOf(args[3]);
            int count = 0;
            ArrayList<Integer> words_after_h1h2 = new ArrayList<Integer>();
            //TODO
            for (int i = 0; i < corpus.size() - 2; i++) {
                if (corpus.get(i) == h1 && corpus.get(i + 1) == h2) {
                    words_after_h1h2.add(corpus.get(i + 2));
                    if (corpus.get(i + 2) == w) {
                        count++;
                    }
                }
            }
            //output
            System.out.println(count);
            System.out.println(words_after_h1h2.size());
            if(words_after_h1h2.size() == 0)
                System.out.println("undefined");
            else
                System.out.println(String.format("%.7f",count/(double)words_after_h1h2.size()));
        }
        else if(flag == 600){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h1 = Integer.valueOf(args[3]);
            int h2 = Integer.valueOf(args[4]);
            //TODO
            HashMap<Integer, HashMap<Integer, Integer>>[] trigramStorage = new HashMap[vocabularySize];
            for (int i = 0; i < vocabularySize; i++) {
                trigramStorage[i] = new HashMap<>();
            }
            for (int i = 0; i < corpus.size() - 2; i++) {
                if (!trigramStorage[corpus.get(i)].containsKey(corpus.get(i + 1))) {
                    HashMap<Integer, Integer> history2 = new HashMap();
                    trigramStorage[corpus.get(i)].put(corpus.get(i + 1), history2);
                }
                HashMap<Integer, Integer> history2 = trigramStorage[corpus.get(i)].get(corpus.get(i + 1));
                if (!history2.containsKey(corpus.get(i + 2))) {
                    history2.put(corpus.get(i + 2), 1);
                } else {
                    history2.put(corpus.get(i + 2), history2.get(corpus.get(i + 2)) + 1);
                }
            }
            if (!trigramStorage[h1].containsKey(h2)) {
                System.out.println("undefined");
                return;
            }
            double p = 0;
            double previous = p;
            double prob = n1 / (double)n2;
            int size = 0;

            for (int i: trigramStorage[h1].get(h2).values()) {
                size = size + i;
            }
            HashMap<Integer, Integer> history2 = trigramStorage[h1].get(h2);
            for (int i = 0; i < vocabularySize; i++) {
                if (history2.containsKey(i)) {
                    p = p + history2.get(i) / (double)size;
                    if (prob <= p) {
                        System.out.println(i);
                        System.out.println(String.format("%.7f",previous));
                        System.out.println(String.format("%.7f",p));
                        return;
                    }
                    previous = p;
                }

            }
            /*HashMap<Integer, Integer>[][] trigramStorage = new HashMap[vocabularySize][vocabularySize];
            for (int i = 0; i < corpus.size() - 2; i++) {
                if (trigramStorage[corpus.get(i)][corpus.get(i + 1)] == null) {
                    trigramStorage[corpus.get(i)][corpus.get(i + 1)] = new HashMap<>();
                }
                if (!trigramStorage[corpus.get(i)][corpus.get(i + 1)].containsKey(corpus.get(i + 2))) {
                    trigramStorage[corpus.get(i)][corpus.get(i + 1)].put(corpus.get(i + 2), 1);
                } else {
                    int word = trigramStorage[corpus.get(i)][corpus.get(i + 1)].get(corpus.get(i + 2));
                    trigramStorage[corpus.get(i)][corpus.get(i + 1)].put(corpus.get(i + 2), trigramStorage[corpus.get(i)][corpus.get(i + 1)].get(word));
                }
            }*/
        }
        else if(flag == 700){
            int seed = Integer.valueOf(args[1]);
            int t = Integer.valueOf(args[2]);
            int h1=0,h2=0;

            Random rng = new Random();
            if (seed != -1) rng.setSeed(seed);

            for (int j: corpus) {
                unigramStorage[j]++;
            }
            int length = corpus.size();
            double p = 0;
            for (int i = 0; i < vocabularySize; i++) {
                p = p + unigramStorage[i] / (double)length;
                unigramProb[i] = p;
            }
            for (int i = 0; i < vocabularySize; i++) {
                bigramStorage[i] = new HashMap<Integer, Integer>();
                //bigramProb[i] = new HashMap<Integer, Double>();
            }
            for (int i = 0; i < corpus.size() - 1; i++) {
                int history = corpus.get(i);
                int word = corpus.get(i + 1);
                if (!bigramStorage[history].containsKey(word)) {
                    bigramStorage[history].put(word, 1);
                } else {
                    int count = bigramStorage[history].get(word);
                    bigramStorage[history].put(word, count + 1);
                }
            }

            HashMap<Integer, HashMap<Integer, Integer>>[] trigramStorage = new HashMap[vocabularySize];
            for (int i = 0; i < vocabularySize; i++) {
                trigramStorage[i] = new HashMap<>();
            }
            for (int i = 0; i < corpus.size() - 2; i++) {
                if (!trigramStorage[corpus.get(i)].containsKey(corpus.get(i + 1))) {
                    HashMap<Integer, Integer> history2 = new HashMap();
                    trigramStorage[corpus.get(i)].put(corpus.get(i + 1), history2);
                }
                HashMap<Integer, Integer> history2 = trigramStorage[corpus.get(i)].get(corpus.get(i + 1));
                if (!history2.containsKey(corpus.get(i + 2))) {
                    history2.put(corpus.get(i + 2), 1);
                } else {
                    history2.put(corpus.get(i + 2), history2.get(corpus.get(i + 2)) + 1);
                }
            }

            if(t == 0){
                // TODO Generate first word using r
                double r = rng.nextDouble();
                for (int i = 0; i < unigramStorage.length; i++) {
                    if (r <= unigramProb[0]) {
                        h1 = 0;
                        break;
                    }
                    if (r <= unigramProb[i]) {
                        h1 = i;
                        break;
                    }
                }
                System.out.println(h1);
                if(h1 == 9 || h1 == 10 || h1 == 12){
                    return;
                }

                // TODO Generate second word using r
                r = rng.nextDouble();
                p = 0;
                double previous = p;
                int size = 0;
                for (int i: bigramStorage[h1].values()) {
                    size = size + i;
                }
                for (int i = 0; i < vocabularySize; i++) {
                    if (bigramStorage[h1].containsKey(i)) {
                        p = p + bigramStorage[h1].get(i) / (double)size;
                        if (r <= p) {
                            h2 = i;
                            break;
                        }
                    }
                }
                System.out.println(h2);
            }
            else if(t == 1){
                h1 = Integer.valueOf(args[3]);
                // TODO Generate second word using r
                double r = rng.nextDouble();
                p = 0;
                int size = 0;
                for (int i: bigramStorage[h1].values()) {
                    size = size + i;
                }
                for (int i = 0; i < vocabularySize; i++) {
                    if (bigramStorage[h1].containsKey(i)) {
                        p = p + bigramStorage[h1].get(i) / (double)size;
                        if (r <= p) {
                            h2 = i;
                            break;
                        }
                    }
                }
                System.out.println(h2);
            }
            else if(t == 2){
                h1 = Integer.valueOf(args[3]);
                h2 = Integer.valueOf(args[4]);
            }

            while(h2 != 9 && h2 != 10 && h2 != 12){
                double r = rng.nextDouble();
                int w  = 0;
                // TODO Generate new word using h1,h2
                if (!trigramStorage[h1].containsKey(h2)) {
                    p = 0;
                    int size = 0;
                    for (int i: bigramStorage[h2].values()) {
                        size = size + i;
                    }
                    for (int i = 0; i < vocabularySize; i++) {
                        if (bigramStorage[h2].containsKey(i)) {
                            p = p + bigramStorage[h2].get(i) / (double)size;
                            if (r <= p) {
                                w = i;
                                break;
                            }
                        }
                    }
                } else {
                    p = 0;
                    int size = 0;
                    for (int i : trigramStorage[h1].get(h2).values()) {
                        size = size + i;
                    }
                    HashMap<Integer, Integer> history2 = trigramStorage[h1].get(h2);
                    for (int i = 0; i < vocabularySize; i++) {
                        if (history2.containsKey(i)) {
                            p = p + history2.get(i) / (double) size;
                            if (r <= p) {
                                w = i;
                                break;
                            }
                        }

                    }
                }
                System.out.println(w);
                h1 = h2;
                h2 = w;
            }
        }
        return;
    }

}
