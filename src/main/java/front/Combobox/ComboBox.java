/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package front.Combobox;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class ComboBox<E> extends JComboBox<E>{

    /**
     * @return the labelText
     */
    public String getLabelText() {
        return labelText;
    }

    /**
     * @param labelText the labelText to set
     */
    public void setLabelText(String labelText) {
        this.labelText = labelText;
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
    
   private String labelText="Label";
   private Color lineColor=new Color(29,151,108);
   private boolean mouseOver;
   

    public ComboBox() {
        
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15,3,5,3));
        setUI(new ComboUI(this));
        setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component com= super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); 
                setBorder(new EmptyBorder(5,5,5,5));
                if(isSelected){
                    com.setBackground(new Color(29,151,108));
                
                }
            
                return com;
            }
        
        });
    }
     private class ComboUI extends BasicComboBoxUI{
         private Animator animator;
    private boolean animateHinText=false;
    private float location;
    private boolean show;
    private ComboBox combo;

        public ComboUI(ComboBox combo) {
            this.combo= combo;
            
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
        
            addItemListener(new ItemListener(){
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(!isFocusOwner()){
                        if(getSelectedIndex()==-1){
                            showing(true);
                        }else{
                            showing(false);
                        
                        }
                    }   
                }
            });
            addPopupMenuListener(new PopupMenuListener(){
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                    arrowButton.setBackground(new Color(200,200,200));
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                    arrowButton.setBackground(new Color(150,150,150));
                }
                @Override
                public void popupMenuCanceled(PopupMenuEvent e) {
                    arrowButton.setBackground(new Color(150,150,150));
                }
            
            });
        TimingTarget target= new TimingTargetAdapter(){
            @Override
            public void begin() {
                animateHinText= getSelectedIndex() == -1;
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
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
        }

        @Override
        protected JButton createArrowButton() {
            return new ArrowButton();
        }

        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup pop=new BasicComboPopup(comboBox){
                @Override
                protected JScrollPane createScroller() {
                    list.setFixedCellHeight(30);
                    JScrollPane scroll= new JScrollPane(list);
                    scroll.setBackground(Color.WHITE);
                    ScrollBarCustom sb= new ScrollBarCustom();
                    sb.setUnitIncrement(30);
                    sb.setForeground(new Color(180,180,180));
                    scroll.setVerticalScrollBar(sb);
                    return scroll;
                 }
                
            };
            pop.setBorder(new LineBorder(new Color(200,200,200),1));
            return pop; 
        };
        
         

        @Override
        public void paint(Graphics g, JComponent c) {
            super.paint(g, c); 
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
        Rectangle2D r2=ft.getStringBounds(combo.getLabelText(), g2);
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
        g2.drawString(combo.getLabelText(), in.right, (int) (in.top +textY +ft.getAscent()-size));
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
    private class ArrowButton extends JButton{

            public ArrowButton() {
                setContentAreaFilled(false);
                setBorder(new EmptyBorder(5,5,5,5));
                setBackground(new Color(150,150,150));
            }

            @Override
            public void paint(Graphics g) {
                super.paint(g); 
                Graphics2D g2=(Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int width=getWidth();
                int height= getHeight();
                int size=10;
                int x=(width-size)/2;
                int y = (height-size )/2+5;
                int px[]={x, x+size, x+size/2};
                int py[]={y,y,y+size};
                g2.setColor(getBackground());
                g2.fillPolygon(px,py,px.length);
                g2.dispose();
            }
            
            
        
    }
    
    
         
     }
     
    
}

