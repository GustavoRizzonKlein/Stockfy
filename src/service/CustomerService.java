package service;

import br.univates.alexandria.persistence.IDao;
import entities.Customer;
import factory.DAOFactory;

import java.util.List;

public class CustomerService
{

    private IDao<Customer, Integer> customerDAO;

    public CustomerService()
    {
        this.customerDAO = DAOFactory.getCustomerDAO();
    }

    public void register(Customer customer) throws Exception
    {

        if (customer == null)
        {
            throw new Exception("Cliente não pode ser nulo.");
        }

        if (customer.getName() == null || customer.getName().isBlank())
        {
            throw new Exception("Nome do cliente é obrigatório.");
        }

        if (customer.getCpf() == null || customer.getCpf().isBlank())
        {
            throw new Exception("CPF é obrigatório.");
        }

        customerDAO.create(customer);
    }

    public void update(Customer customer) throws Exception
    {

        if (customer == null)
        {
            throw new Exception("Cliente inválido.");
        }

        if (customer.getCpf() == null || customer.getCpf().isBlank())
        {
            throw new Exception("CPF não informado.");
        }

        customerDAO.update(customer);
    }

    public void delete(Customer customer) throws Exception
    {

        if (customer == null)
        {
            throw new Exception("Cliente inválido.");
        }

        customerDAO.delete(customer);
    }

    public Customer findById(Integer id) throws Exception
    {

        if (id == null || id <= 0)
        {
            throw new Exception("ID inválido.");
        }

        return customerDAO.read(id);
    }


    public List<Customer> findAll()
    {
        return customerDAO.readAll();
    }
}
