package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import project.model.Song;
import project.recommender.Recommender;
import project.recommender.impl.RandomRecommender;
import project.recommender.impl.SimilarSongRecommender;
import project.recommender.impl.SimilarStudentRecommender;

public class Main {

    private final String[] studentIds;
    // declare recommendedSongs field. It should store recommended songs for each students

    private Recommender randomRecommender;
    private Recommender similarSongRecommender;
    private Recommender similarStudentRecommender;

    public Main(String filePath) {
        studentIds = extractStudentsIds(filePath);
        randomRecommender = new RandomRecommender(filePath); //임의의 노래추천
        similarSongRecommender = new SimilarSongRecommender(filePath); //유사한 노래를 기준으로 추천
        similarStudentRecommender = new SimilarStudentRecommender(filePath); //사용자의 취향의 유사도를 고려하여 음악 추천
    }

    /*
     * From the input file path that contains information about the song
     * preference list, it should extract unique students IDs and it should
     * return it as a String array. There are 36 unique student IDs and you
     * should return only the unique IDs.
     */
    private static String[] extractStudentsIds(String filePath) {
        File inputFile = new File(filePath);
        String[] studentIds = new String[37];
        int i = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"))) {
            for (String line; (line = br.readLine()) != null;) {
                String[] fieldValues = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                
                //i가 0일때는 무조건 배열에 넣고, 그다음부터는 중복되지 않게 studentID를 배열에 넣는다.
                if(i==0||!studentIds[i-1].equals(fieldValues[0])) {
                	studentIds[i++] = fieldValues[0];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return studentIds;
    }
    
    private static boolean checkStudentsIds(String uid) {//userId를 입력시에 csv파일에서 존재하는 user인지 체크해서 true,false를 반환한다.
    	boolean flag = false;
    	String[] studentIds = new String[37];
    	
    	Main runner = new Main("src/resource/song-list.csv");
    	studentIds = runner.extractStudentsIds("src/resource/song-list.csv");
    	
    	for(String id : studentIds){
    		if(uid.equals(id)){
    			flag = true;
    		}
    	}
    	return flag;
    }

    /*
     * A function to print out basic statistics about song and students
     */
    public void printStatistics() {//통계
    	randomRecommender.printSongStatistics();
    	randomRecommender.printStudentStatistics();
    }

    /*
     * A function that calls recommend function that is implemented in different
     * classes
     */
    public void recommend() {
        for (String student : studentIds) {
            Song[] recommendedSongs = randomRecommender.recommend(student);
        }
    }
    
    public static void main(String[] args) {
    	String uid = "";
    	Scanner scanner = new Scanner(System.in);
        Main runner = new Main("src/resource/song-list.csv");
        //runner.printStatistics();
        //runner.recommend();
        while(true){
			System.out.println("1.임의의 노래 추천");
			System.out.println("2.사용자 아이디 추천모드");
			System.out.println("3.통계");
			System.out.println("4.종료");
			System.out.print("원하는번호를 선택하세요:");
		        int menuNumber = Integer.parseInt(scanner.nextLine());
			if(menuNumber==1){
				System.out.println(runner.randomRecommender.recommend("")[0].toString());//무작위로 섞인 배열의 첫번째를 보여준다.
			}
			else if(menuNumber==2){
				while(true){
					System.out.println("1.유사한 노래를 기준으로 추천");
					System.out.println("2.사용자의 취향의 유사도를 고려하여 추천");
					System.out.println("3.나가기");
					System.out.print("원하는번호를 선택하세요:");

					menuNumber = Integer.parseInt(scanner.nextLine());
					
					if(menuNumber==1){
						while(true){
							System.out.print("id를 입력하세요:");
							uid = scanner.nextLine();
							if(checkStudentsIds(uid)){
								break;
							}
							System.out.println("id가 존재하지 않습니다.");
						}
						
						Song[] similarSongRecommender = runner.similarSongRecommender.recommend(uid);
						System.out.println(uid + "의 가장 많이듣는 장르는 " + similarSongRecommender[0].getGenre() + "입니다.");
						System.out.println("이와 유사한 노래들은 아래와 같습니다.");
						for(Song a : similarSongRecommender){
							System.out.println(a.toString());
						}
					}else if(menuNumber==2){
						while(true){
							System.out.print("id를 입력하세요:");
							uid = scanner.nextLine();
							if(checkStudentsIds(uid)){
								break;
							}
							System.out.println("id가 존재하지 않습니다.");
						}
						Song[] similarSongRecommender = runner.similarStudentRecommender.recommend(uid);
						System.out.println(uid + "의 가장 많이듣는 장르는 " + similarSongRecommender[0].getGenre() + "입니다.");
						System.out.println("이와 유사한 노래를 듣는 다른 사용자가 듣는 노래들은 아래와 같습니다.");
						for(Song a : similarSongRecommender){
							System.out.println(a.toString());
						}
					}
					else if(menuNumber==3){
						break;
					}
				}
				
			}
			else if(menuNumber==3){
				runner.printStatistics();
			}
			else if(menuNumber==4){
				System.exit(0);
			}else {
				System.out.println("1,2,3,4중에서 선택해 주세요.");
			}
		}
        
        
        
    }
}
