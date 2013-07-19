/*		
 * Copyright 2013-5-31 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,Down.java,fangj Exp$
 * created at:上午10:44:13
 */
package com.egf.db.command.support;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.egf.db.command.Command;
import com.egf.db.utils.StringUtils;

public class ClientApplet extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	File selectedFile =null;
	
	Container c = this.getContentPane();
	/*JLabel控件*/
	JLabel urlJl = new JLabel();
	
	/*JTextField控件*/
	JTextField versionTf=new JTextField(10);
	
	/*JButton控件*/
	JButton commitJb = new JButton("确定");
	JButton	cancelJb = new JButton("退出");
	
	JPanel p1 = new JPanel(new BorderLayout());
	JPanel p2 = new JPanel();

	public ClientApplet() {
		super("数据库版本控制");
		p1.setBorder(BorderFactory.createEmptyBorder(20, 20,20, 20));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置窗体可以关闭
		/* 设置布局 */
		GridLayout cgl = new GridLayout(2, 1);
		cgl.setHgap(10);
		cgl.setVgap(10);
		c.setLayout(cgl);
		GridLayout p1gl = new GridLayout(1, 2);
		p1.setLayout(p1gl);

		/* 给按钮增加事件监听 */
		commitJb.addActionListener(this);// 增加监听很重要
		cancelJb.addActionListener(this);
		p1.add(new JLabel("<html>请输入需要回滚的版本号：<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(默认为上一个版本)</html>"),BorderLayout.WEST);
		p1.add( versionTf,BorderLayout.CENTER);

		p2.add(commitJb);
		p2.add(cancelJb);

		c.add(p1);
		c.add(p2);

		this.setSize(400, 180);

		/* 设置窗口居中 */
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width / 2; // 获取屏幕的宽
		int screenHeight = screenSize.height / 2; // 获取屏幕的高
		int height = this.getHeight();
		int width = this.getWidth();

		setLocation(screenWidth - width / 2, screenHeight - height / 2);

		this.setVisible(true);
		this.setResizable(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == commitJb) {
				Command command=new ClientCommand();
				String version=versionTf.getText();
				String message=null;
				if(StringUtils.isBlank(version)){
					message=command.down();
					JOptionPane.showMessageDialog(null, message);
				}else{
					  Pattern pattern = Pattern.compile("[0-9]+");
					  if(!pattern.matcher(version).matches()){
						  JOptionPane.showMessageDialog(null, "版本号输入错误!");
					  }else{
						  message=command.down(version);
						  JOptionPane.showMessageDialog(null, message);
					  }
				}
		}
		if (e.getSource() == cancelJb) {
			int exit = JOptionPane.showConfirmDialog(this, "确定要退出程序吗?");
			if (exit == JOptionPane.YES_NO_OPTION) {
				System.exit(0);
			}
		}

	}
	
}
