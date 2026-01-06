package view;

import entities.ProductCategory;
import service.ProductCategoryService;

import javax.swing.*;
import java.awt.*;

public class ProductCategoryForm extends JFrame {

    public ProductCategoryForm() {

        setTitle("Grupo de Produto");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(2, 2, 10, 10));

        JTextField txtName = new JTextField();
        JButton btnSave = new JButton("Salvar");

        add(new JLabel("Nome:"));
        add(txtName);
        add(new JLabel());
        add(btnSave);

        btnSave.addActionListener(e -> {
            try {
                ProductCategory cat = new ProductCategory();
                cat.setName(txtName.getText());

                new ProductCategoryService().register(cat);

                JOptionPane.showMessageDialog(this, "Categoria salva!");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        setVisible(true);
    }
}
