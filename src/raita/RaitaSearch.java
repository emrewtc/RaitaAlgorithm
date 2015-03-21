package raita;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import java.util.Arrays;

public class RaitaSearch 
{
	public static List<Integer> matchIndex = new ArrayList<Integer>();
	public static int matchCount = 0;
	public static int comparision = 0;
	public static long totalTime;
	public static boolean hasFound = false;

	// Step by Step variables
	public static boolean willShift = false; 
	public static int compStartIndex;
	//public static int lastChIndex;
	//public static int firstChIndex;
	//public static int midChIndex;
	// Pre-Processing, bad character table values...
	
	public static void preBmBc(char[] pattern, int patternSize, int[] bmBc)
	{
		for(int i = 0; i < bmBc.length; ++i)
			bmBc[i] = patternSize;			// assign the same value to the characters that is not part of pattern
		
		for(int i = 0; i < patternSize - 1; ++i)
			bmBc[pattern[i]] = patternSize - i - 1;			// assign bad character table to pattern char values
	}

	// Raita
	
	public static void Raita(char[] text, char[] pattern)
	{
		long startTime = System.currentTimeMillis();
		int i;
		matchCount=0; 
		comparision = 0;
		matchIndex.clear();
		hasFound = false;
		int textSize = text.length;
		int patternSize = pattern.length;
		char c, firstCh,middleCh,lastCh;
		char[] shiftedText = new char[patternSize];
		int[] bmBc = new int[65536]; // size of an integer is 4 bytes = 16 bits = 2^16 = 65536
		
		// Pre-Processing
		preBmBc(pattern, patternSize, bmBc);
	    firstCh = pattern[0];
	    middleCh = pattern[patternSize/2];
	    lastCh = pattern[patternSize-1];

	    
	    i = 0;
	    while(i <= textSize - patternSize)
	    {
	        c = text[i + patternSize - 1];
	        // Last character matching check
	        if (lastCh == c)
	        {
	            comparision++;
	            
	            // First character matching check
	            if(firstCh == text[i])
	            {
	                comparision++;
	                // Middle character matching check
	                if(middleCh == text[i + patternSize/2])
	                {
	                	shiftedText = new char[patternSize];
	                    comparision++;
	                    shiftedText = Arrays.copyOfRange(text, i, i + patternSize);
	                    
	                    if(Arrays.equals(pattern, shiftedText))
	                    {
	                        comparision += patternSize;
	                        System.out.println("Pattern is found at " + i);
	                        matchCount++;
	                        matchIndex.add(i);
	                        hasFound = true;
	                        //i += bmBc[c];
	                        //System.out.println("C Value " + bmBc[c]);
	                        
	                    }
	                }
	                
	            }
	        }
	        
	        else
	        {
	        	
	        }
	        
	        	i += bmBc[c]; // shift //
	    	
	    }

		
		if(matchCount == 0)
		{
			System.out.println("Couldn't Found");
			hasFound = false;
			
		}
		
		System.out.println("Match Count: " + matchCount);
		System.out.println("Comparision Count: " + comparision);
		
		long endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		
		
		/*
		// Searching 
		i = 0;		
		while(i <= textSize - patternSize)
		{
			if (Arrays.equals(pattern, shiftedText)) 
			{
				System.out.println("Found at " + i);
				matchIndex.add(i);
				matchCount++;
				hasFound = true;
			}
				// Shifting
				shiftedText = new char[patternSize];

				if(i + patternSize > textSize)
				{
					i += bmBc[text[textSize -1]];
					comparision++;	
				}
					
				else
				{
					if(i+patternSize >= textSize)
						i += bmBc[text[textSize-1]];
					else
						i += bmBc[text[i + patternSize]];
					comparision++;
				}
				
				if(i+patternSize >= textSize)
					shiftedText = Arrays.copyOfRange(text, textSize - patternSize, textSize);
				else
					shiftedText = Arrays.copyOfRange(text, i, i+patternSize);
		}
		
		*/
		
	}
	
	
	/*
	public static void RaitaStepByStep(char[] text, char[] pattern)
	{
		int i;
		matchCount=0; 
		comparision = 0;
		matchIndex.clear();
		hasFound = false;
		int textSize = text.length;
		int patternSize = pattern.length;
		char c, firstCh,middleCh,lastCh;
		char[] shiftedText = new char[patternSize];
		int[] bmBc = new int[65536]; // size of an integer is 4 bytes = 16 bits = 2^16 = 65536
		
		// Pre-Processing
		preBmBc(pattern, patternSize, bmBc);
	    firstCh = pattern[0];
	    middleCh = pattern[patternSize/2];
	    lastCh = pattern[patternSize-1];

	    
	    i = 0;
	    if(willShift && i <= textSize - patternSize)
	    {
	        c = text[i + patternSize - 1];
	        // Last character matching check
	        if (lastCh == c)
	        {
	            comparision++;
	            
	            // First character matching check
	            if(firstCh == text[i])
	            {
	                comparision++;
	                // Middle character matching check
	                if(middleCh == text[i + patternSize/2])
	                {
	                	shiftedText = new char[patternSize];
	                    comparision++;
	                    shiftedText = Arrays.copyOfRange(text, i, i + patternSize);
	                    
	                    if(Arrays.equals(pattern, shiftedText))
	                    {
	                        comparision += patternSize;
	                        System.out.println("Pattern is found at " + i);
	                        matchCount++;
	                        matchIndex.add(i);
	                        hasFound = true;
	                        //i += bmBc[c];
	                        //System.out.println("C Value " + bmBc[c]);
	                        
	                    }
	                }
	                
	            }
	        }
	        
	        	i += bmBc[c]; // shift //
	        	willShift = false;
	    	
	    }

		
		if(matchCount == 0)
		{
			System.out.println("Couldn't Found");
			hasFound = false;
			
		}
		
		
	}
	*/
	
	public static void main(String[] args) 
	{
		
		String text = "GCATCGCAGAGAGTATACAGTACG";
		String pattern = "GCAGAGAG";
		Raita(text.toCharArray(), pattern.toCharArray());
		System.out.println("Run Time: " + totalTime + " ms");
	}
	

}





	



