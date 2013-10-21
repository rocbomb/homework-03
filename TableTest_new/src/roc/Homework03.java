package roc;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class Homework03 {

	private static final int EXIT_ON_CLOSE = 0;

	/**
	 * @param args
	 * @throws IOException 
	 */
	// 程序的入口方法
	public static void main(String[] args) throws Exception  {
////		String s = args[0];
//		for(int i=1; i<args.length ; i++)
//			s = s + " " + args[i];
		String s = "   ";
        RandomAccessFile raf = new RandomAccessFile("D://swap.mm", "rw");
        FileChannel fc = raf.getChannel();
        MappedByteBuffer mbb = fc.map(MapMode.READ_WRITE, 0, 1024);

        int flag = mbb.get(0);//取读写数据的标志
        if(flag == 1){
        	for(int i=0; i<s.length(); i++)
        	mbb.put(i+1,(byte) s.charAt(i));
        	System.exit(0);
        }
        
        mbb.put(0, (byte)1);


		String[] ss = new String[1];

		ss[0] = "input.txt";
		MaxSum mx = new MaxSum(ss);
		
		Tabletest frame = new Tabletest();
		
		frame.setrow(mx.row);
		frame.setline(mx.line);
		frame.setanswer(mx.answer);
		frame.setmatrix(mx.matrix);
		frame.createTab();
		// 设置框架窗体的事件监听(关闭窗体事件)
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// 显示框架窗体
		frame.pack();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
