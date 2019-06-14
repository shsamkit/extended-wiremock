package com.wm.extendedwiremock.util;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class DirectoryUtil {

	public static void forceDeleteDirectoryIfExists(String path) throws IOException {
		File target = new File(path);
		if (target.exists()) {
			FileUtils.forceDelete(new File(path));
		}
	}

	public static void forceMoveDirectoryIfExists(String sourcePath, String destinationPath) throws IOException {
		forceDeleteDirectoryIfExists(destinationPath);
		FileUtils.moveDirectoryToDirectory(new File(sourcePath), new File(destinationPath), true);
	}
}
