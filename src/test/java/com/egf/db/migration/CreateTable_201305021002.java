/*		
 * Copyright 2013-5-2 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,CreateTable_201305021002.java,fangj Exp$
 * created at:上午10:02:30
 */
package com.egf.db.migration;

import org.junit.Test;

import com.egf.db.services.impl.AbstractMigration;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class CreateTable_201305021002 extends AbstractMigration {

	@Test
	public void up() {
		//增加列	
		addColumn(Table("table"), Column("c"),Varchar2(2),Comment("test"));
	}

	public void down() {
		
	}

	
	
}
