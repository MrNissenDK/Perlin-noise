package com.mrnissen.perlinnoise;

public class Random {
	private static long index = 0;
	private long seed;
	private long a;
	private long b;
	
	public float getPerlin(float x, float y, float frq, int octal) {
		float max = 0;
		float amp = potensOf(frq,octal);
		float n = 0;
		for(int i = 0; i < octal; i++) {
			n += getNoise(x, y, frq , amp);
			max += amp;
			amp /= 2f;
			frq += frq;
		}
		max -= amp;
		float res = n/max;
		return Mathf.calm(res, 0, 1f);
		
	}
	private Float PotensOfx;
	private Float PotensOfn;
	private Float PotensOfLastX;
	private float potensOf(float x, int n) {
		if(PotensOfn != null || PotensOfx != null) {
			if(PotensOfn == n && PotensOfx == x)
				return PotensOfLastX;
		}
		for(int i = 1; i < n; i++) {
			x*=2;
		}
		PotensOfLastX = x;
		return x;
	}
	public Random(long seed) {
		this.seed = seed;
		a = seed%10000;
		b = (a * seed)%10000;
		
		System.out.println(seed+"\t"+a+"\t"+b);
	}
	private int getRandom() {
		int val = getRandom(index);
		index++;
		return val;
	}
	private int getRandom(long i) {
		long x = seed+i;
		return (int)Math.abs((a*(x*x*x*x*x)+b)%2147483647);
	}
	public float getNextFloat() {
		int num = getRandom()%1000000000;
		
		return Float.parseFloat("0."+num);
	}
	public float getFloat(int i) {
		int num = getRandom(i)%1000000000;
		
		return Float.parseFloat("0."+num);
	}
	public int getNextInt(int min,int max) {
		int diff = max-min+1;
		return getRandom()%diff+min;
	}
	public int getInt(int i, int min,int max) {
		int diff = max-min+1;
		return getRandom(i)%diff+min;
	}
	
	public float getNoise(float x, float y, float frq, float amp) {
		float points = (500/frq); 
		
		x /= points;
		y /= points;
		
		int lX = (int)(Mathf.floor(x)*points);
		int hX = (int)(Mathf.ceil(x)*points);
		float cX = x-Mathf.floor(x);
		
		int lY = (int)(Mathf.floor(y)*points);
		int hY = (int)(Mathf.ceil(y)*points);
		float cY = y-Mathf.floor(y);
		
		float h1 = getFloat(getIndex(lX, lY));
		float h2 = getFloat(getIndex(hX, lY));
		
		float h3 = getFloat(getIndex(lX, hY));
		float h4 = getFloat(getIndex(hX, hY));
		
		float e1 = ease(cX,h1,h2);
		float e2 = ease(cX,h3,h4);
		float e3 = ease(cY,e1,e2);
		
//		System.out.println(x+"\t"+lX+","+hX+","+cX);
//		System.out.println(y+"\t"+lY+","+hY+","+cY);
//
//		System.out.println(e1+"\t"+h1+","+h2);
//		System.out.println(e2+"\t"+h3+","+h4);
//
//		System.out.println(e3+"\t"+e1+","+e2);
//		
//		System.out.println(e3*amp);
		return e3*amp;
	}
	
	private int getIndex(int x, int y) {
		return x*y+(x*x-y*y);
	}
	
	private float ease(float x,float start,float end) {
		float diff = end-start;
		return (6*Mathf.pow(x,5) - 15*Mathf.pow(x,4) + 10*Mathf.pow(x, 3)) * diff + start;
	}
	private float line(float x,float start,float end) {
		float diff = end-start;
		float a = diff;
		return a*x+start;
	}
	
}
