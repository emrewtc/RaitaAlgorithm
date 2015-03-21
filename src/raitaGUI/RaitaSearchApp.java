package raitaGUI;
import raita.RaitaSearch;

import java.text.*;
import java.util.Arrays;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RaitaSearchApp extends JFrame {

	private JPanel contentPane;
	private DefaultHighlighter highlighter;
	private DefaultHighlightPainter hPainter;
	private boolean willShift = false;
	private boolean lastChStep = false;
	private boolean firstChStep = false;
	private boolean midChStep = false;
	private boolean lastStep = false;
	private boolean firstStep = false;
	private int index = 0;
	private int stepCount = 0;
	private int comparision = 0;
	private int matchCount = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RaitaSearchApp frame = new RaitaSearchApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RaitaSearchApp() {
	    this.highlighter = new DefaultHighlighter();
	    this.hPainter = new DefaultHighlightPainter(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JTextPane textPane_Text = new JTextPane();
		textPane_Text.setBounds(85, 27, 465, 98);
		contentPane.add(textPane_Text);
		textPane_Text.setHighlighter(highlighter);
		
		final JTextPane textPane_Pattern = new JTextPane();
		textPane_Pattern.setBounds(85, 163, 158, 41);
		contentPane.add(textPane_Pattern);
		
		JLabel lblText = new JLabel("Text :");
		lblText.setBounds(12, 59, 61, 24);
		contentPane.add(lblText);
		
		JLabel lblPattern = new JLabel("Pattern :");
		lblPattern.setBounds(12, 174, 61, 16);
		contentPane.add(lblPattern);
		
		JLabel lblTime = new JLabel("Run Time :");
		lblTime.setBounds(505, 229, 83, 16);
		contentPane.add(lblTime);
		
		final JLabel lblTimeresult = new JLabel("");
		lblTimeresult.setBounds(596, 229, 61, 16);
		contentPane.add(lblTimeresult);
		
		JLabel lblComparision = new JLabel("Comparision :");
		lblComparision.setBounds(486, 268, 98, 16);
		contentPane.add(lblComparision);
		
		final JLabel lblComparisionresult = new JLabel("");
		lblComparisionresult.setBounds(596, 268, 61, 16);
		contentPane.add(lblComparisionresult);
		
		JLabel lblMatch = new JLabel("Match :");
		lblMatch.setBounds(529, 316, 61, 16);
		contentPane.add(lblMatch);
		
		final JLabel lblMatchresult = new JLabel("");
		lblMatchresult.setBounds(596, 316, 61, 16);
		contentPane.add(lblMatchresult);
		
		
		JButton btnRaitaSearch = new JButton("Raita Search");
		btnRaitaSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				RaitaSearch.Raita(textPane_Text.getText().toCharArray(), textPane_Pattern.getText().toCharArray());
				lblComparisionresult.setText(Integer.toString(RaitaSearch.comparision));
				lblMatchresult.setText(Integer.toString(RaitaSearch.matchCount));
				lblTimeresult.setText(Long.toString(RaitaSearch.totalTime) + " ms");
			
				if(!RaitaSearch.hasFound)
					JOptionPane.showMessageDialog(null, "Pattern has not found!");
				else 
				{	
					for(int i = 0; i < RaitaSearch.matchIndex.size(); i++)
					{
						try 
						{
							highlighter.addHighlight(RaitaSearch.matchIndex.get(i),RaitaSearch.matchIndex.get(i) + textPane_Pattern.getText().length(), hPainter);
						}
						catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}
				}
				
			}
		});
		btnRaitaSearch.setBounds(577, 22, 117, 56);
		contentPane.add(btnRaitaSearch);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				textPane_Text.setText("");
				textPane_Pattern.setText("");
				lblComparisionresult.setText("");
				lblMatchresult.setText("");
				lblTimeresult.setText("");
				index = 0;
		    	matchCount = 0;
		    	comparision = 0;
				lastChStep = false;
		    	firstChStep = false;
		    	midChStep = false;
		    	lastStep = false; 
		    	
				
			}
		});
		btnClear.setBounds(577, 158, 117, 46);
		contentPane.add(btnClear);
		JButton btnStep = new JButton("Step By Step");
		btnStep.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{ 
				//matchIndex.clear();
				boolean hasFound = false;
				willShift = true;
				int textSize = textPane_Text.getText().length();
				int patternSize = textPane_Pattern.getText().length();
				char[] pattern = textPane_Pattern.getText().toCharArray();
				char [] text = textPane_Text.getText().toCharArray();
				char c, firstCh,middleCh,lastCh;
				char[] shiftedText = new char[patternSize];
				int[] bmBc = new int[65536]; // size of an integer is 4 bytes = 16 bits = 2^16 = 65536
				int totalStepCount = textSize / patternSize;
				
				// Pre-Processing
				RaitaSearch.preBmBc(pattern, patternSize, bmBc);
			    firstCh = pattern[0];
			    middleCh = pattern[patternSize/2];
			    lastCh = pattern[patternSize-1];

			    System.out.println("step count: " + stepCount );
			    System.out.println("total step count: " + totalStepCount );
			    
			    System.out.println("lastchstep: " + lastChStep);
			    System.out.println("firstchstep: " + firstChStep);
			    System.out.println("midchstep: " + midChStep);
			    System.out.println("laststep: " + lastStep);
			    
			    if(index <= textSize - patternSize)
			    {
			    	if(index == 0)
			    	{
			    		// PAINT IT GRAY
			    		for(int j = index; j < index + patternSize; j++)
			    		{
			    			try 
			    			{
			    				hPainter = new DefaultHighlightPainter(Color.LIGHT_GRAY);
			    				highlighter = new DefaultHighlighter();
			    				textPane_Text.setHighlighter(highlighter);
			    				highlighter.addHighlight(index,index + textPane_Pattern.getText().length(), hPainter);
			    			}
			    			catch (BadLocationException e) {
			    				// TODO Auto-generated catch block
			    				e.printStackTrace();
			    			}						
			    		}
			    	}
			    	
			    	else
			    	{
			    		// PAINT IT GRAY
			    		for(int j = index; j < index + patternSize; j++)
			    		{
			    			try 
			    			{
			    				hPainter = new DefaultHighlightPainter(Color.LIGHT_GRAY);
			    				highlighter = new DefaultHighlighter();
			    				textPane_Text.setHighlighter(highlighter);
			    				highlighter.addHighlight(index,index + textPane_Pattern.getText().length(), hPainter);
			    			}
			    			catch (BadLocationException e) {
			    				// TODO Auto-generated catch block
			    				e.printStackTrace();
			    			}						
			    		}
			    		
			    	}
			    	
			    	if(!lastChStep && !firstChStep && !midChStep && !lastStep)
			    	{
			    		lastChStep = true;
			    		firstChStep = false;
			    		midChStep = false;
			    		lastStep = false;
			    		return;			    		
			    	}
			    	
			    	
			    }
			    
			    if(lastChStep && !firstChStep && !midChStep && !lastStep)
			    {
			    	c = text[index + patternSize - 1];				    
				    // Last character matching check
			        if (lastCh == c)
			        {
			            comparision++;			        
			            
			            // PAINT IT GREEN
						try 
						{
							hPainter = new DefaultHighlightPainter(Color.GREEN);
		    				highlighter = new DefaultHighlighter();
		    				textPane_Text.setHighlighter(highlighter);
							highlighter.addHighlight(index + textPane_Pattern.getText().length()-1,index + textPane_Pattern.getText().length(), hPainter);
						}
						catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			        
				    else
				    {
			            comparision++;
			            // PAINT IT RED
						try 
						{
							hPainter = new DefaultHighlightPainter(Color.RED);
		    				highlighter = new DefaultHighlighter();
		    				textPane_Text.setHighlighter(highlighter);
							highlighter.addHighlight(index + textPane_Pattern.getText().length()-1,index + textPane_Pattern.getText().length(), hPainter);
						}
						catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    	lastChStep = false;
				    	firstChStep = false;
				    	midChStep = false;
				    	lastStep = true;
				    	return;
						
				    }
			        
			    	lastChStep = false;
			    	firstChStep = true;
			    	midChStep = false;
			    	lastStep = false; 
			    	return;
			    	
			    }
			    

			    	
			    if(!lastChStep && firstChStep && !midChStep && !lastStep)
			    {
		            comparision++;
		            
		            if(firstCh == text[index])
		            {
		            	// PAINT IT GREEN
		            	try 
		            	{
		            		hPainter = new DefaultHighlightPainter(Color.GREEN);
		    				highlighter = new DefaultHighlighter();
		    				textPane_Text.setHighlighter(highlighter);
		            		highlighter.addHighlight(index,index + 1, hPainter);
		            	}
		            	catch (BadLocationException e) {
		            		// TODO Auto-generated catch block
		            		e.printStackTrace();
		            	}
		            	
		            	lastChStep = false;
		            	firstChStep = false;
		            	midChStep = true;
		            	lastStep = false; 
		            	return;
		            }
		            
				    
				    else
				    {
			            comparision++;
			            // PAINT IT RED
						try 
						{
							hPainter = new DefaultHighlightPainter(Color.RED);
		    				highlighter = new DefaultHighlighter();
		    				textPane_Text.setHighlighter(highlighter);
							highlighter.addHighlight(index,index + 1, hPainter);
						}
						catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
				    	lastChStep = false;
				    	firstChStep = false;
				    	midChStep = false;
				    	lastStep = true; 
				    	return;
				    }
					
			    }
			    
			    
			    if(!lastChStep && !firstChStep && midChStep && !lastStep)
			    {
			    	 if(middleCh == text[index + patternSize/2])
			    	 {
			            comparision++;
			            
		                if(middleCh == text[index + patternSize/2])
		                {
			            // PAINT IT GREEN
			            	
							try 
							{
								hPainter = new DefaultHighlightPainter(Color.GREEN);
			    				highlighter = new DefaultHighlighter();
			    				textPane_Text.setHighlighter(highlighter);
								highlighter.addHighlight(index + patternSize/2, index + patternSize/2 + 1, hPainter);
							}
							catch (BadLocationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                }
			    	 }
			    	 
			    	 else
			    	 {
			    		 comparision++;
				            // PAINT IT RED
			            	
								try 
								{
									hPainter = new DefaultHighlightPainter(Color.RED);
				    				highlighter = new DefaultHighlighter();
				    				textPane_Text.setHighlighter(highlighter);
									highlighter.addHighlight(index + patternSize/2, index + patternSize/2 + 1, hPainter);
								}
								catch (BadLocationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
			    	 }
			    	 
				    	lastChStep = false;
				    	firstChStep = false;
				    	midChStep = false;
				    	lastStep = true;
				    	return;
			    		 
			    }
			    
			    if(!lastChStep && !firstChStep && !midChStep && lastStep)
			    {	
			    	
                	shiftedText = new char[patternSize];
                    shiftedText = Arrays.copyOfRange(text, index, index + patternSize); 
			    	
	                if(Arrays.equals(pattern, shiftedText))
                    {
                    	// PAINT IT GREEN
						try 
						{
							hPainter = new DefaultHighlightPainter(Color.GREEN);
		    				highlighter = new DefaultHighlighter();
		    				textPane_Text.setHighlighter(highlighter);
							highlighter.addHighlight(index, index + patternSize, hPainter);
						}
						catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        comparision += patternSize;
                        System.out.println("Pattern is found at " + index);
                        matchCount++;
                        hasFound = true;
                    }
                    
                    else
                    {
                    	comparision++;
                    	// PAINT IT RED 
						try 
						{
							hPainter = new DefaultHighlightPainter(Color.RED);
		    				highlighter = new DefaultHighlighter();
		    				textPane_Text.setHighlighter(highlighter);	
							highlighter.addHighlight(index, index + patternSize, hPainter);
						}
						catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
                    }
	                
	                c = text[index + patternSize - 1];
			    	lastChStep = false;
			    	firstChStep = false;
			    	midChStep = false;
			    	lastStep = false;
			    	index += bmBc[c]; // shift //
			    	System.out.println("Shift: " + bmBc[c]);
			    	stepCount++;
			    	return;
					
			    }
			    
				lblComparisionresult.setText(Integer.toString(comparision));
				lblMatchresult.setText(Integer.toString(matchCount));

			   }
			
		});

		btnStep.setBounds(577, 91, 117, 56);
		contentPane.add(btnStep);
		
		}
	
	
	
}
