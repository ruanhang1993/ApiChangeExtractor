package cn.edu.fudan.se.apiChangeExtractor.util;

public class PathUtils {
	public static String changeWebsite2Path(String s){
		if(s==null) return s;
		String temp = s.replace("https://github.com/", "");
		temp = temp.replace("/", "-");
		return temp;
	}
}
