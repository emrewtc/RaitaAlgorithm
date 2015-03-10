package raitaGUI;
import raita.RaitaSearch;

import java.text.*;
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
		setBounds(100, 100, 504, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JTextPane textPane_Text = new JTextPane();
		textPane_Text.setBounds(85, 27, 273, 98);
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
		lblTime.setBounds(285, 225, 83, 16);
		contentPane.add(lblTime);
		
		final JLabel lblTimeresult = new JLabel("");
		lblTimeresult.setBounds(395, 225, 61, 16);
		contentPane.add(lblTimeresult);
		
		JLabel lblComparision = new JLabel("Comparision :");
		lblComparision.setBounds(285, 273, 98, 16);
		contentPane.add(lblComparision);
		
		final JLabel lblComparisionresult = new JLabel("");
		lblComparisionresult.setBounds(395, 273, 61, 16);
		contentPane.add(lblComparisionresult);
		
		JLabel lblMatch = new JLabel("Match :");
		lblMatch.setBounds(285, 322, 61, 16);
		contentPane.add(lblMatch);
		
		final JLabel lblMatchresult = new JLabel("");
		lblMatchresult.setBounds(395, 322, 61, 16);
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
		btnRaitaSearch.setBounds(370, 27, 117, 56);
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
			}
		});
		btnClear.setBounds(370, 96, 117, 46);
		contentPane.add(btnClear);
		
	}
}
