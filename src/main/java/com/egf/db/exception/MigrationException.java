/*		
 * Copyright 2013-5-31 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,MigrationException.java,fangj Exp$
 * created at:下午04:35:45
 */
package com.egf.db.exception;

/**
 * 升级异常
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class MigrationException extends RuntimeException{
	
	private static final long serialVersionUID = 8369040032477637540L;
	
	private String code;
	
	private MigrationException(String errorCode){
		this.code = errorCode;
	}
	public String getCode(){
		return this.code;
	}
	
	public static MigrationException newFromErrorCode(String errorCode) {
		return new MigrationException(errorCode);
	}
	
	@Override
	public String toString() {
		return "数据库升级异常：" +this.getClass().getName().toString() + ",错误代码：" + this.getCode();
	}
}
