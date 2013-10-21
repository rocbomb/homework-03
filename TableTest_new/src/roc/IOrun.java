package roc;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
public class IOrun extends Thread{

    Tabletest frame;
    String [] args;
    RandomAccessFile raf;
    FileChannel fc;
    MappedByteBuffer mbb;
    String s = "";
    public IOrun(Tabletest frame,RandomAccessFile raf,MappedByteBuffer mbb){
    	this.frame = frame;
    	this.raf = raf;
    	this.mbb = mbb;
    	this.fc = raf.getChannel();
    }
    
    
    
    public void run() {
    	while(true){
    		try{
    			int flag = mbb.get(1);//取读写数据的标志
    			if(flag != 0){
    				for(int i=1;mbb.get(i)!=0;i++){
    					s = s + mbb.get(i);
    						mbb.put(i,(byte)0);
    				}
    			String[] sss = s.split(" ");
    			MaxSum mx = new MaxSum(sss);
    			frame.setrow(mx.row);
    			frame.setline(mx.line);
    			frame.setanswer(mx.answer);
    			frame.setmatrix(mx.matrix);
    			frame.createTab();
    			}
    		}catch(Exception e){
    			
    		}
    		
    	}
    }
}