/**
 * 
 */
package package1;

import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.util.Random;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.IOException;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * 
 */
public class SMTH implements java.awt.event.ActionListener {

	/**
	 * @author chance.willis {@link http://code.google.com/p/json-simple/,
	 *         https://github.com/meodai/color-names/}
	 * @since 10/14/2025
	 * @param args
	 */

	// Color vars
	public static String colorText;
	public static Color colorRgb;
	public static String colorHex;
	// JFrame variables
	public static JFrame mainFrame;
	public static JButton Random = null;
	public static JButton Enter = null;
	public static JTextField tf1;
	public static JLabel lb2 = new JLabel("RGB:  " + colorRgb);
	public static JLabel lb3 = new JLabel("Hex:  " + colorHex);

	public static Scanner Input = new Scanner(System.in);

	// Parses through the Json Info
	public static void JsonParser(String colorText) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONArray CollorArray = (JSONArray) parser.parse(new FileReader(
				"color-name-list.json"));  //CHANGE THIS TO FILE ON SYSTEM
		if (!colorText.equals("")) {

			for (Object itemInList : CollorArray) { // Iterates through the list to check if the inputed word and the
													// color name match
				JSONObject jsonItemInList = (JSONObject) itemInList;
				String name = (String) jsonItemInList.get("name"); // gets the name from the inside array. For example:
																	// {"name" : "100 Mph","hex" : "#c93f38"}, {}
				if (name.equals(colorText)) {
					colorHex = (String) jsonItemInList.get("hex");
					colorRgb = Color.decode(colorHex);
					lb2.setText("RGB:  " + (colorRgb.toString().replace("java.awt.Color", "")));
					lb3.setText("Hex:  " + colorHex);
					SMTH ChangeBackground = new SMTH();
					ChangeBackground.ChangeBackground(colorRgb);
					return;
				}
			}
			colorRgb = Color.decode((String) colorText);
		}
		System.out.println("\nThis Color is not in the list check if spelled right or try a different color:");
		SMTH ChangeBackground = new SMTH();
		ChangeBackground.ChangeBackground(colorRgb);
		Input.close();
	}

	public void MakeFrame() throws NumberFormatException {
		mainFrame = new JFrame("Color Program");
		GridBagLayout layout = new GridBagLayout();
		mainFrame.setLayout(layout);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1920, 1080);
		JLabel lb1 = new JLabel("Give me your Favorite Color:  ");
		tf1 = new JTextField("", 20);
		Enter = new JButton("Enter");
		Enter.addActionListener((ActionListener) this);

		Random = new JButton("Random Color:");
		Random.setPreferredSize(new Dimension(150, 50));

		Random.addActionListener((ActionListener) this);
		mainFrame.add(lb1);
		GridBagConstraints gridConstraint2 = new GridBagConstraints();

		gridConstraint2.fill = GridBagConstraints.HORIZONTAL;
		gridConstraint2.gridwidth = 2; // 2 columns wide
		gridConstraint2.gridy = 2; // third row

		mainFrame.add(tf1);
		mainFrame.add(Enter, gridConstraint2);
		mainFrame.add(Random);

		GridBagConstraints gridConstraint1 = new GridBagConstraints();

		gridConstraint1.fill = GridBagConstraints.HORIZONTAL;
		gridConstraint1.anchor = GridBagConstraints.LAST_LINE_END;
		gridConstraint1.gridx = 2;
		gridConstraint1.gridy = 2;
		mainFrame.add(lb2, gridConstraint1);
		gridConstraint1.gridy = 3;
		mainFrame.add(lb3, gridConstraint1);
		ChangeBackground(colorRgb);
	}

	public void ChangeBackground(Color colorRgb) {
		((JFrame) mainFrame).getContentPane().setBackground(colorRgb);
		mainFrame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// Code to execute when the button is pressed
		if (e.getSource() == Random) {
			Random rand = new Random();
			Color randColor = new Color(rand.nextInt(0, 255), rand.nextInt(0, 255), rand.nextInt(0, 255));
			lb2.setText("RGB:  " + (randColor.toString().replace("java.awt.Color", "")));
			lb3.setText("Hex:  " + RGBtoHex(randColor));
			((JFrame) mainFrame).getContentPane().setBackground(randColor);
		} else if (e.getSource() == Enter) {
			try {
				String getText1 = tf1.getText();
				String changeTxt = String.valueOf(Character.toUpperCase(getText1.charAt(0)));
				ArrayList<Integer> spaceIndex = new ArrayList<Integer>();
				int irun = 0;
				spaceIndex.add(1);

				for (int i = 0; i <= getText1.length() - 1; i++) {

					if (getText1.charAt(i) == ' ') {
						spaceIndex.add(i + 2);
						changeTxt += getText1.substring(spaceIndex.get(irun), i + 1)
								+ Character.toUpperCase(getText1.charAt(i + 1));
						irun++;
					}
				}
				changeTxt += getText1.substring(spaceIndex.get(spaceIndex.size() - 1));

				JsonParser(changeTxt);
			} catch (IOException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public String RGBtoHex(Color color) {
		String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
		return (hex);
	}

	public static void main(String[] args) throws IllegalAccessException, IOException, ParseException {
		// TODO Auto-generated method stub
		SMTH colorProgram = new SMTH();
		colorProgram.MakeFrame();

	}
}
