package service;

import br.univates.alexandria.persistence.IDao;
import entities.ProductCategory;
import factory.DAOFactory;

import java.util.List;

public class ProductCategoryService {

    private IDao<ProductCategory, Integer> productCategoryDAO;

    public ProductCategoryService() {
        this.productCategoryDAO = DAOFactory.getProductCategoryDAO();
    }

    //CADASTRAR CATEGORIA
    public void register(ProductCategory category) throws Exception
    {

        if (category == null)
        {
            throw new Exception("Categoria inválida.");
        }

        if (category.getName() == null || category.getName().isBlank())
        {
            throw new Exception("Nome da categoria é obrigatório.");
        }

        productCategoryDAO.create(category);
    }

    // ATUALIZAR CATEGORIA
    public void update(ProductCategory category) throws Exception
    {

        if (category == null || category.getId() == null)
        {
            throw new Exception("Categoria inválida.");
        }

        productCategoryDAO.update(category);
    }

    // REMOVER CATEGORIA
    public void delete(ProductCategory category) throws Exception
    {

        if (category == null || category.getId() == null)
        {
            throw new Exception("Categoria inválida.");
        }

        productCategoryDAO.delete(category);
    }

    // BUSCAR POR ID
    public ProductCategory findById(Integer id) throws Exception
    {

        if (id == null || id <= 0)
        {
            throw new Exception("ID inválido.");
        }

        return productCategoryDAO.read(id);
    }


    // LISTAR TODAS
    public List<ProductCategory> findAll()
    {
        return productCategoryDAO.readAll();
    }
}
