/*
 * BigBlueButton - http://www.bigbluebutton.org
 * 
 * Copyright (c) 2008-2009 by respective authors (see below). All rights reserved.
 * 
 * BigBlueButton is free software; you can redistribute it and/or modify it under the 
 * terms of the GNU Affero General Public License as published by the Free Software 
 * Foundation; either version 3 of the License, or (at your option) any later 
 * version. 
 * 
 * BigBlueButton is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along 
 * with BigBlueButton; if not, If not, see <http://www.gnu.org/licenses/>.
 *
 * Author: Richard Alam <ritzalam@gmail.com>
 *
 * $Id: $x
 */
package org.bigbluebutton.deskshare.server.session;

import org.bigbluebutton.deskshare.common.Dimension;

public class Session {
	private BlockManager blockManager;
	
	private final static int KEEP_ALIVE_TIME = 30000;
	
	private long lastUpdate = 0;
	
	public Session(String room, Dimension screen, Dimension block, FrameStreamer streamer) {
		blockManager = new BlockManager(room, screen, block, streamer);
	}
	
	public void initialize() {
		blockManager.initialize();
		synchronized (this) {
			lastUpdate = System.currentTimeMillis();
		}
		
	}
	
	public void updateBlock(int position, byte[] videoData, boolean keyFrame) {
		blockManager.updateBlock(position, videoData, keyFrame);
		synchronized(this) {
			lastUpdate = System.currentTimeMillis();
		}
		
	}
	
	public synchronized boolean keepAlive() {
		return (System.currentTimeMillis() - lastUpdate) < KEEP_ALIVE_TIME;
	}
}