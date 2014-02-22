package org.encuestame.mvc.test.json.v2;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.encuestame.utils.categories.test.DefaultTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(DefaultTest.class)
public class ScheduldedJSONTest extends TestCase {
	
	@Test
	public void voidMethod(){
		Assert.assertTrue(true);
	}
	
	//@Test
	public void testRoot() throws Exception {
//	    mockMvc.perform(get("/").accept(MediaType.TEXT_PLAIN))
//	            // print the request/response in the console
//	            .andExpect(status().isOk()).andExpect(content().contentType(MediaType.TEXT_PLAIN))
//	            .andExpect(content().string("Hello World!"));
	}

	 //@Test
	    public void testScheduled() throws Exception {
	        
//		 	Todo deleted = new TodoBuilder()
//	                .id(1L)
//	                .description("Lorem ipsum")
//	                .title("Foo")
//	                .build();

	       // when(todoServiceMock.deleteById(1L)).thenReturn(deleted);

//	        mockMvc.perform(post("/api/survey/{type}/schedule/{id}", 1L))
//	                .andExpect(status().isOk())
//	                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
//	                .andExpect(jsonPath("$.id", is(1)))
//	                .andExpect(jsonPath("$.description", is("Lorem ipsum")))
//	                .andExpect(jsonPath("$.title", is("Foo")));

	        //verify(todoServiceMock, times(1)).deleteById(1L);
	        //verifyNoMoreInteractions(todoServiceMock);
	    }
}
