package project.model;

/*
 * A class to store the information about each song entry
 * It should contain all the fields that are registered in the song list file
 * You have to declare fields and way to parse comma separated string to the Song class 
 */
public class Song {
	private String studentId;
	private String title; 
	private String singer;
	private String genre;
	private String saleYear;
	private String elbumName;
	private String songwriter;
	private String producer;
	private String saleCountry;
	private String language;
	private String length;

    public Song(String entry) {
        // field element
        // 사용자 아이디, 제목, 가수, 장르, 발매년도, 앨범명, 작곡가, 프로듀서, 발행국가, 언어, 길이
        String[] fieldValues = entry.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
        this.studentId = fieldValues[0];
    	this.title = fieldValues[1]; 
    	this.singer = fieldValues[2];
    	this.genre = fieldValues[3];
    	this.saleYear = fieldValues[4];
    	this.elbumName = fieldValues[5];
    	this.songwriter = fieldValues[6];
    	this.producer = fieldValues[7];
    	this.saleCountry = fieldValues[8];
    	this.language = fieldValues[9];
    	this.length = fieldValues[10];
    }

    /*
     * You have to write your custom toString function to print Song information
     */
    @Override
    public String toString() {
        return "제목=" + title + ", 가수=" + singer + ", 장르=" + genre
				+ ", 발매년도=" + saleYear + ", 앨범이름=" + elbumName + ", 작곡가=" + songwriter + ", 프로듀서="
				+ producer + ", 국가=" + saleCountry + ", 언어=" + language + ", 길이=" + length;
    }

    //getter, setter 메서드
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getSaleYear() {
		return saleYear;
	}

	public void setSaleYear(String saleYear) {
		this.saleYear = saleYear;
	}

	public String getElbumName() {
		return elbumName;
	}

	public void setElbumName(String elbumName) {
		this.elbumName = elbumName;
	}

	public String getSongwriter() {
		return songwriter;
	}

	public void setSongwriter(String songwriter) {
		this.songwriter = songwriter;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getSaleCountry() {
		return saleCountry;
	}

	public void setSaleCountry(String saleCountry) {
		this.saleCountry = saleCountry;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
    
    
}
