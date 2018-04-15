package com.seva.NamoVenkatesaya;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SevaBookingWindow extends Frame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	private JTextField textField;
	public SevaBookingWindow()
	{
		frame = new JFrame(" Sri Venkateswara Suprabatham - Seva Booking");
		frame.setBounds(200, 100, 850, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.yellow);

		JPanel panel = (JPanel) frame.getContentPane();
		JLabel label = new JLabel();
		panel.add(label);
		label.setBounds(10, 20, 600, 495);
		label.setIcon(new ImageIcon("Lord_venkateswara.jpg"));


		textField = new JTextField();
		JPanel panel1 = (JPanel) frame.getContentPane();
		JLabel label1 = new JLabel();
		panel1.add(label1);
		label1.setBounds(480, 95, 390, 20);
		label1.setText("Please provide input file   ");


		textField.setBounds(350, 115, 300, 31);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		final JRadioButton autoOptions1 = new JRadioButton("API");
		final JRadioButton autoOptions2 = new JRadioButton("WEB", true);
		autoOptions1.setBounds(480, 160, 50, 20);
		autoOptions2.setBounds(550, 160, 70, 20);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(autoOptions1);
		buttonGroup.add(autoOptions2);

		frame.getContentPane().add(autoOptions1);frame.getContentPane().add(autoOptions2);


		JButton browseButton = new JButton("Browse");
		browseButton.setBounds(660, 115, 150, 31);
		browseButton.setBackground(Color.GREEN);
		frame.getContentPane().add(browseButton);


		// BrowseButton          
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser chooseFile = new JFileChooser();
				// For Directory
				chooseFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooseFile.setAcceptAllFileFilterUsed(false);

				int rVal = chooseFile.showOpenDialog(null);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					textField.setText(chooseFile.getSelectedFile().toString());
				}
			}
		});

		final JButton clickButton = new JButton("Seva booking");
		clickButton.setBounds(350, 190, 460, 30);
		clickButton.setBackground(Color.GREEN);
		frame.getContentPane().add(clickButton);


		frame.add(new JLabel(new ImageIcon("TTD_image.png")));
		// Process Seva Booking
		clickButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				if(autoOptions1.isSelected()) {
					System.out.println("Moving to TTD API...");
				} else {
					if(!isFirstFriday()) {
						System.out.println("Moving to TTD WEB...");
//						getNextFirstFriday(0);
							try {
								processSevaBooking(textField.getText());
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					} else {
						JOptionPane.showMessageDialog(clickButton,"Try it again in next Month First Friday.."+"\n"+	"ie ;"+
								"\n"+ getNextFirstFriday(0),"Warning!!",JOptionPane.WARNING_MESSAGE);
					}

				}

			}

			private boolean isFirstFriday() {
				Calendar c = Calendar.getInstance();
				int week = c.WEEK_OF_MONTH;
				int i = week-2;
				System.out.println("set Date "+i + "=="+ c.DAY_OF_WEEK);
				return false;
			}

			private String getNextFirstFriday(int offset) { 
				final String DATE_FORMAT_NOW = "dd-MMM-yyyy";
				Calendar cal = Calendar.getInstance();
				Calendar presentCal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
				cal = getFirstFriday(cal, offset);
				System.out.println("Calendar is ::: " + sdf.format(cal.getTime()) + "=====" + sdf.format(presentCal.getTime()));
				return sdf.format(cal.getTime());
			}

			private Calendar getFirstFriday(Calendar cal, int offset) {
				int dayofweek;
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				dayofweek = cal.get(Calendar.DAY_OF_WEEK); 
				if (dayofweek < Calendar.FRIDAY) 
					cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)
							+ Calendar.FRIDAY - dayofweek);
				return cal;
			}

			protected void processSevaBooking(String username) throws InterruptedException {
				System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-extensions");

				WebDriver driver = new ChromeDriver(options);
				driver.navigate().to("https://ttdsevaonline.com/#/login");
				driver.manage().window().maximize();
				driver.get("https://ttdsevaonline.com/#/login");

				driver.findElement(By.xpath("//input[@name=\"uName\"]")).sendKeys(username);
				Thread.sleep(2000);
				driver.findElement(By.xpath("//input[@name=\"pwd\"]")).sendKeys("Raghava123*");
				Thread.sleep(1000);

				driver.findElement(By.xpath("//button[@id=\"login_sub\"]")).click();
				Thread.sleep(10000);
				driver.findElement(By.xpath("//a[contains(@class,\"tplogout_pilgrim\")]")).click();
				System.out.println("Logout Successfully");
				

				driver.close();
			}

		});
		frame.setBackground(Color.yellow);
		frame.addNotify();
		frame.setVisible(true);
	}

}