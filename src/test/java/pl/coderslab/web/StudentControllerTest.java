package pl.coderslab.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import pl.coderslab.exception.BaseEntityNotFoundException;
import pl.coderslab.model.Student;
import pl.coderslab.service.StudentService;



@RunWith(SpringRunner.class)
@WebMvcTest(controllers = StudentController.class)
public class StudentControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private final String STUDENT_LIST_ACTION_VIEW = "student/studentList";

@MockBean
private StudentService studentService;

@Before
public void setUp() {
    mockMvc = webAppContextSetup(webApplicationContext).build();
//         mockMvc =  standaloneSetup(new StudentController(studentService)).build();

    List<Student> students = Arrays.asList(new Student("Jan"), new Student("Janek"), new Student("Janusz"));
    when(this.studentService.listAllStudents()).thenReturn(students);
    Student kowalski = new Student("Jan"); kowalski.setLastName("Kowalski");
    when(this.studentService.getStudentById(1L)).thenReturn(kowalski);
    when(this.studentService.getStudentById(11111L)).thenThrow(new BaseEntityNotFoundException("not found"));
    
}

@Test
public void test_listAction_contains_model_list() throws Exception {
    assertThat(this.studentService).isNotNull();

    mockMvc.perform(get("/students/list"))
            .andExpect(model().attributeExists("list"))
            .andExpect(model().attribute("list", hasSize(3)))
            .andExpect(model().attribute("list", hasItem(anyOf(hasProperty("firstName"), is("Jan")))))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/html;charset=UTF-8"))
            .andExpect(content().string(containsString("Janek")))
            .andExpect(view().name(STUDENT_LIST_ACTION_VIEW))
           ;
}

@Test
public void test_show_student() throws Exception{
    
    mockMvc.perform(get("/students/show/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(view().name("student/addStudent"))
        .andExpect(model().attributeExists("student"))
        
        .andExpect(model().attribute("student", hasProperty("lastName", is("Kowalski"))))
        
        ;
}

@Test
public void when_student_not_found_should_advice_work() throws Exception {
    mockMvc.perform(get("/students/show/{id}", 11111L))
    .andExpect(status().isNotFound())
    .andExpect(view().name("exception-page"))

    ;
}

@Test
public void when_save_not_valid_data_then_show_form_again() throws Exception {
    mockMvc.perform(post("/students/add")
            .param("lastName", RandomStringUtils.randomAlphabetic(10))
            .param("otherName", RandomStringUtils.randomAlphabetic(10))
            )
    .andExpect(view().name("student/addStudent"))
    .andDo(print());
    ;
}

@Test
public void when_save_valid_data_then_redirect() throws Exception {
    mockMvc.perform(post("/students/add")
    .param("firstName", RandomStringUtils.randomAlphabetic(10))
    .param("lastName", RandomStringUtils.randomAlphabetic(8)))
    .andExpect(redirectedUrl("/students/list"))
    .andDo(print());
    ;
}


}
