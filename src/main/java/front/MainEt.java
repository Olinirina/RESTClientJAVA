/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package front;

import GestionEtudiantREST.net.EtudiantClient;
import GestionEtudiantREST.net.ObservationClient;
import front.Table.TableGradientCell;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import modelClient.Etudiant;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelClient.Observation;

/**
 *
 * @author Olinirina
 */
public class MainEt extends javax.swing.JFrame {

    private EtudiantClient etudiantCli;
    private ObservationClient obsCli;
    CardLayout cardLayout;
    private DefaultTableModel tableModel;
    private DefaultTableModel tableModelOb;
    public MainEt() {
        initComponents();
        etudiantCli= new EtudiantClient();
        obsCli= new ObservationClient();
        cardLayout =(CardLayout) (pnlcards.getLayout());
        //FLATLAF
        Component [] components= this.getContentPane().getComponents();
        for(Component component : components){
            if(component instanceof JButton){
                ((JButton) component).setUI(new BasicButtonUI());
                ((JButton) component).setFocusPainted(false);
            }
        }
        
        //Relier tableGradient etudiant
        table.setDefaultRenderer(Object.class, new TableGradientCell());
        //Relier tableGradient observation
        tableOb.setDefaultRenderer(Object.class, new TableGradientCell());
        //style table etudiant
        pnlCard1.putClientProperty(FlatClientProperties.STYLE, ""
        +"border:1,1,1,1,$TableHeader.bottomSeparatorColor,,10");
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
            +"hoverBackground:null;"
            +"pressedBackground:null;"
            +"separatorColor:$TableHeader.background");
        scroll.putClientProperty(FlatClientProperties.STYLE, ""
            +"border:3,0,3,0,$Table.background,10,10");
      scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
      +"hoverTrackColor:null");
      //TextLabel
      txtNumEt.setLabeltext("Numero de l'etudiant");
      txtNomEt.setLabeltext("Nom");
      txtMoyEt.setLabeltext("Moyenne");
      
      //style table observation
        pnlCard2.putClientProperty(FlatClientProperties.STYLE, ""
        +"border:1,1,1,1,$TableHeader.bottomSeparatorColor,,10");
        tableOb.getTableHeader().putClientProperty(FlatClientProperties.STYLE, ""
            +"hoverBackground:null;"
            +"pressedBackground:null;"
            +"separatorColor:$TableHeader.background");
        scrollOb.putClientProperty(FlatClientProperties.STYLE, ""
            +"border:3,0,3,0,$Table.background,10,10");
      scrollOb.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
      +"hoverTrackColor:null");
      //TextLabel
      comboOb.setLabelText("Numero de l'etudiant");
      nomOb.setLabeltext("Nom");
      moyOb.setLabeltext("Moyenne");
      
      // Initialisation du  modèle de table etudiant avec les colonnes nécessaires
    String[] colonnes = {"NumEt", "Nom", "Moyenne"};
    tableModel = new DefaultTableModel(colonnes, 0);
    table.setModel(tableModel);
    
     // Initialisation du  modèle de table obs avec les colonnes nécessaires
    String[] colonnesOb = {"id", "Num_Etudiant", "Nom", "Moyenne", "Observation"};
    tableModelOb = new DefaultTableModel(colonnesOb, 0);
    tableOb.setModel(tableModelOb);
    
    // Ajoutez un écouteur d'événements de sélection au tableau Etudiant
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Obtenez la ligne sélectionnée
                int ligneSelectionnee = table.getSelectedRow();

                // Vérifiez s'il y a une ligne sélectionnée
                if (ligneSelectionnee != -1) {
                    // Récupérez l'ID de l'employé sélectionné dans la première colonne (colonne 0)
                    String codeEtudiantSelectionne = (String) tableModel.getValueAt(ligneSelectionnee, 0);

                    // Récupérer le premier étudiant de la liste
                    List<Etudiant> etudiantSelectionne = etudiantCli.testGetEtudiant(codeEtudiantSelectionne);

               Etudiant etudian = null;
                for (Etudiant etudiant : etudiantSelectionne) {
                    if (etudiant.getNumEtudiant().equals(codeEtudiantSelectionne)) {
                        etudian = etudiant;
                        break;
                    }
                }

                // Vérifier si l'étudiant a été trouvé
                if (etudiantSelectionne != null) {
                    // Mettre à jour les champs de texte
                    txtNumEt.setText(etudian.getNumEtudiant());
                    txtNomEt.setText(etudian.getNom());
                    txtMoyEt.setText(String.valueOf(etudian.getMoyenne()));
                    ajoutEtbtn.setEnabled(false);
                } else {
                    ajoutEtbtn.setEnabled(true);
                }


                }
                
            }
        });
        
        
         // Ajoutez un écouteur d'événements de sélection au tableau Observation
        tableOb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Obtenez la ligne sélectionnée
                int ligneSelectionnee = tableOb.getSelectedRow();

                // Vérifiez s'il y a une ligne sélectionnée
                if (ligneSelectionnee != -1) {
                    // Récupérez l'ID de l'employé sélectionné dans la première colonne (colonne 0)
                    Long codeObSelectionne = (Long) tableModelOb.getValueAt(ligneSelectionnee, 0);

                    // Récupérer le premier étudiant de la liste
                    List<Observation> obsSelectionne = obsCli.testGetObservation(codeObSelectionne);

               Observation observation = null;
                for (Observation obs : obsSelectionne) {
                    if (obs.getIdOb().equals(codeObSelectionne)) {
                        observation = obs;
                        break;
                    }
                }

                // Vérifier si l'étudiant a été trouvé
                if (obsSelectionne != null) {
                    // Mettre à jour les champs de texte
                    comboOb.setSelectedItem(observation.getEtudiant().getNumEtudiant());
                    nomOb.setText(observation.getEtudiant().getNom());
                    moyOb.setText(String.valueOf(observation.getEtudiant().getMoyenne()));
                    comboOb.setEnabled(false);
                    nomOb.setEditable(false);
                    moyOb.setEditable(false);
                    ajtOb.setEnabled(false);
                } else {
                    comboOb.setEnabled(true);
                    nomOb.setEditable(true);
                    moyOb.setEditable(true);
                    ajtOb.setEnabled(true);
                }


                }
                
            }
        });
        
        comboOb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Charger les détails de l'employé en fonction du code sélectionné
                    String selectedEtudiantCode = (String) comboOb.getSelectedItem();
                    List<Etudiant> selected = etudiantCli.testGetEtudiant(selectedEtudiantCode);
                    for(Etudiant et: selected){
                    nomOb.setText(et.getNom());
                    moyOb.setText(String.valueOf(et.getMoyenne()));
                    }
                    
                }
});
     //Moyenne max et min
     Double max= etudiantCli.MaxMoy();
     Double min= etudiantCli.MinMoy();
     Double moy= etudiantCli.Moy();
     maxmoy.setLabeltext("");
     minmoy.setLabeltext("");
     classemoy.setLabeltext("");
     maxmoy.setText(String.valueOf(max));
     minmoy.setText(String.valueOf(min));
     classemoy.setText(String.valueOf(moy));
     maxmoy.setEditable(false);
     minmoy.setEditable(false);
      classemoy.setEditable(false);
    chargerCodeEt();
    chargerDonneesDepuisBase();
    chargerDonneesDepuisBaseOb();
    }
    public void chargerCodeEt(){
        List<String> codeEtudiants=etudiantCli.listerCodeEtudiants();
        DefaultComboBoxModel<String> etModel=new DefaultComboBoxModel<String>(codeEtudiants.toArray(new String[0]));
        comboOb.setModel(etModel);
    
    }
    private void chargerDonneesDepuisBase() {
        // Supprimez toutes les lignes existantes du modèle de table
        tableModel.setRowCount(0);

        // Chargez les données depuis la base de données
        List<Etudiant> etudiants = etudiantCli.listerEtudiants();
        // Ajoutez chaque employé au modèle de table
        for (Etudiant etudiant : etudiants) {
            Object[] ligne = {etudiant.getNumEtudiant(), etudiant.getNom(), etudiant.getMoyenne()};
            tableModel.addRow(ligne);
        }
    }

    
    private void chargerDonneesDepuisBaseOb() {
        // Supprimez toutes les lignes existantes du modèle de table
        tableModelOb.setRowCount(0);

        // Chargez les données depuis la base de données
        List<Observation> obs = obsCli.listerObservations();
        // Ajoutez chaque employé au modèle de table
        for (Observation o : obs) {
            Object[] ligne = {o.getIdOb(),o.getEtudiant().getNumEtudiant(),o.getEtudiant().getNom(),o.getEtudiant().getMoyenne(),o.getObs()};
            tableModelOb.addRow(ligne);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlcards = new javax.swing.JPanel();
        pnlCard1 = new javax.swing.JPanel();
        scroll = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        txtNumEt = new front.TextField.TextField();
        txtMoyEt = new front.TextField.TextField();
        txtNomEt = new front.TextField.TextField();
        jLabel3 = new javax.swing.JLabel();
        ajoutEtbtn = new javax.swing.JButton();
        modEtbtn = new javax.swing.JButton();
        SuppEtBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        maxmoy = new front.TextField.TextField();
        minmoy = new front.TextField.TextField();
        EffEtBtn = new javax.swing.JButton();
        classemoy = new front.TextField.TextField();
        jLabel7 = new javax.swing.JLabel();
        pnlCard2 = new javax.swing.JPanel();
        scrollOb = new javax.swing.JScrollPane();
        tableOb = new javax.swing.JTable();
        comboOb = new front.Combobox.ComboBox();
        nomOb = new front.TextField.TextField();
        moyOb = new front.TextField.TextField();
        jLabel4 = new javax.swing.JLabel();
        ajtOb = new javax.swing.JButton();
        suppOb = new javax.swing.JButton();
        effOb = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(29, 151, 108));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Etudiant");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Observation");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(263, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        pnlcards.setLayout(new java.awt.CardLayout());

        pnlCard1.setBackground(new java.awt.Color(255, 255, 255));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NumEtudiant", "Nom", "Moyenne"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll.setViewportView(table);

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(29, 151, 108));
        jLabel3.setText("Listes des Etudiants");

        ajoutEtbtn.setText("Ajouter");
        ajoutEtbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajoutEtbtnActionPerformed(evt);
            }
        });

        modEtbtn.setText("Modifier");
        modEtbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modEtbtnActionPerformed(evt);
            }
        });

        SuppEtBtn.setText("Supprimer");
        SuppEtBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuppEtBtnActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(29, 151, 108));
        jLabel5.setText("Moyenne maximale:");

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(29, 151, 108));
        jLabel6.setText("Moyenne minimale:");

        EffEtBtn.setText("Effacer");
        EffEtBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EffEtBtnActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(29, 151, 108));
        jLabel7.setText("Moyenne de la classe:");

        javax.swing.GroupLayout pnlCard1Layout = new javax.swing.GroupLayout(pnlCard1);
        pnlCard1.setLayout(pnlCard1Layout);
        pnlCard1Layout.setHorizontalGroup(
            pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCard1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCard1Layout.createSequentialGroup()
                        .addGroup(pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomEt, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlCard1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(ajoutEtbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCard1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(modEtbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(94, 94, 94)
                                .addComponent(SuppEtBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlCard1Layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addComponent(txtMoyEt, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(EffEtBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(65, 65, 65))
                    .addGroup(pnlCard1Layout.createSequentialGroup()
                        .addGroup(pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumEt, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(classemoy, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))))
            .addGroup(pnlCard1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll)
                .addContainerGap())
            .addGroup(pnlCard1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(maxmoy, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(minmoy, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        pnlCard1Layout.setVerticalGroup(
            pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCard1Layout.createSequentialGroup()
                .addGroup(pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCard1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNumEt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCard1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(classemoy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCard1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNomEt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMoyEt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCard1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EffEtBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)))
                .addGroup(pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ajoutEtbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modEtbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuppEtBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCard1Layout.createSequentialGroup()
                        .addGroup(pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(maxmoy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCard1Layout.createSequentialGroup()
                        .addComponent(minmoy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pnlcards.add(pnlCard1, "pnlCard1");

        pnlCard2.setBackground(new java.awt.Color(255, 255, 255));

        tableOb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "id", "Num_Etudiant", "Nom", "Moyenne", "Observation"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollOb.setViewportView(tableOb);

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(29, 151, 108));
        jLabel4.setText("Listes des observations");

        ajtOb.setText("Ajouter");
        ajtOb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajtObActionPerformed(evt);
            }
        });

        suppOb.setText("Supprimer");
        suppOb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suppObActionPerformed(evt);
            }
        });

        effOb.setText("Effacer");
        effOb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                effObActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCard2Layout = new javax.swing.GroupLayout(pnlCard2);
        pnlCard2.setLayout(pnlCard2Layout);
        pnlCard2Layout.setHorizontalGroup(
            pnlCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCard2Layout.createSequentialGroup()
                .addGroup(pnlCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCard2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollOb, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE))
                    .addGroup(pnlCard2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(pnlCard2Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(pnlCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nomOb, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                    .addComponent(moyOb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboOb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ajtOb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(suppOb, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(effOb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(54, 54, 54))
        );
        pnlCard2Layout.setVerticalGroup(
            pnlCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCard2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(pnlCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboOb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ajtOb, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(pnlCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomOb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suppOb, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(pnlCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moyOb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(effOb, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(scrollOb, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlcards.add(pnlCard2, "pnlCard2");

        jSplitPane1.setRightComponent(pnlcards);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        setSize(new java.awt.Dimension(976, 683));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        cardLayout.show(pnlcards, "pnlCard1");
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        cardLayout.show(pnlcards, "pnlCard2");
    }//GEN-LAST:event_jLabel2MouseClicked

    private void SuppEtBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuppEtBtnActionPerformed
        String numEt= txtNumEt.getText();
        String nomEt= txtNomEt.getText();
        Double moy= Double.parseDouble(txtMoyEt.getText());
        if ("".equals(numEt) || "".equals(nomEt) || "".equals(moy)) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir correctement tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }else{
        Etudiant et= new Etudiant(numEt,nomEt,moy);
         etudiantCli.DeleteEtudiant(numEt);
          JOptionPane.showMessageDialog(null, "Supression réussie", "Succès", JOptionPane.INFORMATION_MESSAGE);
            // Rafraîchissez la table après l'ajout d'un nouvel employé
            chargerDonneesDepuisBase();
            chargerDonneesDepuisBaseOb();
            chargerCodeEt();
        }
        txtNumEt.setText("");
        txtNomEt.setText("");
        txtMoyEt.setText("");
        ajoutEtbtn.setEnabled(true);
        //Moyenne max et min
     Double max= etudiantCli.MaxMoy();
     Double min= etudiantCli.MinMoy();
     maxmoy.setLabeltext("");
     minmoy.setLabeltext("");
     maxmoy.setText(String.valueOf(max));
     minmoy.setText(String.valueOf(min));
     maxmoy.setEditable(false);
     minmoy.setEditable(false);
    }//GEN-LAST:event_SuppEtBtnActionPerformed

    private void ajoutEtbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajoutEtbtnActionPerformed
        String numEt= txtNumEt.getText();
        String nomEt= txtNomEt.getText();
        Double moy= Double.parseDouble(txtMoyEt.getText());
         if ("".equals(numEt) || "".equals(nomEt) || "".equals(moy)) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir correctement tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }else{
         Etudiant et= new Etudiant(numEt,nomEt,moy);
         etudiantCli.AddEtudiant(et);
          JOptionPane.showMessageDialog(null, "Opération réussie", "Succès", JOptionPane.INFORMATION_MESSAGE);
            // Rafraîchissez la table après l'ajout d'un nouvel employé
            chargerDonneesDepuisBase();
       chargerCodeEt();
         }
         txtNumEt.setText("");
        txtNomEt.setText("");
        txtMoyEt.setText("");
     //Moyenne max et min
     Double max= etudiantCli.MaxMoy();
     Double min= etudiantCli.MinMoy();
     Double moy2= etudiantCli.Moy();
     maxmoy.setLabeltext("");
     minmoy.setLabeltext("");
     classemoy.setLabeltext("");
     maxmoy.setText(String.valueOf(max));
     minmoy.setText(String.valueOf(min));
     classemoy.setText(String.valueOf(moy2));
     maxmoy.setEditable(false);
     minmoy.setEditable(false);
      classemoy.setEditable(false);
        
    }//GEN-LAST:event_ajoutEtbtnActionPerformed

    private void modEtbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modEtbtnActionPerformed
        String numEt= txtNumEt.getText();
        String nomEt= txtNomEt.getText();
        Double moy= Double.parseDouble(txtMoyEt.getText());
         if ("".equals(numEt) || "".equals(nomEt) || "".equals(moy)) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir correctement tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }else{
         Etudiant et= new Etudiant(numEt,nomEt,moy);
         etudiantCli.UpdateEtudiant(et);
          JOptionPane.showMessageDialog(null, "Mise à jour réussie", "Succès", JOptionPane.INFORMATION_MESSAGE);
            // Rafraîchissez la table après l'ajout d'un nouvel employé
            chargerDonneesDepuisBase();
            chargerDonneesDepuisBaseOb();
         }
         txtNumEt.setText("");
        txtNomEt.setText("");
        txtMoyEt.setText("");
        ajoutEtbtn.setEnabled(true);
        //Moyenne max et min
     Double max= etudiantCli.MaxMoy();
     Double min= etudiantCli.MinMoy();
     Double moy2= etudiantCli.Moy();
     maxmoy.setLabeltext("");
     minmoy.setLabeltext("");
     classemoy.setLabeltext("");
     maxmoy.setText(String.valueOf(max));
     minmoy.setText(String.valueOf(min));
     classemoy.setText(String.valueOf(moy2));
     maxmoy.setEditable(false);
     minmoy.setEditable(false);
      classemoy.setEditable(false);
    }//GEN-LAST:event_modEtbtnActionPerformed

    private void ajtObActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajtObActionPerformed
        String selectedEtudiantCode = (String) comboOb.getSelectedItem();
        String nom= nomOb.getText();
        if("".equals(selectedEtudiantCode)){
            JOptionPane.showMessageDialog(null, "Veuillez remplir correctement tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);  
        }else {
            Etudiant etudiant= new Etudiant(selectedEtudiantCode,nom,0.0);
                obsCli.AddObs(etudiant);      
            JOptionPane.showMessageDialog(null, "Opération réussie", "Succès", JOptionPane.INFORMATION_MESSAGE);
            chargerDonneesDepuisBaseOb();
        }
        comboOb.setEnabled(true);
        nomOb.setText("");
       moyOb.setText("");
       ajtOb.setEnabled(true);
    }//GEN-LAST:event_ajtObActionPerformed

    private void suppObActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suppObActionPerformed
        // Obtenez la ligne sélectionnée
                int ligneSelectionnee = tableOb.getSelectedRow();

                // Vérifiez s'il y a une ligne sélectionnée
                if (ligneSelectionnee != -1) {
                    // Récupérez l'ID de l'employé sélectionné dans la première colonne (colonne 0)
                    Long codeObSelectionne = (Long) tableModelOb.getValueAt(ligneSelectionnee, 0);
                    obsCli.DeleteOb(codeObSelectionne);
                    JOptionPane.showMessageDialog(null, "Supression réussie", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    chargerDonneesDepuisBaseOb();
                
                }else{
                    JOptionPane.showMessageDialog(null, "Verifier correctement tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);  
                
                }
                comboOb.setEnabled(true);
                nomOb.setText("");
       moyOb.setText("");
        ajtOb.setEnabled(true);
    }//GEN-LAST:event_suppObActionPerformed

    private void effObActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_effObActionPerformed
      //comboOb.setSelectedIndex(-1);
      comboOb.setEnabled(true);
       nomOb.setText("");
       moyOb.setText("");
        ajtOb.setEnabled(true);
    }//GEN-LAST:event_effObActionPerformed

    private void EffEtBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EffEtBtnActionPerformed
        txtNumEt.setText("");
        txtNomEt.setText("");
        txtMoyEt.setText("");
        ajoutEtbtn.setEnabled(true);
    }//GEN-LAST:event_EffEtBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainEt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainEt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainEt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainEt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //Source properties
        FlatLaf.registerCustomDefaultsSource("flatlaf");
        //Noir
        FlatMacLightLaf.setup();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainEt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EffEtBtn;
    private javax.swing.JButton SuppEtBtn;
    private javax.swing.JButton ajoutEtbtn;
    private javax.swing.JButton ajtOb;
    private front.TextField.TextField classemoy;
    private front.Combobox.ComboBox comboOb;
    private javax.swing.JButton effOb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane1;
    private front.TextField.TextField maxmoy;
    private front.TextField.TextField minmoy;
    private javax.swing.JButton modEtbtn;
    private front.TextField.TextField moyOb;
    private front.TextField.TextField nomOb;
    private javax.swing.JPanel pnlCard1;
    private javax.swing.JPanel pnlCard2;
    private javax.swing.JPanel pnlcards;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JScrollPane scrollOb;
    private javax.swing.JButton suppOb;
    private javax.swing.JTable table;
    private javax.swing.JTable tableOb;
    private front.TextField.TextField txtMoyEt;
    private front.TextField.TextField txtNomEt;
    private front.TextField.TextField txtNumEt;
    // End of variables declaration//GEN-END:variables
}
