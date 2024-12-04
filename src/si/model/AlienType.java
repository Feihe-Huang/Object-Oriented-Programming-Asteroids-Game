package si.model;

//枚举类     可以用此画陨石    将长宽换成不同多边形
public enum AlienType {    // 32 29     // 8 7    // 10 8
	Lar(32, 31, 25), Med(20, 23, 50), Sma(11, 10, 100);
	private int width;
	private int height;
	private int score;

	private AlienType(int w, int h, int s) {
		width = w;
		height = h;
		score = s;
	}

	public int getWidth() {
		return width;
	}

	public int getScore() {
		return score;
	}

	public int getHeight() {return height; }
}
