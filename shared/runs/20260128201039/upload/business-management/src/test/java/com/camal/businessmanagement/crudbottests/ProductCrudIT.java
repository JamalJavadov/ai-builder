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
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.camal.businessmanagement.entity.Product;
import com.camal.businessmanagement.repository.ProductRepository;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ProductCrudIT {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper om;
    @Autowired private ProductRepository repository;


    @Test
    void fullCrudFlow() throws Exception {
        // Arrange: ensure clean slate for this entity
        repository.deleteAll();
        repository.flush();


        String createJson = """
                {
                    "url": "https://example.com/product",
                    "productName": "Test Product",
                    "boughtPrice": 10.50,
                    "sellPrice": 15.99,
                    "description": "A test product description"
                }
                """;

        // 1) Create
        MvcResult createdRes = mvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.success").value(true))
                .andReturn();

        JsonNode created = om.readTree(createdRes.getResponse().getContentAsString()).get("data");
        assertThat(created).isNotNull();

        String idStr = created.get("id").asText();
        long version = created.get("version").asLong();

        // 2) Get
        mvc.perform(get("/api/v1/products/" + idStr))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(idStr));

        // 3) List
        mvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalElements").value(1));

        // 4) Patch (valid version)
        String patchJson = """
                {
                    "version": %d,
                    "url": "test2"
                }
                """.formatted(version);
        MvcResult patchedRes = mvc.perform(patch("/api/v1/products/" + idStr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn();

        JsonNode patched = om.readTree(patchedRes.getResponse().getContentAsString()).get("data");
        long newVersion = patched.get("version").asLong();
        assertThat(newVersion).isGreaterThanOrEqualTo(version);

        // 5) Patch with stale version -> 409
        String stalePatch = "{\n            \"version\": " + version + "\n        }";
        mvc.perform(patch("/api/v1/products/" + idStr)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stalePatch))
                .andExpect(status().isConflict());

        // 6) Delete (soft delete)
        mvc.perform(delete("/api/v1/products/" + idStr))
                .andExpect(status().isNoContent());

        // 7) List should hide deleted (soft delete filter)
        mvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.totalElements").value(0));
    }

    /** Reflection helpers so tests don't depend on Lombok setters. */
    static class ReflectionUtil {
        static void fillDefaults(Object entity) {
            // Try to set some safe defaults to avoid NOT NULL issues in H2 schema.
            // Skips id/version/auditing/deleted and relation fields.
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
                    // Collections/relations are left alone.
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
