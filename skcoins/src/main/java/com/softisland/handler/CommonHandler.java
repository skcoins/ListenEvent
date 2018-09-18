/**
 * 
 */
package com.softisland.handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 *
 */
public class CommonHandler {

	public static ExecutorService commonPool = Executors.newFixedThreadPool(50);  
}
