package ua.org.shaddy.microtools;

import java.util.List;
import java.util.Map;

public class ObjectPrinter {
	public static String identString = "    ";
	public static String nlString = "\n";
	public static Integer maxNestedLevel = 20;
	public static void print(Object obj){
		System.out.println(format(obj));
	}
	/**
	 * prints object to the screen
	 * @param obj
	 * @return
	 */
	public static String format(Object obj){
		return format(obj,0);
	}
	
	private static String format(Object obj, Integer lvl){
		if (lvl > maxNestedLevel) {
			return "Maximum nested level (" + maxNestedLevel + ") reached";
		}
		StringBuilder sb = new StringBuilder();
		String indenter = genIdent(lvl+1);
		String curLvlIndenter = genIdent(lvl);
		if (obj instanceof Map){
			sb.append("[" + getNonAnonymousParentName(obj.getClass()) + "]").append("{").append(nlString);
			for (Object keyObj : ((Map<?, ?>) obj).keySet()){
				Object elem = ((Map<?, ?>) obj).get(keyObj);
				String keyStr = keyObj == null ? "null" : keyObj.toString();
				sb.append(indenter).append(keyStr + "=>").append(format(elem, lvl + 1));
				//sb.append(objectToString(elem, ++lvl));
			}
			sb.append(curLvlIndenter).append("}").append(nlString);
		} else if (obj instanceof List){
			sb.append("[" + getNonAnonymousParentName(obj.getClass())+"]").append("{").append(nlString);
			for (Integer key = 0; key < ((List<?>) obj).size(); key ++ ){
				Object elem = ((List<?>) obj).get(key);
				sb.append(indenter).append(key + "=>").append(format(elem, lvl + 1));
			}
			sb.append(curLvlIndenter).append("}").append(nlString);
		} else if (obj instanceof String){
			sb.append("\"").append(obj).append("\"").append(nlString);
		} else if (obj == null){
			sb.append("null").append(nlString);
		} else {
			sb.append("("+obj.getClass().getName()+")").append(obj.toString()).append(nlString);
		}
		return sb.toString();
	}
	
	private static String genIdent(Integer count){
		StringBuilder sb = new StringBuilder();
		for (int i = 0 ; i < count ; i++){
			sb.append(identString);
		}
		return sb.toString();
	}
	
	private static String getNonAnonymousParentName(Class c){
		try {
			c = getNonAnonymousParent(c);
			return c.getCanonicalName();
		} catch (Exception e) {
			return "Error!Class!Name";
		}
		
		
	}
	private static Class getNonAnonymousParent(Class c){
		Class cl = c;
		while (cl != null && cl.isAnonymousClass()){
			cl = cl.getSuperclass();
		}
		return cl;
	}
}
