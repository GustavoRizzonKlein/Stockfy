package service;

import br.univates.alexandria.persistence.IDao;
import entities.Product;
import factory.DAOFactory;

import java.math.BigDecimal;
import java.util.List;

public class ProductService
{

    private IDao<Product, Integer> productDAO;

    public ProductService()
    {
        this.productDAO = DAOFactory.getProductDAO();
    }

    //CADASTRAR PRODUTO
    public void register(Product product) throws Exception {

        if (product == null) {
            throw new Exception("Produto não pode ser nulo.");
        }

        if (product.getName() == null || product.getName().isBlank()) {
            throw new Exception("Nome do produto é obrigatório.");
        }

        if (product.getPrice() == null ||
                product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Preço do produto inválido.");
        }

        if (product.getQuantity() == null || product.getQuantity() < 0) {
            throw new Exception("Quantidade inválida.");
        }

        productDAO.create(product);
    }

    //ATUALIZAR PRODUTO
    public void update(Product product) throws Exception {

        if (product == null || product.getId() == null) {
            throw new Exception("Produto inválido.");
        }

        productDAO.update(product);
    }

    //REMOVER PRODUTO
    public void delete(Product product) throws Exception {

        if (product == null || product.getId() == null) {
            throw new Exception("Produto inválido.");
        }

        productDAO.delete(product);
    }


    //BUSCAR POR ID
    public Product findById(Integer id) throws Exception
    {

        if (id == null || id <= 0)
        {
            throw new Exception("ID inválido.");
        }

        return productDAO.read(id);
    }

    // LISTAR TODOS
    public List<Product> findAll() {
        return productDAO.readAll();
    }

    // REGRA DE NEGÓCIO: BAIXAR ESTOQUE
    public void decreaseStock(Product product, Integer quantity)
            throws Exception {

        if (quantity == null || quantity <= 0) {
            throw new Exception("Quantidade inválida.");
        }

        if (product.getQuantity() < quantity) {
            throw new Exception("Estoque insuficiente.");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productDAO.update(product);
    }
}
