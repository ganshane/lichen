/*		
 * Copyright 2013-6-8 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,Main.java,fangj Exp$
 * created at:下午02:25:04
 */
package lichen.migration;

import lichen.migration.command.Command;
import lichen.migration.command.support.ClientApplet;
import lichen.migration.command.support.ClientCommand;

/**
 * 程序执行入口
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public class Main {
	
	public static void main(String[] args) {
		if("U".equals(args[0].toUpperCase())){
			Command command=new  ClientCommand();
			command.up();
		}else{
			new  ClientApplet();
		}
	}
}
