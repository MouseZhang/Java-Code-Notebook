package org.fangsoft.loc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;

public class LocParser {
	private static final String JAVA_FILE_EXTENSION = ".java";
	public static final boolean DEBUG=false;
	
	private int loc;//代码总行数
	private Map<String, LocData> locMap = new TreeMap<String, LocData>();
	public static String getJavaFileExtension() {
		return JAVA_FILE_EXTENSION;
	}
	public int getLoc() {
		return loc;
	}
	public void setLoc(int loc) {
		this.loc = loc;
	}
	public Map<String, LocData> getLocMap() {
		return locMap;
	}
	public void setLocMap(Map<String, LocData> locMap) {
		this.locMap = locMap;
	}
	public static boolean isDebug() {
		return DEBUG;
	}
	public final void debug(Object msg) {
		if (DEBUG) {
			System.out.println(msg);
		}
	}
	public final int parseLoc(File javaFile) {
		int loc = 0;
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(javaFile);
			br = new BufferedReader(fr);
			String line = "";
			boolean inBlockComment = false;
			int count = 0;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				debug((++count) + ": " + line);
				if (inBlockComment) {
					int index = line.indexOf(Comment.FINISH_BLOCK_COMMENT);
					if (index == -1) {
						continue;
					}
					else {
						inBlockComment = false;
						if (line.endsWith(Comment.FINISH_BLOCK_COMMENT)) {
							continue;
						}
						line = line.substring(index + 2);
					}
				}
				if (!line.startsWith(Comment.LINE_COMMENT) && line.length() > 0) {
					String noQuote = Comment.noQuoted(line);
					int lineCommentIndex = noQuote.indexOf(Comment.LINE_COMMENT);
					if (lineCommentIndex != -1) {
						noQuote = noQuote.substring(0, lineCommentIndex);
					}
					String noBlockComment = Comment.noBlockComment(noQuote);
					if (!noBlockComment.startsWith(Comment.START_BLOCK_COMMENT)) {
						loc += countingLoc(noBlockComment);
						int startBlockCommentIndex = noBlockComment.indexOf(Comment.START_BLOCK_COMMENT);
						if (-1 < startBlockCommentIndex) {
							inBlockComment = true;
						}
					}
					else {
						inBlockComment = true;
					}
				}
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			this.close(br);
		}
		return loc;
	}
	private void close(Reader reader) {
		try {
			if (reader != null) {
				reader.close();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public final int countingLoc(String line) {
		if (line.startsWith("import")) {
			return 0;
		}
		else if (line.startsWith("package")) {
			return 0;
		}
		else if ("{".equals(line)) {
			return 0;
		}
		else if ("}".equals(line)) {
			return 0;
		}
		else if ("(".equals(line)) {
			return 0;
		}
		else if (")".equals(line)) {
			return 0;
		}
		else if (line.startsWith("@")) {
			return 0;
		}
		return countSemicolon(line);
	}
	public final int countSemicolon(String line) {
		int index = -1;
		int count = 0;
		while ((index = line.indexOf(";", index + 1)) != -1) {
			count++;
		}
		if (count == 0) {
			count = 1;
		}
		else if (!line.endsWith(";")) {
			count++;
		}
		debug("count: " + count);
		return count;
	}
	public void countLoc(String srcPath, LocData data) {
		File[] javaFiles = new File(srcPath).listFiles();
		if (javaFiles != null) {
			for (File f: javaFiles) {
				if (f.isFile() && f.getName().endsWith(JAVA_FILE_EXTENSION)) {
					int javaLoc = parseLoc(f);
					data.getLocMap().put(f.getAbsolutePath(), javaLoc);
					this.loc += javaLoc;
				}
				else {
					countLoc(f.getPath(), data);
				}
			}
		}
	}
	public void countLoc(String...srcPaths) {
		if (srcPaths == null) {
			return;
		}
		for (String srcPath: srcPaths) {
			LocData data = new LocData();
			data.setBasePath(srcPath);
			this.locMap.put(srcPath, data);
			this.countLoc(srcPath, data);
		}
	}
	public static void main(String[] args) {
		if (args != null && args.length > 0) {
			LocParser locParser = new LocParser();
			locParser.countLoc(args);
			new LocOutput().report(locParser);
		}
		else {
			System.out.println("usage: java org.fangsoft.loc.LocParser srcPath");
		}
	}
}
