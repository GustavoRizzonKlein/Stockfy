package view;

import entities.*;
import service.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class OrderScreen extends JFrame {

    private Order order;

    private JComboBox<Customer> cbCustomer;
    private JComboBox<Product> cbProduct;
    private JTextField txtQuantity;
    private JLabel lblTotal;

    private JTable table;
    private DefaultTableModel tableModel;

    private OrderService orderService = new OrderService();
    private CustomerService customerService = new CustomerService();
    private ProductService productService = new ProductService();

    public OrderScreen() {

        setTitle("Pedidos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        initTop();
        initCenter();
        initBottom();

        setVisible(true);
    }

    private void initTop() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        cbCustomer = new JComboBox<>();
        loadCustomers();

        JButton btnCreate = new JButton("Criar Pedido");

        btnCreate.addActionListener(e -> {
            try {
                order = new Order();
                order.setCustomer((Customer) cbCustomer.getSelectedItem());
                orderService.createOrder(order);
                JOptionPane.showMessageDialog(this, "Pedido criado com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        panel.add(new JLabel("Cliente:"));
        panel.add(cbCustomer);
        panel.add(btnCreate);

        add(panel, BorderLayout.NORTH);
    }

    private void initCenter() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel itemPanel = new JPanel(new FlowLayout());

        cbProduct = new JComboBox<>();
        loadProducts();

        txtQuantity = new JTextField(5);

        JButton btnAdd = new JButton("Adicionar Item");

        btnAdd.addActionListener(e -> addItem());

        itemPanel.add(new JLabel("Produto:"));
        itemPanel.add(cbProduct);
        itemPanel.add(new JLabel("Quantidade:"));
        itemPanel.add(txtQuantity);
        itemPanel.add(btnAdd);

        tableModel = new DefaultTableModel(
                new Object[]{"Produto", "Qtd", "Preço", "Total"}, 0
        );

        table = new JTable(tableModel);

        panel.add(itemPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);
    }

    private void initBottom() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        lblTotal = new JLabel("Total: R$ 0.00");

        JButton btnFinish = new JButton("Finalizar Pedido");

        btnFinish.addActionListener(e -> finishOrder());

        panel.add(lblTotal);
        panel.add(btnFinish);

        add(panel, BorderLayout.SOUTH);
    }

    private void addItem() {

        try {
            if (order == null) {
                throw new Exception("Crie um pedido primeiro.");
            }

            Product product = (Product) cbProduct.getSelectedItem();
            Integer quantity = Integer.parseInt(txtQuantity.getText());

            orderService.addItem(order, product, quantity);

            BigDecimal totalItem = product.getPrice()
                    .multiply(BigDecimal.valueOf(quantity));

            tableModel.addRow(new Object[]{
                    product.getName(),
                    quantity,
                    product.getPrice(),
                    totalItem
            });

            updateTotal();
            txtQuantity.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void finishOrder() {

        try {
            if (order == null) {
                throw new Exception("Nenhum pedido criado.");
            }

            BigDecimal total = orderService.finishOrder(order);
            lblTotal.setText("Total: R$ " + total);

            JOptionPane.showMessageDialog(this, "Pedido finalizado!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateTotal() {

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {
            total = total.add(item.getTotal());
        }

        lblTotal.setText("Total: R$ " + total);
    }

    private void loadCustomers() {
        try {
            List<Customer> customers = customerService.findAll();
            customers.forEach(cbCustomer::addItem);
        } catch (Exception ignored) {}
    }

    private void loadProducts() {
        try {
            List<Product> products = productService.findAll();
            products.forEach(cbProduct::addItem);
        } catch (Exception ignored) {}
    }
}
