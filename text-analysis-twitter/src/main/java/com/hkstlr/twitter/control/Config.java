/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.hkstlr.twitter.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {

	private Properties props = new Properties();
	private Logger log = Logger.getLogger(this.getClass().getName());
	private String configFile;

	public Config() {
		checkSystemConfig();
		init();
	}

	public Config(Path filepath) {
		this.configFile = filepath.toString();
		init();
	}

	public Config(String pathstring) {
		this.configFile = pathstring;
		init();
	}

	public Config(Properties props) {
		this.props = props;
	}

	private void checkSystemConfig() {
		String systemPropsKey = this.getClass().getCanonicalName();
		Optional<String> systemProps = Optional.ofNullable(System.getProperty(systemPropsKey));
		configFile = systemProps.orElse(configFile);
	}

	final void init() {
		Optional<String> oConfigFile = Optional.ofNullable(configFile);
		File cf = null;
		if (oConfigFile.isPresent()) {
			cf = new File(configFile);
		}
		Optional<File> oFile = Optional.ofNullable(cf);
		if (oFile.isPresent() && oFile.get().exists()) {
			loadFromFileStream(cf);
		} else {
			loadFromResourceStream(configFile);
		}
		 
		if (props.isEmpty()) {
			loadFromResourceStream("app.properties");
		}

	}

	public void loadFromFileStream(File file) {

		try (InputStream is = new FileInputStream(file);) {
			
				props.load(is);
				
		} catch (Exception e) {
			log.log(Level.WARNING, null, e.getMessage());
		}

	}

	public void loadFromResourceStream(String resourceName) {

		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(resourceName);) {
			props.load(is);
		} catch (Exception e) {
			log.log(Level.WARNING, null, e.getMessage());
		}

	}

	public Properties getProps() {
		return props;
	}

}
