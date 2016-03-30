package cz.cuni.mff.ConfigMapper;

import cz.cuni.mff.ConfigMapper.Nodes.ConfigNode;

/**
 * Maps {@link ConfigNode} structures to objects
 */
public class ConfigMapper<MappedObject> {

    /**
     * Class of mapped objects
     */
    private Class<MappedObject> cls;

    /**
     * @param cls Class the mapper should map config objects to
     */
    public ConfigMapper(Class<MappedObject> cls) {
        this.cls = cls;
    }

    /**
     * Map config to a newly created instance of MappedObject
     * @param config The configuration tree to be mapped
     * @param mode The mapping mode
     * @return A new instance of MappedObject with options from config
     */
    public<MappedObject> MappedObject load(ConfigNode config, LoadingMode mode) {
        try {
            return (MappedObject) cls.newInstance();
        } catch (java.lang.InstantiationException e) {

        } catch (IllegalAccessException e) {

        }

        return null;
    }

    /**
     * Store mapped options from a MappedObject instance to an existing config structure.
     * Any config nodes not affected by the values in the mapped object remain the same.
     * @param object The source instance
     * @param config The config structure into which the mapped options are stored
     */
    public void save(MappedObject object, ConfigNode config) {

    }
}
