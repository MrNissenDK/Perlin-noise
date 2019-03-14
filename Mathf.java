package com.mrnissen.perlinnoise;

public class Mathf {
	public static int ceil(float x) {
		return (int) x+1;
	}
	public static int floor(float x) {
		return (int) x;
	}
	public static int round(float x) {
		return (int) (x+0.5);
	}
	public static float abs(float x) {
		if(x < 0)
			x *= -1;
		return x;
	}
	public static float pow(float x, int n) {
		float res = x;
		for(int i = 1; i < n; i++)
			res*=x;
		return res;
	}
	public static float calm(float x,float min,float max) {
		if(x < min)
			return min;
		else if(x > max)
			return max;
		return x;
	}
}
