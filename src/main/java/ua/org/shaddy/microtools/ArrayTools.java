package ua.org.shaddy.microtools;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ArrayTools {
	public static String join(Object[] list, String joiner) {
		return join(Arrays.asList(list), joiner);
	}
	public static String join(List list, String joiner) {
		return join(list.iterator(),joiner);
	}
	
	public static String join(Iterator it, String joiner) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		while (it.hasNext())
		{
			Object element = it.next();
			if (first)
				first = false;
			else
				sb.append(joiner);
			sb.append(element);
		}
		return sb.toString();
	}
}
