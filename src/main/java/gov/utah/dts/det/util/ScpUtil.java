package gov.utah.dts.det.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class ScpUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ScpUtil.class);
	
	private static final int DEFAULT_SSH_PORT = 22;
	
	private ScpUtil() {
		
	}
	
	private static int checkAck(InputStream in) throws IOException {
		int b = in.read();
		// b may be 0 for success,
		//          1 for error,
		//          2 for fatal error,
		//          -1
		if (b == 0) {
			return b;
		}
		if (b == -1) {
			return b;
		}
		
		if (b == 1 || b == 2) {
			StringBuffer sb = new StringBuffer();
			int c;
			do {
				c = in.read();
				sb.append((char) c);
			} while (c != '\n');
			if (b == 1) { // error
				System.out.print(sb.toString());
			}
			if (b == 2) { // fatal error
				System.out.print(sb.toString());
			}
		}
		return b;
	}
	
	public static void scpTo(String host, int port, String username, String password, File file, String remotePath) throws Exception {
		if (StringUtils.isBlank(host)) {
			throw new IllegalArgumentException("Host must not be blank.");
		}
		if (StringUtils.isBlank(username)) {
			throw new IllegalArgumentException("Username must not be blank.");
		}
		if (StringUtils.isBlank(password)) {
			throw new IllegalArgumentException("Password must not be blank.");
		}
		if (StringUtils.isBlank(remotePath)) {
			throw new IllegalArgumentException("Remote path must not be blank.");
		}
		if (file == null) {
			throw new IllegalArgumentException("File must not be null.");
		}
		
		Session session = null;
		Channel channel = null;
		try {
			int _port = DEFAULT_SSH_PORT;
			if (port > 0 && port < 65535) {
				_port = port;
			}
			
			SshUserInfo userInfo = new SshUserInfo(username, password);
			
			JSch jsch = new JSch();
			session = jsch.getSession(username, host, _port);
			session.setUserInfo(userInfo);
			logger.debug("Connecting to " + host + ":" + _port);
			session.connect();
		
			// exec 'scp -t rfile' remotely
			String command = "scp -t " + remotePath;
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			
			// get I/O streams for remote scp
			OutputStream out = null;
			InputStream in = null;
			try {
				out = channel.getOutputStream();
				in = channel.getInputStream();
				
				channel.connect();
				
				if (checkAck(in) != 0) {
					throw new Exception("Unable to send file!");
				}

				logger.debug("Sending file " + file.getName() + " to " + host + ":" + _port);
				// send "C0644 filesize filename", where filename should not include '/'
				long filesize = file.length();
				command = "C0644 " + filesize + " " + file.getName() + "\n";
				out.write(command.getBytes());
				out.flush();
				
				if (checkAck(in) != 0) {
					throw new Exception("Unable to send file!");
				}
				
				// send a content of lfile
				FileInputStream fis = null;
				byte[] buf = new byte[1024];
				try {
					fis = new FileInputStream(file);
					while (true) {
						int len = fis.read(buf, 0, buf.length);
						if(len <= 0) {
							break;
						}
						out.write(buf, 0, len); //out.flush();
					}
				} finally {
					if (fis != null) {
						fis.close();
					}
				}
				// send '\0'
				buf[0] = 0;
				out.write(buf, 0, 1);
				out.flush();
				
				if (checkAck(in) != 0) {
					throw new Exception("Unable to send file!");
				}
			} finally {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			}
		} finally {
			if (channel != null) {
				channel.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}
		logger.debug("File successfully sent");
	}
	
	public static class SshUserInfo implements UserInfo, UIKeyboardInteractive {
		private String username;
		private String password;
	
		public SshUserInfo(String username, String password) {
			if (StringUtils.isBlank(username)) {
				throw new IllegalArgumentException("Username must not be blank.");
			}
			if (StringUtils.isBlank(password)) {
				throw new IllegalArgumentException("Password must not be blank.");
			}
			
			this.username = username;
			this.password = password;
		}
	
		@Override
		public String getPassphrase() {
			return null;
		}
	
		@Override
		public String getPassword() {
			return password;
		}
	
		@Override
		public boolean promptPassphrase(String message) {
			return true;
		}
	
		@Override
		public boolean promptPassword(String message) {
			return true;
		}
	
		@Override
		public boolean promptYesNo(String message) {
			return true;
		}
	
		@Override
		public void showMessage(String message) {
			
		}
		
		public String getUsername() {
			return username;
		}
		
		@Override
		public String[] promptKeyboardInteractive(String destination, String name, String instruction, String[] prompt, boolean[] echo) {
			if (prompt.length != 1 || echo[0] || this.password == null) {
				return null;
			}
			String[] response = new String[1];
			response[0] = this.password;
			return response;
		}
	}
}