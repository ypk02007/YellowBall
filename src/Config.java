
public class Config {
	private static Config fl;
	private int backgroundCode = 0;
	private float bgmVolume = -20.0f;
	
	private Config() {}
	public static Config getInstance() {
		if(fl == null)
			fl = new Config();
		return fl;
	}
	
	public void setBackgroundCode() {
		if(backgroundCode == 0)
			backgroundCode = 1;
	}
	public int getBackgroundCode() {return backgroundCode;}
	public float getbgmVolume() {return bgmVolume;}
}
