import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForwardRate extends JFrame{
    private static int FRAME_WIDTH = 400;
    private static int FRAME_HEIGHT = 350;
    private JPanel mainPanel;
    private JTextField currencyPairTextField;
    private JTextField spotQuotationTextField;
    private JTextField forwardPointsTextField;
    private JTextField timeTextField;
    private JTextField forwardRateTextField;
    private JPanel inputAndOutputPanel;
    private JLabel currencyPairLabel;
    private JLabel spotQuotationLabel;
    private JLabel forwardPointsLabel;
    private JLabel timeLabel;
    private JPanel buttonPanel;
    private JPanel textAreaPanel;
    private JScrollPane scrollPane1;
    private JTextArea message;
    private JButton checkButton;
    private JButton clearButton;
    private JPanel titlePanel;
    private JLabel forwardRateLabel;
    private JLabel titleLabel;
    private JButton loadButton;
    private String textAreaMessage; //będzie składana
    private CurrencyChooserDialog chooseCurrencyDialog;
    private List<String> currencyList;
    private List<String> bidList;
    private List<String> askList;
    private static String[] comboArray;

     static String[] getComboArray() {
        return comboArray;
    }

    public ForwardRate(String title){
//        createUIComponents();
        setTitle(title);
        setFrameInTheMiddle(this,FRAME_WIDTH,FRAME_HEIGHT);
        setContentPane(this.mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        currencyPairTextField.setToolTipText("Type for example: USD/EUR");
        currencyPairTextField.setText("");
        loadButton.setToolTipText("Load quotation via Internet.");
        forwardPointsTextField.setToolTipText("Type forward points");
        forwardPointsTextField.setText("");
        timeTextField.setToolTipText("type nubmer (integer)");
        timeTextField.setText("");
        forwardRateTextField.setEditable(false);
        forwardRateTextField.setText("");
        loadButton.addActionListener(event->{
            makeCurrencyComboList();
        });
    }
    public static void main(String[] args) {
        ForwardRate forwardRate = new ForwardRate("ForwardRate");
    }

    private void createUIComponents() {

        mainPanel=new JPanel();
        currencyPairTextField=new JTextField();
        spotQuotationTextField=new JTextField();
        forwardPointsTextField=new JTextField();
        timeTextField=new JTextField();
        forwardRateTextField=new JTextField();
        inputAndOutputPanel=new JPanel();
        inputAndOutputPanel=new JPanel();
        currencyPairLabel=new JLabel();
        spotQuotationLabel=new JLabel();
        forwardPointsLabel=new JLabel();
        timeLabel=new JLabel();
        currencyPairLabel=new JLabel();
        buttonPanel=new JPanel();
        textAreaPanel=new JPanel();
        scrollPane1= new JScrollPane();
        message=new JTextArea();
        checkButton =new JButton();
        clearButton =new JButton();
        titlePanel=new JPanel();
        forwardRateLabel=new JLabel();
        titleLabel=new JLabel();
        loadButton=new JButton();

    }
    public static void setFrameInTheMiddle(Window window,int frameWidth, int frameHeight){
        FRAME_WIDTH=frameWidth;
        FRAME_HEIGHT=frameHeight;
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        window.setBounds(screenWidth / 2 - FRAME_WIDTH / 2, screenHeight / 2 - FRAME_HEIGHT / 2, FRAME_WIDTH, FRAME_HEIGHT);
    }
    public void makeCurrencyComboList(){
        Document doc=null;
        try {
            doc= Jsoup.connect("https://www.nbp.pl/home.aspx?f=/kursy/kursyc.html").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements currencyPairsElementsPartI=doc.getElementsByClass("bgt1 right");
        for(Element el:currencyPairsElementsPartI){  // użyj wyrażenia regularnego bgt? right
            if(el.text().equals("100 HUF")){
                el.text("HUF");
            }else el.text(el.text().replace("1 ",""));
            el.appendText(" ");
        }
        Elements currencyPairsElementsPartII=doc.getElementsByClass("bgt2 right");
        for(Element el:currencyPairsElementsPartII) {  // użyj wyrażenia regularnego bgt? right
            if (el.text().equals("100 JPY")) {
                el.text("JPY");
            }else el.text(el.text().replace("1 ",""));
            el.appendText(" ");
        }

        Elements elements= new Elements();
        elements.addAll(currencyPairsElementsPartI);
        elements.addAll(currencyPairsElementsPartII);

        currencyList =new ArrayList(13);
        bidList =new ArrayList(13);
        askList =new ArrayList(13);
        String lineOfData=elements.text();
        String [] piecesofDataTable = lineOfData.split(" ");
        System.out.println(Arrays.toString(piecesofDataTable));
        for ( int i=0;i<piecesofDataTable.length;i+=3)
        {
            currencyList.add(piecesofDataTable[i]);
        }
        for ( int i=1;i<piecesofDataTable.length;i+=3)
        {
            bidList.add(piecesofDataTable[i]);
        }
        for ( int i=2;i<piecesofDataTable.length;i+=3)
        {
            askList.add(piecesofDataTable[i]);
        }

        System.out.println(currencyList);
        System.out.println(bidList);
        System.out.println(askList);

        comboArray = new String[currencyList.size()];
        currencyList.toArray(comboArray);
        for (int i=0;i<comboArray.length;i++){
            comboArray[i]=comboArray[i]+"/PLN";
        }
        chooseCurrencyDialog=new CurrencyChooserDialog(this);
    }
}
