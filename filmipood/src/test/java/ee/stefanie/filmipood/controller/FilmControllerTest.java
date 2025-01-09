package ee.stefanie.filmipood.controller;

import ee.stefanie.filmipood.entity.Film;
import ee.stefanie.filmipood.repository.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmControllerTest {

    private MockMvc mvc;

    @Mock
    FilmRepository filmRepository;

    @InjectMocks
    FilmController filmController;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(filmController).build();
    }

    @Test
    void addFilmBadRequest() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                .post("/films")).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void addFilm() throws Exception {
        String requestBody = "{}";
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                .post("/films")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void film404() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                .post("/film")).andReturn().getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void filmPutMethodNotAllowed() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                .put("/films")).andReturn().getResponse();
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), response.getStatus());
    }

    @Test
    void removeFilm() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                .delete("/films/99")).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void changeFilmTypeError() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                .patch("/films?filmId=1&filmType=BLABLA")).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void changeFilmType() throws Exception {
        when(filmRepository.findById(50L)).thenReturn(Optional.of(new Film()));
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                .patch("/films?filmId=50&filmType=REGULAR")).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getFilms() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                .get("/films")).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getAvailableFilms() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                .get("/available-films")).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}