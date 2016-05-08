package cz.cuni.mff.ConfigMapper;

import cz.cuni.mff.ConfigMapper.Adapters.ConfigAdapter;

import java.io.*;

/**
 * Provides a simple API for the configuration mapping functionality
 */
public class ConfigFacade<MappedObject> {
	/**
	 * The configuration adapter used to work with configuration files
	 */
	private final ConfigAdapter adapter;

	/**
	 * The configuration mapper
	 */
	private final ConfigMapper mapper;

	/**
	 * The class on which the configuration is mapped
	 */
	private final Class<MappedObject> cls;

	/**
	 * @param cls The class on which configuration files should be mapped
	 * @param adapter The adapter used to read and write configuration files
	 */
	public ConfigFacade(Class<MappedObject> cls, ConfigAdapter adapter) {
		this.adapter = adapter;
		this.cls = cls;
		this.mapper = new ConfigMapper();
	}

	/**
	 * Load an object from an InputStream
	 * @param input The input stream
	 * @param mode The loading mode
	 * @return A new instance of the mapped class
	 * @throws MappingException when the configuration file cannot be mapped onto this mappers class
	 * @throws ConfigurationException when the configuration file is malformed
	 */
	public MappedObject load(InputStream input, LoadingMode mode) throws MappingException, ConfigurationException {
		return mapper.load(adapter.read(input), cls, mode);
	}

	/**
	 * Load an object from an InputStream, using the strict mode
	 * @param input The input stream
	 * @return A new instance of the mapped class
	 * @throws MappingException when the configuration file cannot be mapped onto this mappers class
	 * @throws ConfigurationException when the configuration file is malformed
	 */
	public MappedObject load(InputStream input) throws MappingException, ConfigurationException {
		return load(input, LoadingMode.STRICT);
	}

	/**
	 * Load an object from a file
	 * @param file The input file
	 * @param mode The loading mode
	 * @return A new instance of the mapped class
	 * @throws FileNotFoundException when the input file cannot be found
	 * @throws MappingException when the configuration file cannot be mapped onto this mappers class
	 * @throws ConfigurationException when the configuration file is malformed
	 */
	public MappedObject load(File file, LoadingMode mode) throws FileNotFoundException, MappingException, ConfigurationException {
		return load(new FileInputStream(file), mode);
	}

	/**
	 * Load an object from a file, using the strict mode
	 * @param file The input file
	 * @return A new instance of the mapped class
	 * @throws FileNotFoundException when the input file cannot be found
	 * @throws MappingException when the configuration file cannot be mapped onto this mappers class
	 * @throws ConfigurationException when the configuration file is malformed
	 */
	public MappedObject load(File file) throws FileNotFoundException, MappingException, ConfigurationException {
		return load(new FileInputStream(file), LoadingMode.STRICT);
	}

	/**
	 * Save an object into an OutputStream
	 * @param object The mapped object
	 * @param output The output stream
	 * @throws ConfigurationException When the file cannot be saved in the format supported by the adapter
	 */
	public void save(MappedObject object, OutputStream output) throws ConfigurationException, IOException {
		adapter.write(mapper.save(object), output);
	}

	/**
	 * Save an object into a file
	 * @param object The mapped object
	 * @param file The output file
	 * @throws IOException when there is a problem with the output file
	 * @throws ConfigurationException When the file cannot be saved in the format supported by the adapter
	 */
	public void save(MappedObject object, File file) throws IOException, ConfigurationException {
		save(object, new FileOutputStream(file));
	}

	/**
	 * Save the default values for the mapped class into an OutputStream
	 * @param output The output stream
	 * @throws IOException when there is a problem with the output file
	 * @throws ConfigurationException when the default values cannot be saved in the format supported by the adapter
	 */
	public void saveDefaults(OutputStream output) throws IOException, ConfigurationException {

	}

	/**
	 * Save the default values for the mapped class into a file
	 * @param file The output file
	 * @throws IOException when there is a problem with the output file
	 * @throws ConfigurationException when the default values cannot be saved in the format supported by the adapter
	 */
	public void saveDefaults(File file) throws IOException, ConfigurationException {
		saveDefaults(new FileOutputStream(file));
	}
}
