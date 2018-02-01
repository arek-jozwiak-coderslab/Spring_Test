package pl.coderslab;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.coderslab.web.HomeController;
import pl.coderslab.web.PageController;

@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = PageController.class)
public class PageControllerMockTestStandalone {

    private static PageController pageController;
    private static String PAGE_VIEW_NAME_PAGE = "page/index";
    private static String PAGE_VIEW_NAME_HOME = "page/home";

    @BeforeClass
    public static void setUp() {
        pageController = new PageController();
    }

    @Test
    public void test_home_action_return_index() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(pageController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/page"))
                .andExpect(MockMvcResultMatchers.view().name(PAGE_VIEW_NAME_PAGE));
    }

@Test
public void test_home_action_return_index_static_import() throws Exception {
    MockMvc mockMvc = standaloneSetup(pageController).build();
    mockMvc.perform(get("/"))
            .andExpect(view().name(PAGE_VIEW_NAME_HOME))
            .andExpect(status().isOk())
            .andDo(print());
}
    
}
