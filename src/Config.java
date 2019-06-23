
public class Config {
	private static Config fl;
	private int backgroundCode = 0;
	private boolean battleEnd = true; // ���� thread�� �߰��� ���߱� ���� flag
	
	private Config() {}
	public static Config getInstance() {
		if(fl == null)
			fl = new Config();
		return fl;
	}
	
	public void changeBackgroundCode() { // EnterŰ �Է����� ��� ��ȯ, Ÿ��Ʋ ȭ��� ���ӿ��� ȭ�鿡���� ���δ�
		if(backgroundCode == 0 || backgroundCode == 6)
			backgroundCode = 1;
	}
	public void setBackgroundCode(int code) {backgroundCode = code;} // �Ķ���ͷ� ��� ��ȯ
	public int getBackgroundCode() {return backgroundCode;}
	
	public void setBattleEnd(boolean flag) {battleEnd = flag;}
	public boolean isBattleEnd() {return battleEnd;}
}
