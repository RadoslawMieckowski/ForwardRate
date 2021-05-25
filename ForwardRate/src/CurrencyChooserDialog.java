import javax.swing.*;
import java.awt.*;

public class CurrencyChooserDialog extends JDialog{

    private JComboBox<String>currencyPairComboBox;
    private JButton okButton;

    public CurrencyChooserDialog(ForwardRate owner){
        super(owner,"Choose currency Pair",true);
        JPanel panel =new JPanel(new GridLayout(3,1,3,3));
        panel.add(new JLabel("Choose Currency Pair",SwingConstants.CENTER));
        currencyPairComboBox=new JComboBox<>(ForwardRate.getComboArray());
        panel.add(currencyPairComboBox);
        okButton = new JButton("ok");
        okButton.addActionListener(event->setVisible(false));
        ForwardRate.setFrameInTheMiddle(this,250,190);
        panel.add(okButton);
        add(panel);
        setSize(250,190);
        setVisible(true);
        }
    }
