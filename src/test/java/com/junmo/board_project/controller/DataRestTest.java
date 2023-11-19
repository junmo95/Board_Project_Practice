package com.junmo.board_project.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("Spring Data Rest 통합테스트는 불필요하므로 제외시킴")
@DisplayName("Data Rest Test")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {

    private final MockMvc mvc;

    @Autowired
    public DataRestTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName(" [api] 게시글 리스트 조회")
    @Test
    void givenNothing_whenRequestArticles_thenReturnArticles() throws Exception {
        // given

        // when
        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andDo(print());

        // then

    }

    @DisplayName(" [api] 게시글 단건 조회")
    @Test
    void givenNothing_whenRequestArticle_thenReturnArticle() throws Exception {
        // given

        // when
        mvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andDo(print());

        // then

    }

//    @DisplayName(" [api] 게시글 댓글 조회")
//    @Test
//    void givenNothing_whenRequestArticleCommentsFromArticle_thenReturnArticleCommentsJsonResponse() throws Exception {
//        // given
//
//        // when
//        mvc.perform(get("/api/articles/1/articleComments"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
//                .andDo(print());
//
//        // then
//
//    }

    @DisplayName(" [api] 댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestArticleComments_thenReturnArticleCommentsJsonResponse() throws Exception {
        // given

        // when
        mvc.perform(get("/api/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andDo(print());

        // then

    }
//
//    @DisplayName(" [api] 댓글 단건 조회")
//    @Test
//    void givenNothing_whenRequestArticleComment_thenReturnArticleCommentsJsonResponse() throws Exception {
//        // given
//
//        // when
//        mvc.perform(get("/api/articleComments/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
//                .andDo(print());
//
//        // then
//
//    }

}
