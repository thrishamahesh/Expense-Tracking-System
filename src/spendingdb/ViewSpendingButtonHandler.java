package spendingdb;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewSpendingButtonHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        openViewSpendingWindow();
    }

    void openViewSpendingWindow() {
        ViewSpending viewSpending = new ViewSpending();
        viewSpending.setVisible(true);
    }
}

