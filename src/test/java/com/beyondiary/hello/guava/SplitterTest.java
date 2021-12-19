package com.beyondiary.hello.guava;

import com.google.common.base.Splitter;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author muzhi
 * @version 1.0.0
 * @date 2021-12-19 12:13
 * reverse of splitter {@link JoinerTest}
 * @see Splitter
 */
public class SplitterTest {
	@Test
	public void test1(){
		List<String> list = Splitter.on("|").splitToList("hello|world");
		Assertions.assertNotNull(list);
		Assertions.assertEquals(list.size(), 2);
		Assertions.assertEquals(list.get(0), "hello");
		Assertions.assertEquals(list.get(1), "world");
	}

	@Test
	public void test2(){
		List<String> list = Splitter.on("|").splitToList("hello|world|||");
		Assertions.assertNotNull(list);
		Assertions.assertEquals(list.size(), 5);

		List<String> list1 = Splitter.on("|").omitEmptyStrings().splitToList("hello|world|||");
		Assertions.assertEquals(list1.size(), 2);

		List<String> list2 = Splitter.on("|").trimResults().omitEmptyStrings().splitToList(" hello | world |||");
		Assertions.assertEquals(list2.size(), 2);
		Assertions.assertEquals(list2.get(0), "hello");
	}

	@Test
	public void test3(){
		List<String> list2 = Splitter.fixedLength(5).splitToList("helloWorldLimit");
		Assertions.assertEquals(list2.size(), 3);
		Assertions.assertEquals(list2.get(0), "hello");
	}

	@Test
	public void test4(){
		List<String> list2 = Splitter.on("").splitToList("helloWorldLimit");
		Assertions.assertEquals(list2.size(), 3);
		Assertions.assertEquals(list2.get(0), "hello");
	}

	@Test
	public void test5(){
		List<String> list2 = Splitter.on("_").limit(2).splitToList("hello_World_Limit_OKay");
		Assertions.assertEquals(list2.size(), 2);
		Assertions.assertEquals(list2.get(1), "World_Limit_OKay");
	}

	@Test
	public void test6(){
		List<String> list2 = Splitter.onPattern("_").limit(2).splitToList("hello_World_Limit_OKay");
		Assertions.assertEquals(list2.size(), 2);
		Assertions.assertEquals(list2.get(1), "World_Limit_OKay");
	}

	@Test
	public void test7(){
		List<String> list2 = Splitter.on(Pattern.compile("_")).limit(2).splitToList("hello_World_Limit_OKay");
		Assertions.assertEquals(list2.size(), 2);
		Assertions.assertEquals(list2.get(1), "World_Limit_OKay");
	}

	@Test
	public void test8(){
		Map<String, String> split = Splitter.on(Pattern.compile(","))
											.withKeyValueSeparator(":")
											.split("hello:World,Limit:OKay");
		Assertions.assertEquals(split.size(), 2);
		Assertions.assertEquals(split.get("hello"), "World");
	}
}
