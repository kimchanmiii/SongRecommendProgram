package project.recommender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import project.model.Song;

public abstract class Recommender {

    protected final Song[] songs;

    public abstract Song[] recommend(String studentId);

    public Recommender(String filePath) {
        this.songs = buildSongObjects(filePath);
    }

    /*
     * Read the input file and fill the Song[] array
     */
    private Song[] buildSongObjects(String filePath) {
        Song[] songs = new Song[375];
        File inputFile = new File(filePath);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"))) {
            String line;
            for (int i = 0; (line = br.readLine()) != null; ++i) {
                Song song = new Song(line);
                songs[i] = song;
                //System.out.println(songs[i].toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return songs;
    }

    /*
     * A function to print statistics about Song Title, Singer, Genre, ... You
     * have to implement it
     */
    public void printSongStatistics() {
    	int rank=1;
    	
    	Map<String, Integer> titleMap = new HashMap<String, Integer>();
    	Map<String, Integer> singerMap = new HashMap<String, Integer>();
    	Map<String, Integer> languageMap = new HashMap<String, Integer>();
    	for(Song a:songs) {
    		//노래
    		if(!titleMap.containsKey(a.getTitle())) {
    			titleMap.put(a.getTitle(), 0);
    		}
    		titleMap.put(a.getTitle(), titleMap.get(a.getTitle())+1);
    		
    		//가수
    		if(!singerMap.containsKey(a.getSinger())) {
    			singerMap.put(a.getSinger(), 0);
    		}
    		singerMap.put(a.getSinger(), singerMap.get(a.getSinger())+1);
    		
    		//언어
    		if(!languageMap.containsKey(a.getLanguage())) {
    			languageMap.put(a.getLanguage(), 0);
    		}
    		languageMap.put(a.getLanguage(), languageMap.get(a.getLanguage())+1);
    	}
    	
    	Iterator sortTitleMap = sortByValue(titleMap).iterator();
    	Iterator sortSingerMap = sortByValue(singerMap).iterator();
    	Iterator sortLanguageMap = sortByValue(languageMap).iterator();
    	
    	//노래 순위
    	 while(sortTitleMap.hasNext()&&rank<6) {
    		 if(rank==1) {
    			 System.out.println("노래 통계 순위");
    		 }
             String temp = (String) sortTitleMap.next();
             System.out.println(rank+"위 " + temp + "(" + titleMap.get(temp)+ ")");
             rank++;
         }
    	 
    	 //가수 순위
    	 rank=1; //순위 초기화
    	 while(sortSingerMap.hasNext()&&rank<6) {
    		 if(rank==1) {
    			 System.out.println("가수 통계 순위");
    		 }
             String temp = (String) sortSingerMap.next();
             System.out.println(rank+"위 " + temp + "(" + singerMap.get(temp)+ ")");
             rank++;
         }
    	 
    	 //나라별 순위
    	 rank=1; //순위 초기화
    	 while(sortLanguageMap.hasNext()&&rank<6) {
    		 if(rank==1) {
    			 System.out.println("발매국가 통계 순위");
    		 }
             String temp = (String) sortLanguageMap.next();
             System.out.println(rank+"위 " + temp + "(" + languageMap.get(temp)+ ")");
             rank++;
         }
    }

    /*
     * A function to print basic statistics for each student You have to
     * implement it
     */
    public void printStudentStatistics() {
    	HashMap<String, HashMap<String, Integer>> result = new HashMap<String, HashMap<String, Integer>>();
    	HashMap<String, Integer> map;
    	int rank=1;
    	
    	for(Song a:songs) {
    		if(!result.containsKey(a.getStudentId())){
    			 map = new HashMap<String, Integer>();
    			 result.put(a.getStudentId(), map);
    		}else{
    			if(!result.get(a.getStudentId()).containsKey(a.getGenre())) {
    				result.get(a.getStudentId()).put(a.getGenre(), 0);
        		}
    			result.get(a.getStudentId()).put(a.getGenre(), result.get(a.getStudentId()).get(a.getGenre())+1);
    		}
    	}
    	System.out.println("==============");
    	System.out.println("유저별 장르 통계 순위");
    	for (String key : result.keySet()) {
            System.out.println("아이디 : " + key);
            
            Iterator sortMap = sortByValue(result.get(key)).iterator();
            
            while(sortMap.hasNext()) {
                String temp = (String) sortMap.next();
                System.out.println(rank + "위 " + temp + "(" + result.get(key).get(temp)+ ")");
                rank++;
            }
            rank = 1;
    	}
    }
    
    //map은 정렬이 되지 않기때문에 정렬메서드를 사용했습니다.
    public List sortByValue(final Map map) {
        List<String> list = new ArrayList();
        list.addAll(map.keySet());

        Collections.sort(list,new Comparator() {

        	public int compare(Object o1,Object o2) {
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);

                return ((Comparable) v2).compareTo(v1);
            }
        });

        //Collections.reverse(list); //내림차순으로 하려면 주석을 풀어주면 됩니다.
        return list;
    }
}
