package demo;

import java.io.File;

public class Test02 {
	public static String fileName = "C:\\Users\\cch\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0";

	// 递归方式 计算文件的大小
	private long getTotalSizeOfFilesInDir(final File file) {
		if (file.isFile()) {
			return file.length();
		}
		final File[] children = file.listFiles();
		long total = 0;
		if (children != null) {
			for (final File child : children) {
				total += getTotalSizeOfFilesInDir(child);
			}
		}
		return total;
	}

	public static void main(final String[] args) {
		final long start = System.nanoTime();

		final long total = new Test02().getTotalSizeOfFilesInDir(new File(fileName));
		final long end = System.nanoTime();
		System.out.println("Total Size: " + total);//total/1024/1024
		System.out.println("Time taken: " + (end - start) / 1.0e9);
	}

}
