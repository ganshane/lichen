/*		
 * Copyright 2013-5-31 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,Up.java,fangj Exp$
 * created at:上午10:44:06
 */
package com.egf.db;

import com.egf.db.command.Command;
import com.egf.db.command.support.ClientCommand;

/**
 * 升级
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class Up {

	public static void main(String[] args) {
		Command command=new  ClientCommand();
		command.up();
	}
}
