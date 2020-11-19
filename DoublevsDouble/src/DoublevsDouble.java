import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DoublevsDouble extends JPanel {
    private List<Double> x;
    private List<Double> y;
    private int PaddingTop = 40, PaddingLeft = 40, PaddingBottom = 50, PaddingRight = 30;
    private Color lineColor = Color.LIGHT_GRAY;
    private Color dotColor = Color.BLUE;

    int xScale, yScale, xNumWeight, yNumWeight, xStart, yStart;

    public DoublevsDouble(List<Double> x, List<Double> y){
        this.x = x;
        this.y = y;
    }

    @Override
    protected void paintComponent(Graphics g){
        BubbleSort(x);
        BubbleSort(y);
        //Adding Label to top
        String Label = "Power vs Perception Reaction Time";
        g.setFont(new Font("Serif", Font.BOLD, 12));
        g.drawString(Label , getWidth()/2 - 100, PaddingTop/2 + 5);
        //Drawing outer rectangle.
        g.drawRect(PaddingLeft, PaddingTop, getWidth() - PaddingLeft - PaddingRight, getHeight() - PaddingBottom - PaddingTop);

        xScale = (getWidth() - PaddingRight - PaddingLeft) / (x.size());
        yScale = (getHeight() - PaddingTop - PaddingBottom) / (y.size() * 2);

        xNumWeight = (int) Math.round(getMax(x) / (x.size()));
        yNumWeight = (int) Math.round(getMax(y) / (y.size() * 2));

        DrawXLines(g);
        DrawYLines(g);
        DrawPoints(g);
    }

    public void DrawPoints(Graphics g){
        g.setColor(Color.BLUE);
        int xpos;
        int ypos;

        //These two variables are very important because they account for starting at the minimum value
        int xMinvalPadding = (int) Math.round((getMin(x) / xNumWeight) * xScale);
        int yMinvalPadding = (int) Math.round((getMin(y) / yNumWeight) * yScale);

        //x and y must have the same number of vars.
        for(int i = 0; i < x.size(); i++){
            xpos = PaddingLeft + (int) Math.round((x.get(i) / xNumWeight) * xScale) - xMinvalPadding - 5;
            ypos = getHeight() - PaddingBottom - ((int) Math.round(+ ((y.get(i) / yNumWeight)) * yScale)) + yMinvalPadding -5;
            System.out.println("( " + x.get(i) + ", " + y.get(i) + ")");
            g.fillOval(xpos,ypos,10,10);
        }
    }
    //Draw vertical lines and add numbers on bottom
    public void DrawXLines(Graphics g){
        int xPixels;
        xStart = (int) Math.round((getMin(x)));
        System.out.println("xNumWeight: " + xNumWeight);

        for(int i = 0; i < x.size(); i++){
            xPixels = PaddingLeft + i * xScale;
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(xStart), xPixels, getHeight() - PaddingBottom / 2);
            xStart += xNumWeight;
            g.setColor(lineColor);
            g.drawLine(xPixels, getHeight() - PaddingBottom, xPixels, getHeight() - (getHeight() - PaddingTop));
        }
    }
    //Draw horizontal lines and add numbers on left.
    public void DrawYLines(Graphics g){
        int yPixels;
        System.out.println(yScale);
        yStart = (int) Math.round((getMin(y)));

        for(int i = 0; i < y.size() * 2; i++){
            yPixels = getHeight() - PaddingBottom - i*yScale;
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(yStart), 1, yPixels);
            yStart += yNumWeight;
            g.setColor(lineColor);
            g.drawLine(PaddingLeft, yPixels,getWidth() - PaddingRight, yPixels);
        }
    }
  //returns the largest double
    public double getMax(List<Double> L){
        double max = L.get(0);
        for(Double var : L){
            if(var > max){
                max = var;
            }
        }
        return max;
    }
    //returns the smallest double
    public double getMin(List<Double> L){
        double min = L.get(0);
        for(Double var : L){
            if(var < min){
                min = var;
            }
        }
        return min;
    }
    private static void CreateGUI(){

        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();

        DoublevsDouble graph = new DoublevsDouble(x,y);
        int MaxPoints = 20;

        Random rand = new Random();

        for(int i = 0; i < MaxPoints; i++){
            x.add(rand.nextDouble() * 300 + 30) ;
            y.add(rand.nextDouble() * 1000);
        }

        JFrame frame = new JFrame("Graph");
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(graph);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void BubbleSort(List<Double> L){
        int i;
        int swap = 1;
        double temp = 0;

        while(swap == 1){
            swap = 0;

            for(i = 0; i < L.size() - 1; i++){
                if(L.get(i) > L.get(i + 1)){
                    temp = L.get(i);
                    L.set(i, L.get(i + 1));
                    L.set(i + 1, temp);
                    swap = 1;
                }
            }
        }
    }
    public static void main(String[] args){
        CreateGUI();
    }
}
