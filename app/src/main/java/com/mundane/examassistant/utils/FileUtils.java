package com.mundane.examassistant.utils;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by mundane on 2016/12/27 14:08
 */

public class FileUtils {
	//	Environment.getExternalStorageDirectory().getAbsolutePath()和Environment.getExternalStorageDirectory()会有不同吗?
	private static String SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

	private static String SDStateString = Environment.getExternalStorageState();


	public static void copy(File src, File outPut) {
		try {
			BufferedInputStream fis = null;
			BufferedOutputStream fos = null;
			try {
				fis = new BufferedInputStream(new FileInputStream(src));
				fos = new BufferedOutputStream(new FileOutputStream(outPut));
				int len;
				byte[] buffer = new byte[8 * 1024];
				while ((len = fis.read()) != -1) {
					fos.write(buffer, 0, len);
				}
			} finally {
				if (fis != null) {//对于文件流, 能关一个是一个
					try {
						fis.close();
					} finally {
						if (fos != null) {
							fos.close();
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void copy(InputStream inputStream, File outPutFile) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outPutFile);
			byte[] buffer = new byte[1024 * 8];
			int len;
			while ((len = inputStream.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void writeStringToFile(String string, File outPut) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(outPut));
			bw.write(string);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void writeStringToFile2(String string, File outPut) {
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(outPut));
			bos.write(string.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String readStringFromFile(File src) {
		BufferedReader br = null;
		String line;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader(src));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	public static String readStringFromFile2(File src) {
		BufferedInputStream bis = null;
		StringBuilder sb = new StringBuilder();
		try {
			bis = new BufferedInputStream(new FileInputStream(src));
			int len;
			byte[] buffer = new byte[1024 * 8];
			while ((len = bis.read(buffer)) != -1) {
				sb.append(new String(buffer, 0, len));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return sb.toString();
	}

	public static String readStringFromInputStream(InputStream inputStream) {
		BufferedReader br = null;
		String line;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	//	这个方法读取文本有问题, 有的地方有乱码
	public static String readStringFromInputStream2(InputStream inputStream) {
		BufferedInputStream bis = null;

		//StringBuilder比StringBuffer效率高
		StringBuilder sb = new StringBuilder();
		try {
			bis = new BufferedInputStream(inputStream);
			int len;
			byte[] buffer = new byte[1024 * 8];
			while ((len = bis.read(buffer)) != -1) {
				sb.append(new String(buffer, 0, len));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return sb.toString();
	}

	public static String readStringFromInputStream3(InputStream inputStream) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int i = -1;
			while ((i = inputStream.read()) != -1) {
				baos.write(i);
			}
			return baos.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return baos.toString();
	}


	/**
	 * 在sd卡上创建一个文件
	 *
	 * @param dir
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File createFileInSDCard(String dir, String fileName) throws IOException {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 在sd卡上创建一个文件夹
	 *
	 * @param dir
	 * @return
	 */
	public static File creatSDDir(String dir) {
		File dirFile = new File(SDCardRoot + dir + File.separator);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		return dirFile;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 *
	 * @param dir      目录路径
	 * @param fileName 文件名称
	 * @return
	 */
	public static boolean isFileExist(String dir, String fileName) {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		return file.exists();
	}

	public static long getSDAvailableSize() {
		if (SDStateString.equals(Environment.MEDIA_MOUNTED)) {
			// 取得sdcard文件路径
			File pathFile = Environment.getExternalStorageDirectory();
			android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());
			// 获取SDCard上每个block的SIZE
			long nBlocSize = statfs.getBlockSize();
			// 获取可供程序使用的Block的数量
			long nAvailaBlock = statfs.getAvailableBlocks();
			// 计算 SDCard 剩余大小Byte
			long nSDFreeSize = nAvailaBlock * nBlocSize;
			return nSDFreeSize;
		}
		return 0;
	}

	public static boolean write2SD(String dir, String fileName, byte[] bytes) {
		if (bytes == null) {
			return false;
		}
		OutputStream output = null;
		try {
			// 拥有可读可写权限，并且有足够的容量
			if (SDStateString.equals(Environment.MEDIA_MOUNTED) && bytes.length < getSDAvailableSize()) {
				File file = null;
				creatSDDir(dir);
				file = createFileInSDCard(dir, fileName);
				output = new BufferedOutputStream(new FileOutputStream(file));
				output.write(bytes);
				output.flush();
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static byte[] readFromSD(String dir, String fileName) {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		if (!file.exists()) {
			return null;
		}
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(file));
			byte[] data = new byte[inputStream.available()];
			inputStream.read(data);
			return data;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static File write2SDFromInput(String dir, String fileName, InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			int size = input.available();
			// 拥有可读可写权限，并且有足够的容量
			if (SDStateString.equals(Environment.MEDIA_MOUNTED) && size < getSDAvailableSize()) {
				creatSDDir(dir);
				file = createFileInSDCard(dir, fileName);
				output = new BufferedOutputStream(new FileOutputStream(file));
				byte buffer[] = new byte[4 * 1024];
				int temp;
				while ((temp = input.read(buffer)) != -1) {
					output.write(buffer, 0, temp);
				}
				output.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}


}
