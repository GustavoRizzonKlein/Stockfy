package view;

import entities.Customer;
import service.CustomerService;

import javax.swing.*;
import java.awt.*;

public class CustomerForm extends JFrame {

    public CustomerForm() {

        setTitle("Cadastro de Cliente");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(5, 2, 10, 10));

        JTextField txtName = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtCity = new JTextField();
        JTextField txtUf = new JTextField();

        JButton btnSave = new JButton("Salvar");

        add(new JLabel("Nome:"));
        add(txtName);
        add(new JLabel("CPF:"));
        add(txtCpf);
        add(new JLabel("Cidade:"));
        add(txtCity);
        add(new JLabel("UF:"));
        add(txtUf);
        add(new JLabel());
        add(btnSave);

        btnSave.addActionListener(e -> {
            try {
                Customer c = new Customer();
                c.setName(txtName.getText());
                c.setCpf(txtCpf.getText());
                c.setCity(txtCity.getText());
                c.setUf(txtUf.getText());

                new CustomerService().register(c);

                JOptionPane.showMessageDialog(this, "Cliente cadastrado!");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        setVisible(true);
    }
}
