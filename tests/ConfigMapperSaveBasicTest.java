import cz.cuni.mff.ConfigMapper.Annotations.ConfigOption;
import cz.cuni.mff.ConfigMapper.Annotations.ConfigSection;
import cz.cuni.mff.ConfigMapper.ConfigMapper;
import cz.cuni.mff.ConfigMapper.MappingException;
import cz.cuni.mff.ConfigMapper.Nodes.Root;
import cz.cuni.mff.ConfigMapper.Nodes.ScalarOption;
import cz.cuni.mff.ConfigMapper.Nodes.Section;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by teyras on 9.5.16.
 */
public class ConfigMapperSaveBasicTest {
	static class BasicMappedClass {
		@ConfigOption(section = "section1")
		String optionString;

		@ConfigOption(section = "section1")
		int optionInt;
	}

	@Test
	public void saveBasic() throws Exception {
		BasicMappedClass object = new BasicMappedClass();
		object.optionString = "foo";
		object.optionInt = 10;

		ConfigMapper mapper = new ConfigMapper();
		Root config = mapper.save(object);

		Root expected = new Root("", Arrays.asList(
			new Section("section1", Arrays.asList(
				new ScalarOption("optionString", "foo"),
				new ScalarOption("optionInt", "10")
			))
		));

		assertEquals(expected, config);
	}

	@Test(expected = MappingException.class)
	public void missingOptionThrows() throws Exception {
		BasicMappedClass object = new BasicMappedClass();
		object.optionString = null;
		object.optionInt = 10;

		ConfigMapper mapper = new ConfigMapper();
		mapper.save(object);
	}

	static class StructuredMappedClass {
		static class SectionClass {
			@ConfigOption(name = "optionStringFoo")
			String optionString;

			@ConfigOption
			int optionInt;
		}

		@ConfigSection
		SectionClass section1;
	}

	@Test
	public void saveStructured() throws Exception {
		StructuredMappedClass object = new StructuredMappedClass();
		object.section1 = new StructuredMappedClass.SectionClass();
		object.section1.optionString = "foo";
		object.section1.optionInt = 10;

		ConfigMapper mapper = new ConfigMapper();
		Root config = mapper.save(object);

		Root expected = new Root("", Arrays.asList(
			new Section("section1", Arrays.asList(
				new ScalarOption("optionStringFoo", "foo"),
				new ScalarOption("optionInt", "10")
			))
		));

		assertEquals(expected, config);
	}

	static class OptionalOptionMappedClass {
		@ConfigOption(section = "section", optional = true)
		Integer option;
	}

	@Test
	public void saveOptionalOptionNotPresent() throws Exception {
		OptionalOptionMappedClass object = new OptionalOptionMappedClass();
		object.option = null;

		ConfigMapper mapper = new ConfigMapper();
		Root config = mapper.save(object);

		Root expected = new Root("", Collections.emptyList());

		assertEquals(expected, config);
	}

	static class OptionalSectionMappedClass {
		static class SectionClass {
			@ConfigOption
			int option;
		}

		@ConfigSection(optional = true)
		SectionClass section;
	}

	@Test
	public void saveOptionalSectionNotPresent() throws Exception {
		OptionalSectionMappedClass object = new OptionalSectionMappedClass();
		object.section = null;

		ConfigMapper mapper = new ConfigMapper();
		Root config = mapper.save(object);

		Root expected = new Root("", Collections.emptyList());

		assertEquals(expected, config);
	}
}
