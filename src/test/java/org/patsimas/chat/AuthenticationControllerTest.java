package org.patsimas.chat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.patsimas.chat.dto.users.AuthenticationRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = {"spring.application.name=AuthenticationControllerTest",
        "spring.jmx.default-domain=AuthenticationControllerTest"})
public class AuthenticationControllerTest extends BasicWiremockTest {

    private static final String WRONG_PASSWORD = "sot";

    @Test
    public void authenticateSuccess() throws Exception {

        AuthenticationRequest authenticationRequest = AuthenticationRequest
                .builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        this.mockMvc.perform(
                post(CONTEXT_PATH + "/authenticate").contextPath(CONTEXT_PATH)
                        .content(asJsonString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void authenticateFailed() throws Exception {

        AuthenticationRequest authenticationRequest = AuthenticationRequest
                .builder()
                .email(EMAIL)
                .password(WRONG_PASSWORD)
                .build();

        this.mockMvc.perform(
                post(CONTEXT_PATH + "/authenticate").contextPath(CONTEXT_PATH)
                        .content(asJsonString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().is(401));
    }
}
