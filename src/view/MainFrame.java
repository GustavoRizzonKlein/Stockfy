package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("Sistema de Vendas");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnCustomer = new JButton("Cadastro de Cliente");
        JButton btnCategory = new JButton("Grupo de Produto");
        JButton btnProduct = new JButton("Produto");
        JButton btnOrder = new JButton("Pedidos");
        JButton btnExit = new JButton("Sair");

        btnCustomer.addActionListener(e -> openCustomerForm());
        btnCategory.addActionListener(e -> openCategoryForm());
        btnProduct.addActionListener(e -> openProductForm());
        btnOrder.addActionListener(e -> openOrderScreen());
        btnExit.addActionListener(e -> System.exit(0));

        add(btnCustomer);
        add(btnCategory);
        add(btnProduct);
        add(btnOrder);
        add(btnExit);

        setVisible(true);
    }

    // ===============================
    // MÉTODOS DE ABERTURA DAS TELAS
    // ===============================
    private void openCustomerForm() {
        try {
            new CustomerForm();
        } catch (Exception e) {
            showError(e);
        }
    }

    private void openCategoryForm() {
        try {
            new ProductCategoryForm();
        } catch (Exception e) {
            showError(e);
        }
    }

    private void openProductForm() {
        try {
            new ProductForm();
        } catch (Exception e) {
            showError(e);
        }
    }

    private void openOrderScreen() {
        try {
            new OrderScreen();
        } catch (Exception e) {
            showError(e);
        }
    }

    private void showError(Exception e) {
        JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
