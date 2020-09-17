package br.com.projuris.os.controller;

import br.com.projuris.os.entities.Event;
import br.com.projuris.os.entities.ServiceOrder;
import br.com.projuris.os.repositories.EventRepository;
import br.com.projuris.os.repositories.ServiceOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ServiceOrderController {

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private EventRepository eventRepository;

    @PostMapping("/addServiceOrder")
    public String addServiceOrder(ServiceOrder serviceOrder, Model model) {
        serviceOrder.setStart(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        serviceOrder.setStatus("Em Andamento");
        serviceOrderRepository.save(serviceOrder);
        return "redirect:/index";
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<ServiceOrder> search(@RequestBody ServiceOrder serviceOrder) {
        List<ServiceOrder> list = serviceOrderRepository.
                findByStatusOrWorkerName(serviceOrder.getStatus(), serviceOrder.getWorkerName());
        return list;
    }

    @GetMapping("/new")
    public String newServiceOrder(ServiceOrder serviceOrder) {
        return "add-service-order";
    }

    @GetMapping("/edit/{id}")
    public String editServiceOrder(@PathVariable("id") long id, Model model) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ordem de serviço inválida:" + id));
        model.addAttribute("serviceOrder", serviceOrder);
        return "update-service-order";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("serviceOrders", serviceOrderRepository.findAll());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ordem de serviço inválida:" + id));
        serviceOrderRepository.delete(serviceOrder);
        model.addAttribute("serviceOrders", serviceOrderRepository.findAll());
        return "redirect:/index";
    }

    @PostMapping("/updateServiceOrder/{id}")
    public String updateServiceOrder(@PathVariable("id") long id, ServiceOrder serviceOrder, Model model) {

        ServiceOrder so = serviceOrderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ordem de serviço inválida:" + id));
        serviceOrder.setHistory(so.getHistory());
        serviceOrderRepository.save(serviceOrder);
        model.addAttribute("serviceOrders", serviceOrderRepository.findAll());
        return "redirect:/index";
    }

    @PostMapping(value = "/event/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addEvent(@PathVariable("id") long id, @RequestBody Event ev, Model model) {
        ServiceOrder s = serviceOrderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ordem de serviço inválida:" + id));
        ev.setServiceOrder(s);
        s.getHistory().add(ev);
        s = serviceOrderRepository.save(s);
        model.addAttribute("serviceOrder", s);
        return "redirect:/edit/" + id;
    }


}
