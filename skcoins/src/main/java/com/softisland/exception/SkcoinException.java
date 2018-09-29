/**
 * 
 */
package com.softisland.exception;

import com.softisland.config.ErrorCodeEum;

/**
 * @author Administrator
 *
 */
public class SkcoinException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6595324332183553335L;

	public SkcoinException() {
		super();
	}
	
	public SkcoinException(String msg) {
		super(msg);
	}

	public SkcoinException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public SkcoinException(Throwable cause) {
		super(cause);
	}
	
	public SkcoinException(ErrorCodeEum e){
		super(e.getCode()+"_"+e.getMsg());
	}

}
