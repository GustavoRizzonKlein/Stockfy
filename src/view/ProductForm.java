package view;

import entities.Product;
import entities.ProductCategory;
import service.ProductCategoryService;
import service.ProductService;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class ProductForm extends JFrame {

    public ProductForm() throws Exception {

        setTitle("Produto");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(5, 2, 10, 10));

        JTextField txtName = new JTextField();
        JTextField txtPrice = new JTextField();
        JTextField txtQty = new JTextField();

        List<ProductCategory> categories = new ProductCategoryService().findAll();
        JComboBox<ProductCategory> cbCategory = new JComboBox<>(categories.toArray(new ProductCategory[0]));

        JButton btnSave = new JButton("Salvar");

        add(new JLabel("Nome:"));
        add(txtName);
        add(new JLabel("Categoria:"));
        add(cbCategory);
        add(new JLabel("Preço:"));
        add(txtPrice);
        add(new JLabel("Quantidade:"));
        add(txtQty);
        add(new JLabel());
        add(btnSave);

        btnSave.addActionListener(e -> {
            try {
                Product p = new Product();
                p.setName(txtName.getText());
                p.setProductCategory((ProductCategory) cbCategory.getSelectedItem());
                p.setPrice(new BigDecimal(txtPrice.getText()));
                p.setQuantity(Integer.parseInt(txtQty.getText()));

                new ProductService().register(p);

                JOptionPane.showMessageDialog(this, "Produto cadastrado!");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        setVisible(true);
    }
}
