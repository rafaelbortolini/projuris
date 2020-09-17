package br.com.projuris.os.repositories;

import br.com.projuris.os.entities.ServiceOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceOrderRepository extends CrudRepository<ServiceOrder, Long> {
    List<ServiceOrder> findByStatusOrWorkerName(String status, String workerName);
}
