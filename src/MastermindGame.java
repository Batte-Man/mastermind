package newpackage1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicComboBoxEditor;

// Jeu Mastermind Examen 8 Juin 2023 Principes algorithmiques et programmation  Martin BUCHET

public class MastermindGame extends JFrame implements ActionListener {
    
    // Déclaration de Variables & Jcomponents
    
    private static final int LONGUEUR_CODE = 4;
    private static final int ESSAIS_MAX = 10;

    private final JComboBox[] boxEntrée;
    private final JButton boutonValider;
    private final JButton boutonReset;
    private final JButton boutonRegles;
    private final JButton boutonScore0;
    
    private final JLabel scoreLabel;
    private final JLabel feedbackLabel1;
    private final JLabel feedbackLabel2;
    private final JLabel feedbackLabel3;
    
    private final JLabel labelTitre;
    private final JLabel titreHistorique;
    private final JLabel[] labelsHistorique;

    private String[] codeSecret;
    private int essaisRestants;
    private int essaisPrecedent;
    private int score = 0;

    // Constructeur de la frame de jeu
    
    public MastermindGame() {
        
        setTitle("Mon Mastermind");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        getContentPane().setBackground(new Color(123, 50, 250));
        
        // Panel jeu
        
        JPanel panelJeu = new JPanel(new BorderLayout());
        JPanel gridPanelJeu = new JPanel(new GridLayout(1, LONGUEUR_CODE,10,0));
        
        panelJeu.setBorder(BorderFactory.createLineBorder(new Color(123, 50, 250),9));
        panelJeu.add(gridPanelJeu, BorderLayout.CENTER);
        
        gridPanelJeu.setBorder(BorderFactory.createLineBorder(new Color(123, 50, 250),9));
        gridPanelJeu.setBackground(new Color(123,50,250));
        
        /*  crée chacun des 4 ComboBox 
        -> boucle for crée chaque combobox à l'identique en les mettant dans l'array 
        */
        
        String[] couleurs = {"Rouge", "Vert", "Bleu", "Jaune", "Orange", "Mauve"};
        
        boxEntrée = new JComboBox[LONGUEUR_CODE];
        
        for (int i = 0; i < LONGUEUR_CODE; i++) {
            boxEntrée[i] = new JComboBox(couleurs);
            boxEntrée[i].setFont(new Font("Comic sans", Font.BOLD, 35));
            boxEntrée[i].setForeground(Color.yellow);
            boxEntrée[i].setBackground(new Color(123,50,250));
            boxEntrée[i].setFocusable(false);
            boxEntrée[i].setEditor(new BasicComboBoxEditor());
            gridPanelJeu.add(boxEntrée[i]);
        }
        
        // bouton valider
        
        boutonValider = new JButton("Valider");
        boutonValider.setFont(new Font("Comic sans", Font.BOLD, 35));
        boutonValider.addActionListener(this);
        boutonValider.setBackground(new Color(255,255,255));
        boutonValider.setForeground(new Color(123,50,250));
        boutonValider.setFocusable(false);
        
        // bouton Reset
        
        boutonReset = new JButton("RESET");
        boutonReset.setFont(new Font("Comic sans", Font.BOLD, 35));
        boutonReset.addActionListener(this);
        boutonReset.setBackground(new Color(255,0,0));
        boutonReset.setForeground(new Color(255,255,255));
        boutonReset.setFocusable(false);
        
        // bouton regles du jeu
        
        boutonRegles = new JButton("?");
        
        boutonRegles.setFont(new Font("Comic sans", Font.BOLD, 35));
        boutonRegles.addActionListener(this);
        boutonRegles.setBackground(Color.YELLOW);
        boutonRegles.setForeground(new Color(123,50,250));
        boutonRegles.setFocusable(false);
        
        //bouton score à 0
        
        boutonScore0 = new JButton("Score à 0");
        boutonScore0.setFont(new Font("Comic sans", Font.BOLD, 15));
        boutonScore0.addActionListener(this);
        boutonScore0.setBackground(Color.YELLOW);
        boutonScore0.setForeground(new Color(123,55,255));
        boutonScore0.setFocusable(false);
        
        // Labels Feedback 
        
        feedbackLabel1 = new JLabel("Entrez votre proposition et cliquez sur Valider.", SwingConstants.CENTER);
        feedbackLabel1.setFont(new Font("Comic sans", Font.BOLD, 26));
        feedbackLabel1.setForeground(new Color(255, 255, 255));
        
        feedbackLabel2 = new JLabel("", SwingConstants.CENTER);
        feedbackLabel2.setFont(new Font("Comic sans", Font.BOLD, 26));
        feedbackLabel2.setForeground(new Color(255, 255, 255));
        
        feedbackLabel3 = new JLabel("", SwingConstants.CENTER);
        feedbackLabel3.setFont(new Font("Comic sans", Font.BOLD, 26));
        feedbackLabel3.setForeground(Color.GREEN);
        
        // Label du Titre
        
        labelTitre = new JLabel("MasterMind", SwingConstants.CENTER);
        labelTitre.setFont(new Font("Comic sans", Font.BOLD,100));
        labelTitre.setForeground(new Color(255, 255, 255));
        
        // Labels de l'historiques (montre l'historique des essais)
        
        JPanel panelHistorique = new JPanel(new BorderLayout());
        
        JPanel panelLabelsHistorique = new JPanel(new GridLayout(ESSAIS_MAX, 1, 0, 0));
        
        titreHistorique = new JLabel("   Essais   ", SwingConstants.CENTER);
        titreHistorique.setFont(new Font("Comic sans", Font.BOLD, 35));
        titreHistorique.setForeground(new Color(255, 255, 255));
        
        // Idem que pour boxEntree (labels historique de chaque essai)
        
        labelsHistorique = new JLabel[ESSAIS_MAX];
        
        for (int i = 0; i < ESSAIS_MAX; i++) {
            labelsHistorique[i] = new JLabel("", SwingConstants.CENTER);
            labelsHistorique[i].setFont(new Font("Comic sans", Font.BOLD, 15));
            labelsHistorique[i].setForeground(Color.YELLOW);
            labelsHistorique[i].setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255),1));
            panelLabelsHistorique.add(labelsHistorique[i]);
        }
        
        // label score
        
        scoreLabel = new JLabel();
        scoreLabel.setText("    "+ score + "    ");
        scoreLabel.setFont(new Font("Comic sans", Font.BOLD, 25));
        scoreLabel.setForeground(Color.YELLOW);
        
        TitledBorder border = BorderFactory.createTitledBorder("SCORE");
        
        border.setTitleColor( Color.WHITE );
        border.setTitleColor(new Color(255, 255, 255));
        border.setTitleFont(new Font("Comic sans", Font.BOLD, 12));
        scoreLabel.setBorder( border );
        
        // Panel feedback
        
        JPanel feedbackPanel = new JPanel(new GridLayout(3,1,0,0));
        feedbackPanel.add(feedbackLabel2);
        feedbackPanel.add(feedbackLabel1);
        feedbackPanel.add(feedbackLabel3);
        feedbackPanel.setBackground(new Color(123, 50, 250));
        
        // Panel Boutons
        
        JPanel panelBouton = new JPanel(new FlowLayout());
        panelBouton.add(boutonValider);
        panelBouton.setBackground(new Color(123, 50, 250));
        
        // Panel Historique
        
        panelLabelsHistorique.setBackground(new Color(123, 50, 250));
        panelHistorique.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255),2));
        panelHistorique.setBackground(new Color(123, 50, 250));
        panelHistorique.add(titreHistorique, BorderLayout.NORTH);
        panelHistorique.add(panelLabelsHistorique, BorderLayout.CENTER);
        
        // Top Panel (panels jeu & bouton)
        
        JPanel topPanel = new JPanel(new BorderLayout());
        
        topPanel.add(gridPanelJeu, BorderLayout.CENTER);
        topPanel.add(panelBouton, BorderLayout.EAST);
        
        // Main Panel (Top panel & Feedback Label)

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(feedbackPanel, BorderLayout.CENTER);
        
        // Panel Barre outils
        
        JPanel panelBarreOutils = new JPanel(new BorderLayout());
        JPanel panelBarreOutilsCenter = new JPanel();
        JPanel panelBarreOutilsNorth = new JPanel(new FlowLayout());
        
        panelBarreOutilsCenter.setLayout(new BoxLayout(panelBarreOutilsCenter, BoxLayout.Y_AXIS));
        
        boutonRegles.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonScore0.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Box layout setup
        
        panelBarreOutilsCenter.add(Box.createRigidArea(new Dimension(0, 15)));
        panelBarreOutilsCenter.add(boutonScore0);
        panelBarreOutilsCenter.add(Box.createRigidArea(new Dimension(0, 500)));
        panelBarreOutilsCenter.add(boutonRegles);
        panelBarreOutilsNorth.add(scoreLabel);
        
        panelBarreOutilsCenter.setBackground(new Color(123,50,250));
        panelBarreOutilsNorth.setBackground(new Color(123,50,250));
        
        panelBarreOutils.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255),2));
        
        panelBarreOutils.add(panelBarreOutilsNorth, BorderLayout.NORTH);
        panelBarreOutils.add(panelBarreOutilsCenter, BorderLayout.CENTER);
        panelBarreOutils.add(boutonReset, BorderLayout.SOUTH);
        panelBarreOutils.setBackground(new Color(123, 50, 250));
        
        // Fenêtre (Main Panel, History Panel, Title Label

        add(mainPanel, BorderLayout.CENTER);
        add(panelHistorique, BorderLayout.WEST);
        add(labelTitre, BorderLayout.NORTH);
        add(panelBarreOutils, BorderLayout.EAST);
        
        ImageIcon logo = new ImageIcon("logo.png");
        setIconImage(logo.getImage());
        pack();
        setSize(1280, 900);
        setLocationRelativeTo(null);
        setVisible(true);

        nouvellePartie();
    }
    
    // Méthodes

    private void nouvellePartie() {
        // Initialise le jeu
        
        codeSecret = genereCodeSecret();
        essaisRestants = ESSAIS_MAX;
        essaisPrecedent = 0;
        
        for(int i=0; i<ESSAIS_MAX; i++){    
            labelsHistorique[i].setText("");    
        }
        feedbackLabel1.setText("Et cliquez sur Valider ! ! !");
        feedbackLabel2.setText("Choisissez les bonnes couleurs !");
        feedbackLabel3.setText("Tentatives restantes: " + essaisRestants);
        activationBox(true);
    }

    private String[] genereCodeSecret() {
        // Génère le code secret
        
        String[] codeCouleurs = {"Rouge", "Vert", "Bleu", "Jaune", "Orange", "Mauve"};
        Random random = new Random();
        String[] code = new String[LONGUEUR_CODE];
        
        for (int i = 0; i < LONGUEUR_CODE; i++) {
            int index = random.nextInt(codeCouleurs.length);
            code[i] = codeCouleurs[index];
        }
        return code;
    }

    private void activationBox(boolean active) {
        // active/désactives les box/bouton valider
        for (JComboBox champ : boxEntrée) {
            champ.setEnabled(active);
        }
        boutonValider.setEnabled(active);
    }

    private void verificationEssai() {
        // vérifie que l'essai correct -> gagné !
        String[] essai = new String[LONGUEUR_CODE];
        
        for (int i = 0; i < LONGUEUR_CODE; i++) {
            essai[i] = boxEntrée[i].getSelectedItem().toString();
        }
        
        int positionsCorrectes = 0;
        int couleursCorrectes = 0;

        for (int i = 0; i < LONGUEUR_CODE; i++) {
            
            if (essai[i].equals(codeSecret[i])) {
                positionsCorrectes++;
            } else if (Arrays.asList(codeSecret).contains(essai[i])) {
                couleursCorrectes++;
            }
        }

        if (positionsCorrectes == LONGUEUR_CODE) {
            
            String historyString = Arrays.toString(essai) + "   V: " + positionsCorrectes + "  C: " + couleursCorrectes;
            
            labelsHistorique[essaisPrecedent].setText(historyString);
            
            feedbackLabel1.setText("Félicitations! Vous avez deviné le code secret!");
            feedbackLabel1.setFont(new Font("Comics sans", Font.BOLD, 40));
            feedbackLabel1.setForeground(Color.YELLOW);
            
            ImageIcon artifice = new ImageIcon("artifice.png");
            feedbackLabel1.setIcon(artifice);
            
            score += essaisRestants*essaisRestants*essaisRestants;
            
            activationBox(false);
            
        } else {
            essaisRestants--;
            if (essaisRestants == 0) {
                
                feedbackLabel1.setText("Game over !");
                feedbackLabel1.setForeground(Color.RED);
                feedbackLabel1.setFont(new Font("Comics sans", Font.BOLD, 40));
                feedbackLabel3.setText(" Le code secret était: " + Arrays.toString(codeSecret));
                feedbackLabel3.setForeground(Color.YELLOW);
                
                ImageIcon triste = new ImageIcon("triste.png");
                feedbackLabel1.setIcon(triste);
                
            
                activationBox(false);
                
                score -= 15;
                
            } else {
                
                if(positionsCorrectes != 0){
                score += 2 * positionsCorrectes;
                }
                
                if(couleursCorrectes != 0){
                score += couleursCorrectes;
                }
                
                String historyString = Arrays.toString(essai) + "   V: " + positionsCorrectes + "  C: " + couleursCorrectes;
                labelsHistorique[essaisPrecedent].setText(historyString);
                
                feedbackLabel1.setText("Tentative incorrecte !");
                feedbackLabel1.setFont(new Font("Comics sans", Font.BOLD, 40));
                feedbackLabel1.setForeground(Color.orange);
                
                feedbackLabel2.setText("Bonnes positions (V): " + positionsCorrectes +
                        ". Mauvaises position (C): " + couleursCorrectes);
                
                if (essaisRestants > 5){
                    feedbackLabel3.setText(" Tentatives restantes: " + essaisRestants);
                    feedbackLabel3.setForeground(Color.GREEN);
                } else if (essaisRestants >3){
                    feedbackLabel3.setText(" Tentatives restantes: " + essaisRestants);
                    feedbackLabel3.setForeground(Color.YELLOW);
                } else {
                    feedbackLabel3.setText(" Tentatives restantes: " + essaisRestants);
                    feedbackLabel3.setForeground(Color.RED);
                }
            }
        }
        essaisPrecedent++;
        scoreLabel.setText("    "+ score + "    ");
    }
    
    // ActionEvents
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonValider) {
            verificationEssai();
        }
        if (e.getSource() == boutonReset) {
            nouvellePartie();
            feedbackLabel1.setForeground(new Color(255, 255, 255));
            feedbackLabel1.setIcon(null);
            feedbackLabel3.setForeground(Color.GREEN);
        }
        if (e.getSource() == boutonRegles){
            JOptionPane.showMessageDialog(null, 
                    "Voici les règles du jeu: Trouvez le code secret à l'aide des indices donnés "
                    + "(positions et couleurs correctes) et de l'historique de vos tentatives, "
                    + "attention il peut y avoir plusieurs fois la même couleur !!!"
                    ,"Règles",JOptionPane.INFORMATION_MESSAGE);
        }
        if (e.getSource() == boutonScore0) {
            score = 0;
            scoreLabel.setText("    " + score + "    ");
        }
    }
    
    // Méthode main

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MastermindGame::new);
    }
}