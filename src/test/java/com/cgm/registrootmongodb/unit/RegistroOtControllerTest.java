package com.cgm.registrootmongodb.unit;

import com.cgm.registrootmongodb.controller.RegistroOtController;
import com.cgm.registrootmongodb.entity.ResgistroOt;
import com.cgm.registrootmongodb.enumeration.Estado;
import com.cgm.registrootmongodb.service.RegistroOtService;
import com.cgm.registrootmongodb.util.JsonUtil;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistroOtController.class)
public class RegistroOtControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegistroOtService registroOtService;

    @Value("${registroot.security.token}")
    private String TOKEN;

    private String PREFIX_URL = "/api/v1/registro-ot";

    ResgistroOt resgistroOt = new ResgistroOt();

    @Test
    @Order(1)
    public void listAllResgistrosOTWhenGetMethod() throws Exception{

        //ResgistroOt resgistroOt = new ResgistroOt();
        resgistroOt.setNombreSistema("test nombre");
        resgistroOt.setNumeroOt("100");
        resgistroOt.setDescipcion("test descripcion");
        resgistroOt.setEstado(Estado.EN_CONFIGURACION);

        List<ResgistroOt> listResgistroOTs = Arrays.asList(resgistroOt);

        given(registroOtService.getAllRegistro()).willReturn(listResgistroOTs);

        mvc.perform(get(PREFIX_URL)
                .contentType(MediaType.APPLICATION_JSON).header("token", TOKEN))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$",hasSize(1)))
           .andExpect(jsonPath("$[0].nombreSistema",is(resgistroOt.getNombreSistema())));
    }

    @Test
    @Order(2)
    public void dontlistAllResgistroOTWhenDontPassToken() throws Exception{

        resgistroOt.setNombreSistema("test nombre no token");
        resgistroOt.setNumeroOt("100");
        resgistroOt.setDescipcion("test descripcion no token");
        resgistroOt.setEstado(Estado.EN_CONFIGURACION);

        List<ResgistroOt> listResgistroOTs = Arrays.asList(resgistroOt);

        given(registroOtService.getAllRegistro()).willReturn(listResgistroOTs);

        mvc.perform(get(PREFIX_URL)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(3)
    public void createRegistroOtWhenPostMethod() throws Exception{
        resgistroOt.setNombreSistema("test create nombre");
        resgistroOt.setNumeroOt("100");
        resgistroOt.setDescipcion("test create descripcion");
        resgistroOt.setEstado(Estado.EN_CONFIGURACION);

        given(registroOtService.saveRegistroOt(resgistroOt)).willReturn(resgistroOt);

        mvc.perform(post(PREFIX_URL)
                    .header("token", TOKEN)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.toJson(resgistroOt)))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.nombreSistema",is(resgistroOt.getNombreSistema())));

    }

    @Test
    @Order(4)
    public void createRegistroOtWhenDontPassToken() throws Exception{
        resgistroOt.setNombreSistema("test create nombre");
        resgistroOt.setNumeroOt("100");
        resgistroOt.setDescipcion("test create descripcion");
        resgistroOt.setEstado(Estado.EN_CONFIGURACION);

        given(registroOtService.saveRegistroOt(resgistroOt)).willReturn(resgistroOt);

        mvc.perform(post(PREFIX_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(resgistroOt)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(5)
    public void deleteRegistroOtWhenDeleteMethod() throws Exception{
        resgistroOt.setNombreSistema("test create nombre");
        resgistroOt.setNumeroOt("100");
        resgistroOt.setDescipcion("test create descripcion");
        resgistroOt.setEstado(Estado.EN_CONFIGURACION);

        //given(registroOtService.saveRegistroOt(resgistroOt)).willReturn(resgistroOt);

        doNothing().when(registroOtService).deleteRegistroOt(resgistroOt.getRegistroId());

        mvc.perform(delete(PREFIX_URL + "/" + resgistroOt.getRegistroId())
                        .header("token", TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }






}
