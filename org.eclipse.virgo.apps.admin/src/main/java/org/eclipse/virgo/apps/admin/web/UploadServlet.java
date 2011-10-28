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
package org.eclipse.virgo.apps.admin.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 */
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String PICKUP_DIR = "/pickup";
	
	private static final Logger log = LoggerFactory.getLogger(UploadServlet.class);

	private final String serverHome;

	public UploadServlet(String serverHome) {
		this.serverHome = serverHome;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			@SuppressWarnings("unchecked")
			List<FileItem> items = (List<FileItem>) upload.parseRequest(request);
			for (FileItem fileItem : items) {
				if (!fileItem.isFormField()) {
					String name = fileItem.getName();
					if(name != null && name.length() > 0){
						File uploadedFile = new File(String.format("%s%s/%s", this.serverHome, PICKUP_DIR, name));
						fileItem.write(uploadedFile);
						log.warn(String.format("Uploaded artifact of size (%db) to %s", fileItem.getSize(), uploadedFile.getPath()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			response.setContentType("text/plain");
			PrintWriter writer = response.getWriter();
			writer.append("Complete");
			writer.close();
		}
	}

}