package ua.org.shaddy.microtools.httptools;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;

import ua.org.shaddy.microtools.StringTools;

public class PostFile {
	private String name;
	private String path;
	private String contentType;
	
	public String getContentType() {
		return contentType;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	
	public PostFile(String path){
		String[] chunks = StringTools.split(path, "/");
		name = chunks[chunks.length - 1];
		this.path = path; 
		init();
	}
	
	public PostFile(String name, String path){
		this.path = path;
		this.name = name;
		init();
		
	}
	public PostFile(File file){
		path = file.getAbsolutePath();
		name = file.getName();
		init();
	}
	
	private void init(){
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		contentType = mimeTypesMap.getContentType(path);
	}
}
