package com.camal.businessmanagement.crudbottests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Field;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.camal.businessmanagement.entity.Allop;
import com.camal.businessmanagement.repository.AllopRepository;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class AllopCrudIT {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper om;
    @Autowired private AllopRepository repository;


    @Test
    void fullCrudFlow() throws Exception {
        repository.deleteAll();
        repository.flush();


        String createJson = """
{
            "url": "test",
            "productName": "test",
            "boughtPrice": 1.0,
            "sellPrice": 1.0,
            "description": "test"
        }
        """;

        MvcResult createdRes = mvc.perform(post("/api/v1/allops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode created = om.readTree(createdRes.getResponse().getContentAsString());
        assertThat(created).isNotNull();

        String idStr = created.get("id").asText();

        mvc.perform(get("/api/v1/allops/" + idStr))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idStr));

        mvc.perform(get("/api/v1/allops"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        mvc.perform(put("/api/v1/allops/" + idStr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/v1/allops/" + idStr))
                .andExpect(status().isNoContent());

        mvc.perform(get("/api/v1/allops"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    static class ReflectionUtil {
        static void fillDefaults(Object entity) {
            for (Field f : entity.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                String n = f.getName();
                if (n.equals("id") || n.equals("version") || n.equals("createdAt") || n.equals("updatedAt") ||
                    n.equals("createdBy") || n.equals("updatedBy") || n.equals("deleted")) {
                    continue;
                }
                Class<?> t = f.getType();
                try {
                    Object cur = f.get(entity);
                    if (cur != null) continue;

                    if (t.equals(String.class)) f.set(entity, "test");
                    else if (t.equals(Integer.class) || t.equals(int.class)) f.set(entity, 1);
                    else if (t.equals(Long.class) || t.equals(long.class)) f.set(entity, 1L);
                    else if (t.equals(Double.class) || t.equals(double.class)) f.set(entity, 1.0d);
                    else if (t.equals(Float.class) || t.equals(float.class)) f.set(entity, 1.0f);
                    else if (t.equals(Boolean.class) || t.equals(boolean.class)) f.set(entity, false);
                    else if (t.getName().equals("java.time.Instant")) f.set(entity, Instant.now());
                    else if (t.getName().equals("java.time.LocalDate")) f.set(entity, java.time.LocalDate.parse("2025-01-01"));
                    else if (t.getName().equals("java.time.LocalDateTime")) f.set(entity, java.time.LocalDateTime.parse("2025-01-01T10:00:00"));
                } catch (Exception ignored) {}
            }
        }

        static Object getField(Object entity, String fieldName) {
            try {
                Field f = entity.getClass().getDeclaredField(fieldName);
                f.setAccessible(true);
                return f.get(entity);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
