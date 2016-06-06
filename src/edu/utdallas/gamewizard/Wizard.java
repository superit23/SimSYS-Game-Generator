/*This tool was created by Ryan Naugle and David Savoia.
 * 
 * The tool can currently run only Milwaukee Public Schools
 * 4th Grade Math.  This is due to the fact that the repository
 * needs to be added to.  Specifically their learning objectives.
 * The tool will create an XML file that can be ran on the 
 * preview tool.
 * 
 * WizardRepo Folder is used for importing XML files and png files
 * 
 */

package edu.utdallas.gamewizard;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.*;
import javax.swing.tree.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.SAXException;

import simsys.schema.components.*;
import edu.utdallas.gamegeneratorcollection.GameOutput.GameExport;
import edu.utdallas.old.SimSYSGamePlatform;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class Wizard implements ActionListener {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 700;

    private JFrame window = new JFrame();
    private JTree wizardTree;
    private JPanel treePanel;
    private WizardPanel mainPanel;

    private final JComboBox<String> institutionBox;
    private JComboBox<String> domainBox;
    private final JComboBox<String> gradeBox;
    private final JComboBox<String> subjectBox;
    private final JComboBox<String> taxBox;
    private final JComboBox<String> backgroundBox;
    private final JComboBox<String> actBox;

    private ArrayList<JComboBox<String>> gameCreationBoxes = new ArrayList<JComboBox<String>>();

    private JComboBox<String>[] firstQuesBox;
    private JComboBox<String>[] secondQuesBox;

    private String[] institutions;
    private String[] domains;
    private String[] grades;
    private String[] subjects;

    public KnowledgeRepo knowRepo;

    final DefaultTreeModel model;
    final DefaultMutableTreeNode mainRoot;
    final DefaultMutableTreeNode rootNode;
    final DefaultMutableTreeNode learningObjectiveNode;
    final DefaultMutableTreeNode learningTaxonomyNode;
    final DefaultMutableTreeNode subTaxNode;
    final DefaultMutableTreeNode typeOfChallengeNode;
    final DefaultMutableTreeNode conditionsNode;
    final DefaultMutableTreeNode lowerSummaryNode;
    final DefaultMutableTreeNode topNode;
    final DefaultMutableTreeNode characterNode;
    final DefaultMutableTreeNode locationNode;
    final DefaultMutableTreeNode actNode;
    final DefaultMutableTreeNode addActNode;
    final DefaultMutableTreeNode actSumNode;
    final DefaultMutableTreeNode loActNode;
    final DefaultMutableTreeNode quesTableNode;
    final DefaultMutableTreeNode finalSumNode;
    final DefaultMutableTreeNode conclusionNode;
    DefaultMutableTreeNode[] learningObjectives;

    // Initial introduction button
    private final JButton welcomeButton;

    // Controls Text That Prompts User What To Do
    private final JTextArea speechBubble;

    // After User Selects Domain
    private final JButton submitButton;

    // After User Selects Knowledge Area
    private final JButton subLOButton;

    // After User Selects sub-Learning Objectives
    private final JButton submitLOButton;

    // Button for learning objective summary
    private final JButton summaryContinue;

    // If the user has errors in learning objective selection
    private final JButton summaryBack;

    // Learning Taxonomy Button
    private final JButton subTaxContButton;

    // If the user has errors in learning taxonomy selection
    private final JButton taxBackButton;

    // Condition button
    private final JButton conditionContinue;

    // IF the user has errors in the condition selection
    private final JButton conditionErrorBack;

    // Challeng selection button
    private final JButton challengeButton;

    // If the user has errors in challenge selection
    private final JButton challengeErrorBack;

    // Summary button of lower half of selection
    private final JButton fullSumContinue;

    // The next introduction page button
    private final JButton introTwoButton;

    // Learning Taxonomy Continue Button
    private final JButton taxContButton;

    // Button where user selects theme
    private final JButton locationButton;

    // Button where user is notified of common characters for theme
    private final JButton charButton;

    // Shows user initial set of acts
    private final JButton actButton;

    // Allows user to add acts/questions
    private final JButton addActButton;

    // After user finishes adding acts/questions
    private final JButton contActButton;

    // Allows user to choose what learning objectives go with each act they make
    private final JButton loActButton;

    // User Selects Type of Questions
    private final JButton quesButton;

    // Shows User Final Summary
    private final JButton finalSumButton;

    // Allows user to save their game
    private final JButton saveButton;

    // Error Button if Subject Not In Repository
    private final JButton repoErrorButton;

    boolean bloomSelected;
    boolean learningError;

    private JScrollPane bloomsScroll;
    private JScrollPane templateScroll;

    private JTextPane taxPane;
    private JTextPane finalSumPane;
    private JTextPane backgroundPane;
    private JTextPane summary;
    private JTextPane template;

    // Used for determining the number of progress acts user selects
    int progressActs = 0;

    // List of learning objectives
    ArrayList<String> loList;

    // file path
    String pathName;

    // Background the user selects
    String backGroundChoice;

    // Fonts used throughout
    private final Font font = new Font("Comic Sans MS", Font.CENTER_BASELINE, 28);
    private final Font font2 = new Font("Comic Sans MS", Font.CENTER_BASELINE, 22);
    private final Font font3 = new Font("Comic Sans MS", Font.CENTER_BASELINE, 18);
    private final Font font4 = new Font("Comic Sans MS", Font.PLAIN, 18);

    // This is used for importing the first set of acts user sees
    private GameTemplate gametemplate;

    // This is used for importing the knowledge areas
    private AreaSelection areaselection;

    // Knowledge Areas Table
    JTable table;
    // Learning Objective Table
    JTable loTable;
    // Conditions Table
    JTable conditionsTable;
    // Taxonomy Table
    JTable subTaxTable;
    // Challenge Table
    JTable challengeTable;
    // Character Table
    JTable charTable;
    // Learning Objective for Each Progress Table
    JTable loActTable;
    // Question Selection Table
    JTable questionTable;

    // Used for importing characters for environment
    CharList charList;

    public Wizard() {
        window.setSize(WIDTH, HEIGHT);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setTitle("Game Design");

        mainPanel = new WizardPanel("introBackground2.png", 0, 0);
        treePanel = new JPanel();

        mainPanel.setLayout(null);

        // Begin Making Tree
        wizardTree = new JTree();
        mainRoot = new DefaultMutableTreeNode("Start/Introduction");
        model = new DefaultTreeModel(mainRoot);

        wizardTree.setRootVisible(false);
        wizardTree.setModel(model);

        rootNode = new DefaultMutableTreeNode("Start/Introduction");
        learningObjectiveNode = new DefaultMutableTreeNode("Knowledge Areas");
        learningTaxonomyNode = new DefaultMutableTreeNode("Learning Standard");
        subTaxNode = new DefaultMutableTreeNode("Select Taxonomies");
        typeOfChallengeNode = new DefaultMutableTreeNode("Type of Challenge");
        conditionsNode = new DefaultMutableTreeNode("Conditions");
        lowerSummaryNode = new DefaultMutableTreeNode("Summary");
        topNode = new DefaultMutableTreeNode("Game Components");
        locationNode = new DefaultMutableTreeNode("Location");
        characterNode = new DefaultMutableTreeNode("Typical Characters");
        actNode = new DefaultMutableTreeNode("First Acts");
        addActNode = new DefaultMutableTreeNode("Add An Act/Acts");
        actSumNode = new DefaultMutableTreeNode("Act Summary");
        loActNode = new DefaultMutableTreeNode("Specify Acts");
        quesTableNode = new DefaultMutableTreeNode("Question Selection");
        finalSumNode = new DefaultMutableTreeNode("Final Summary");
        conclusionNode = new DefaultMutableTreeNode("Save Game");

        treePanel.add(wizardTree);
        treePanel.setBackground(java.awt.Color.white);

        // Pane for Summaries
        finalSumPane = new JTextPane();
        taxPane = new JTextPane();

        // Importing for user acts and knowledge areas
        try {
            File file = new File("WizardRepo\\Templates/template.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(GameTemplate.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            gametemplate = (GameTemplate) jaxbUnmarshaller.unmarshal(file);

            File file2 = new File("WizardRepo\\LearningAreas/areaSelection.xml");
            JAXBContext jaxbContext2 = JAXBContext.newInstance(AreaSelection.class);
            Unmarshaller jaxbUnmarshaller2 = jaxbContext2.createUnmarshaller();
            areaselection = (AreaSelection) jaxbUnmarshaller2.unmarshal(file2);

        } catch (Exception e2) {
            e2.printStackTrace();
        }


        // Main tree listener for each node
        wizardTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            @SuppressWarnings("deprecation")
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) wizardTree.getLastSelectedPathComponent();

                // Root Node is initial selection of subjects node
                if (selectedNode != null && selectedNode == rootNode) {

//                    mainPanel.removeAll();
//
//                    mainPanel.changeCoord(0, 0);
//
//                    speechBubble.setFont(font);
//                    speechBubble.setBounds(400, 100, 420, 120);
//                    speechBubble.setText("   Who is This Game For?");

                    rebuildGameCreationBoxes();

//                    institutionBox.setBounds(625, 300, 425, 50);
//                    institutionBox.setFont(font2);
//                    institutionBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
//                    mainPanel.add(institutionBox);
//
//                    domainBox.setBounds(625, 375, 425, 50);
//                    domainBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
//                    domainBox.setFont(font2);
//                    domainBox.setEnabled(false);
//                    mainPanel.add(domainBox);
//
//                    gradeBox.setBounds(625, 450, 425, 50);
//                    gradeBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
//                    gradeBox.setFont(font2);
//                    gradeBox.setEnabled(false);
//                    mainPanel.add(gradeBox);
//
//                    subjectBox.setBounds(625, 525, 425, 50);
//                    subjectBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
//                    subjectBox.setFont(font2);
//                    subjectBox.setEnabled(false);
//                    mainPanel.add(subjectBox);

//                    submitButton.setFont(font2);
//                    submitButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
//
//                    mainPanel.add(speechBubble);
//                    mainPanel.add(submitButton);
//
//                    mainPanel.changeFileName("introBackground2.png");
//                    mainPanel.updateUI();

                } else if (selectedNode != null && selectedNode == learningObjectiveNode) {

                    mainPanel.removeAll();

                    mainPanel.changeFileName("wizardBackground.png");
                    mainPanel.changeCoord(0, -70);

                    speechBubble.setFont(font);
                    speechBubble.setBounds(415, 45, 400, 90);
                    speechBubble.setText("Please Select Your Knowledge\n Areas and Continue");

                    JScrollPane scroll = JTable.createScrollPaneForTable(table);
                    scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                    scroll.setBounds(100, 300, 950, 350);

                    subLOButton.setFont(font2);

                    mainPanel.add(scroll);
                    mainPanel.add(speechBubble);
                    mainPanel.add(submitLOButton);

                    mainPanel.updateUI();

                } else if (selectedNode != null && selectedNode == learningTaxonomyNode) {

                    if (isErrorLearningObjective()) {
                        printErrorLearningObjective();

                    } else if (isErrorLO()) {
                        printErrorLO();

                    } else {

                        mainPanel.removeAll();

                        speechBubble.setFont(font);
                        speechBubble.setBounds(347, 65, 400, 78);
                        speechBubble.setText("     Please Select Your\n     Learning Taxonomy");

                        bloomsScroll = new JScrollPane(taxPane);
                        taxPane.setCaretPosition(0);

                        bloomsScroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                        bloomsScroll.setBounds(70, 300, 400, 260);

                        mainPanel.add(bloomsScroll);
                        mainPanel.add(taxBox);
                        mainPanel.add(speechBubble);

                        mainPanel.changeFileName("learningTaxonomy.png");
                        mainPanel.changeCoord(-100, 0);

                        treePanel.add(wizardTree);
                        mainPanel.add(taxContButton);

                        mainPanel.updateUI();
                    }
                } else if (selectedNode != null && selectedNode == subTaxNode) {

                    if (isErrorLearningObjective()) {
                        printErrorLearningObjective();
                        return;

                    } else if (taxBox.getSelectedIndex() == 0) {
                        printErrorLearningTaxonomy();
                        return;

                    } else if (isErrorLO()) {
                        printErrorLO();

                    } else {

                        mainPanel.removeAll();

                        mainPanel.changeFileName("wizardBackground.png");
                        mainPanel.changeCoord(0, -70);

                        JScrollPane scroll = JTable.createScrollPaneForTable(subTaxTable);
                        scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                        scroll.setBounds(85, 300, 950, 260);

                        speechBubble.setFont(font);
                        speechBubble.setBounds(415, 45, 400, 90);
                        speechBubble.setText("      Please Select Type\n         Taxonomies");

                        mainPanel.add(subTaxContButton);
                        mainPanel.add(speechBubble);
                        mainPanel.add(scroll);

                        mainPanel.updateUI();
                        //mainPanel.updateUI();
                    }
                } else if (selectedNode != null && selectedNode == typeOfChallengeNode) {

                    if (isErrorLearningObjective()) {
                        printErrorLearningObjective();
                    } else if (isErrorLO()) {
                        printErrorLO();
                    } else if (taxBox.getSelectedIndex() == 0) {
                        printErrorLearningTaxonomy();
                        return;
                    } else {

                        mainPanel.changeFileName("wizardBackground.png");
                        mainPanel.changeCoord(0, -70);

                        mainPanel.removeAll();

                        Vector<Vector<Object>> vector = generateChallengeTable();

                        challengeTable = new JTable(new MyTableModel(vector, false, false, false));
                        challengeTable.setRowHeight(45);
                        challengeTable.setShowHorizontalLines(true);
                        challengeTable.setRowSelectionAllowed(true);
                        challengeTable.setColumnSelectionAllowed(true);
                        challengeTable.getColumnModel().getColumn(1).setMaxWidth(75);
                        challengeTable.getTableHeader().setFont(font3);
                        challengeTable.setFont(font2);
                        challengeTable.setGridColor(java.awt.Color.black);

                        JScrollPane scroll = JTable.createScrollPaneForTable(challengeTable);
                        scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                        scroll.setBounds(85, 300, 950, 260);

                        challengeButton.setFont(font2);
                        challengeButton.setBounds(650, 225, 150, 50);
                        challengeButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                        challengeButton.setEnabled(true);

                        // place button here
                        speechBubble.setFont(font);
                        speechBubble.setBounds(415, 45, 400, 90);
                        speechBubble.setText("      Please Select Type\n         of Challenge");

                        mainPanel.add(challengeButton);
                        mainPanel.add(scroll);
                        mainPanel.add(speechBubble);

                        treePanel.updateUI();
                        mainPanel.updateUI();
                    }
                } else if (selectedNode != null && selectedNode == conditionsNode) {

                    if (isErrorLearningObjective()) {
                        printErrorLearningObjective();

                    } else if (isErrorChallenge()) {
                        printErrorChallenge();

                    } else if (isErrorLO()) {
                        printErrorLO();

                    } else if (taxBox.getSelectedIndex() == 0) {
                        printErrorLearningTaxonomy();
                        return;

                    } else {

                        mainPanel.removeAll();

                        speechBubble.setFont(font);
                        speechBubble.setBounds(415, 45, 400, 90);
                        speechBubble.setText("      Please Select Your\n        Game Conditions");

                        mainPanel.changeFileName("wizardBackground.png");
                        mainPanel.changeCoord(0, -70);

                        JScrollPane scroll = JTable.createScrollPaneForTable(conditionsTable);
                        scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                        scroll.setBounds(85, 300, 950, 260);

                        conditionContinue.setFont(font2);

                        mainPanel.add(conditionContinue);
                        mainPanel.add(scroll);
                        mainPanel.add(speechBubble);

                        mainPanel.updateUI();
                    }
                }
                // First summary of all lower level selection
                else if (selectedNode != null && selectedNode == lowerSummaryNode) {

                    mainPanel.removeAll();

                    if (isErrorChallenge()) {
                        printErrorChallenge();
                        mainPanel.updateUI();


                    } else if (isErrorLearningObjective()) {
                        printErrorLearningObjective();
                        mainPanel.updateUI();


                    } else if (isErrorLO()) {
                        printErrorLO();
                        mainPanel.updateUI();


                    } else {
                        generateSummary();
                        mainPanel.updateUI();


                    }

                } else if (selectedNode != null && selectedNode == locationNode) {

                    mainPanel.removeAll();

                    speechBubble.setFont(font);
                    speechBubble.setBounds(347, 65, 400, 78);
                    speechBubble.setText("     Please Select Your\n        Location");

                    mainPanel.changeFileName("learningTaxonomy.png");
                    mainPanel.changeCoord(-100, 0);

                    treePanel.add(wizardTree);

                    mainPanel.add(speechBubble);
                    mainPanel.add(backgroundBox);
                    mainPanel.add(backgroundPane);
                    mainPanel.add(locationButton);

                    mainPanel.updateUI();

                } else if (selectedNode != null && selectedNode == characterNode) {
                    if (isLocationError()) {
                        printLocationError();
                    } else {

                        mainPanel.removeAll();

                        JScrollPane scroll = JTable.createScrollPaneForTable(charTable);
                        scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                        scroll.setBounds(85, 300, 950, 260);

                        speechBubble.setFont(font);
                        speechBubble.setBounds(415, 45, 400, 90);
                        speechBubble.setText("      Here are Your\n       Typical Characters!");

                        charButton.setBounds(715, 225, 150, 50);
                        charButton.setFont(font2);

                        mainPanel.add(scroll);
                        mainPanel.changeFileName("wizardBackground.png");
                        mainPanel.changeCoord(0, -70);

                        mainPanel.add(speechBubble);
                        mainPanel.add(charButton);

                        treePanel.updateUI();
                        mainPanel.updateUI();
                        //mainPanel.updateUI();
                    }
                } else if (selectedNode != null && selectedNode == actNode) {
                    if (isLocationError()) {
                        printLocationError();
                    } else {

                        mainPanel.removeAll();
                        mainPanel.changeFileName("wizardBackground.png");
                        mainPanel.changeCoord(0, -70);

                        template = new JTextPane();

                        showActs();

                        templateScroll = new JScrollPane(template);
                        template.setCaretPosition(0);
                        templateScroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                        templateScroll.setBounds(180, 300, 720, 330);
                        template.updateUI();
                        templateScroll.updateUI();

                        speechBubble.setFont(font2);
                        speechBubble.setBounds(425, 30, 345, 100);
                        speechBubble.setText("     Let's Begin With Creating\n         The First Acts of\n             Your Game!");

                        actButton.setText("Continue");
                        actButton.setFont(font2);
                        actButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                        actButton.setBounds(715, 225, 150, 50);
                        actButton.setVisible(true);

                        mainPanel.add(actButton);
                        mainPanel.add(speechBubble);
                        mainPanel.add(templateScroll);

                        mainPanel.updateUI();
                    }
                } else if (selectedNode != null && selectedNode == addActNode) {
                    if (isLocationError()) {
                        printLocationError();
                    } else {

                        mainPanel.removeAll();
                        mainPanel.changeFileName("wizardBackground.png");
                        mainPanel.changeCoord(0, 0);

                        speechBubble.setFont(font3);
                        speechBubble.setBounds(455, 100, 345, 105);
                        speechBubble.setText("There Are Two Choices To Change\n         Your Game:\n 1. Multiply The Previous Set-Up\n2. Add An Act To the Summary");

                        actBox.setFont(font2);

                        mainPanel.add(addActButton);
                        mainPanel.add(actBox);
                        mainPanel.add(speechBubble);

                        mainPanel.updateUI();
                    }
                } else if (selectedNode != null && selectedNode == actSumNode) {
                    if (isLocationError()) {
                        printLocationError();
                    } else {

                        mainPanel.removeAll();

                        mainPanel.changeFileName("wizardBackground.png");
                        mainPanel.changeCoord(0, -70);
                        template = new JTextPane();

                        showActs();

                        templateScroll = new JScrollPane(template);
                        template.setCaretPosition(0);

                        templateScroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                        templateScroll.setBounds(180, 300, 720, 330);

                        template.updateUI();
                        templateScroll.updateUI();

                        speechBubble.setFont(font2);
                        speechBubble.setBounds(425, 30, 345, 100);
                        speechBubble.setText("     Let's Begin With Creating\n         The First Acts of\n             Your Game!");

                        actButton.setText("Add More");
                        actButton.setFont(font2);
                        actButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                        actButton.setBounds(515, 225, 150, 50);
                        actButton.setVisible(true);
                        mainPanel.add(actButton);

                        contActButton.setText("Continue");
                        contActButton.setFont(font2);
                        contActButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                        contActButton.setBounds(715, 225, 150, 50);

                        mainPanel.add(contActButton);
                        mainPanel.add(speechBubble);
                        mainPanel.add(templateScroll);

                        mainPanel.updateUI();
                    }
                } else if (selectedNode != null && selectedNode == loActNode) {
                    if (isLocationError()) {
                        printLocationError();
                    } else {

                        mainPanel.removeAll();

                        mainPanel.changeFileName("wizardBackground.png");
                        mainPanel.changeCoord(0, -70);

                        speechBubble.setFont(font2);
                        speechBubble.setBounds(415, 45, 400, 90);
                        speechBubble.setText("    Please Select Which Learning\n      Objectives You Would Like\n            For Each Act!");

                        makeLOActTable();

                        JScrollPane scroll = JTable.createScrollPaneForTable(loActTable);
                        scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                        scroll.setBounds(40, 300, 1000, 350);

                        loActButton.setText("Continue");
                        loActButton.setFont(font2);
                        loActButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                        loActButton.setBounds(715, 225, 150, 50);

                        mainPanel.add(scroll);
                        mainPanel.add(speechBubble);
                        mainPanel.add(loActButton);

                        mainPanel.updateUI();
                    }
                } else if (selectedNode != null && selectedNode == quesTableNode) {
                    if (!checkLOActTable()) {
                        printLOActTableError();
                    } else if (isLocationError()) {
                        printLocationError();
                    } else {

                        mainPanel.removeAll();

                        mainPanel.changeFileName("wizardBackground.png");
                        mainPanel.changeCoord(0, -70);

                        speechBubble.setFont(font);
                        speechBubble.setBounds(415, 45, 400, 90);
                        speechBubble.setText("Please Select Your Types\n Of Questions!");

                        JScrollPane scroll = JTable.createScrollPaneForTable(questionTable);
                        scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                        scroll.setBounds(100, 300, 950, 350);

                        mainPanel.add(scroll);
                        mainPanel.add(speechBubble);
                        mainPanel.add(quesButton);

                        mainPanel.updateUI();
                    }
                } else if (selectedNode != null && selectedNode == finalSumNode) {
                    if (!checkLOActTable()) {
                        printLOActTableError();
                    } else if (isLocationError()) {
                        printLocationError();
                    } else {

                        mainPanel.removeAll();

                        mainPanel.changeFileName("wizardBackground.png");
                        mainPanel.changeCoord(0, -70);

                        generateFinalSum();

                        JScrollPane scroll = new JScrollPane(finalSumPane);
                        scroll.setBounds(180, 300, 720, 330);
                        scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                        wizardTree.expandRow(1);

                        speechBubble.setFont(font);
                        speechBubble.setBounds(415, 45, 400, 90);
                        speechBubble.setText("Here Is A Final Summary!");

                        mainPanel.add(scroll);
                        mainPanel.add(speechBubble);
                        mainPanel.add(finalSumButton);

                        mainPanel.updateUI();
                    }
                }

                // save screen
                else if (selectedNode != null && selectedNode == conclusionNode) {
                    if (!checkLOActTable()) {
                        printLOActTableError();
                    } else if (isLocationError()) {
                        printLocationError();
                    } else {

                        mainPanel.removeAll();

                        mainPanel.changeFileName("introBackground2.png");
                        mainPanel.changeCoord(0, 0);

                        speechBubble.setFont(font);
                        speechBubble.setBounds(400, 100, 420, 120);
                        speechBubble.setText("Congratulations You Are Done!\nPress Save To Save The Game");

                        mainPanel.add(speechBubble);
                        mainPanel.add(saveButton);
                        mainPanel.updateUI();
                    }
                }
            }
        });

        // Splits the wizard tree and the main panel
        JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, mainPanel);
        main.setDividerLocation(160);

        // Initializing subject selection boxes
        domains = new String[1];
        grades = new String[1];
        subjects = new String[1];

        domains[0] = "Please Choose Your Domain";
        grades[0] = "Please Choose Your Level";
        subjects[0] = "Please Choose Your Subject";

        // initializing and importing institutions
        institutions = new String[1 + areaselection.getInstitutions().size()];
        institutions[0] = "Please Choose Your Institution";

        for (int i = 1; i < 1 + areaselection.getInstitutions().size(); i++) {
            institutions[i] = areaselection.getInstitutions().get(i - 1).getName();
        }
        institutionBox = new JComboBox<String>(institutions);
        domainBox = new JComboBox<String>(domains);
        gradeBox = new JComboBox<String>(grades);
        subjectBox = new JComboBox<String>(subjects);

        // Add to gameCreationBoxes array for easy loop-through.
        gameCreationBoxes.add(institutionBox);
        gameCreationBoxes.add(domainBox);
        gameCreationBoxes.add(gradeBox);
        gameCreationBoxes.add(subjectBox);


        // Initial opening screen
        speechBubble = new JTextArea("  Welcome to the SimSYS Game \n            Design Tool! \n  Please Click Continue To Begin!");
        speechBubble.setFont(font);
        speechBubble.setEditable(false);
        speechBubble.setBounds(375, 80, 479, 140);

        mainPanel.add(speechBubble);

        // Initialization of bloom's screen
        String[] taxonomy = {"Please Choose Your Taxonomy", "Bloom's", "Wisconsin Standard"};
        taxBox = new JComboBox<String>(taxonomy);

        // Initialization of loList
        loList = new ArrayList<String>();

        // Initialization of backgroundBox
        String[] backgrounds = {"Please Choose A Location", "Classroom", "Enchanted Forest"};
        backgroundBox = new JComboBox<String>(backgrounds);

        // Initialization of add act box
        String[] actChoices = {"Keep The Current Set-Up", "Add Another Set of Acts", "Add a Question", "Back to Original"};

        // Initializing of buttons
        // Bounds sets the location of buttons and size
        submitButton = new JButton("Continue");
        submitButton.setVisible(false);
        submitButton.setEnabled(false);
        submitButton.setBounds(400, 475, 150, 50);

        submitLOButton = new JButton("Continue");
        submitLOButton.setVisible(true);
        submitLOButton.setEnabled(true);
        submitLOButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        submitLOButton.setFont(font2);
        submitLOButton.setBounds(650, 225, 150, 50);

        subLOButton = new JButton("Continue");
        subLOButton.setVisible(true);
        subLOButton.setEnabled(true);
        subLOButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        subLOButton.setBounds(650, 225, 150, 50);

        summaryContinue = new JButton();
        summaryBack = new JButton();

        welcomeButton = new JButton("Continue");
        welcomeButton.setVisible(true);
        welcomeButton.setEnabled(true);
        welcomeButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        welcomeButton.setFont(font2);
        welcomeButton.setBounds(550, 475, 150, 50);

        subTaxContButton = new JButton("Continue");
        taxContButton = new JButton("Continue");

        challengeButton = new JButton("Continue");
        mainPanel.add(welcomeButton);

        conditionContinue = new JButton("Continue");
        conditionContinue.setVisible(true);
        conditionContinue.setEnabled(true);
        conditionContinue.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        conditionContinue.setBounds(650, 225, 150, 50);

        conditionErrorBack = new JButton();

        fullSumContinue = new JButton("Continue");

        challengeErrorBack = new JButton("Back");
        challengeErrorBack.setFont(font2);
        challengeErrorBack.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

        taxBackButton = new JButton("Back");
        taxBackButton.setFont(font2);
        taxBackButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

        actButton = new JButton("Continue");
        actButton.setFont(font2);
        actButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        actButton.setBounds(715, 225, 150, 50);
        actButton.setVisible(true);

        introTwoButton = new JButton("Continue");
        introTwoButton.setVisible(true);
        introTwoButton.setEnabled(true);
        introTwoButton.setBounds(600, 475, 150, 50);
        introTwoButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

        charButton = new JButton("Continue");
        charButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        charButton.setBounds(715, 225, 150, 50);
        charButton.setFont(font2);

        actBox = new JComboBox<String>(actChoices);
        actBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        actBox.setBounds(400, 370, 425, 50);

        locationButton = new JButton("Continue");
        locationButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

        template = new JTextPane();
        template.setEditable(false);

        addActButton = new JButton("Continue");
        addActButton.setFont(font2);
        addActButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        addActButton.setBounds(550, 450, 150, 50);
        addActButton.setVisible(true);

        contActButton = new JButton("Continue");
        contActButton.setFont(font2);
        contActButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        contActButton.setBounds(715, 225, 150, 50);
        contActButton.setVisible(true);

        loActButton = new JButton("Continue");
        loActButton.setFont(font2);
        loActButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        loActButton.setBounds(715, 225, 150, 50);
        loActButton.setVisible(true);

        quesButton = new JButton("Continue");
        quesButton.setFont(font2);
        quesButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        quesButton.setBounds(650, 225, 150, 50);

        finalSumButton = new JButton("Continue");
        finalSumButton.setFont(font2);
        finalSumButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        finalSumButton.setBounds(650, 225, 150, 50);

        saveButton = new JButton("Save");
        saveButton.setFont(font2);
        saveButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        saveButton.setBounds(600, 475, 150, 50);

        repoErrorButton = new JButton("Back");
        repoErrorButton.setFont(font2);
        repoErrorButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        repoErrorButton.setBounds(650, 475, 150, 50);

        // Beginning of all listeners
        // Goes to Subject Selection Screen
        welcomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();

                mainPanel.changeCoord(0, 0);
                mainRoot.add(rootNode);

                DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                model.reload(mainRoot);

                wizardTree.expandRow(0);

                speechBubble.setFont(font);
                speechBubble.setBounds(400, 100, 420, 120);
                speechBubble.setText("   Who is This Game For?");

                institutionBox.setBounds(625, 300, 425, 50);
                institutionBox.setMaximumRowCount(5);
                institutionBox.setFont(font2);
                institutionBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                mainPanel.add(institutionBox);

                domainBox.setBounds(625, 375, 425, 50);
                domainBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                domainBox.setMaximumRowCount(5);
                domainBox.setFont(font2);
                domainBox.setEnabled(false);
                mainPanel.add(domainBox);

                gradeBox.setBounds(625, 450, 425, 50);
                gradeBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                gradeBox.setFont(font2);
                gradeBox.setMaximumRowCount(5);
                gradeBox.setEnabled(false);
                mainPanel.add(gradeBox);

                subjectBox.setBounds(625, 525, 425, 50);
                subjectBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                subjectBox.setFont(font2);
                subjectBox.setMaximumRowCount(5);
                subjectBox.setEnabled(false);

                submitButton.setFont(font2);
                submitButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                mainPanel.add(speechBubble);
                mainPanel.add(subjectBox);
                mainPanel.add(submitButton);

                mainPanel.changeFileName("introBackground2.png");
                mainPanel.updateUI();
            }
        });
        institutionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int i = institutionBox.getSelectedIndex();

                gradeBox.setEnabled(false);
                subjectBox.setEnabled(false);
                submitButton.setVisible(false);

                rootNode.removeAllChildren();

                model.reload();

                treePanel.add(wizardTree);
                treePanel.updateUI();

                // Error catch
                if (institutions[i] == institutions[0]) {

                    domainBox.setEnabled(false);
                    gradeBox.setEnabled(false);
                    submitButton.setVisible(false);
                    subjectBox.setEnabled(false);

                    mainPanel.changeFileName("errorBackground.png");
                    mainPanel.updateUI();

                    speechBubble.setText("Oops. Please Enter Institution");
                    return;
                }
                // Record Choice
                domains = new String[1 + areaselection.getInstitutions().get(i - 1).getDomains().size()];
                domains[0] = "Please Choose Your Domain";

                for (int j = 1; j < 1 + areaselection.getInstitutions().get(i - 1).getDomains().size(); j++) {
                    domains[j] = areaselection.getInstitutions().get(i - 1).getDomains().get(j - 1).getName();
                }
                domainBox.setModel(new JComboBox<>(domains).getModel());
                domainBox.setEnabled(true);

                speechBubble.setText("   Thanks. Who is This Game\n   For Specifically?");
                mainPanel.changeFileName("introBackground2.png");

                mainPanel.updateUI();
            }
        });
        domainBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int i = domainBox.getSelectedIndex();
                int j = institutionBox.getSelectedIndex();

                subjectBox.setEnabled(false);
                submitButton.setVisible(false);

                rootNode.removeAllChildren();

                model.reload();

                treePanel.add(wizardTree);
                treePanel.updateUI();

                // Error Checking
                if (domains[i] == domains[0]) {

                    gradeBox.setEnabled(false);
                    submitButton.setVisible(false);
                    subjectBox.setEnabled(false);

                    mainPanel.changeFileName("errorBackground.png");
                    mainPanel.updateUI();

                    speechBubble.setText("Oops. Please Enter Domain");
                    return;
                }

                // Record Choice
                grades = new String[1 + areaselection.getInstitutions().get(j - 1).getDomains().get(i - 1).getGrades().size()];
                grades[0] = "Please Choose Your Level";

                for (int k = 1; k < 1 + areaselection.getInstitutions().get(j - 1).getDomains().get(i - 1).getGrades().size(); k++) {
                    grades[k] = areaselection.getInstitutions()
                            .get(j - 1).getDomains()
                            .get(i - 1).getGrades()
                            .get(k - 1).getName();
                }
                gradeBox.setModel(new JComboBox<>(grades).getModel());
                gradeBox.setEnabled(true);

                speechBubble.setText("   Gotcha. What Level will \n   be playing this game?");

                mainPanel.changeFileName("introBackground2.png");
                mainPanel.updateUI();
            }
        });
        gradeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = domainBox.getSelectedIndex();
                int j = institutionBox.getSelectedIndex();
                int k = gradeBox.getSelectedIndex();

                submitButton.setVisible(false);

                rootNode.removeAllChildren();

                model.reload();

                treePanel.add(wizardTree);
                treePanel.updateUI();

                // Error Checking
                if (grades[k] == grades[0]) {

                    subjectBox.setEnabled(false);
                    submitButton.setVisible(false);

                    speechBubble.setText("   Oops. Please Enter Level");
                    mainPanel.changeFileName("errorBackground.png");

                    mainPanel.updateUI();
                    return;
                }

                // Record Choice
                subjects = new String[1 + areaselection.getInstitutions()
                        .get(j - 1).getDomains()
                        .get(i - 1).getGrades()
                        .get(k - 1).getSubjects().size()];

                subjects[0] = "Please Choose Your Level";

                for (int l = 1; l < 1 + areaselection.getInstitutions()
                        .get(j - 1).getDomains()
                        .get(i - 1).getGrades()
                        .get(k - 1).getSubjects().size(); l++) {

                    subjects[l] = areaselection.getInstitutions()
                            .get(j - 1).getDomains()
                            .get(i - 1).getGrades()
                            .get(k - 1).getSubjects()
                            .get(l - 1).getName();
                }
                subjectBox.setModel(new JComboBox<>(subjects).getModel());
                subjectBox.setEnabled(true);

                speechBubble.setText("   Sounds Good. What Subject \n   will this game teach?");

                mainPanel.changeFileName("introBackground2.png");
                mainPanel.updateUI();
            }
        });
        subjectBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = subjectBox.getSelectedIndex();

                rootNode.removeAllChildren();
                model.reload();

                treePanel.add(wizardTree);
                treePanel.updateUI();

                // If no errors
                if (subjects[i] != subjects[0]) {

                    // MOVE ON To next section
                    submitButton.setEnabled(true);
                    speechBubble.setText("   Thanks! Click the button to\n   continue!");

                    mainPanel.changeFileName("introBackground2.png");
                    submitButton.setVisible(true);
                    mainPanel.updateUI();
                }
                // If errors
                else {
                    submitButton.setEnabled(false);
                    submitButton.setVisible(false);

                    speechBubble.setText("   Oops. Please Enter Subject");

                    mainPanel.changeFileName("errorBackground.png");
                    mainPanel.updateUI();
                }
            }
        });

        // First Learning Objective Screen
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                submitButton.setEnabled(false);
                rootNode.add(learningObjectiveNode);
                rootNode.setUserObject(gradeBox.getSelectedItem() + " " + subjectBox.getSelectedItem());

                model.nodeChanged(rootNode);

                wizardTree.expandRow(0);

                String institutionSelcted = (String) institutionBox.getSelectedItem();
                String gradeSelcted = (String) gradeBox.getSelectedItem();
                String subjectSelcted = (String) subjectBox.getSelectedItem();

                try {

                    File file = new File("WizardRepo\\KnowledgeAreas/"
                            + institutionSelcted + "_" + gradeSelcted + "_"
                            + subjectSelcted + ".xml");

                    JAXBContext jaxbContext = JAXBContext.newInstance(KnowledgeRepo.class);

                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    knowRepo = (KnowledgeRepo) jaxbUnmarshaller.unmarshal(file);

                } catch (Exception e2) {
                    mainPanel.removeAll();

                    submitButton.setVisible(false);
                    domainBox.setEnabled(false);
                    gradeBox.setEnabled(false);
                    subjectBox.setEnabled(false);

                    mainPanel.changeFileName("errorBackground.png");
                    mainPanel.updateUI();

                    speechBubble.setText("   Oops. We Haven't Set \n    That Subject Up Yet!");

                    mainPanel.add(speechBubble);
                    mainPanel.add(repoErrorButton);

                    mainPanel.updateUI();
                    return;
                }

                Vector<Vector<Object>> vector = new Vector<Vector<Object>>();
                // Uses the import from knowRepo to make table
                for (int i = 0; i < knowRepo.getKnowledgeAreas().size(); i++) {
                    Vector<Object> vector1 = new Vector<Object>();
                    vector1.add(knowRepo.getKnowledgeAreas().get(i).getName());
                    vector1.add(false);
                    vector.add(vector1);
                }
                table = new JTable(new MyTableModel(vector, false, false, false));

                treePanel.add(wizardTree);

                mainPanel.removeAll();

                mainPanel.changeFileName("wizardBackground.png");
                mainPanel.changeCoord(0, -70);

                table.setRowHeight(45);
                table.setShowHorizontalLines(true);
                table.setRowSelectionAllowed(true);
                table.setColumnSelectionAllowed(true);
                table.getColumnModel().getColumn(1).setMaxWidth(75);
                table.getTableHeader().setFont(font3);
                table.setFont(font2);
                table.setGridColor(java.awt.Color.black);

                DefaultTableCellRenderer r = new DefaultTableCellRenderer() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        super.getTableCellRendererComponent(table, value,
                                isSelected, hasFocus, row, column);
                        if (((String) value).charAt(0) == '-') {
                            setFont(font4);
                        }
                        return this;
                    }
                };
                table.getColumnModel().getColumn(0).setCellRenderer(r);

                speechBubble.setFont(font);
                speechBubble.setBounds(415, 45, 400, 90);
                speechBubble.setText("    Please Select Your \n      Knowledge Area");

                @SuppressWarnings("deprecation")
                JScrollPane scroll = JTable.createScrollPaneForTable(table);
                scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                scroll.setBounds(100, 300, 950, 260);

                subLOButton.setFont(font2);

                mainPanel.add(scroll);
                mainPanel.add(speechBubble);
                mainPanel.add(submitLOButton);

                mainPanel.updateUI();
                treePanel.updateUI();
            }
        });

        // Sub-Learning Objective Screen
        submitLOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                wizardTree.expandRow(0);
                mainPanel.changeFileName("wizardBackground.png");
                mainPanel.changeCoord(0, -70);
                speechBubble.setFont(font);
                speechBubble.setBounds(413, 45, 403, 90);
                speechBubble.setText("Please Select Your Knowledge\n Areas and Continue");

                // following is a little bit of old code mixed with new. It
                // works, but it isn't necessary to create subAreas
                ArrayList<String> subAreas = new ArrayList<String>();
                Vector<Vector<Object>> lastTable = ((MyTableModel) table.getModel()).getData();

                for (int i = 0; i < lastTable.size(); i++) {
                    if (((boolean) lastTable.get(i).get(1))) {
                        if (((String) lastTable.get(i).get(0)).charAt(0) == '-') {
                            subAreas.add(((String) lastTable.get(i).get(0)).substring(2, ((String) lastTable.get(i).get(0)).length()));

                        }
                    }
                }

                Vector<Vector<Object>> newData = new Vector<Vector<Object>>();

                for (int i = 0; i < subAreas.size(); i++) {
                    for (int j = 0; j < knowRepo.getKnowledgeAreas().size(); j++) {
                        for (int k = 0; k < knowRepo.getKnowledgeAreas().get(j).getSubKnowledgeArea().size(); k++) {
                            if (subAreas.get(i).equals(
                                    (knowRepo.getKnowledgeAreas().get(j)
                                            .getSubKnowledgeArea().get(k)
                                            .getName()).substring(2))) {

                                if (knowRepo.getKnowledgeAreas().get(j)
                                        .getSubKnowledgeArea().get(k)
                                        .getLearningObjectives() != null) {

                                    Vector<Object> vector = new Vector<Object>();
                                    vector.add(subAreas.get(i));
                                    vector.add(true);

                                    newData.add(vector);

                                    for (int l = 0; l < knowRepo
                                            .getKnowledgeAreas().get(j)
                                            .getSubKnowledgeArea().get(k)
                                            .getLearningObjectives().size(); l++) {

                                        if (!knowRepo.getKnowledgeAreas()
                                                .get(j).getSubKnowledgeArea()
                                                .get(k).getLearningObjectives()
                                                .get(l).getTaxonomyCategories()
                                                .equals("")) {

                                            Vector<Object> vector2 = new Vector<Object>();
                                            vector2.add(knowRepo.getKnowledgeAreas().get(j)
                                                    .getSubKnowledgeArea().get(k)
                                                    .getLearningObjectives().get(l)
                                                    .getTaxonomyCategories());

                                            vector2.add(false);
                                            newData.add(vector2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Changes text in the text area depending on what type of
                // learning objective it is
                DefaultTableCellRenderer r = new DefaultTableCellRenderer() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                        if (((String) value).length() != 0 && ((String) value).charAt(0) == '-') {

                            JTextArea text = new JTextArea();
                            text.setText((String) value);
                            text.setLineWrap(true);
                            text.setWrapStyleWord(true);
                            text.setFont(font4);

                            return text;
                        } else {
                            this.setFont(font2);
                        }
                        return this;
                    }
                };
                loTable = new JTable(new MyTableModel(newData, false, true, false));
                loTable.getColumnModel().getColumn(0).setCellRenderer(r);

                loTable.setRowHeight(120);
                loTable.setShowHorizontalLines(true);
                loTable.setRowSelectionAllowed(true);
                loTable.setColumnSelectionAllowed(true);
                loTable.getColumnModel().getColumn(1).setMaxWidth(75);
                loTable.getTableHeader().setFont(font3);
                loTable.setFont(font3);
                loTable.setGridColor(java.awt.Color.black);

                @SuppressWarnings("deprecation")
                JScrollPane scroll = JTable.createScrollPaneForTable(loTable);
                scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                scroll.setBounds(40, 300, 1000, 350);

                subLOButton.setFont(font2);

                mainPanel.add(scroll);
                mainPanel.add(subLOButton);
                mainPanel.add(speechBubble);

                treePanel.updateUI();
                mainPanel.updateUI();
            }
        });

        // Summary Screen of Learning Objectives
        subLOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mainPanel.removeAll();

                loList = new ArrayList<String>();

                JTextPane selectedLO = new JTextPane();
                selectedLO.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                StyledDocument doc = selectedLO.getStyledDocument();

                Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

                Style normal = doc.addStyle("font 2", def);
                StyleConstants.setFontFamily(def, "Comic Sans MS");
                StyleConstants.setFontSize(def, 22);
                StyleConstants.setUnderline(def, false);
                StyleConstants.setBold(def, true);

                Style s = doc.addStyle("font 3", normal);
                StyleConstants.setFontSize(s, 18);
                StyleConstants.setBold(s, false);

                Style s2 = doc.addStyle("font 4", s);
                StyleConstants.setFontSize(s2, 15);

                MyTableModel tm = (MyTableModel) table.getModel();
                MyTableModel tm2 = (MyTableModel) loTable.getModel();

                Vector<Vector<Object>> vector = tm.getData();
                Vector<Vector<Object>> vector1 = tm2.getData();

                int mainCount = 0;
                int subCount = 0;
                boolean finalError = false;
                learningError = false;

                for (int i = 0; i < table.getRowCount(); i++) {
                    if ((boolean) vector.get(i).get(1)) {
                        if (((String) (vector.get(i).get(0))).charAt(0) == '-') {
                            try {
                                doc.insertString(doc.getLength(), vector.get(i).get(0) + "\n", doc.getStyle("font 3"));

                                for (int j = 0; j < loTable.getRowCount(); j++) {

                                    int length = ((String) (vector.get(i).get(0))).length();

                                    String sub = ((String) (vector.get(i).get(0))).substring(2, length);
                                    String lo = ((String) (vector1.get(j).get(0)));

                                    if (sub.equals(lo)) {
                                        int k = j;
                                        int l = k;

                                        while (j + 1 < loTable.getRowCount() && ((String) (vector1.get(j + 1).get(0))).charAt(0) == '-') {
                                            if ((boolean) vector1.get(j + 1).get(1)) {
                                                doc.insertString(doc.getLength(), "----"
                                                        + vector1.get(j + 1).get(0)
                                                        + "\n", doc
                                                        .getStyle("font 4"));

                                                loList.add((String) vector1.get(j + 1).get(0));
                                                k++;
                                            }
                                            j++;
                                        }
                                        if (k == l) {
                                            learningError = true;
                                        }
                                    }
                                }
                            } catch (Exception e1) {
                                System.out.println("Font does not Exist!");
                            }
                            subCount++;
                        } else {
                            try {
                                doc.insertString(doc.getLength(),
                                        vector.get(i).get(0) + "\n",
                                        doc.getStyle("font 2"));

                                int j = 1;
                                boolean error = true;
                                while (((String) (vector.get(i + j).get(0))).charAt(0) == '-') {
                                    if ((boolean) vector.get(i + j).get(1)) {
                                        error = false;
                                    }
                                    j++;
                                }
                                if (error) {
                                    finalError = true;
                                    mainCount++;
                                    break;
                                }
                            } catch (Exception e1) {
                                System.out.println("Font does not Exist!");
                            }
                            mainCount++;
                        }
                    }
                }
                summaryContinue.setText("Continue");
                summaryBack.setText("Back");

                summaryContinue.setFont(font2);
                summaryBack.setFont(font2);

                summaryContinue.setBounds(700, 225, 150, 50);
                summaryBack.setBounds(520, 225, 150, 50);

                summaryContinue.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                summaryBack.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                // Error Checking
                if (mainCount == 0) {

                    mainPanel.removeAll();

                    speechBubble.setBounds(415, 45, 400, 170);
                    speechBubble.setText("\n      You Must Select A \n         Knowledge Area!\n       Please Click Back!");
                    speechBubble.setFont(font);

                    summaryBack.setBounds(700, 550, 150, 50);

                    mainPanel.changeCoord(0, 0);
                    mainPanel.add(speechBubble);
                    mainPanel.add(summaryBack);

                    mainPanel.changeFileName("errorBackground.png");
                    mainPanel.updateUI();

                } else if (subCount == 0 && mainCount != 0 || finalError) {

                    mainPanel.removeAll();

                    speechBubble.setBounds(415, 45, 400, 170);
                    speechBubble.setText("\n        You Must Select \n        Sub-Knowledge Area!\n        Please Click Back!");
                    speechBubble.setFont(font);

                    summaryBack.setBounds(700, 550, 150, 50);

                    mainPanel.changeCoord(0, 0);

                    mainPanel.add(speechBubble);
                    mainPanel.changeFileName("errorBackground.png");
                    mainPanel.add(summaryBack);

                    mainPanel.updateUI();

                } else if (learningError) {

                    mainPanel.removeAll();

                    speechBubble.setBounds(415, 45, 400, 170);
                    speechBubble.setText("\n        You Must Select \n       Learning Objectives!\n        Please Click Back!");
                    speechBubble.setFont(font);

                    summaryBack.setBounds(700, 550, 150, 50);

                    mainPanel.changeCoord(0, 0);

                    mainPanel.add(speechBubble);
                    mainPanel.changeFileName("errorBackground.png");
                    mainPanel.add(summaryBack);

                    mainPanel.updateUI();
                }
                // For good input
                else {
                    selectedLO.setEditable(false);
                    selectedLO.setCaretPosition(0);

                    JScrollPane scroll = new JScrollPane(selectedLO);
                    scroll.setBounds(100, 300, 950, 260);
                    scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                    speechBubble.setBounds(415, 45, 400, 90);
                    speechBubble.setText("Here is a Summary of Your \nLearning Objectives Selected");
                    speechBubble.setFont(font);

                    mainPanel.add(speechBubble);
                    mainPanel.add(summaryContinue);
                    mainPanel.add(summaryBack);
                    mainPanel.add(scroll);

                    mainPanel.updateUI();
                }
            }
        });

        // If there are errors in learning objectives takes back to main
        // learning objective screen
        summaryBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mainPanel.removeAll();

                speechBubble.setFont(font);
                speechBubble.setBounds(415, 45, 400, 90);
                speechBubble.setText("Please Select Your Learning\n Objectives and Continue");

                @SuppressWarnings("deprecation")
                JScrollPane scroll = JTable.createScrollPaneForTable(table);
                scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                scroll.setBounds(100, 300, 950, 260);

                subLOButton.setFont(font2);
                subLOButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                loList = new ArrayList<String>();

                mainPanel.changeFileName("wizardBackground.png");
                mainPanel.changeCoord(0, -70);

                mainPanel.add(scroll);
                mainPanel.add(speechBubble);
                mainPanel.add(submitLOButton);

                mainPanel.updateUI();
            }
        });

        // If all learning objective selection is fine, then cintinue to
        // taxonomy screen
        summaryContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (learningObjectiveNode.getNextSibling() != learningTaxonomyNode) {
                    rootNode.add(learningTaxonomyNode);
                    DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                    model.reload(mainRoot);
                }
                wizardTree.expandRow(0);
                mainPanel.removeAll();
                taxPane.setEditable(false);

                // import learning taxonomy in future

                speechBubble.setFont(font);
                speechBubble.setBounds(347, 65, 400, 78);
                speechBubble.setText("     Please Select Your\n     Learning Taxonomy");

                mainPanel.changeFileName("learningTaxonomy.png");
                mainPanel.changeCoord(-100, 0);

                taxContButton.setBounds(750, 475, 165, 50);
                taxContButton.setVisible(true);
                taxContButton.setEnabled(false);
                taxContButton.setFont(font2);
                taxContButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                taxBox.setFont(font2);
                taxBox.setBounds(625, 375, 425, 50);
                taxPane.setBounds(70, 300, 400, 260);
                taxPane.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                mainPanel.add(taxPane);
                mainPanel.add(taxBox);
                mainPanel.add(taxContButton);
                mainPanel.add(speechBubble);

                treePanel.add(wizardTree);
                mainPanel.updateUI();
                treePanel.updateUI();
            }
        });

        // Learning Standard Screen
        taxBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int i = taxBox.getSelectedIndex();

                mainPanel.removeAll();

                mainPanel.add(taxBox);
                taxBox.setSelectedIndex(i);
                mainPanel.add(taxContButton);

                mainPanel.updateUI();

                if (i == 0) {
                    taxContButton.setEnabled(false);

                    speechBubble.setBounds(347, 65, 400, 78);
                    speechBubble.setText("     Please Select Your\n       Standard");
                    speechBubble.setFont(font);

                    taxPane = new JTextPane();
                    taxPane.setBounds(70, 300, 400, 260);
                    taxPane.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                    mainPanel.add(taxContButton);
                    mainPanel.add(taxPane);
                    mainPanel.add(speechBubble);

                    mainPanel.updateUI();

                } else {
                    taxPane = new JTextPane();
                    taxPane.updateUI();
                    taxPane.setEditable(false);

                    StyledDocument doc = taxPane.getStyledDocument();
                    javax.swing.text.Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
                    javax.swing.text.Style normal = doc.addStyle("font 2", def);

                    StyleConstants.setFontFamily(def, "Comic Sans MS");
                    StyleConstants.setFontSize(def, 22);
                    StyleConstants.setBold(def, true);
                    StyleConstants.setUnderline(def, true);

                    javax.swing.text.Style s = doc.addStyle("font 3", normal);
                    StyleConstants.setUnderline(s, false);
                    StyleConstants.setFontSize(s, 18);
                    StyleConstants.setBold(s, false);

                    if (taxBox.getSelectedItem().equals("Bloom's")) {

                        bloomSelected = true;

                        speechBubble.setFont(font);
                        speechBubble.setBounds(347, 65, 400, 78);
                        speechBubble.setText(" Great! You Have Selected \n           Blooms!");

                        taxContButton.setEnabled(true);

                        try {
                            doc.insertString(doc.getLength(), "Bloom's\n", doc.getStyle("font 2"));
                            doc.insertString(doc.getLength(), "-Knowledge (K)\n", doc.getStyle("font 3"));
                            doc.insertString(doc.getLength(), "-Comprehension (C)\n", doc.getStyle("font 3"));
                            doc.insertString(doc.getLength(), "-Application (AP)\n", doc.getStyle("font 3"));
                            doc.insertString(doc.getLength(), "-Analysis (An)\n", doc.getStyle("font 3"));
                            doc.insertString(doc.getLength(), "-Synthesis (S)\n", doc.getStyle("font 3"));
                            doc.insertString(doc.getLength(), "-Evaluation (E)\n", doc.getStyle("font 3"));

                        } catch (Exception e2) {
                            System.out.println("catch blooms");
                        }

                        mainPanel.updateUI();

                    } else {

                        bloomSelected = false;

                        speechBubble.setFont(font);
                        speechBubble.setBounds(347, 65, 400, 78);
                        speechBubble.setText(" Great! You Have Selected\n    The Wisconsion Standard!");

                        taxContButton.setEnabled(true);
                        mainPanel.updateUI();

                        try {
                            doc.insertString(doc.getLength(), "Wisconsin State Standards\n", doc.getStyle("font 2"));
                            doc.insertString(doc.getLength(), "-Make sense of problems and persevere in solving them\n", doc.getStyle("font 3"));
                            doc.insertString(doc.getLength(), "-Reason abstractly and quantitatively\n", doc.getStyle("font 3"));
                            doc.insertString(doc.getLength(), "-Construct viable arguments and critique the reasoning of others\n", doc.getStyle("font 3"));
                            doc.insertString(doc.getLength(), "-Model with mathematics\n", doc.getStyle("font 3"));
                            doc.insertString(doc.getLength(), "-Use appropriate tools strategy\n", doc.getStyle("font 3"));
                            doc.insertString(doc.getLength(), "-Attend to precision\n", doc.getStyle("font 3"));
                            doc.insertString(doc.getLength(), "-Look for and make use of structure\n", doc.getStyle("font 3"));
                            doc.insertString(doc.getLength(), "-Look for and express regularity in repeated reasoning.\n", doc.getStyle("font 3"));

                        } catch (Exception e2) {
                            System.out.println("catch other");
                        }
                    }
                    // taxPane.setStyledDocument(doc);
                    bloomsScroll = new JScrollPane(taxPane);
                    taxPane.setCaretPosition(0);

                    bloomsScroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                    bloomsScroll.setBounds(70, 300, 400, 260);

                    taxPane.updateUI();
                    bloomsScroll.updateUI();

                    mainPanel.add(bloomsScroll);
                    mainPanel.add(speechBubble);
                    mainPanel.add(taxContButton);

                    mainPanel.updateUI();
                }
            }
        });

        // Taxonomy Screen; i.e. knowledge, comprehension, etc.
        taxContButton.addActionListener(new ActionListener() {
            @Override
            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e) {
                if (learningTaxonomyNode.getNextSibling() != subTaxNode) {

                    rootNode.add(subTaxNode);
                    DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                    model.reload(mainRoot);

                }
                wizardTree.expandRow(0);

                mainPanel.removeAll();
                mainPanel.changeFileName("wizardBackground.png");
                mainPanel.changeCoord(0, -70);

                JScrollPane scroll;

                if (taxBox.getSelectedItem().equals("Bloom's")) {

                    Vector<Vector<Object>> vector = new Vector<Vector<Object>>();
                    Vector<Object> v1 = new Vector<Object>();
                    Vector<Object> v2 = new Vector<Object>();
                    Vector<Object> v3 = new Vector<Object>();
                    Vector<Object> v4 = new Vector<Object>();
                    Vector<Object> v5 = new Vector<Object>();
                    Vector<Object> v6 = new Vector<Object>();
                    v1.add("-Knowledge (K)");
                    v1.add(false);
                    v2.add("-Comprehension (C)");
                    v2.add(false);
                    v3.add("-Application (AP)");
                    v3.add(false);
                    v4.add("-Analysis (An)");
                    v4.add(false);
                    v5.add("-Synthesis (S)");
                    v5.add(false);
                    v6.add("-Evaluation (E)");
                    v6.add(false);
                    vector.add(v1);
                    vector.add(v2);
                    vector.add(v3);
                    vector.add(v4);
                    vector.add(v5);
                    vector.add(v6);

                    subTaxTable = new JTable(new MyTableModel(vector, false, false, false));
                    subTaxTable.setRowHeight(45);
                    subTaxTable.setShowHorizontalLines(true);
                    subTaxTable.setRowSelectionAllowed(true);
                    subTaxTable.setColumnSelectionAllowed(true);
                    subTaxTable.getColumnModel().getColumn(1).setMaxWidth(75);
                    subTaxTable.getTableHeader().setFont(font3);
                    subTaxTable.setFont(font2);
                    subTaxTable.setGridColor(java.awt.Color.black);

                    scroll = JTable.createScrollPaneForTable(subTaxTable);
                    scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                    scroll.setBounds(85, 300, 950, 260);

                } else {
                    Vector<Vector<Object>> vector = new Vector<Vector<Object>>();
                    Vector<Object> v1 = new Vector<Object>();
                    Vector<Object> v2 = new Vector<Object>();
                    Vector<Object> v3 = new Vector<Object>();
                    Vector<Object> v4 = new Vector<Object>();
                    Vector<Object> v5 = new Vector<Object>();
                    Vector<Object> v6 = new Vector<Object>();
                    Vector<Object> v7 = new Vector<Object>();
                    v1.add("-Make sense of problems and persevere in solving them");
                    v1.add(false);
                    v2.add("-Reason abstractly and quantitatively");
                    v2.add(false);
                    v3.add("-Model with mathematics");
                    v3.add(false);
                    v4.add("-Use appropriate tools strategy");
                    v4.add(false);
                    v5.add("-Attend to precision");
                    v5.add(false);
                    v6.add("-Look for and make use of structure");
                    v6.add(false);
                    v7.add("-Look for and express regularity in repeated reasoning");
                    v7.add(false);
                    vector.add(v1);
                    vector.add(v2);
                    vector.add(v3);
                    vector.add(v4);
                    vector.add(v5);
                    vector.add(v6);
                    vector.add(v7);

                    subTaxTable = new JTable(new MyTableModel(vector, false, false, false));
                    subTaxTable.setRowHeight(45);
                    subTaxTable.setShowHorizontalLines(true);
                    subTaxTable.setRowSelectionAllowed(true);
                    subTaxTable.setColumnSelectionAllowed(true);
                    subTaxTable.getColumnModel().getColumn(1).setMaxWidth(75);
                    subTaxTable.getTableHeader().setFont(font3);
                    subTaxTable.setFont(font2);
                    subTaxTable.setGridColor(java.awt.Color.black);

                    scroll = JTable.createScrollPaneForTable(subTaxTable);
                    scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                    scroll.setBounds(85, 300, 950, 260);
                }

                subTaxContButton.setVisible(true);
                subTaxContButton.setEnabled(true);
                subTaxContButton.setFont(font2);
                subTaxContButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                subTaxContButton.setBounds(650, 225, 150, 50);
                speechBubble.setFont(font);
                speechBubble.setBounds(415, 45, 400, 90);
                speechBubble.setText("      Please Select Type\n         Taxonomies");

                mainPanel.add(subTaxContButton);
                mainPanel.add(speechBubble);
                mainPanel.add(scroll);
                mainPanel.updateUI();
            }
        });

        // Type of Challenge Screen
        subTaxContButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (subTaxNode.getNextSibling() != typeOfChallengeNode) {
                    rootNode.add(typeOfChallengeNode);
                    DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                    model.reload(mainRoot);
                }

                wizardTree.expandRow(0);

                mainPanel.removeAll();

                mainPanel.changeFileName("wizardBackground.png");
                mainPanel.changeCoord(0, -70);

                Vector<Vector<Object>> vector = generateChallengeTable();

                challengeTable = new JTable(new MyTableModel(vector, false, false, false));
                challengeTable.setRowHeight(45);
                challengeTable.setShowHorizontalLines(true);
                challengeTable.setRowSelectionAllowed(true);
                challengeTable.setColumnSelectionAllowed(true);
                challengeTable.getColumnModel().getColumn(1).setMaxWidth(75);
                challengeTable.getTableHeader().setFont(font3);
                challengeTable.setFont(font2);
                challengeTable.setGridColor(java.awt.Color.black);

                @SuppressWarnings("deprecation")
                JScrollPane scroll = JTable.createScrollPaneForTable(challengeTable);
                scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                scroll.setBounds(85, 300, 950, 260);

                challengeButton.setFont(font2);
                challengeButton.setBounds(650, 225, 150, 50);
                challengeButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                challengeButton.setEnabled(true);

                // place button here
                speechBubble.setFont(font);
                speechBubble.setBounds(415, 45, 400, 90);
                speechBubble.setText("      Please Select Type\n         of Challenge");

                mainPanel.add(challengeButton);
                mainPanel.add(scroll);
                mainPanel.add(speechBubble);

                treePanel.updateUI();
                mainPanel.updateUI();
            }
        });

        // Conditions Screen
        challengeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (typeOfChallengeNode.getNextSibling() != conditionsNode) {
                    rootNode.add(conditionsNode);
                    DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                    model.reload(mainRoot);
                }
                wizardTree.expandRow(0);
                mainPanel.removeAll();

                speechBubble.setFont(font);
                speechBubble.setBounds(415, 45, 400, 90);
                speechBubble.setText("      Please Select Your\n        Game Conditions");

                mainPanel.changeFileName("wizardBackground.png");
                mainPanel.changeCoord(0, -70);

                if (isErrorChallenge()) {
                    printErrorChallenge();
                } else {
                    Vector<Vector<Object>> vNew = new Vector<Vector<Object>>();
                    Vector<Object> v1 = new Vector<Object>();
                    Vector<Object> v2 = new Vector<Object>();
                    Vector<Object> v3 = new Vector<Object>();
                    Vector<Object> v4 = new Vector<Object>();
                    Vector<Object> v5 = new Vector<Object>();
                    v1.add("Timed");
                    v1.add(false);
                    v2.add("Limited Resources");
                    v2.add(false);
                    v3.add("Reward/Penalty");
                    v3.add(false);
                    v4.add("Collaborative");
                    v4.add(false);
                    v5.add("Antagonistic");
                    v5.add(false);
                    vNew.add(v1);
                    vNew.add(v2);
                    vNew.add(v3);
                    vNew.add(v4);
                    vNew.add(v5);

                    conditionsTable = new JTable(new MyTableModel(vNew, false, false, false));
                    conditionsTable.setRowHeight(45);
                    conditionsTable.setShowHorizontalLines(true);
                    conditionsTable.setRowSelectionAllowed(true);
                    conditionsTable.setColumnSelectionAllowed(true);
                    conditionsTable.getColumnModel().getColumn(1).setMaxWidth(75);
                    conditionsTable.getTableHeader().setFont(font3);
                    conditionsTable.setFont(font2);
                    conditionsTable.setGridColor(java.awt.Color.black);

                    @SuppressWarnings("deprecation")
                    JScrollPane scroll = JTable.createScrollPaneForTable(conditionsTable);
                    scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                    scroll.setBounds(85, 300, 950, 260);

                    conditionContinue.setFont(font2);

                    mainPanel.add(conditionContinue);
                    mainPanel.add(scroll);
                    mainPanel.add(speechBubble);
                    mainPanel.updateUI();
                    treePanel.updateUI();
                }
            }
        });

        // If user fails to select challenge, it goes back to challenge screen
        challengeErrorBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.changeFileName("wizardBackground.png");
                mainPanel.changeCoord(0, -70);
                mainPanel.removeAll();

                @SuppressWarnings("deprecation")
                JScrollPane scroll = JTable.createScrollPaneForTable(challengeTable);
                scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                scroll.setBounds(85, 300, 950, 260);

                challengeButton.setFont(font2);
                challengeButton.setBounds(650, 225, 150, 50);
                challengeButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                speechBubble.setFont(font);
                speechBubble.setBounds(415, 45, 400, 90);
                speechBubble.setText("      Please Select Type\n         of Challenge");

                mainPanel.add(challengeButton);
                mainPanel.add(scroll);
                mainPanel.add(speechBubble);

                treePanel.updateUI();
                mainPanel.updateUI();
            }
        });

        // If all is correct there is a summary of lower node
        conditionContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // method found at bottom
                generateSummary();
            }
        });
        // If user fails to select condition, takes them back to condition
        // screen
        conditionErrorBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.changeFileName("wizardBackground.png");
                mainPanel.changeCoord(0, -70);
                mainPanel.add(conditionContinue);

                speechBubble.setFont(font);
                speechBubble.setBounds(415, 45, 400, 90);
                speechBubble.setText("      Please Select Your\n        Game Conditions");

                @SuppressWarnings("deprecation")
                JScrollPane scroll = JTable.createScrollPaneForTable(conditionsTable);
                scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                scroll.setBounds(85, 300, 950, 260);
                mainPanel.add(scroll);
                mainPanel.add(speechBubble);

                mainPanel.updateUI();
                treePanel.updateUI();

            }
        });

        // if user fails to select taxonomy, takes them back to taxonomy screen
        taxBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speechBubble.setFont(font);
                speechBubble.setBounds(347, 65, 400, 78);
                speechBubble.setText("     Please Select Your\n     Learning Taxonomy");

                mainPanel.changeFileName("learningTaxonomy.png");
                mainPanel.changeCoord(-100, 0);
                mainPanel.removeAll();

                bloomsScroll.updateUI();

                mainPanel.add(bloomsScroll);
                mainPanel.add(speechBubble);

                taxContButton.setEnabled(false);

                mainPanel.add(taxContButton);
                mainPanel.add(taxBox);

                mainPanel.updateUI();
            }
        });

        // Environment Selection Screen
        fullSumContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();

                if (rootNode.getNextSibling() != topNode) {
                    mainRoot.add(topNode);
                    topNode.add(locationNode);

                    DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                    model.reload(mainRoot);

                    wizardTree.expandRow(1);
                    wizardTree.collapseRow(0);

                } else {
                    wizardTree.setSelectionPath(new TreePath(locationNode));
                    wizardTree.expandRow(1);
                    wizardTree.collapseRow(0);
                }

                backgroundPane = new JTextPane();
                backgroundPane.removeAll();
                backgroundPane.updateUI();

                speechBubble.setFont(font);
                speechBubble.setBounds(347, 65, 400, 78);
                speechBubble.setText("     Please Select Your\n        Location");

                backgroundBox.setBounds(625, 375, 425, 50);
                locationButton.setBounds(750, 475, 175, 50);
                locationButton.setEnabled(false);
                locationButton.setFont(font2);
                backgroundPane.setBounds(50, 300, 400, 260);
                backgroundPane.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                backgroundBox.setFont(font2);

                mainPanel.changeFileName("learningTaxonomy.png");
                mainPanel.changeCoord(-100, 0);
                mainPanel.add(speechBubble);
                mainPanel.add(backgroundBox);
                mainPanel.add(backgroundPane);
                mainPanel.add(locationButton);

                treePanel.updateUI();
                mainPanel.updateUI();
            }
        });

        // Typical Character Screen
        locationButton.addActionListener(new ActionListener() {
            // Error Checking
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isLocationError()) {
                    printLocationError();
                } else {
                    mainPanel.removeAll();
                    if (locationNode.getNextSibling() != characterNode) {

                        topNode.add(characterNode);
                        DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                        model.reload(mainRoot);
                    }

                    wizardTree.expandRow(1);
                    Vector<Vector<Object>> vector = new Vector<Vector<Object>>();
                    try {
                        File file = new File("WizardRepo\\EnvTemplates/"
                                + backgroundBox.getSelectedItem()
                                + "_Chars.xml");

                        JAXBContext jaxbContext = JAXBContext.newInstance(CharList.class);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                        charList = (CharList) jaxbUnmarshaller.unmarshal(file);

                        int numCharacters = charList.getCharacters().size();

                        for (int i = 0; i < numCharacters; i++) {
                            Vector<Object> vector1 = new Vector<Object>();
                            ImageIcon icon = new ImageIcon(ImageIO.read(new File(
                                            "Office, Classroom\\Characters/"
                                                    + charList.getCharacters()
                                                    .get(i)
                                                    .getFileName())));

                            String charName = charList.getCharacters().get(i).getName();
                            String charType = charList.getCharacters().get(i).getType();

                            vector1.add(charName);
                            vector1.add(charType);
                            vector1.add(icon);
                            vector.add(vector1);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                    charTable = new JTable(new MyTableModel(vector, true, false, false));
                    charTable.getColumnModel().getColumn(1).setPreferredWidth(150);
                    charTable.getColumnModel().getColumn(0).setPreferredWidth(150);
                    charTable.getColumnModel().getColumn(2).setPreferredWidth(375);
                    charTable.setRowHeight(300);
                    charTable.setCellSelectionEnabled(false);
                    charTable.getTableHeader().setFont(font3);
                    charTable.setFont(font4);

                    @SuppressWarnings("deprecation")
                    JScrollPane scroll = JTable.createScrollPaneForTable(charTable);
                    scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                    scroll.setBounds(85, 300, 950, 260);

                    speechBubble.setFont(font);
                    speechBubble.setBounds(415, 45, 400, 90);
                    speechBubble.setText("      Here are Your\n       Typical Characters!");

                    charButton.setBounds(715, 225, 150, 50);
                    charButton.setFont(font2);

                    mainPanel.add(scroll);
                    mainPanel.changeFileName("wizardBackground.png");
                    mainPanel.changeCoord(0, -70);
                    mainPanel.add(speechBubble);
                    mainPanel.add(charButton);

                    treePanel.updateUI();
                    mainPanel.updateUI();
                }
            }
        });

        // Changes the text pane based on location user selects
        backgroundBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String background = (String) backgroundBox.getSelectedItem();
                if (background.equals("Please Choose A Location")) {
                    mainPanel.removeAll();
                    mainPanel.changeFileName("learningTaxonomy.png");
                    mainPanel.changeCoord(-100, 0);

                    backgroundPane = new JTextPane();
                    backgroundPane.removeAll();
                    backgroundPane.updateUI();
                    backgroundPane.setBounds(50, 300, 400, 260);
                    backgroundPane.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                    speechBubble.setFont(font);
                    speechBubble.setBounds(347, 65, 400, 78);
                    speechBubble.setText("     Please Select Your\n        Location");
                    locationButton.setEnabled(false);

                    mainPanel.add(locationButton);
                    mainPanel.add(speechBubble);
                    mainPanel.add(backgroundBox);
                    mainPanel.add(backgroundPane);
                    mainPanel.updateUI();

                } else if (background.equals("Classroom")) {
                    mainPanel.removeAll();
                    mainPanel.changeFileName("learningTaxonomy.png");

                    backgroundPane = new JTextPane();
                    backgroundPane.removeAll();
                    backgroundPane.updateUI();
                    backgroundPane.setBounds(50, 300, 400, 260);
                    backgroundPane.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                    try {
                        ImageIcon icon = new ImageIcon(ImageIO.read(new File("Office, Classroom\\Backdrops/Classroom_1.png")));
                        backgroundPane.insertIcon(icon);

                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    backGroundChoice = "Office, Classroom\\Backdrops/Classroom_1.png";

                    speechBubble.setFont(font);
                    speechBubble.setBounds(347, 65, 400, 78);
                    speechBubble.setText("   You Chose a Classroom!");

                    locationButton.setEnabled(true);

                    mainPanel.add(locationButton);
                    mainPanel.add(speechBubble);
                    mainPanel.add(backgroundBox);
                    mainPanel.add(backgroundPane);

                    mainPanel.updateUI();

                } else if (background.equals("Enchanted Forest")) {
                    mainPanel.removeAll();

                    speechBubble.setFont(font);
                    speechBubble.setBounds(347, 65, 400, 78);

                    backgroundPane = new JTextPane();
                    backgroundPane.removeAll();
                    backgroundPane.updateUI();
                    backgroundPane.setBounds(50, 300, 400, 260);
                    backgroundPane.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                    try {
                        ImageIcon icon = new ImageIcon(ImageIO.read(new File("Office, Classroom\\Backdrops/Forest night.png")));
                        backgroundPane.insertIcon(icon);

                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    speechBubble.setText("  You Chose the Enchanted \n          Forest!");

                    locationButton.setEnabled(true);

                    mainPanel.add(locationButton);
                    mainPanel.add(speechBubble);
                    mainPanel.add(backgroundBox);
                    mainPanel.add(backgroundPane);

                    mainPanel.updateUI();

                    backGroundChoice = "Office, Classroom\\Backdrops/Forest night.png";
                }
            }
        });

        // First Set of Acts Screen
        charButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.changeFileName("wizardBackground.png");
                mainPanel.changeCoord(0, -70);

                if (characterNode.getNextSibling() != actNode) {
                    topNode.add(actNode);
                    DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                    model.reload(mainRoot);
                }
                wizardTree.expandRow(1);
                template = new JTextPane();
                template.setEditable(false);

                showActs();

                templateScroll = new JScrollPane(template);
                template.setCaretPosition(0);
                templateScroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                templateScroll.setBounds(180, 300, 720, 330);
                template.updateUI();
                templateScroll.updateUI();

                speechBubble.setFont(font2);
                speechBubble.setBounds(425, 30, 345, 100);
                speechBubble.setText("     Let's Begin With Creating\n         The First Acts of\n             Your Game!");

                actButton.setText("Continue");
                actButton.setFont(font2);
                actButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                actButton.setBounds(715, 225, 150, 50);
                actButton.setVisible(true);

                mainPanel.add(actButton);
                mainPanel.add(speechBubble);
                mainPanel.add(templateScroll);

                mainPanel.updateUI();
            }
        });

        // Screen where user can add set of acts, question, keep the same, or
        // return to original
        actButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.changeCoord(0, 0);

                if (actNode.getNextSibling() != addActNode) {
                    topNode.add(addActNode);
                    DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                    model.reload(mainRoot);
                }
                wizardTree.expandRow(1);
                mainPanel.removeAll();

                speechBubble.setFont(font3);
                speechBubble.setBounds(455, 100, 345, 105);
                speechBubble.setText("There Are Two Choices To Change\n         Your Game:\n 1. Add Another Set of Acts\n2. Add Additional Questions");

                actBox.setFont(font2);

                mainPanel.add(addActButton);
                mainPanel.add(actBox);
                mainPanel.add(speechBubble);

                mainPanel.updateUI();
            }
        });

        // Updates template based on what user selects and shows them a summary
        // of their new acts
        addActButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.changeFileName("wizardBackground.png");
                mainPanel.changeCoord(0, -70);

                actButton.setText("Add More");
                actButton.setFont(font2);
                actButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                actButton.setBounds(515, 225, 150, 50);
                actButton.setVisible(true);

                contActButton.setText("Continue");
                contActButton.setFont(font2);
                contActButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                contActButton.setBounds(715, 225, 150, 50);
                contActButton.setVisible(true);

                if (addActNode.getNextSibling() != actSumNode) {
                    topNode.add(actSumNode);
                    DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                    model.reload(mainRoot);
                }
                wizardTree.expandRow(1);
                if (actBox.getSelectedItem().equals("Add Another Set of Acts")) {
                    template = new JTextPane();
                    template.setEditable(false);

                    addMultiply();
                    showActs();
                    templateScroll = new JScrollPane(template);
                    template.setCaretPosition(0);
                    templateScroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                    templateScroll.setBounds(180, 300, 720, 330);
                    template.updateUI();
                    templateScroll.updateUI();

                    speechBubble.setFont(font2);
                    speechBubble.setBounds(425, 30, 345, 100);
                    speechBubble.setText("\n       Here is How It Looks!");

                    mainPanel.add(actButton);
                    mainPanel.add(speechBubble);
                    mainPanel.add(templateScroll);
                    mainPanel.add(contActButton);
                    mainPanel.updateUI();

                } else if (actBox.getSelectedItem().equals("Add a Question")) {
                    template = new JTextPane();
                    template.setEditable(false);

                    addQuestion();

                    templateScroll = new JScrollPane(template);
                    template.setCaretPosition(0);
                    templateScroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                    templateScroll.setBounds(180, 300, 720, 330);
                    template.updateUI();
                    templateScroll.updateUI();

                    showActs();

                    speechBubble.setFont(font2);
                    speechBubble.setBounds(425, 30, 345, 100);
                    speechBubble.setText("\n       Here is How It Looks!");

                    mainPanel.add(actButton);
                    mainPanel.add(speechBubble);
                    mainPanel.add(templateScroll);
                    mainPanel.add(contActButton);
                    mainPanel.updateUI();
                } else if (actBox.getSelectedItem().equals("Back to Original")) {
                    File file = new File("WizardRepo\\Templates/template.xml");
                    JAXBContext jaxbContext;
                    try {
                        jaxbContext = JAXBContext.newInstance(GameTemplate.class);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                        gametemplate = (GameTemplate) jaxbUnmarshaller.unmarshal(file);

                    } catch (JAXBException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    template = new JTextPane();
                    template.setEditable(false);

                    showActs();

                    templateScroll = new JScrollPane(template);
                    template.setCaretPosition(0);
                    templateScroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                    templateScroll.setBounds(180, 300, 720, 330);
                    template.updateUI();
                    templateScroll.updateUI();

                    speechBubble.setFont(font2);
                    speechBubble.setBounds(425, 30, 345, 100);
                    speechBubble.setText("\n       Here is How It Looks!");

                    mainPanel.add(actButton);
                    mainPanel.add(speechBubble);
                    mainPanel.add(templateScroll);
                    mainPanel.add(contActButton);
                    mainPanel.updateUI();

                } else {
                    mainPanel.removeAll();
                    if (actSumNode.getNextSibling() != loActNode) {
                        topNode.add(loActNode);
                        DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                        model.reload(mainRoot);
                    }
                    wizardTree.expandRow(1);
                    mainPanel.changeFileName("wizardBackground.png");
                    mainPanel.changeCoord(0, -70);

                    speechBubble.setFont(font2);
                    speechBubble.setBounds(415, 45, 400, 90);
                    speechBubble.setText("    Please Select Which Learning\n      Objectives You Would Like\n            For Each Act!");

                    makeLOActTable();

                    @SuppressWarnings("deprecation")
                    JScrollPane scroll = JTable.createScrollPaneForTable(loActTable);
                    scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                    scroll.setBounds(40, 300, 1000, 350);

                    loActButton.setText("Continue");
                    loActButton.setFont(font2);
                    loActButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                    loActButton.setBounds(715, 225, 150, 50);

                    mainPanel.add(scroll);
                    mainPanel.add(loActButton);
                    mainPanel.add(speechBubble);
                    mainPanel.updateUI();
                }
                mainPanel.updateUI();
            }
        });

        // Allows user to select learning objectives for each progress act
        contActButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                mainPanel.removeAll();
                if (actSumNode.getNextSibling() != loActNode) {

                    topNode.add(loActNode);
                    DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                    model.reload(mainRoot);

                }
                wizardTree.expandRow(1);

                mainPanel.changeFileName("wizardBackground.png");
                mainPanel.changeCoord(0, -70);

                speechBubble.setFont(font2);
                speechBubble.setBounds(415, 45, 400, 90);
                speechBubble.setText("    Please Select Which Learning\n      Objectives You Would Like\n            For Each Act!");

                makeLOActTable();

                @SuppressWarnings("deprecation")
                JScrollPane scroll = JTable.createScrollPaneForTable(loActTable);
                scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                scroll.setBounds(40, 300, 1000, 350);

                loActButton.setText("Continue");
                loActButton.setFont(font2);
                loActButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                loActButton.setBounds(715, 225, 150, 50);

                mainPanel.add(scroll);
                mainPanel.add(loActButton);
                mainPanel.add(speechBubble);
                mainPanel.updateUI();

            }
        });

        // Allows user to select each question
        loActButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                mainPanel.removeAll();
                // Error Checking
                if (!checkLOActTable()) {
                    printLOActTableError();
                    return;
                }

                if (loActNode.getNextSibling() != quesTableNode) {
                    topNode.add(quesTableNode);
                    DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                    model.reload(mainRoot);
                }

                wizardTree.expandRow(1);
                mainPanel.changeFileName("wizardBackground.png");
                mainPanel.changeCoord(0, -70);

                speechBubble.setFont(font);
                speechBubble.setBounds(415, 45, 400, 90);
                speechBubble.setText("Please Select Your Types\n Of Questions!");

                int numOfQuestions = numOfQuestions();
                makeQuesTable(numOfQuestions);

                @SuppressWarnings("deprecation")
                JScrollPane scroll = JTable.createScrollPaneForTable(questionTable);
                scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                scroll.setBounds(100, 300, 950, 350);

                mainPanel.add(scroll);
                mainPanel.add(speechBubble);
                mainPanel.add(quesButton);
                mainPanel.updateUI();

            }
        });

        // Final summary for the user
        quesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                mainPanel.removeAll();
                mainPanel.changeFileName("wizardBackground.png");
                mainPanel.changeCoord(0, -70);

                if (quesTableNode.getNextSibling() != finalSumNode) {

                    topNode.add(finalSumNode);
                    DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                    model.reload(mainRoot);

                }
                generateFinalSum();

                JScrollPane scroll = new JScrollPane(finalSumPane);
                scroll.setBounds(180, 300, 720, 330);
                scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                wizardTree.expandRow(1);

                speechBubble.setFont(font);
                speechBubble.setBounds(415, 45, 400, 90);
                speechBubble.setText("Here Is A Final Summary!");

                mainPanel.add(scroll);
                mainPanel.add(speechBubble);
                mainPanel.add(finalSumButton);

                mainPanel.updateUI();
            }
        });

        // Allows user to save the game in XML
        finalSumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                mainPanel.removeAll();
                mainPanel.changeFileName("introBackground2.png");
                mainPanel.changeCoord(0, 0);

                if (finalSumNode.getNextSibling() != conclusionNode) {

                    topNode.add(conclusionNode);
                    DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                    model.reload(mainRoot);

                }
                wizardTree.expandRow(1);

                speechBubble.setFont(font);
                speechBubble.setBounds(400, 100, 420, 120);
                speechBubble.setText("Congratulations You Are Done!\nPress Save To Save The Game");

                mainPanel.add(speechBubble);
                mainPanel.add(saveButton);

                mainPanel.updateUI();
            }
        });

        // Saves the game in XML
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Used from InputWizard.java
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Enter a name for the file");
                chooser.setFileFilter(new FileNameExtensionFilter("Game XML", "XML"));
                chooser.setAcceptAllFileFilterUsed(false);

                int retval = chooser.showSaveDialog(null);

                if (retval == JFileChooser.APPROVE_OPTION) {

                    File file = chooser.getSelectedFile();
                    // check for .xml (of any case variation) at the end ($) of
                    // the filename
                    if (!file.getName().matches(".*[.][Xx][Mm][Ll]$")) {
                        System.out.println("didn't match");
                        file = new File(file.getPath() + ".XML");
                    }

                    pathName = file.getPath();
                    System.out.println("saved as " + file.getPath());

                    createGame(1);
                } else {
                    System.out.println("Save command cancelled by user.");
                }

            }

        });

        // If there is an error importing the subject the user selects
        repoErrorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.changeCoord(0, 0);

                speechBubble.setFont(font);
                speechBubble.setBounds(400, 100, 420, 120);
                speechBubble.setText("   Who is This Game For?");

                institutionBox.setBounds(625, 300, 425, 50);
                institutionBox.setFont(font2);
                institutionBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                mainPanel.add(institutionBox);

                domainBox.setBounds(625, 375, 425, 50);
                domainBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                domainBox.setFont(font2);
                domainBox.setEnabled(false);
                mainPanel.add(domainBox);

                gradeBox.setBounds(625, 450, 425, 50);
                gradeBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                gradeBox.setFont(font2);
                gradeBox.setEnabled(false);
                mainPanel.add(gradeBox);

                subjectBox.setBounds(625, 525, 425, 50);
                subjectBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
                subjectBox.setFont(font2);
                subjectBox.setEnabled(false);

                submitButton.setFont(font2);
                submitButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

                mainPanel.add(speechBubble);
                mainPanel.add(subjectBox);
                mainPanel.add(submitButton);

                mainPanel.changeFileName("introBackground2.png");
                mainPanel.updateUI();
            }
        });

        window.add(main);
        window.setVisible(true);

    }

    private void rebuildGameCreationBoxes()
    {
        mainPanel.removeAll();

        mainPanel.changeCoord(0, 0);

        speechBubble.setFont(font);
        speechBubble.setBounds(400, 100, 420, 120);
        speechBubble.setText("   Who is This Game For?");

        int y = 300;
        for (JComboBox<String> currBox: gameCreationBoxes) {
            currBox.setBounds(625, y, 425, 50);
            currBox.setFont(font2);
            currBox.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
            currBox.setEnabled(false);
            currBox.setMaximumRowCount(5);
            mainPanel.add(currBox);

            y += 75;
        }

        institutionBox.setEnabled(true);

        submitButton.setFont(font2);
        submitButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

        mainPanel.add(speechBubble);
        mainPanel.add(submitButton);

        mainPanel.changeFileName("introBackground2.png");
        mainPanel.updateUI();
    }

    // If user adds another set of acts
    public void addMultiply() {

        for (int i = 0; i < 3; i++) {
            Act2 a = new Act2(gametemplate.getActs().get(i));
            gametemplate.getActs().add(a);
        }
    }

    // If user adds another question
    public void addQuestion() {
        // reference thing
        for (int i = 0; i < gametemplate.getActs().size(); i++) {
            if (gametemplate.getActs().get(i).getName().equals("Progress")) {
                Scene2 scene = new Scene2(gametemplate.getActs().get(i).getScenes().get(0));
                gametemplate.getActs().get(i).getScenes().add(scene);
            }
        }

    }

    // Returns number of questions created
    public int numOfQuestions() {
        int multiply = gametemplate.getActs().size() / 3;
        int ques = gametemplate.getActs().get(1).getScenes().size();
        return multiply * ques;
    }

    // Creates game when user hits the save button
    public void createGame(int numOfMuls) {
        Game game = new Game();
        try {
            File file = new File("WizardRepo\\EnvTemplates/" + backgroundBox.getSelectedItem() + "_Template.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Game.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            game = (Game) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for (int i = 1; i < gametemplate.getActs().size() / 3; i++) {
            Game dummy = new Game();

            // imports new template based on environment selected
            try {
                File file = new File("WizardRepo\\EnvTemplates/" + backgroundBox.getSelectedItem() + "_Template.xml");
                JAXBContext jaxbContext = JAXBContext.newInstance(Game.class);

                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                dummy = (Game) jaxbUnmarshaller.unmarshal(file);

            } catch (JAXBException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Adds new acts depending what user selects
            Act a1 = dummy.getActs().get(0);
            Act a2 = dummy.getActs().get(1);
            Act a3 = dummy.getActs().get(2);

            game.getActs().add(a1);
            game.getActs().add(a2);
            game.getActs().add(a3);
        }

        for (int i = 1; i < gametemplate.getActs().size(); i += 3) {
            if (gametemplate.getActs().get(i).getName().equals("Progress")) {
                for (int j = 1; j < gametemplate.getActs().get(i).getScenes()
                        .size(); j++) {
                    Game dummy2 = new Game();
                    try {
                        File file = new File("WizardRepo\\EnvTemplates/" + backgroundBox.getSelectedItem() + "_Template.xml");
                        JAXBContext jaxbContext = JAXBContext.newInstance(Game.class);

                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                        dummy2 = (Game) jaxbUnmarshaller.unmarshal(file);

                    } catch (JAXBException e) {
                        e.printStackTrace();
                    }
                    // Adds new scenes to Progress
                    Scene s = dummy2.getActs().get(1).getScene().get(0);
                    game.getActs().get(i).getScene().add(s);
                }
            }
        }

        int quesIndex = 0;
        for (int i = 0; i < gametemplate.getActs().size(); i++) {
            // Adds learning objectives to acts
            ArrayList<LearningObjectiveType> los = new ArrayList<LearningObjectiveType>();
            for (int currLO = 0; currLO < gametemplate.getActs().get(i).getLearningObjective().size(); currLO++) {

                // EDITED BY DAN
                //LearningObjectiveType toAdd = new LearningObjectiveType();
                //toAdd.setLearningObjective(gametemplate.getActs().get(i)
                //		.getLearningObjective().get(currLO)
                //		.getTaxonomyCategories());

                LearningObjectiveType toAdd = gametemplate.getActs().get(i).getLearningObjective().get(currLO);

                los.add(toAdd);

                //game.getLearningObjectives().add(toAdd);

            }

            // Adds learning objectives to scenes
            game.getActs().get(i).setLearningObjective(los);

            for (int j = 0; j < gametemplate.getActs().get(i).getScenes().size(); j++) {
                game.getActs().get(i).getScene().get(j).setLearningObjective(los);

                for (int k = 0; k < gametemplate.getActs().get(i).getScenes().get(j).getScreens().size(); k++) {

                    // EDITED BY DAN
                    // Adds learning objective to screen
                    //game.getActs().get(i).getScene().get(j).getScreen().get(k)
                    //		.setLearningObjective(los);

                    game.getActs().get(i).getScene().get(j).getScreen().get(k).getLearningObjective().addAll(los);


                    if (gametemplate.getActs().get(i).getScenes().get(j).getScreens().get(k).getType().equals("Question")) {

                        // Question addition
                        QuizChallenge quizChallenge = new QuizChallenge();
                        Vector<Vector<Object>> vector = ((MyTableModel) (questionTable.getModel())).getData();

                        String challengeType = ((String) vector.get(quesIndex).get(1)).substring(0, 6);

                        quesIndex++;

                        // just gets first Learning Objective of act
                        // random number between 0 and size-1 of los

                        Random rand = new Random();
                        //int randLO = rand.nextInt(((los.size() - 1) - 0) + 1) + 0;
                        int randLO = rand.nextInt(los.size() - 1);
                        //String str = los.get(randLO).getLearningObjectives();

                        String str = los.get(randLO).getTaxonomyCategories().get(0);
                        str = str.substring(3, 33);

                        try {
                            File file = new File("WizardRepo\\QuestionRepo/"
                                    + str + "_" + challengeType + ".xml");
                            JAXBContext jaxbContext;
                            jaxbContext = JAXBContext.newInstance(SuggQuestion.class);
                            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                            SuggQuestion sugg = new SuggQuestion();

                            // random number between 0 and
                            sugg = (SuggQuestion) jaxbUnmarshaller.unmarshal(file);

                            // Just get first question
                            //int randQues = rand.nextInt(((sugg.getQuesList().size() - 1) - 0) + 1) + 0;
                            int randQues = rand.nextInt(sugg.getQuesList().size() - 1);
                            quizChallenge = sugg.getQuesList().get(randQues);

                        } catch (JAXBException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        game.getActs().get(i).getScene().get(j).getScreen().get(k).getChallenge().add(quizChallenge);
                    }
                }
            }
        }
        GameExport noth = new GameExport();
        try {
            // exports the game
            try {
                noth.exportGame(game, pathName);

            } catch (SAXException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // creates the learning objective selection for each progress act table
    public void makeLOActTable() {
        int progressActs = gametemplate.getActs().size() / 3;

        Vector<Vector<Object>> vector = new Vector<Vector<Object>>();

        for (int i = 0; i < progressActs; i++) {

            Vector<Object> vector1 = new Vector<Object>();
            vector1.add("Progress " + (i + 1));
            vector1.add(true);

            vector.add(vector1);

            for (int j = 0; j < loList.size(); j++) {
                Vector<Object> vector2 = new Vector<Object>();

                vector2.add(loList.get(j));
                vector2.add(false);
                vector.add(vector2);
            }
        }

        DefaultTableCellRenderer r = new DefaultTableCellRenderer() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
                if (((String) value).charAt(0) == '-') {
                    JTextArea text = new JTextArea();
                    text.setText((String) value);
                    text.setLineWrap(true);
                    text.setWrapStyleWord(true);
                    text.setFont(font4);
                    return text;
                } else {
                    this.setFont(font2);
                }
                return this;

            }
        };

        loActTable = new JTable(new MyTableModel(vector, false, true, false));
        loActTable.getColumnModel().getColumn(0).setCellRenderer(r);
        loActTable.setRowHeight(120);
        loActTable.setShowHorizontalLines(true);
        loActTable.setRowSelectionAllowed(true);
        loActTable.setColumnSelectionAllowed(true);
        loActTable.getColumnModel().getColumn(1).setMaxWidth(75);
        loActTable.getTableHeader().setFont(font3);
        loActTable.setFont(font3);
        loActTable.setGridColor(java.awt.Color.black);
    }

    // Generates screen if there is an error with learning objective progress
    // screen
    public void printLOActTableError() {
        mainPanel.removeAll();

        speechBubble.setBounds(415, 45, 400, 170);
        speechBubble.setText("\n   You Must Select Atleast One\n      Learning Objective for\n Each Progress! Please Click Back!");

        speechBubble.setFont(font2);
        contActButton.setText("Back");
        contActButton.setFont(font2);
        contActButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        contActButton.setBounds(700, 550, 150, 50);

        mainPanel.changeCoord(0, 0);
        mainPanel.add(speechBubble);
        mainPanel.add(contActButton);
        mainPanel.changeFileName("errorBackground.png");

        mainPanel.updateUI();
    }

    // Error checking for learning objective progress table
    public boolean checkLOActTable() {
        Vector<Vector<Object>> vector = ((MyTableModel) (loActTable.getModel())).getData();

        boolean selected = false;
        for (int i = 1; i < vector.size(); i++) {

            if ((boolean) vector.get(i).get(1) && !((String) vector.get(i).get(0)).substring(0, 8).equals("Progress")) {
                selected = true;

            } else if (((String) vector.get(i).get(0)).substring(0, 8).equals("Progress") || i == vector.size() - 1 || i == vector.size()) {
                if (selected) {
                    return false;
                } else {
                    selected = false;
                }
            }
        }

        return true;
    }

    // Generates the question table
    public void makeQuesTable(int numOfQuestions) {
        ArrayList<String> challengesSelected = new ArrayList<String>();

        Vector<Vector<Object>> data2 = ((MyTableModel) challengeTable.getModel()).getData();

        for (int i = 0; i < data2.size(); i++) {
            if ((boolean) data2.get(i).get(1)) {
                challengesSelected.add((String) data2.get(i).get(0));
            }
        }

        ArrayList<String> conditionsSelected = new ArrayList<String>();

        Vector<Vector<Object>> data = ((MyTableModel) conditionsTable.getModel()).getData();

        for (int i = 0; i < data.size(); i++) {
            if ((boolean) data.get(i).get(1)) {
                conditionsSelected.add((String) data.get(i).get(0));
            }
        }

        firstQuesBox = new JComboBox[numOfQuestions];
        secondQuesBox = new JComboBox[numOfQuestions];

        String[] challenges = new String[challengesSelected.size() + 1];
        String[] conditions = new String[conditionsSelected.size() + 1];

        challenges[0] = "Please Select Challenge";
        conditions[0] = "Please Select Condition";

        for (int j = 0; j < challengesSelected.size(); j++) {
            challenges[j + 1] = challengesSelected.get(j);
        }

        for (int j = 0; j < conditionsSelected.size(); j++) {
            conditions[j + 1] = conditionsSelected.get(j);
        }

        Vector<Vector<Object>> vector = new Vector<Vector<Object>>();
        // Adds comboboxes to the table
        for (int i = 0; i < numOfQuestions; i++) {
            firstQuesBox[i] = new JComboBox(challenges);
            secondQuesBox[i] = new JComboBox(conditions);

            firstQuesBox[i].setFont(font2);
            secondQuesBox[i].setFont(font2);

            Vector<Object> vector1 = new Vector<Object>();
            vector1.add("Question " + (i + 1));
            vector1.add(challenges[0]);
            vector1.add(conditions[0]);

            vector.add(vector1);
        }
        // When making comboboxes in table, you must change the cell editor to
        // the combobox
        questionTable = new JTable(new MyTableModel(vector, false, false, true));

        TableColumn challengeColumn = questionTable.getColumnModel().getColumn(1);
        TableColumn conditionsColumn = questionTable.getColumnModel().getColumn(2);

        challengeColumn.setCellEditor(new DefaultCellEditor(firstQuesBox[0]));
        conditionsColumn.setCellEditor(new DefaultCellEditor(secondQuesBox[0]));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");

        challengeColumn.setCellRenderer(renderer);
        conditionsColumn.setCellRenderer(renderer);

        questionTable.setRowHeight(45);
        questionTable.setShowHorizontalLines(true);
        questionTable.setRowSelectionAllowed(true);
        questionTable.setColumnSelectionAllowed(true);
        questionTable.getTableHeader().setFont(font3);
        questionTable.setFont(font2);
        questionTable.setGridColor(java.awt.Color.black);

        questionTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        questionTable.getColumnModel().getColumn(1).setPreferredWidth(490);
        questionTable.getColumnModel().getColumn(2).setPreferredWidth(300);
    }

    // Displays the acts the user chooses
    public void showActs() {
        StyledDocument doc = template.getStyledDocument();

        javax.swing.text.Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        javax.swing.text.Style normal = doc.addStyle("font 2", def);
        StyleConstants.setFontFamily(def, "Comic Sans MS");
        StyleConstants.setFontSize(def, 22);
        StyleConstants.setBold(def, true);
        StyleConstants.setUnderline(def, true);

        javax.swing.text.Style s = doc.addStyle("font 3", normal);
        StyleConstants.setUnderline(s, false);
        StyleConstants.setFontSize(s, 18);
        StyleConstants.setBold(s, false);

        ArrayList<String> challengesSelected = new ArrayList<String>();
        Vector<Vector<Object>> data2 = ((MyTableModel) challengeTable.getModel()).getData();

        for (int i = 0; i < data2.size(); i++) {
            if ((boolean) data2.get(i).get(1)) {
                challengesSelected.add((String) data2.get(i).get(0));
            }
        }
        int challengeIndex = 0;
        try {
            int count = 1;
            int numActs = gametemplate.getActs().size();

            for (int i = 0; i < numActs; i++) {
                doc.insertString(doc.getLength(), gametemplate.getActs().get(i).getName() + "\n", doc.getStyle("font 2"));

                int numScene2s = gametemplate.getActs().get(i).getScenes().size();

                for (int j = 0; j < numScene2s; j++) {
                    doc.insertString(doc.getLength(), "\tScene " + (j + 1) + "\n", doc.getStyle("font 3"));

                    int numScreens = gametemplate.getActs().get(i).getScenes().get(j).getScreens().size();

                    for (int k = 0; k < numScreens; k++) {
                        doc.insertString(doc.getLength(), "\t"
                                        + "\tScreen "
                                        + (k + 1)
                                        + "="
                                        + gametemplate.getActs().get(i).getScenes()
                                        .get(j).getScreens().get(k).getType(),
                                doc.getStyle("font 3"));

                        String type = gametemplate.getActs().get(i).getScenes().get(j).getScreens().get(k).getType();

                        if (type.equals("Question")) {
                            doc.insertString(doc.getLength(), "  " + count + "\n", doc.getStyle("font 3"));

                            count++;

                            doc.insertString(
                                    doc.getLength(),
                                    "\t\t\tChallenge: " + challengesSelected.get(challengeIndex) + "\n",
                                    doc.getStyle("font 3"));

                            if (challengeIndex >= challengesSelected.size() - 1) {
                                challengeIndex = 0;
                            } else {
                                challengeIndex++;
                            }

                        } else {
                            doc.insertString(doc.getLength(), "\n", doc.getStyle("font 3"));
                        }
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        template.setCaretPosition(0);
    }

    // Generates the Final Summary
    public void generateFinalSum() {
        finalSumPane = new JTextPane();
        finalSumPane.updateUI();
        finalSumPane.setEditable(false);

        StyledDocument doc = finalSumPane.getStyledDocument();
        javax.swing.text.Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        javax.swing.text.Style normal = doc.addStyle("font 2", def);

        StyleConstants.setAlignment(normal, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(def, "Comic Sans MS");
        StyleConstants.setFontSize(def, 22);
        StyleConstants.setBold(def, true);
        doc.setParagraphAttributes(0, doc.getLength(), def, false);
        StyleConstants.setUnderline(def, false);

        javax.swing.text.Style s = doc.addStyle("font 1", normal);
        StyleConstants.setFontSize(s, 24);
        StyleConstants.setBold(s, true);
        StyleConstants.setUnderline(s, true);

        javax.swing.text.Style s2 = doc.addStyle("font 3", normal);
        StyleConstants.setUnderline(s2, false);
        StyleConstants.setFontSize(s2, 18);
        StyleConstants.setBold(s2, false);

        javax.swing.text.Style s3 = doc.addStyle("font 4", normal);
        StyleConstants.setUnderline(s3, false);
        StyleConstants.setFontSize(s3, 20);
        StyleConstants.setBold(s3, false);

        MyTableModel tm = (MyTableModel) loActTable.getModel();
        Vector<Vector<Object>> vector = tm.getData();

        MyTableModel tm2 = (MyTableModel) questionTable.getModel();
        Vector<Vector<Object>> vector1 = tm2.getData();

        int numActs = gametemplate.getActs().size();

        ArrayList<String> challengesSelected = new ArrayList<String>();
        Vector<Vector<Object>> data2 = ((MyTableModel) challengeTable.getModel()).getData();

        for (int i = 0; i < data2.size(); i++) {
            if ((boolean) data2.get(i).get(1)) {
                challengesSelected.add((String) data2.get(i).get(0));
            }
        }

        ArrayList<String> conditionsSelected = new ArrayList<String>();
        Vector<Vector<Object>> data = ((MyTableModel) conditionsTable.getModel()).getData();

        for (int i = 0; i < data.size(); i++) {
            if ((boolean) data.get(i).get(1)) {
                conditionsSelected.add((String) data.get(i).get(0));
            }
        }

        int count = 0;
        try {
            // Prints out environment/location
            doc.insertString(doc.getLength(), "Location\n", doc.getStyle("font 1"));

            doc.insertString(doc.getLength(), backgroundBox.getSelectedItem() + "\n\n", doc.getStyle("font 3"));

            doc.insertString(doc.getLength(), "Game Layout\n", doc.getStyle("font 1"));

        } catch (BadLocationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        for (int i = 0; i < numActs; i++) {
            try {
                // Also adds learning objectives to acts for importing into game
                doc.insertString(doc.getLength(), gametemplate.getActs().get(i).getName() + "\n", doc.getStyle("font 2"));

                if (gametemplate.getActs().get(i).getName().equals("Conclusion")) {
                    doc.insertString(doc.getLength(), "\n", doc.getStyle("font 2"));
                }

                if (gametemplate.getActs().get(i).getName().equals("Progress")) {
                    doc.insertString(doc.getLength(), "\tLearning Objectives:\n", doc.getStyle("font 4"));

                    for (int j = count * (loList.size() + 1); j <= (loList.size() * (count + 1)); j++) {

                        if (((String) vector.get(j).get(0)).substring(0, 8).equals("Progress")) {
                            for (int n = 0; n <= loList.size(); n++) {
                                if ((boolean) vector.get(j + n).get(1)) {

                                    // Prints out learning objectives for
                                    // progress acts
                                    if (!((String) vector.get(j + n).get(0)).substring(0, 8).equals("Progress")) {
                                        LearningObjectiveType lo = new LearningObjectiveType();

                                        // EDITED BY DAN
                                        //lo.setLearningObjective((String) v.get(j + n).get(0));
                                        lo.getTaxonomyCategories().add((String) vector.get(j + n).get(0));

                                        gametemplate.getActs().get(i).getLearningObjective().add(lo);

                                        doc.insertString(doc.getLength(), "\t"
                                                + vector.get(j + n).get(0)
                                                + "\n", doc.getStyle("font 3"));
                                    }
                                }
                            }
                        }
                    }
                    count++;
                } else {
                    for (int k = 0; k < loList.size(); k++) {
                        LearningObjectiveType lo = new LearningObjectiveType();

                        // EDITED BY DAN
                        //lo.setLearningObjective(loList.get(k));
                        lo.getTaxonomyCategories().add(loList.get(k));

                        gametemplate.getActs().get(i).getLearningObjective().add(lo);
                    }
                }
            } catch (BadLocationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            // Prints out game questions and specifications to game questions
            doc.insertString(doc.getLength(), "Game Questions\n", doc.getStyle("font 1"));

            for (int i = 0; i < vector1.size(); i++) {
                doc.insertString(doc.getLength(), "Question " + (i + 1) + "\n", doc.getStyle("font 4"));

                if (vector1.get(i).get(1).equals("Please Select Challenge")) {
                    vector1.get(i).set(1, challengesSelected.get(0));
                }
                if (vector1.get(i).get(2).equals("Please Select Condition")) {
                    vector1.get(i).set(2, conditionsSelected.get(0));
                }
                doc.insertString(doc.getLength(), "Challenge = " + vector1.get(i).get(1) + "\n", doc.getStyle("font 3"));
                doc.insertString(doc.getLength(), "Condition = " + vector1.get(i).get(2) + "\n\n", doc.getStyle("font 3"));
            }
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finalSumPane.setEditable(false);
        finalSumPane.setCaretPosition(0);
    }

    // Makes the typical character table
    public JTable constructCharTable() {
        JTable jt;
        Vector<Vector<Object>> vector = new Vector<Vector<Object>>();
        ImageIcon character;
        for (int i = 1; i <= 29; i++) {
            try {
                Vector<Object> vector1 = new Vector<Object>();
                if (i == 16) {
                    character = new ImageIcon(ImageIO.read(new File(
                            "Office, Classroom\\characters/character_" + i
                                    + "/char" + i + "_StandSmileOpen.png")));
                    vector1.add(character);
                    vector1.add(false);
                    vector.add(vector1);

                } else {
                    character = new ImageIcon(ImageIO.read(new File(
                            "Office, Classroom\\characters/character_" + i
                                    + "/char" + i + "_StandSmileClosed.png")));
                    vector1.add(character);
                    vector1.add(false);
                    vector.add(vector1);

                }

            } catch (Exception io) {
                System.out.println("char table prob: " + i);

            }
        }
        jt = new JTable(new MyTableModel(vector, true, false, false));
        jt.setRowHeight(275);
        jt.setRowSelectionAllowed(false);
        jt.getTableHeader().setFont(font3);
        jt.setFont(font2);
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jt.setGridColor(java.awt.Color.black);

        return jt;
    }

    // Checks if there is an error in the learning objective table
    private boolean isErrorLO() {
        MyTableModel tm = (MyTableModel) loTable.getModel();
        Vector<Vector<Object>> v = tm.getData();

        for (int i = 0; i < tm.getRowCount(); i++) {
            if ((boolean) v.get(i).get(1)) {
                if (((String) (v.get(i).get(0))).charAt(0) == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    // Also checks if there is an error in the learning objective table
    private boolean isErrorLearningObjective() {
        MyTableModel tm = (MyTableModel) table.getModel();
        Vector<Vector<Object>> vector = tm.getData();

        int mainCount = 0;
        boolean finalError = false;
        for (int i = 0; i < table.getRowCount(); i++) {
            if ((boolean) vector.get(i).get(1)) {
                if (((String) (vector.get(i).get(0))).charAt(0) == '-') {
                } else {

                    int j = 1;
                    boolean error = true;
                    while (((String) (vector.get(i + j).get(0))).charAt(0) == '-') {
                        if ((boolean) vector.get(i + j).get(1)) {
                            error = false;
                        }
                        j++;
                    }
                    if (error) {
                        finalError = true;
                        break;
                    }
                    mainCount++;
                }
            }
        }
        if (mainCount == 0 || finalError) {
            return true;
        } else {
            return false;
        }
    }

    // Shows error screen if no learning taxonmy selected
    private void printErrorLearningTaxonomy() {
        mainPanel.removeAll();

        speechBubble.setBounds(415, 45, 400, 170);
        speechBubble.setText("\n      You Must Select A \n          Taxonomy!\n       Please Click Back!");
        speechBubble.setFont(font);

        taxBackButton.setBounds(700, 550, 150, 50);

        mainPanel.changeCoord(0, 0);
        mainPanel.add(speechBubble);
        mainPanel.add(taxBackButton);
        mainPanel.changeFileName("errorBackground.png");

        mainPanel.updateUI();
    }

    // Shows error screen if there are learning objective errors, meaning user
    // fails to select learning objectives
    private void printErrorLO() {
        mainPanel.removeAll();

        speechBubble.setBounds(415, 45, 400, 170);
        speechBubble.setText("\n      You Must Select A \n      Learning Objective!\n       Please Click Back!");
        speechBubble.setFont(font);

        summaryBack.setBounds(700, 550, 150, 50);

        mainPanel.changeCoord(0, 0);
        mainPanel.add(speechBubble);
        mainPanel.add(summaryBack);
        mainPanel.changeFileName("errorBackground.png");

        mainPanel.updateUI();
    }

    // Shows error screen if there are learning objective table errors, meaning
    // user fails to select learning objectives
    private void printErrorLearningObjective() {
        mainPanel.removeAll();

        speechBubble.setBounds(415, 45, 400, 170);
        speechBubble.setText("\n      You Must Select A \n        Main/Sub Objective!\n       Please Click Back!");
        speechBubble.setFont(font);

        summaryBack.setBounds(700, 550, 150, 50);

        mainPanel.changeCoord(0, 0);
        mainPanel.add(speechBubble);
        mainPanel.add(summaryBack);
        mainPanel.changeFileName("errorBackground.png");

        mainPanel.updateUI();
    }

    // If user fails to select a location
    public boolean isLocationError() {
        if (backgroundBox.getSelectedIndex() == 0) {
            return true;
        } else {
            return false;
        }
    }

    // Prints Location Error
    public void printLocationError() {
        mainPanel.removeAll();

        speechBubble.setBounds(415, 45, 400, 170);
        speechBubble.setText("\n      You Must Select A \n          Location!\n       Please Click Back!");
        speechBubble.setFont(font);

        fullSumContinue.setBounds(700, 550, 150, 50);
        fullSumContinue.setText("Back");

        mainPanel.changeCoord(0, 0);
        mainPanel.add(speechBubble);
        mainPanel.add(fullSumContinue);
        mainPanel.changeFileName("errorBackground.png");

        mainPanel.updateUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }

    // Checks to see if user fails to select a challenge from table
    private boolean isErrorChallenge() {
        MyTableModel tm = (MyTableModel) challengeTable.getModel();
        Vector<Vector<Object>> v = tm.getData();

        boolean empty = true;
        for (int i = 0; i < v.size(); i++) {
            if ((boolean) v.get(i).get(1)) {
                empty = false;
            }
        }
        if (empty) {
            return true;
        } else {
            return false;
        }
    }

    // Shows error screen if user fails to select challenge from table
    private void printErrorChallenge() {
        mainPanel.removeAll();

        speechBubble.setBounds(415, 45, 400, 170);
        speechBubble.setText("\n      You Must Select A \n             Challenge!\n       Please Click Back!");
        speechBubble.setFont(font);

        challengeErrorBack.setBounds(700, 550, 150, 50);

        mainPanel.changeCoord(0, 0);
        mainPanel.add(speechBubble);
        mainPanel.add(challengeErrorBack);
        mainPanel.changeFileName("errorBackground.png");

        mainPanel.updateUI();
    }

    // Makes the challenge table
    private Vector<Vector<Object>> generateChallengeTable() {
        MyTableModel tm = (MyTableModel) subTaxTable.getModel();

        Vector<Vector<Object>> v = tm.getData();
        Vector<Vector<Object>> v2 = new Vector<Vector<Object>>();

        boolean hit = false;

        if ((boolean) v.get(0).get(1)
                && (boolean) v.get(1).get(1)
                && (boolean) v.get(2).get(1)
                && (boolean) v.get(3).get(1)
                && (boolean) v.get(5).get(1)) {
            hit = true;

            Vector<Object> v1 = new Vector<Object>();
            v1.add("Composition (K,C,Ap,An,E) -evaluate, create & consequence");
            v1.add(false);
            v2.add(v1);
        }

        if ((boolean) v.get(0).get(1)
                && (boolean) v.get(1).get(1)
                && (boolean) v.get(2).get(1)
                && (boolean) v.get(4).get(1)
                && (boolean) v.get(5).get(1)) {
            hit = true;

            Vector<Object> v1 = new Vector<Object>();
            v1.add("Deliberation (K,C,Ap,S,E) -drag and drop & consequence");
            v1.add(false);
            v2.add(v1);
        }
        if ((boolean) v.get(0).get(1)
                && (boolean) v.get(1).get(1)
                && (boolean) v.get(2).get(1)) {
            hit = true;

            Vector<Object> v1 = new Vector<Object>();
            v1.add("Dialog (K,C,Ap) -question/answer/& follow-up");
            v1.add(false);
            v2.add(v1);
        }
        if (!hit) {

            Vector<Object> v1 = new Vector<Object>();
            v1.add("Dialog (K,C,Ap) -question/answer/& follow-up");
            v1.add(false);
            v2.add(v1);
        }
        return v2;
    }

    // Runs the wizard GUI
    public static void main(String[] args) {
        Wizard wiz = new Wizard();
    }

    // Generates the first summary text pane
    public void generateSummary() {
        mainPanel.removeAll();

        mainPanel.changeFileName("wizardBackground.png");
        mainPanel.changeCoord(0, -70);

        summary = new JTextPane();
        summary.setAlignmentX(Component.CENTER_ALIGNMENT);
        summary.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

        StyledDocument doc = summary.getStyledDocument();

        javax.swing.text.Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        javax.swing.text.Style normal = doc.addStyle("font 2", def);

        StyleConstants.setAlignment(def, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), def, false);
        StyleConstants.setFontFamily(def, "Comic Sans MS");
        StyleConstants.setFontSize(def, 22);
        StyleConstants.setUnderline(def, false);
        StyleConstants.setBold(def, true);

        javax.swing.text.Style s = doc.addStyle("font 3", normal);
        StyleConstants.setFontSize(s, 18);
        StyleConstants.setBold(s, false);

        MyTableModel tm = (MyTableModel) conditionsTable.getModel();
        Vector<Vector<Object>> v = tm.getData();

        int mainCount = 0;
        try {
            doc.insertString(doc.getLength(), "Institution:\n", doc.getStyle("font 2"));
            doc.insertString(doc.getLength(), institutionBox.getSelectedItem() + "\n", doc.getStyle("font 3"));

            doc.insertString(doc.getLength(), "Domain:\n", doc.getStyle("font 2"));
            doc.insertString(doc.getLength(), domainBox.getSelectedItem() + "\n", doc.getStyle("font 3"));

            doc.insertString(doc.getLength(), "Level:\n", doc.getStyle("font 2"));
            doc.insertString(doc.getLength(), gradeBox.getSelectedItem() + "\n", doc.getStyle("font 3"));

            doc.insertString(doc.getLength(), "Content:\n", doc.getStyle("font 2"));
            doc.insertString(doc.getLength(), subjectBox.getSelectedItem() + "\n", doc.getStyle("font 3"));

        } catch (Exception e1) {
            System.out.println("Font does not Exist!");
        }
        MyTableModel tmLO = (MyTableModel) table.getModel();
        Vector<Vector<Object>> vLO = tmLO.getData();

        for (int i = 0; i < table.getRowCount(); i++) {
            if ((boolean) vLO.get(i).get(1)) {
                if (((String) (vLO.get(i).get(0))).charAt(0) == '-') {
                    try {
                        doc.insertString(doc.getLength(), vLO.get(i).get(0) + "\n", doc.getStyle("font 3"));

                    } catch (Exception e1) {
                        System.out.println("Font does not Exist!");
                    }
                } else {
                    try {
                        doc.insertString(doc.getLength(), vLO.get(i).get(0) + "\n", doc.getStyle("font 2"));

                    } catch (Exception e1) {
                        System.out.println("Font does not Exist!");
                    }
                }
            }
        }
        try {
            doc.insertString(doc.getLength(), "BLOOMS Learning Taxonomy\n", doc.getStyle("font 2"));
            doc.insertString(doc.getLength(), "-Knowledge (K)\n", doc.getStyle("font 3"));
            doc.insertString(doc.getLength(), "-Comprehension (C)\n", doc.getStyle("font 3"));
            doc.insertString(doc.getLength(), "-Application (AP)\n", doc.getStyle("font 3"));
            doc.insertString(doc.getLength(), "-Analysis (An)\n", doc.getStyle("font 3"));
            doc.insertString(doc.getLength(), "-Synthesis (S)\n", doc.getStyle("font 3"));
            doc.insertString(doc.getLength(), "-Evaluation (E)\n", doc.getStyle("font 3"));

        } catch (Exception e1) {
            System.out.println("Font does not Exist!");
        }

        try {
            doc.insertString(doc.getLength(), "Challenge\n", doc.getStyle("font 2"));

            MyTableModel tm2 = (MyTableModel) challengeTable.getModel();
            Vector<Vector<Object>> v1 = tm2.getData();

            for (int i = 0; i < v1.size(); i++) {
                if ((boolean) (v1.get(i).get(1))) {
                    doc.insertString(doc.getLength(), v1.get(i).get(0) + "\n", doc.getStyle("font 3"));

                }
            }
        } catch (Exception e1) {
            System.out.println("Font does not Exist!");
        }
        try {
            doc.insertString(doc.getLength(), "Conditions\n", doc.getStyle("font 2"));

        } catch (Exception e1) {
            System.out.println("Font does not Exist!");
        }

        for (int i = 0; i < conditionsTable.getRowCount(); i++) {
            if ((boolean) v.get(i).get(1)) {
                try {
                    doc.insertString(doc.getLength(), v.get(i).get(0) + "\n", doc.getStyle("font 3"));

                } catch (Exception e1) {
                    System.out.println("Font does not Exist!");
                }
                mainCount++;
            }
        }
        conditionErrorBack.setText("Back");
        fullSumContinue.setFont(font2);
        conditionErrorBack.setFont(font2);

        fullSumContinue.setBounds(700, 225, 150, 50);
        conditionErrorBack.setBounds(520, 225, 150, 50);

        fullSumContinue.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));
        fullSumContinue.setText("Continue");
        conditionErrorBack.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

        // Error Checking
        if (mainCount == 0) {
            mainPanel.removeAll();

            speechBubble.setBounds(415, 45, 400, 170);
            speechBubble.setText("\n      You Must Select A \n             Condition!\n       Please Click Back!");
            speechBubble.setFont(font);

            conditionErrorBack.setBounds(700, 550, 150, 50);

            mainPanel.changeCoord(0, 0);
            mainPanel.add(speechBubble);
            mainPanel.add(conditionErrorBack);
            mainPanel.changeFileName("errorBackground.png");

            mainPanel.updateUI();
        } else {
            if (conditionsNode.getNextSibling() != lowerSummaryNode) {
                rootNode.add(lowerSummaryNode);
                DefaultTreeModel model = (DefaultTreeModel) wizardTree.getModel();
                model.reload(mainRoot);
            }
            wizardTree.expandRow(0);

            summary.setEditable(false);
            summary.setCaretPosition(0);

            JScrollPane scroll = new JScrollPane(summary);
            scroll.setBounds(180, 300, 720, 330);
            scroll.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, java.awt.Color.GRAY));

            speechBubble.setBounds(415, 45, 400, 90);
            speechBubble.setText("Here is a Summary of Your \n      Game Thus Far!!!");
            speechBubble.setFont(font);

            mainPanel.add(speechBubble);
            mainPanel.add(fullSumContinue);
            mainPanel.add(conditionErrorBack);
            mainPanel.add(scroll);

            treePanel.updateUI();
            mainPanel.updateUI();
        }
    }

    // Creation of the table
    class MyTableModel extends AbstractTableModel {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private String[] columnNames = {"Learning Objective", "Include"};
        private String[] columnNamesImages = {"Image", "Type", "Include"};
        private String[] columnNamesQuestions = {"Question", "Challenge", "Condition"};

        private Vector<Vector<Object>> data;

        public boolean isLOTable;
        public boolean isImageTable;
        public boolean isQuesTable;

        public MyTableModel(Vector<Vector<Object>> v, boolean b, boolean b2, boolean b3) {
            data = v;
            // For characters
            isImageTable = b;
            // For one string and check box
            isLOTable = b2;
            // For question table with comboboxes
            isQuesTable = b3;
        }

        public Vector<Vector<Object>> getData() {
            return data;
        }

        @Override
        public int getColumnCount() {
            if (isImageTable) {
                return columnNamesImages.length;
            }
            if (isQuesTable) {
                return columnNamesQuestions.length;
            }
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        // Used for error checking
        public boolean nothingSelected() {
            for (int i = 0; i < data.size(); i++) {
                if ((Boolean) data.get(i).get(1)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String getColumnName(int col) {
            if (isImageTable) {
                return columnNamesImages[col];
            }
            if (isQuesTable) {
                return columnNamesQuestions[col];
            }
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            return (data.get(row)).get(col);
        }

        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            // Note that the data/cell address is constant,
            // No matter where the cell appears on screen.
            if (isImageTable) {

                return false;

            }

            if (isQuesTable && col > 0) {

                return true;

            }

            if (isQuesTable && col == 0) {
                return false;
            }

            if (isLOTable) {
                if (col == 1 && row == 0) {
                    return false;
                } else if (col == 1 && ((String) (data.get(row)).get(0)).substring(0, 8).equals("Progress")) {
                    return false;
                }
            }
            if (col < 1) {
                return false;
            } else {
                return true;
            }
        }

        public void makeImageTable() {
            isImageTable = true;
        }

        public void fillFalse() {
            for (int i = 0; i < data.size(); i++) {
                data.get(i).set(1, false);
            }
        }

        // Used to create sub-learning objective tables
        public Object[][] findLearningObjective(String name) {
            int i = 0;
            boolean x = false;
            Object[][] data2 = null;

            for (i = 0; i < knowRepo.getKnowledgeAreas().size(); i++) {
                String knowledgeAreaName = knowRepo.getKnowledgeAreas().get(i).getName();

                if (knowledgeAreaName.equals(name)) {
                    x = true;
                    knowledgeAreaName = knowRepo.getKnowledgeAreas().get(i).getName();
                    break;
                }
            }
            if (x) {
                data2 = new Object[knowRepo.getKnowledgeAreas().get(i).getSubKnowledgeArea().size()][2];

                for (int j = 0; j < knowRepo.getKnowledgeAreas().get(i).getSubKnowledgeArea().size(); j++) {
                    data2[j][0] = knowRepo.getKnowledgeAreas().get(i).getSubKnowledgeArea().get(j).getName();
                    data2[j][1] = false;
                }
            }
            return data2;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (isLOTable) {
                if ((Boolean) (data.get(row)).get(col)) {

                    data.get(row).set(col, false);
                    fireTableCellUpdated(row, col);
                    mainPanel.updateUI();

                    return;
                } else {

                    data.get(row).set(col, true);
                    fireTableCellUpdated(row, col);
                    mainPanel.updateUI();

                    return;
                }
            }

            if (isQuesTable) {
                if (col == 0) {
                    return;
                } else {

                    data.get(row).set(col, value);
                    fireTableCellUpdated(row, col);
                    mainPanel.updateUI();

                    return;
                }
            }

            if (col < 1) {
                return;
            } else {
                String name = (String) data.get(row).get(0);
                if (!((Boolean) (data.get(row)).get(col)) && !(name.charAt(0) == '-')) {
                    data.get(row).set(col, true);
                    Object[][] subLearning = findLearningObjective(name);

                    if (subLearning != null) {
                        for (int i = 0; i < subLearning.length; i++) {
                            Vector<Object> vector = new Vector<Object>();
                            vector.add(subLearning[i][0]);
                            vector.add(subLearning[i][1]);

                            data.add(row + 1 + i, vector);
                        }
                    }
                    mainPanel.updateUI();

                } else if (name.charAt(0) == '-') {
                    if ((Boolean) (data.get(row)).get(col)) {

                        data.get(row).set(col, false);
                        fireTableCellUpdated(row, col);
                        mainPanel.updateUI();

                    } else {

                        data.get(row).set(col, true);
                        fireTableCellUpdated(row, col);
                        mainPanel.updateUI();

                    }
                } else {
                    int i = 1;
                    data.get(row).set(col, false);
                    while (i + row < data.size() && ((String) data.get(row + i).get(0)).charAt(0) == '-') {
                        data.remove(row + i);
                    }
                    fireTableCellUpdated(row, col);
                    mainPanel.updateUI();
                }
            }
            fireTableCellUpdated(row, col);
        }
    }
}