/*******************************************************************************
 * Copyright (c) 2008, 2011 VMware Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   VMware Inc. - initial contribution
 *******************************************************************************/
package org.eclipse.virgo.apps.admin.web.stubs.moo;

import junit.framework.Assert;

import sun.org.mozilla.javascript.internal.Context;
import sun.org.mozilla.javascript.internal.Function;
import sun.org.mozilla.javascript.internal.FunctionObject;
import sun.org.mozilla.javascript.internal.Scriptable;
import sun.org.mozilla.javascript.internal.ScriptableObject;

/**
 * 
 *
 */
public class Request extends ParentStub {

	private static final long serialVersionUID = 1L;
	
	private static Scriptable global_scope = null;
	
	public static Function onSuccess;

	public static String Url;
	
	/**
	 * Prototype constructor
	 */
	public Request() {
	}
	
	/**
	 * JavaScript Constructor
	 */
	public Request(ScriptableObject options) {
		Url = (String) ScriptableObject.getProperty(options, "url");
		onSuccess = (Function) ScriptableObject.getProperty(options, "onSuccess");
	}
	
	/**
	 * Need to store the global scope so we can create extensions of this class from it
	 * 
	 * @param scope
	 * @param constructor
	 * @param prototype
	 */
	public static void finishInit(Scriptable scope, FunctionObject constructor, Scriptable prototype){
		Request.global_scope = scope;
	}
	
	public void jsFunction_send(){
	}

	/**
	 * Just return a normal JS Request object, no need to differentiate  
	 * 
	 * @param options
	 * @return
	 */
	public static ScriptableObject jsStaticFunction_JSON(ScriptableObject options){
		Function fObj = (Function) Request.global_scope.get(Request.class.getSimpleName(), Request.global_scope);
		if (fObj instanceof Function) {
		    Function constructor = (Function)fObj;
		    return (ScriptableObject) constructor.construct(Context.getCurrentContext(), constructor.getParentScope(), new Object[]{options});
		} else {
			Assert.fail("Request constructor not found");
			return null;
		}	
	}
	
}