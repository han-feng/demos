package com.cvicse.refactor.util;

import java.util.Comparator;
import java.util.List;

/**
 * 源码重写工具类
 * 
 * @author han_feng
 *
 */
public class StringRewriter {

	/**
	 * 对源码进行重写的信息
	 * 
	 * @author han_feng
	 *
	 */
	public static class Info {
		int start;
		int end;
		String string;

		/**
		 * 源码重写信息
		 * 
		 * @param start       重写起点
		 * @param end         重写终点
		 * @param writeString 新写入内容
		 */
		public Info(int start, int end, String string) {
			this.start = start;
			this.end = end;
			this.string = string;
		}

		public int getEnd() {
			return end;
		}

		public int getStart() {
			return start;
		}

		public String getString() {
			return string;
		}

		public String toString() {
			return "{(" + start + "," + end + ")->'" + string + "'}";
		}
	}

	public static String rewrite(String source, List<Info> infos) {
		if(infos.size()<=0)
			return null;

		StringBuilder result = new StringBuilder(source);
		// 从后向前执行重写，才能保证start值的正确性
		infos.sort(Comparator.comparingInt(Info::getStart).reversed());
		int nextStart = infos.get(0).end;
		for (Info info : infos) {
			if (info.end <= nextStart) {
				try {
					result.replace(info.start, info.end, info.string);
				} catch (Exception e) {
					System.err.println("{"+info.start+", "+info.end+", "+info.string+"} "+e.getMessage());
				}
			} else {
				System.err.println("替换未执行：存在重叠区间[" + info.start + "," + info.end + "]<=" + nextStart);
			}
			nextStart = info.start;
		}
		return result.toString();
	}

}
