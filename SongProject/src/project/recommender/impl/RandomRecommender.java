package project.recommender.impl;

import project.model.Song;
import project.recommender.Recommender;

/*
 * A song recommender that simply relies on random algorithm.
 * Recommend whatever you want to
 */
public class RandomRecommender extends Recommender {

	public RandomRecommender(String filePath) {
		super(filePath);
	}

	/*
	 * Write your own recommender module. You can remove the for loop and write
	 * your custom logic
	 */
	@Override
	public Song[] recommend(String studentId) {
		int count=0;
		int x=0;
		//randSong = this.songs.clone();//원본Song배열 유지를 위해 clone함수 사용
		for(int i=0; i<this.songs.length; i++){
			if(!this.songs[i].getStudentId().equals(studentId)){
				count++;
			}
		}
		
		Song[] randSong = new Song[count];
		for(int i=0; i<this.songs.length; i++){
			if(!this.songs[i].getStudentId().equals(studentId)){//임의의 노래를 추천시에는 자기자신(studentId)의 노래는 제외한다.
				randSong[x++] = this.songs[i];
			}
		}

		randSong = shuffle(randSong); //randSong을 무작위로 섞는다.
		
		return randSong;
	}
	
	//Song배열 섞기
	public Song[] shuffle(Song[] randSong) {
		for (int i = 0; i < randSong.length; i++) {
			int a = (int) (Math.random() * randSong.length);
			Song tmp = randSong[a];
			randSong[a] = randSong[i];
			randSong[i] = tmp;
		}
		return randSong;
	}
}
