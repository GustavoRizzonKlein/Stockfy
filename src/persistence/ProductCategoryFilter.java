package persistence;

import br.univates.alexandria.persistence.Filter;

public class ProductCategoryFilter implements Filter {

    private final Integer categoryId;

    public ProductCategoryFilter(Integer categoryId)
    {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId()
    {
        return categoryId;
    }

    @Override
    public boolean isAccept(Object o) {
        return true;
    }
}
