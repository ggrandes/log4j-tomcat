package org.javastack.log4j;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.catalina.loader.WebappClassLoaderBase;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Layout;

public class WebAppDailyRollingFileAppender extends DailyRollingFileAppender {
	public WebAppDailyRollingFileAppender() {
	}

	public WebAppDailyRollingFileAppender(final Layout layout, final String filename, final String datePattern)
			throws IOException {
		super(layout, mapFileName(filename), datePattern);
	}

	private static String mapFileName(final String filename) {
		final String REGEX = "\\{context.name(|:(?<default>[^}]+))\\}";
		final String NO_CTX = "unknown";
		final Pattern p = Pattern.compile(REGEX);
		final Matcher m = p.matcher(filename);
		final String ctxName = getContextName();
		final StringBuilder sb = new StringBuilder(filename.length());
		int lastOff = 0;
		while (m.find()) {
			final String ctxDefName = m.group("default");
			sb.append(filename.subSequence(lastOff, m.start()));
			if (ctxName != null) {
				sb.append(ctxName);
			} else if (ctxDefName != null) {
				sb.append(ctxDefName);
			} else {
				sb.append(NO_CTX);
			}
			lastOff = m.end();
		}
		sb.append(filename.subSequence(lastOff, filename.length()));
		return sb.toString();
	}

	private final static String getContextName() {
		final ClassLoader cl = WebAppDailyRollingFileAppender.class.getClassLoader();
		if (cl instanceof WebappClassLoaderBase) {
			final WebappClassLoaderBase wcl = ((WebappClassLoaderBase) cl);
			String cn = wcl.getContextName();
			if (cn.isEmpty()) {
				return "ROOT";
			} else {
				if (cn.charAt(0) == '/') {
					cn = cn.substring(1);
				}
				cn = cn.replace('/', '#');
				return cn;
			}
		}
		return null;
	}

	@Override
	public void setFile(final String file) {
		super.setFile(mapFileName(file));
	}

	@Override
	public void setFile(final String fileName, final boolean append, final boolean bufferedIO,
			final int bufferSize) throws IOException {
		super.setFile(mapFileName(fileName), append, bufferedIO, bufferSize);
	}
}
