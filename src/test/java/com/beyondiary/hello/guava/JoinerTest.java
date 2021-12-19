package com.beyondiary.hello.guava;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/**
 * @author muzhi
 * @version 1.0.0
 * @date 2021-12-19 11:39
 * reverse of splitter {@link SplitterTest}
 * @see Joiner
 */
public class JoinerTest {
	private static final List<String> list = Arrays.asList("A", "B", "C");
	private static final List<String> listWithNull = Arrays.asList("A", "B", "C", null);
	private static final Map<String, String> map = ImmutableMap.of("k1", "v1", "k2", "v2");

	@Test
	public void testJoin(){
		String join = Joiner.on(":").join(list);
		Assertions.assertEquals(join, "A:B:C");
	}

	@Test
	public void testJoin2(){
		Assertions.assertThrows(NullPointerException.class, new Executable() {
			public void execute() throws Throwable {
				String join = Joiner.on(":").join(listWithNull);
			}
		});
	}

	@Test
	public void testJoin3(){
		String join = Joiner.on(":").skipNulls().join(listWithNull);
		Assertions.assertEquals(join, "A:B:C");
	}

	@Test
	public void testJoin4(){
		String join = Joiner.on(":").useForNull("_").join(listWithNull);
		Assertions.assertEquals(join, "A:B:C:_");
	}

	@Test
	public void testJoin5(){
		StringBuilder builder = new StringBuilder("HEAD#");
		StringBuilder result = Joiner.on(":").useForNull("_").appendTo(builder, listWithNull);
		Assertions.assertEquals(result.toString(), "HEAD#A:B:C:_");
		Assertions.assertSame(result, builder);
	}

	@Test
	public void testJoin6(){
		String output = "src/test/resources/joiner/output.txt";
		try (FileWriter writer = new FileWriter(new File(output))){
			Joiner.on(":").useForNull("_").appendTo(writer, listWithNull);
			Assertions.assertTrue(Files.isFile().test(new File(output)));
		} catch (IOException e) {
			Assertions.fail();
		}
	}

	@Test
	public void testJoin7(){
		String collect = listWithNull.stream()
									 .filter(item -> item != null && !item.isEmpty())
									 .collect(Collectors.joining(":"));
		Assertions.assertEquals(collect, "A:B:C");
	}

	@Test
	public void testJoin8(){
		String collect = listWithNull.stream()
									 .map(this::getDefaultValue)
									 .collect(Collectors.joining(":"));
		Assertions.assertEquals(collect, "A:B:C:_");
	}

	private String getDefaultValue(final String item) {
		return item == null || item.isEmpty() ? "_" : item;
	}

	@Test
	public void testJoin9(){
		String join = Joiner.on(":").withKeyValueSeparator("=>").join(map);
		Assertions.assertEquals(join, "k1=>v1:k2=>v2");
	}
}
