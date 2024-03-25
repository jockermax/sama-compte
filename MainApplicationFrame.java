import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainApplicationFrame extends JFrame {
    private Map<String, Double> accountBalances;
    private Map<String, List<String>> userTransactions;
    private String currentUser;

    private JLabel balanceLabel;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton historyButton;
    private JButton exitButton;

    public MainApplicationFrame(Map<String, Double> accountBalances, Map<String, List<String>> userTransactions, String currentUser) {
        this.accountBalances = accountBalances;
        this.userTransactions = userTransactions;
        this.currentUser = currentUser;

        setTitle("Banking Application Main Menu");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        balanceLabel = new JLabel("Balance: $" + String.format("%.2f", accountBalances.getOrDefault(currentUser, 0.0)));
        depositButton = new JButton("Depot");
        withdrawButton = new JButton("Retrait");
        historyButton = new JButton("Historique ");
        exitButton = new JButton("Quitter");

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(JOptionPane.showInputDialog(MainApplicationFrame.this, "Entrez le montant du depot:"));
                deposit(amount);
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(JOptionPane.showInputDialog(MainApplicationFrame.this, "Entrez le montant du retrait:"));
                withdraw(amount);
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTransactionHistory();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel panel = new JPanel();
        panel.add(balanceLabel);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(historyButton);
        panel.add(exitButton);

        add(panel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void deposit(double amount) {
        accountBalances.put(currentUser, accountBalances.getOrDefault(currentUser, 0.0) + amount);
        updateUserTransactionHistory("Depot: +$" + amount);
        updateBalanceLabel();
    }

    private void withdraw(double amount) {
        if (amount > accountBalances.getOrDefault(currentUser, 0.0)) {
            JOptionPane.showMessageDialog(this, "Fonds insuffisants!");
        } else {
            accountBalances.put(currentUser, accountBalances.get(currentUser) - amount);
            updateUserTransactionHistory("Retraits: -$" + amount);
            updateBalanceLabel();
        }
    }

    private void showTransactionHistory() {
        StringBuilder historyText = new StringBuilder("Historique de le transaction:\n");
        for (String transaction : userTransactions.getOrDefault(currentUser, new ArrayList<>())) {
            historyText.append(transaction).append("\n");
        }
        JOptionPane.showMessageDialog(this, historyText.toString(), "Historique de le transaction", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Compte: $" + String.format("%.2f", accountBalances.getOrDefault(currentUser, 0.0)));
    }

    private void updateUserTransactionHistory(String transaction) {
        List<String> transactions = userTransactions.getOrDefault(currentUser, new ArrayList<>());
        transactions.add(transaction);
        userTransactions.put(currentUser, transactions);
    }
}
