package br.com.ada.tech.ecommerce.usecases.impl.order;

import br.com.ada.tech.ecommerce.model.Customer;
import br.com.ada.tech.ecommerce.model.Order;
import br.com.ada.tech.ecommerce.model.OrderStatus;
import br.com.ada.tech.ecommerce.usecases.order.ICreateOrderUseCase;
import br.com.ada.tech.ecommerce.usecases.repository.ICustomerRepository;
import br.com.ada.tech.ecommerce.usecases.repository.IOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class CreateOrderUseCaseImpl implements ICreateOrderUseCase {

    private IOrderRepository repository;
    private ICustomerRepository customerRepository;

    public CreateOrderUseCaseImpl(IOrderRepository repository, ICustomerRepository customerRepository) {
        this.repository = repository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Order create(Customer customer) {
        validCustomer(customer);
        Order order = new Order();
        order.setCustomer(customer);
        order.setItems(new ArrayList<>());
        order.setStatus(OrderStatus.OPEN);
        order.setShippingAddress("Minha casa sempre");
        order.setOrderedAt(LocalDateTime.now());
        repository.save(order);
        return order;
    }

    private void validCustomer(Customer customer) {
        Customer found = customerRepository.findByDocument(customer.getDocument());
        if (found == null) {
            throw new IllegalStateException("Cliente não encontrado");
        }
    }

}
