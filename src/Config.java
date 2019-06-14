
public class Config {
	private static Config fl;
	private int backgroundCode = 0;
	
	private Config() {}
	public static Config getInstance() {
		if(fl == null)
			fl = new Config();
		return fl;
	}
	
	public void changeBackgroundCode() { // Enter키 입력으로 배경 전환, 타이틀 화면과 게임오버 화면에서만 쓰인다
		if(backgroundCode == 0 || backgroundCode == 6)
			backgroundCode = 1;
	}
	public void setBackgroundCode(int code) {backgroundCode = code;} // 파라미터로 배경 전환
	public int getBackgroundCode() {return backgroundCode;}
}
