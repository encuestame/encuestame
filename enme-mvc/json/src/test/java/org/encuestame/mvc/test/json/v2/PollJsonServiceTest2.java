package org.encuestame.mvc.test.json.v2;

import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import net.petrikainulainen.spring.testmvc.todo.dto.TodoDTO;





import org.encuestame.mvc.test.config.AbstractJsonMVCUnitBeans2;
import org.encuestame.mvc.test.config.TestUtil;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.web.CreatePollBean;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.web.servlet.ResultActions;

//@Category(DefaultTest.class)
public class PollJsonServiceTest2 extends AbstractJsonMVCUnitBeans2 {

	//@Test
    public void testSearchPollByType() throws Exception {
        
    	final String[] answer = {"a", "b"};
        final String[] hashtag = {"hastag1", "hastag2"};
    	//final CreatePollBean cb = createPollBean("ssssssssssss", answer, hashtag, "APPROVE", "ALL", true, null, null);
        final CreatePollBean createPollBean = new CreatePollBean();
		createPollBean.setQuestionName("ssss");
		createPollBean.setAnswers(answer);
		createPollBean.setHashtags(hashtag);
		createPollBean.setAllowAdd(Boolean.FALSE); //disable by default
		createPollBean.setLimitVote(3L);
		createPollBean.setMultiple(true);
		createPollBean.setResults("APROVE");
		createPollBean.setShowComments("ALL");
		createPollBean.setCloseDate(null);
		createPollBean.setFolder_name(null); //disable by default

        ResultActions a = mockMvc.perform(post("/api/survey/poll")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(createPollBean)));
                //.andExpect(status().is())
                //.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
//                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
//                .andExpect(jsonPath("$.fieldErrors[0].path", is("title")))
//                .andExpect(jsonPath("$.fieldErrors[0].message", is("The title cannot be empty.")));
        System.out.println("---->" + a.andReturn().getResponse().getContentAsString());
        //verifyZeroInteractions(getWebApplicationContext());
    }
}
