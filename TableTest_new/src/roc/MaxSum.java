package roc;

import java.io.*;
import java.lang.String;
import java.nio.CharBuffer;



public class MaxSum {
    File file;
    Reader reader;
    int max;
    public int mode,row,line;
    int x1,y1,x2,y2;
    public int[][] matrix;
    public int[][] answer;
    String[] args;
    String filename;
    String input;
    MaxSum(String[] string){
    	matrix = new int[100][100];
    	answer = new int[100][100];
    	this.args = string;
    	analyse();
    	this.file = new File(filename);
    	readfile();
    	toMatrix();
    	switch(this.mode){
    	case 0:
        	this.max = maxNum2(this.row, this.line);
    		break;
    	case 1:
        	this.max = maxNum5(this.row, this.line);
    		break;
    	case 2:
        	this.max = maxNum4(this.row, this.line);
    		break;
    	case 3:
        	this.max = maxNum6(this.row, this.line);
    		break;
    		
    	}

    	System.out.println("");
      	for(int i=0; i<=row+1; i++){
    		for(int j=0; j<=line+1; j++)
    			System.out.print(this.answer[i][j] + " ");
    		System.out.println("");
    	}
      	System.out.println(this.max);
    	
    }
    void analyse(){
    	if(args.length == 0)
    	{
    		System.out.println("error");
    		//System.exit(0);
    	}
    	else if(args.length == 1){
    		this.filename = args[0];
    		mode = 0;
    	}
    	else if(args.length == 2){
    		this.filename = args[1];
    		mode = args[0].equals("/v")? 1:2; //1是垂直 2是水平
    	}
    	else if(args.length == 3){
    		this.filename = args[2];
    		mode = 3;
    	}
    	
    }
    void readfile(){
        CharBuffer cbuf = null;
        try
        {
            FileReader fReader = new FileReader(file);
            cbuf = CharBuffer.allocate((int) file.length());
            fReader.read(cbuf);
            input = new String(cbuf.array());
            System.out.println(input);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    void toMatrix(){
    	int temp=0;
    	String[] nums = null;
    	nums = input.split(",");
    	System.out.println(nums.length);
    	for(int i=0; i<nums.length; i++){
    		temp = 0;
    		int set=1;
    		for(int j=0; j<nums[i].length();j++){
    			char here = nums[i].charAt(j);
    			if(here == '-')
    				set = -1;
    			if (here >= '0' &&  here <= '9')
    				temp = temp*10+ (here - '0' );
    		}
    		temp = temp * set;
    		System.out.println(temp+" "+i);
    		if(i==0)
    			this.row = temp;
    		else if(i==1)
    			this.line = temp;
    		else matrix[(i-2)/this.line+1][(i-2)%this.line+1] = temp;
    	}
    	for(int i=0; i<=row+1; i++){
    		for(int j=0; j<=line+1; j++)
    			System.out.print(matrix[i][j] + " ");
    		System.out.println("");
    	}
    	
    	

    	
    }

    int maxNum1(int m,int n){
    	int pre[][] = new int[100][100];
    	int sum = 0,max = 0;
    	for(int i=1;i<=m;i++)
    		for(int j=1;j<=n;j++)
    			pre[i][j]=pre[i-1][j]+pre[i][j-1]-pre[i-1][j-1]+matrix[i][j];

    	for(int i=1; i<=m; i++)
    		for(int j=1;j<=n;j++)
    			for(int k=i; k<=m; k++)
    				for(int l=j; l<=n; l++){
    					sum = pre[k][l] - pre[k][j-1] - pre[i-1][l] + pre[i-1][j-1];
    					if(sum > max){
    						max = sum;
    						x1=i;
    						y1=j;
    						x2=k;
    						y2=l;
    					}
    				}
    	for(int i=x1; i<=x2; i++)
    		for(int j=y1; j<=y2; j++){
    			this.answer[i][j] = 1;
    		}
    	return max;
    }
    int maxNum2(int m,int n){
    	int pre[][] = new int[100][100];
    	int sum = -1,max = 0;
    	int columnsMax = 0;
    	for(int i=1;i<=m;i++)
    		for(int j=1;j<=n;j++)
    			pre[i][j] = pre[i][j-1] + matrix[i][j];
    	int prex1=0;
    	for(int i=1; i<=n; i++)
    		for(int j=i;j<=n;j++){
    			for(int k=1; k<=m; k++){
    		        if(sum<0){
    		        	prex1 = k;
    					sum=pre[k][j] - pre[k][i-1];
    		        }
    					
    		        else
    					sum+=pre[k][j] - pre[k][i-1];
    				if(columnsMax < sum){
    					columnsMax = sum;
						x1=prex1;
						y1=i;
						x2=k;
						y2=j;
    				}
    				
    			}
    			sum = -1;
    		}
    	for(int i=x1; i<=x2; i++)
    	    for(int j=y1; j<=y2; j++){
    	    	this.answer[i][j] = 1;
    	    }

    	return columnsMax;
    }
    //水平
    int maxNum4(int m, int n){
    	int pre[][] = new int[100][100];
    	int sum = 0,max = 0;
    	int columnsMax = 0;
    	for(int i=1;i<=m;i++)
    		for(int j=1;j<=2*n;j++)
    			pre[i][j] = pre[i][j-1] + matrix[i][(j-1)%n +1];
    	int prex1=0;
    	for(int i=1; i<=n; i++)
    		for(int j=i;j<=i+n-1;j++){
    			for(int k=1; k<=m; k++){
    		        if(sum<0){
    					sum=pre[k][j] - pre[k][i-1];
    					prex1 = k;
    		        }
    		        else
    					sum+=pre[k][j] - pre[k][i-1];
    				if(columnsMax < sum){
    					columnsMax = sum;
						x1=prex1;
						y1=i;
						x2=k;
						y2=j;
    				}
    			}
    			sum = -1;
    		}
    	for(int i=x1; i<=x2; i++)
    	    for(int j=y1; j<=y2; j++){
    	    	this.answer[i][(j-1)%n+1] = 1;
    	    }

    	return columnsMax;
    }

    //竖直环
    int maxNum5(int m,int n){
    	int pre[][] = new int[100][100];
    	int sum = -1,max = 0;
    	int columnsMax = 0;
    	for(int i=1;i<=m;i++)
    		for(int j=1;j<=n;j++)
    			pre[i][j] = pre[i][j-1] + matrix[i][j];

    	int x=1,prex1=0;
    	for(int i=1; i<=n; i++)
    		for(int j=i;j<=n;j++){
    			prex1 = 0;
    			for(int k=1; k <= m*2; k++){
    				if( x >= m){
    					x=1;
    					k = prex1+1;
    					prex1 = k;
    					System.out.println("k:"+k+" m"+m);
    					sum=pre[(k-1)%m + 1][j] - pre[(k-1)%m + 1][i-1];
    				}
    				else if(sum < 0 ){
    					x=1;
    					prex1 = k;
    					sum=pre[(k-1)%m + 1][j] - pre[(k-1)%m + 1][i-1];
    				}
    		        else{
    					x++;
    					sum+=pre[(k-1)%m + 1][j] - pre[(k-1)%m + 1][i-1];
    				}
    				if(columnsMax < sum){
    					columnsMax = sum;
						x1=prex1;
						y1=i;
						x2=k;
						y2=j;
    				}
    			}
    			sum = -1;
    		}
    	for(int i=x1; i<=x2; i++)
    	    for(int j=y1; j<=y2; j++){
    	    	this.answer[(i-1)%m+1][j] = 1;
    	    }
    	return columnsMax;
    }
    //备胎
    int maxNum6(int m, int n){
    	int pre[][] = new int[100][100];
    	int sum = 0;
    	int columnsMax = 0;
    	for(int i=1;i<=m;i++)
    		for(int j=1;j<=2*n;j++)
    			pre[i][j] = pre[i][j-1] + matrix[i][(j-1)%n +1];

    	int x=1;
    	for(int i=1; i<=n; i++)
    		for(int j=i;j<=i+n-1;j++){
    			int prex1=0;
    			for(int k=1; k <= m*2; k++){
    				if( x >= m){
    					x=1;
    					k = prex1+1;
    					prex1 = k;
    					sum=pre[(k-1)%m + 1][j] - pre[(k-1)%m + 1][i-1];
    				}
    				else if(sum<0){
    					x=1;
    					sum=pre[(k-1)%m + 1][j] - pre[(k-1)%m + 1][i-1];
    				}
    		        else{
    					x++;
    					sum+=pre[(k-1)%m + 1][j] - pre[(k-1)%m + 1][i-1];
    				}
    				if(columnsMax < sum)
    					columnsMax = sum;
						x1=prex1;
						y1=i;
						x2=k;
						y2=j;
    			}
    			sum = 0;
    		}
    	for(int i=x1; i<=x2; i++)
    	    for(int j=y1; j<=y2; j++){
    	    	this.answer[(i-1)%m+1][(j-1)%n+1] = 1;
    	    }
    	return columnsMax;
    	
    }
}
