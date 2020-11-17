package project.recommender.impl;

import java.util.HashMap;
import java.util.Iterator;

import project.model.Song;
import project.recommender.Recommender;

/*
 * A song recommender based on the similarity of songs
 */
public class SimilarSongRecommender extends Recommender {

    public SimilarSongRecommender(String filePath) {
        super(filePath);
    }

    @Override
    public Song[] recommend(String studentId) {
    	HashMap<String, Integer> map = new HashMap<String, Integer>(); 
    	boolean flag = true; 
    	String similarGenre = "";
    	
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
        
        
        //가장 첫번째로 나온 장르가 가장 많이 들은 장르
        similarGenre = (String) sortSimilarSongMap.next();
        
        int count=0;
        for (Song song : this.songs) {//전체 노래들중 해당장르가 몇개있는지 카운트한다.
        	if(song.getGenre().equals(similarGenre)){
        		count++;
			}
        }
        
        Song[] similarSong = new Song[count];
        int x = 0;
        for (Song song : this.songs){//전체 노래들중 해당장르에 해당하는 것만 similarSong배열에 넣는다.
        	if(song.getGenre().equals(similarGenre)){
        		similarSong[x++] = song;
			}
        }
        
        return similarSong;
    }

}
