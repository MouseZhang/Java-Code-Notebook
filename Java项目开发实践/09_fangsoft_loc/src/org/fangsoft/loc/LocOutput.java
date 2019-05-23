package org.fangsoft.loc;

public class LocOutput {
	public static final String LOC_FORMAT = "%1$-35s:%2$-5s%n";
	
	private String locFormat;
	public String getLocFormat() {
		return locFormat;
	}
	public void setLocFormat(String locFormat) {
		this.locFormat = locFormat;
	}
	public void report(LocParser locParser) {
		if (locParser.getLocMap() == null) {
			return;
		}
		int javaFiles = 0;
		for (String path: locParser.getLocMap().keySet()) {
			this.output("current base path", path);
			LocData data = locParser.getLocMap().get(path);
			for (String fileName: data.getLocMap().keySet()) {
				int loc = data.getLocMap().get(fileName);
				this.output(fileName.substring(data.getBasePath().length() + 1), String.valueOf(loc));
				javaFiles++;
			}
		}
		this.output("total loc", String.valueOf(locParser.getLoc()));
		this.output("total java file", String.valueOf(javaFiles));
		this.output("loc per java file", String.valueOf(locParser.getLoc() / javaFiles));
	}
	private void output(String prompt, String str) {
		System.out.printf(LOC_FORMAT, prompt, str);
	}
}
