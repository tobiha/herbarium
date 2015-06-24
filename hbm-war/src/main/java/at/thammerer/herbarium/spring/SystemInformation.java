package at.thammerer.herbarium.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

final class SystemInformation {
	private static final Logger log = LoggerFactory.getLogger(SystemInformation.class);
	private static final Charset UTF_8 = Charset.forName("UTF-8");

	private final String hostname;
	private final String deploymentDateAsString;

	public SystemInformation() {
		hostname = loadHostname();

		String ds;
		Date d;
		try {
			d = loadDeploymentDate();
			ds = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ssZ").format(d);
		} catch (Exception e) {
			ds = null;
		}

		this.deploymentDateAsString = ds;
	}

	public String getHostname() {
		return hostname;
	}

	public String getDeploymentDateAsString() {
		return deploymentDateAsString;
	}

	protected String loadHostname() {
		try {
			Process process = Runtime.getRuntime().exec("hostname");
			try (InputStream stdout = process.getInputStream()) {
				String hostname = readFully(stdout, UTF_8)
					.replace("\r","").replace("\n","");
				log.info("Running on host '{}'", hostname);
				return hostname;
			} finally {
				process.destroy();
			}
		} catch (IOException e) {
			log.warn("Using hostname 'localhost'");
			return "localhost";
		}
	}

	protected Date loadDeploymentDate() throws IOException, URISyntaxException {
		// we get a path like: "file:/home/joe/app.war!/WEB-INF/classes!/" or "file:/home/joe/app/WEB-INF/classes/"
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();

		// strip classes
		if (path.contains("classes!/")) {
			path = path.substring(0, path.lastIndexOf("classes!/"));
		}

		// strip the rest
		while (path.contains("!/")) {
			path = path.substring(0, path.lastIndexOf("!/"));
		}

		// if the .war file was exploded, get the timestamp from the WEB-INF directory
		if (path.contains("/WEB-INF/")) {
			path = path.substring(0, path.lastIndexOf("/WEB-INF/") + "/WEB-INF/".length());
		}

		// strip "file:/" on Windows
		boolean isWindows = System.getProperty("os.name", "").startsWith("Windows");
		if (isWindows && path.startsWith("file:/")) {
			path = path.substring("file:/".length());
		}

		File file = new File(path);
		Date deploymentDate = new Date(file.lastModified());
		log.info("Timestamp on {} is {}", file.getPath(), deploymentDate);

		return deploymentDate;
	}

	private String readFully(InputStream inputStream, Charset encoding)
		throws IOException {
		return new String(readFully(inputStream), encoding);
	}

	private byte[] readFully(InputStream inputStream)
		throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[128];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			baos.write(buffer, 0, length);
		}
		return baos.toByteArray();
	}
}
