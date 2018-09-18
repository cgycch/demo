package demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Test01 {
	public static void main(String[] args) throws IOException {

		// 获取文件大小的三种方法

		// 大文件
		String fileStr = "E:\\src\\cn_windows_7_ultimate_with_sp1_x64_dvd_618537.iso";
		// 小文件
		String fileStr01 = "E:\\src\\Chrome-Goagent.7z";

		File file = new File(fileStr);

		long size01 = getFileSize01(file);
		long size02 = getFileSize02(file);
		int size03 = getFileSize03(file);

		System.out.println("小文件计算文件大小");
		System.out.println("文件名称:" + file.getName());
		System.out.println("length方法获取文件大小:" + size01 + " byte.");
		System.out.println("FileChannel方法获取文件大小:" + size02 + " byte.");
		System.out.println("available方法获取文件大小:" + size03 + " byte.");
		System.out.println("\nInteger类型数据的最大值:" + Integer.MAX_VALUE);
		// double size1 = size01/1024/1024;//以M为单位

	}

	/*
	 * 获取文件大小的方法01: 通过文件的length() 方法获取文件大小，这个通常可以应用于本地文件的大小计算；
	 */
	private static long getFileSize01(File file) {

		return file.length();
	}

	/*
	 * 获取文件大小的方法02: 通过FileChannel类来获取文件大小，这个方法通常结合输入流相关， 因此可以用于文件网络传输时实时计算文件大小；
	 */
	private static long getFileSize02(File file) throws IOException {

		FileInputStream fis = new FileInputStream(file);
		FileChannel fc = fis.getChannel();
		return fc.size();
	}

	/*
	 * 获取文件大小的方法03: FileInputStream的available()方法看可以用于小文件的文件大小计算，
	 * 因为available()的返回值类型为int型，可能会存在溢出的情况， 所以 available()方法计算文件大小不建议使用；
	 */
	private static int getFileSize03(File file) throws IOException {

		FileInputStream fis = new FileInputStream(file);

		return fis.available();
	}

}
