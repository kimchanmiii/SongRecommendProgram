package project.recommender.impl;

import java.util.HashMap;
import java.util.Iterator;

import project.model.Song;
import project.recommender.Recommender;

/*
 * A song recommender based on the students' preference similarity
 */
public class SimilarStudentRecommender extends Recommender {

    public SimilarStudentRecommender(String filePath) {
        super(filePath);
    }

    @Override
    public Song[] recommend(String studentId) {
    	HashMap<String, Integer> map = new HashMap<String, Integer>(); 
    	HashMap<String, HashMap<String, Integer>> result = new HashMap<String, HashMap<String, Integer>>();
    	HashMap<String, Integer> map1;
    	HashMap<String, String> similarMap = new HashMap<String, String>();
    	Song[] similarStudent;
    	String similarGenre = "";
    	String similarUserId = "";
    	
    	//studnetId가 가장 많이듣는 장르를 찾는다.
        for (Song song : this.songs) {
        	if(song.getStudentId().equals(studentId)){
				if(!map.containsKey(song.getGenre())) {
					map.put(song.getGenre(), 0);
	    		}
				map.put(song.getGenre(), map.get(song.getGenre())+1);
			}
        }
        
        Iterator sortSimilarSongMap = sortByValue(map).iterator(); //가장많이 듣는 장르를 찾기위해 정렬을한다.
        similarGenre = (String) sortSimilarSongMap.next();
        
        //가장많이 듣는 장르를 듣는 다른 유저들을 찾는다.
    	for(Song a:songs) {
    		if(!result.containsKey(a.getStudentId())){
    			 map1 = new HashMap<String, Integer>();
    			 result.put(a.getStudentId(), map1);
    		}else{
    			if(!result.get(a.getStudentId()).containsKey(a.getGenre())) {
    				result.get(a.getStudentId()).put(a.getGenre(), 0);
        		}
    			result.get(a.getStudentId()).put(a.getGenre(), result.get(a.getStudentId()).get(a.getGenre())+1);
    		}
    	}
    	//다른 유저들중에서 가장 많이 듣는 장르가 studentId와 같은 userId를 찾는다.
    	for (String key : result.keySet()) {
            Iterator sortMap = sortByValue(result.get(key)).iterator();
            similarMap.put(key, (String) sortMap.next());
            
          //userId가 다르고 && 동일한 장르인 유저를 찾는다. 
            if(!studentId.equals(key)&&similarGenre.equals(similarMap.get(key))){
            	similarUserId = key;
            	
            }
    	}
    	int count=0;
    	for (Song song : this.songs){//전체 노래들중 위에서 찾은 유저가 몇개의 노래를 갖고있는지 카운트 한다.
        	if(song.getStudentId().equals(similarUserId)){
        		count++;
			}
        }
    	similarStudent = new Song[count];
    	
        int x = 0;
        for (Song song : this.songs){//전체 노래들중 위에서 찾은 유저의 노래를 similarStudent배열에 넣는다.
        	if(song.getStudentId().equals(similarUserId)){
        		similarStudent[x++] = song;
			}
        }
        
        return similarStudent;
    }
}
