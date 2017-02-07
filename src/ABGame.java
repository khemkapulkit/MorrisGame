import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class ABGame {


	public static void main(String[] args)
	{
		//char [] board = new char[] {'X','X', 'B', 'X', 'X', 'X', 'X', 'W', 'W', 'X', 'W', 'X', 'B', 'X', 'W', 'B', 'X', 'B', 'X', 'W', 'X', 'B', 'X'};
		char[] board = readfile(args[0]);
		System.out.print("Input Board is -");
		for(int j=0;j<23; j++)
		{
			System.out.print(board[j]);
		}
		System.out.println();
		
		Output out= ABMidgameEndgame(Integer.parseInt(args[2]), true, board,Integer.MIN_VALUE, Integer.MAX_VALUE);
		System.out.print("Output Board is -");
		for(int j=0;j<23; j++)
		{
			System.out.print(out.b[j]);
		}
		System.out.println();
		System.out.println("Minmax Estimate - " + out.est);
		System.out.println("Positions Evaluated - " + out.posEval);
		writefile(args[1],out.b);				
	}
	
	public static char[] readfile(String s)
	{
		 try {
	            FileReader reader = new FileReader(s);
	            char[] c = new char[23] ;
	            reader.read(c);
	            reader.close();
	            return c;
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		return null;		
	}
	
	public static void writefile(String s, char[] c)
	{
		 try {
	            FileWriter writer = new FileWriter(s);
	            writer.write(c);
	            writer.close();            
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
				
	}
	
	public static int getNumPieces(char a, char[] board)
	{
		int num = 0;
		for(int i=0;i<23; i++)
		{
			if(board[i]==a)
			{
				num++;
			}
		}
		return num;
		
	}
	
	public static int statEstMidgameEndgame(char[] board)
	{
		int w = getNumPieces('W', board);
		int b = getNumPieces('B', board);
		ArrayList<char[]> list = generateMovesMidgameEndgameBlack(board);
		int numBlackMoves = list.size();
		if (b <= 2) 
			{return(10000);}
		else
			if (w <= 2) 
			{return(-10000);}
			else 
				if (numBlackMoves==0) 
					{return(10000);}
				else 
					{return ( (1000*(w - b)) - numBlackMoves);}

	}
	
	public static ArrayList<char[]> generateMovesMidgameEndgameBlack(char[] board)
	{
		char[] b = board.clone();
		for(int i=0;i<23;i++)
		{
			if(b[i]=='W')
			{
				b[i]='B';
			}
			else
				if(b[i]=='B')
				{
					b[i]='W';
				}
		}
		ArrayList <char[]> l= generateMovesMidgameEndgame(b);
		for (int j = 0; j < l.size(); j++)
		{
			char[] b1 = l.get(j);
			for(int i=0;i<23;i++)
			{
				if(b1[i]=='W')
				{
					b1[i]='B';
				}
				else
					if(b1[i]=='B')
					{
						b1[i]='W';
					}
			}
			l.set(j, b1);
		}
		return l;			
	}
	
	public static ArrayList<char[]> generateMovesMidgameEndgame(char[] board)
	{
		if (getNumPieces('W', board) == 3)
		{
			return generateHopping(board);
		}
		else
		{
			return generateMove(board);
		}
	}
	
	private static ArrayList<char[]> generateHopping(char[] board) 
	{
		ArrayList<char[]> l = new ArrayList<char[]>();
		for (int i = 0; i < 23; i++)
		{
			if (board[i] == 'W')
			{
				for (int j = 0; j < 23; j++)
				{
					if (board[j] == 'X')
					{
						char[] b = board.clone();
						b[i] = 'X';
						b[j] = 'W';
						if (closeMill(j, b))
						{
							l = generateRemove(b, l);
						}
						else
						{
							l.add(b);
						}
					}
				}
			}
		}
		return l;
	}

	private static ArrayList<char[]> generateMove(char[] board) 
	{
		ArrayList<char[]> l = new ArrayList<char[]>();
		for (int i = 0; i < 23; i++)
		{
			if (board[i] == 'W')
			{
				int n[] = neighbors(i);
				for (int j = 0; j<n.length ;j++)
				{
					int k =n[j];
					if (board[k] == 'X')
					{
						char[] b = board.clone();
						b[i]='X';
						b[k]='W';
						if (closeMill(k, b))
						{
							l = generateRemove(b, l);
						}
						else
						{
							l.add(b);
						}
					}
				}
			}
		}
		return l;
	
	}
	
	public static ArrayList<char[]> generateRemove(char[] board, ArrayList<char[]> l)
	{
		for (int i = 0; i < 23; i++)
		{
			if (board[i] == 'B')
			{
				if (!closeMill(i, board))
				{
					char[] b = board.clone();
					b[i] ='X';
					l.add(b);
				}
			}
		}
		return l;
	}
	
	public static int[] neighbors(int j)
	{		
		switch(j)
		{
			case 0:
				{int a[] = {1, 3, 8};return a;}
			case 1:
				{int a[] = {0, 2, 4};return a;}
			case 2:
				{int a[] = {1, 5, 13};return a;}
			case 3:
				{int a[] = {0, 4, 6, 9};return a;}
			case 4:
				{int a[] = {1, 3, 5};return a;}
			case 5:
				{int a[] = {2, 4, 7, 12};return a;}
			case 6:
				{int a[] = {3, 7, 10};return a;}
			case 7:
				{int a[] = {5, 6, 11};return a;}
			case 8:
				{int a[] = {0, 9, 20};return a;}
			case 9:
				{int a[] = {3, 8, 10, 17};return a;}
			case 10:
				{int a[] = {6, 9, 14};return a;}
			case 11:
				{int a[] = {7, 12, 16};return a;}
			case 12:
				{int a[] = {5, 11, 13, 19};return a;}
			case 13:
				{int a[] = {2, 12, 22};return a;}
			case 14:
				{int a[] = {10, 15, 17};return a;}
			case 15:
				{int a[] = {14, 16, 18};return a;}
			case 16:
				{int a[] = {11, 15, 19};return a;}
			case 17:
				{int a[] = {9, 14, 18, 20};return a;}
			case 18:
				{int a[] = {15, 17, 19, 21};return a;}
			case 19:
				{int a[] = {12, 16, 18, 22};return a;}
			case 20:
				{int a[] = {8, 17, 21};return a;}
			case 21:
				{int a[] = {18, 20, 22};return a;}
			case 22:
				{int a[] = {13, 19, 21};return a;}
			default:
				{int a[] = {};return a;}
		}
	}

	public static boolean closeMill(int j, char[] b)
	{
		
		if (b[j] == 'X')
		{
			return false;
		}
		else
		{
			char C = b[j];
			switch(j)
			{
				case 0:
					return (checkMill(b, C, 1, 2) || checkMill(b, C, 8, 20) || checkMill(b, C, 3, 6));
				case 1:
					return (checkMill(b, C, 0, 2));
				case 2:
					return (checkMill(b, C, 0, 1) || checkMill(b, C, 5, 7) || checkMill(b, C, 13, 22));
				case 3:
					return (checkMill(b, C, 0, 6) || checkMill(b, C, 4, 5) || checkMill(b, C, 9, 17));
				case 4:
					return (checkMill(b, C, 3, 5));
				case 5:
					return (checkMill(b, C, 3, 4) || checkMill(b, C, 2, 7) || checkMill(b, C, 12, 19));
				case 6:
					return (checkMill(b, C, 0, 3) || checkMill(b, C, 10, 14));
				case 7:
					return (checkMill(b, C, 2, 5) || checkMill(b, C, 11, 16));
				case 8:
					return (checkMill(b, C, 0, 20) || checkMill(b, C, 9, 10));
				case 9:
					return (checkMill(b, C, 8, 10) || checkMill(b, C, 3, 17));
				case 10:
					return (checkMill(b, C, 8, 9) || checkMill(b, C, 6, 14));
				case 11:
					return (checkMill(b, C, 7, 16) || checkMill(b, C, 12, 13));
				case 12:
					return (checkMill(b, C, 11, 13) || checkMill(b, C, 5, 19));
				case 13:
					return (checkMill(b, C, 11, 12) || checkMill(b, C, 2, 22));
				case 14:
					return (checkMill(b, C, 17, 20) || checkMill(b, C, 15, 16) || checkMill(b, C, 6, 10));
				case 15:
					return (checkMill(b, C, 14, 16) || checkMill(b, C, 18, 21));
				case 16:
					return (checkMill(b, C, 14, 15) || checkMill(b, C, 19, 22) || checkMill(b, C, 7, 11));
				case 17:
					return (checkMill(b, C, 3, 9) || checkMill(b, C, 14, 20) || checkMill(b, C, 18, 19));
				case 18:
					return (checkMill(b, C, 17, 19) || checkMill(b, C, 15, 21));
				case 19:
					return (checkMill(b, C, 17, 18) || checkMill(b, C, 16, 22) || checkMill(b, C, 5, 12));
				case 20:
					return (checkMill(b, C, 0, 8) || checkMill(b, C, 14, 17) || checkMill(b, C, 21, 22));
				case 21:
					return (checkMill(b, C, 20, 22) || checkMill(b, C, 15, 18));
				case 22:
					return (checkMill(b, C, 2, 13) || checkMill(b, C, 16, 19) || checkMill(b, C, 20, 21));
				default:
					return false;
			}
			
		}
	}
	
	private static boolean checkMill(char[] b, char C, int v1, int v2)
	{
		return(b[v1] == C && b[v2] == C);		
	}
	
	public static Output ABMidgameEndgame(int depth, boolean isWhite, char[] board,int alpha, int beta)
	{
		Output next = new Output();

		if (depth == 0)
		{
			next.est = statEstMidgameEndgame(board);
			next.posEval++;
			return next;
		}

		Output prev = new Output();
		ArrayList<char[]> nextMoves = (isWhite) ? generateMovesMidgameEndgame(board) : generateMovesMidgameEndgameBlack(board);
		for (char[] b : nextMoves)
		{
			if (isWhite)
			{
				prev = ABMidgameEndgame(depth - 1, false, b,alpha,beta);
				next.posEval += prev.posEval;
				next.posEval++;
				if (prev.est >= alpha)
				{
					alpha = prev.est;
					next.b = b;
				}
			}
			else
			{
				prev = ABMidgameEndgame(depth - 1, true, b, alpha, beta);
				next.posEval += prev.posEval;
				next.posEval++;
				if (prev.est <= beta)
				{
					beta = prev.est;
					next.b = b;
				}				
			}
			if (alpha >= beta)
			{
				break;
			}
		}
		next.est = (isWhite) ? alpha : beta;
		return next;
	}
}



	

