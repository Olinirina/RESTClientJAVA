
package front.TextField;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;



public class TextField extends JTextField{

    /**
     * @return the labeltext
     */
    public String getLabeltext() {
        return labeltext;
    }

    /**
     * @param labeltext the labeltext to set
     */
    public void setLabeltext(String labeltext) {
        this.labeltext = labeltext;
    }

    /**
     * @return the lineColor
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * @param lineColor the lineColor to set
     */
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }
    private final Animator animator;
    private boolean animateHinText=false;
    private float location;
    private boolean show;
    private boolean mouseOver=false;
    private String labeltext="Label";
    private Color lineColor= new Color(29,151,108);
    public TextField(){
        setBorder(new EmptyBorder(20,3,10,3));  
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseOver=true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseOver=false;
                repaint();
            }
            
        });
        addFocusListener(new FocusAdapter(){
            @Override
            public void focusGained(FocusEvent e) {
               showing(false);
            }

            @Override
            public void focusLost(FocusEvent e) {
                 showing(true);
            }
        
            
        });
        TimingTarget target= new TimingTargetAdapter(){
            @Override
            public void begin() {
                animateHinText= getText().equals("");
            }

            @Override
            public void timingEvent(float fraction) {
                location= fraction;
                repaint();
            }
            
        
        };
        animator=new Animator(300,target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
    }
    private void showing(boolean action){
    
        if(animator.isRunning()){
        
            animator.stop();
        }else{
        
            location=1;
        }
        animator.setStartFraction(1f-location);
        show=action;
        location=1f-location;
        animator.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2=(Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        int width= getWidth();
        int heigth= getHeight();
        if(mouseOver){
            g2.setColor(getLineColor()); 
        }else{
        
            g2.setColor(new Color(150,150,150));
        }
        g2.fillRect(2, heigth-1, width-4, 1);
        createHintText(g2);
        createLineStyle(g2);
        g2.dispose();
    }
    
    public void createHintText(Graphics2D g2){
        Insets in=getInsets();
        g2.setColor(new Color(150,150,150));
        FontMetrics ft= g2.getFontMetrics();
        Rectangle2D r2=ft.getStringBounds(getLabeltext(), g2);
        double height=getHeight()-in.top - in.bottom;
        double textY=(height- r2.getHeight())/2;
        double size;
        if(animateHinText){
            if(show){
                size= 18*(1- location);
            }else{
                size= 18*location;
            }
        }else{
            size=18;
        }
        g2.drawString(getLabeltext(), in.right, (int) (in.top +textY +ft.getAscent()-size));
    }

    public void createLineStyle(Graphics2D g2){
        if(isFocusOwner()){
            double width= getWidth() - 4;
            int heigth= getHeight();
            g2.setColor(getLineColor());
            double size;
            if(show){
                size =width *(1- location);
            }else{
                size= width * location;
                
            }
            double x=(width- size)/2;
            g2.fillRect((int)(x+2), heigth-2, (int) size, 2);
        }
    }
    @Override
    public void setText(String t) {
        if(!getText().equals(t)){
            showing(t.equals(""));
        }
        super.setText(t);
    }
    
    
    
}
