package br.com.projuris.os.controller;


import br.com.projuris.os.entities.ServiceOrder;
import br.com.projuris.os.repositories.EventRepository;
import br.com.projuris.os.repositories.ServiceOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.util.NestedServletException;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ServiceOrderController.class)
public class ServiceOrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ServiceOrderRepository serviceOrderRepository;

    @MockBean
    private EventRepository eventRepository;

    @Mock
    private Model model;

    /**
     * Method: addServiceOrder(ServiceOrder serviceOrder, Model model)
     */
    @Test
    public void testAddServiceOrder() throws Exception {

        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setStart(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        serviceOrder.setStatus("Em Andamento");

        mvc.perform(post("/addServiceOrder")
                .content(objectMapper.writeValueAsString(serviceOrder)))
                .andExpect(status().isFound());
        verify(serviceOrderRepository).save(serviceOrder);
    }

    /**
     * Method: search(@RequestBody ServiceOrder serviceOrder)
     */
    @Test
    public void testSearch() throws Exception {
        ServiceOrder serviceOrder = new ServiceOrder();
        mvc.perform(post("/search")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(serviceOrder)))
                .andExpect(status().isOk());
        verify(serviceOrderRepository, VerificationModeFactory.times(1)).findByStatusOrWorkerName(serviceOrder.getStatus(), serviceOrder.getWorkerName());
    }

    /**
     * Method: newServiceOrder(ServiceOrder serviceOrder)
     */
    @Test
    public void testNewServiceOrder() throws Exception {
        mvc.perform(get("/new")).andExpect(status().isOk());
    }

    /**
     * Method: editServiceOrder(@PathVariable("id") long id, Model model)
     */
    @Test
    public void testEditServiceOrder() throws Exception {
        Assertions.assertThrows(NestedServletException.class, () -> {
                    mvc.perform(get("/edit/{id}", 1L)).andExpect(status().isOk());
                }
        );
        verify(serviceOrderRepository).findById(1L);
    }

    /**
     * Method: index(Model model)
     */
    @Test
    public void testIndex() throws Exception {
//TODO:
    }

    /**
     * Method: deleteUser(@PathVariable("id") long id, Model model)
     */
    @Test
    public void testDeleteUser() throws Exception {
//TODO:
    }

    /**
     * Method: updateServiceOrder(@PathVariable("id") long id, ServiceOrder serviceOrder, Model model)
     */
    @Test
    public void testUpdateServiceOrder() throws Exception {
//TODO:
    }

    /**
     * Method: addEvent(@PathVariable("id") long id, @RequestBody Event ev, Model model)
     */
    @Test
    public void testAddEvent() throws Exception {
//TODO:
    }

}
