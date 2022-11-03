package com.example.seleniumdemo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.example.seleniumdemo.excelUtility;

@SuppressWarnings("serial")
public class CaseNumber_Runner extends JFrame implements ActionListener {

	@SuppressWarnings("rawtypes")
	JComboBox c1, c2, c3, c4, c5, c6;
	JLabel l1, l2, l3, l4, l5, l6, l7;
	JTextField t1, t2, t3;
	JButton b1, b2;
	JFrame frame;
	ArrayList<ArrayList<String>> Exceldata = new ArrayList<ArrayList<String>>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CaseNumber_Runner() {

		frame = new JFrame();
		frame.setBounds(200, 100, 700, 500);
		frame.setTitle("E-COURTS\r\n" + "OFFICIAL WEBSITE OF DISTRICT COURTS");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		l1 = new JLabel("Court Code");
		l1.setBounds(20, 50, 100, 30);
		frame.getContentPane().add(l1);

		String[] CourtCode = { "Court Complex", "Court Establishment" };
		c1 = new JComboBox(CourtCode);
		c1.setBounds(180, 50, 200, 30);
		frame.getContentPane().add(c1);

		l2 = new JLabel("Court Complex");
		l2.setBounds(20, 100, 100, 30);
		frame.getContentPane().add(l2);

		String[] CourtComplex = { "Select court complex", "Hingna, Civil and Criminal Court",
				"Bhiwapur, Civil and Criminal Court", "Mouda, Civil and Criminal Court",
				"Kalmeshwar, Civil and Criminal Cour", "Narkhed, Civil and Criminal Court",
				"Katol, Civil and Criminal Court", "Kuhi, Civil and Criminal Court",
				"Kamptee, Civil and Criminal Court", "Paresoni, Civil and Criminal Court",
				"Ramtek, Civil and Criminal Court", "Saoner, Civil and Criminal Court",
				"Umrer, Civil and Criminal Court", "Nagpur, District Sessions Court III",
				"Nagpur, District and Sessions Court" };
		c2 = new JComboBox(CourtComplex);
		c2.setBounds(180, 100, 350, 30);
		frame.getContentPane().add(c2);

		l2 = new JLabel("Court Establishment");
		l2.setBounds(20, 150, 150, 30);
		frame.getContentPane().add(l2);

		String[] CourtEstablishment = { "Select court establishment", "District and Session Court , Nagpur",
				"Chief Judicial Magistrate , Nagpur", "Civil Court Senior Division , Nagpur", "Railway Court , Nagpur",
				"Small Causes Court, Nagpur", "Civil Court Junior Division , Umrer",
				"Civil Court Junior Division , Ramtek", "Motor Vechicle Court , Nagpur",
				"Civil Court Junior Division , Saoner", "Civil Court Junior Division , Kamptee",
				"Civil Judge Junior Division , Katol", "Civil Judge Junior Division , Kuhi",
				"Civil Judge Junior Division , Narkhed", "Civil Court Junior Division , Bhiwapur",
				"Civil Court Junior Division , Hingna", "Civil Court Junior Division , Mouda",
				"Civil Court Junior Division , Kalmeshwar", "Civil Court Junior Division , Parseoni" };
		c3 = new JComboBox(CourtEstablishment);
		c3.setBounds(180, 150, 350, 30);
		frame.getContentPane().add(c3);

		l3 = new JLabel("Case Type");
		l3.setBounds(20, 200, 100, 30);
		frame.getContentPane().add(l3);

		String[] CaseType = { "AC Cri.M.A.", "Arbitration Case", "Arbitration R.D", "Atro.Spl.Case", "B.G.P.E.Act Case",
				"C.Appln.", "Chapter Case", "Civil Appeal PPE", "Civil M.A.", "Civil Revn.", "Civil Suit",
				"Commercial Suit", "Contempt Proceeding", "Cri.Appeal", "Cri.Bail Appln.", "Cri.Case", "Cri.M.A.",
				"Cri.Municipal Appeal", "Cri.Rev.App.", "Darkhast", "Distress Warrant", "E.C.Act.Spl.Case",
				"Elec.Petn.", "Election Appeal", "E.S.I.Act Case", "Final Decree", "Guardian  Wards Case", "I.C.M.A.",
				"Insolvency", "Juvenile", "Juvenile Cri.MA", "L.A.R. - Land Ref.", "L.R.DKST.", "L.R.M.A.", "M.A.C.P.",
				"MACP C Appln.", "MACP. Dkst.", "MACP. M.A.", "MACP M.A.N.R.J.I.", "MACP Spl.", "MAHA P.I.D. 1999.",
				"M.A.N.R.J.I.", "Marriage Petn.", "M.C.A. - Misc.Civil Appeal", "MCOCO1999", "MCOCO.Revn.",
				"Mesne Profit", "M.J.Cases - M.J.C", "MOCCA M.A.", "MPID M.A.", "MPID M.A. Others", "MSEB MA",
				"Munci. Appeal", "NDPS Cri.Revn.", "NDPS M.A..", "NDPS. S. Case", "Other Misc.Appln.",
				"Other Misc.Cri.Appln", "Pauper Appln.", "P.C.M.Appln.", "Probate", "PWDVA Appeal", "PWDVA Appln.",
				"PWDVA Execution", "PWDVA Revi.", "R.C.A. - Civil Appeal", "R.C.C. - Reg.Cri.Case",
				"R.C.S. - Reg.Civil Suit", "Reg Dkst", "Reg.Sum.Suit", "Rent Appeal", "Rent Suit", "Review Appln.",
				"S.C.C. - Sum Case", "Sessions Case", "Small Cause Dkst", "Small Cause Suit", "Spl.Case",
				"Spl.Case ACB", "Spl.Case ATS", "Spl.Case Child Prot. - Spl.Case Child Prot. Act",
				"Spl. Case Drug Cosm. - Spl. Case Drug Cosmetic", "Spl Case MSEB", "Spl.Cri.M.A.",
				"Spl.C.S. - Spl.Civ.Suit", "Spl .Dkst", "Spl.M.A. Child Prot. - Spl.M.A. Child Prot. Act",
				"Spl. Marriage Petn.", "Spl.Sum.Suit", "Std. Rent Appln.", "Succession", "Suit Indian Divorce Act",
				"Suit Trade Mark Act", "Sum.Civ.Suit", "Sum. Darkhast", "TADA S. C.", "T.Cri.M.A.", "Trust Appeal",
				"Trust Suit", "W.C.F.A.Case", "W.C.N.F.A. Case" };
		c4 = new JComboBox(CaseType);
		c4.setBounds(180, 200, 350, 30);
		frame.getContentPane().add(c4);

		l6 = new JLabel("Case Number");
		l6.setBounds(20, 250, 100, 30);
		frame.getContentPane().add(l6);

		t3 = new JTextField();
		t3.setBounds(180, 250, 100, 30);
		frame.getContentPane().add(t3);

		l7 = new JLabel("Or");
		l7.setBounds(330, 250, 100, 30);
		frame.getContentPane().add(l7);

		b2 = new JButton("Upload");
		b2.setBounds(380, 250, 100, 30);
		frame.getContentPane().add(b2);
		b2.addActionListener(submit);

		l4 = new JLabel("Year");
		l4.setBounds(20, 300, 100, 30);
		frame.getContentPane().add(l4);

		t1 = new JTextField();
		t1.setBounds(180, 300, 100, 30);
		frame.getContentPane().add(t1);

//		l5 = new JLabel("No of Records to be extracted ");
//		l5.setBounds(20, 350, 100, 30);
//		frame.getContentPane().add(l5);
//
//		t2 = new JTextField();
//		t2.setBounds(180, 350, 100, 30);
//		frame.getContentPane().add(t2);

		b1 = new JButton("Submit");
		b1.setBounds(280, 400, 100, 40);
		b1.setForeground(Color.GREEN);
		frame.getContentPane().add(b1);
		b1.addActionListener(submit);

		frame.setVisible(true);
	}

	ActionListener submit = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			Object o = e.getSource();
			if (o == b1) {
				String data = "";
				String caseCode = c1.getSelectedItem().toString();
				String complex = c2.getSelectedItem().toString();
				String establisment = c3.getSelectedItem().toString();
				String casetype = c4.getSelectedItem().toString();
				String yr = t1.getText();
				String caseNumber = t3.getText();
				// int NoofRecord = Integer.parseInt(t2.getText());

				frame.dispose();

				if (complex.equalsIgnoreCase("Select court complex"))
					data = establisment;
				else if (establisment.equalsIgnoreCase("Select court establishment"))
					data = complex;

				FetchData Fd = new FetchData();
				try {
					Fd.testCaseNo(caseCode, data, casetype, yr, 1, caseNumber, Exceldata);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			} else if (o == b2) {
				JFileChooser jfs = new JFileChooser();
				jfs.setDialogTitle("Input Case No");
				jfs.showSaveDialog(null);
				File ifc = jfs.getSelectedFile();
				String path = ifc.getAbsolutePath();
				try {
					Exceldata = excelUtility.getDetailsFromExcel(path, "");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
	};

	public static void main(String[] args) throws Exception {
		new CaseNumber_Runner();
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
