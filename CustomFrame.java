    package com.mycompany.medicinestock;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import javax.swing.table.DefaultTableModel;
    import java.io.*;
    import java.util.logging.Level;
    import java.util.logging.Logger;
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;
    import javax.swing.table.JTableHeader;

    public class CustomFrame extends JFrame {
        
        private JPanel middlePanel;
        private JPanel doctorPanel;
        private JPanel patientPanel;
        private JTable doctorTable;
        private JTextField nameField, emailField, passwordField;
        private JTextField patientNameField, symptomsField;
       private DefaultTableModel tableModel;
    private DefaultTableModel patientTableModel;
        public static String DATA_FILE = System.getProperty("user.home") + File.separator + "DoctorDatabase.txt";
        public static String AccDatabase = System.getProperty("user.home") + File.separator+"AccountDataBase.txt";
         public static String PATIENT_DATA_FILE = System.getProperty("user.home") + File.separator + "PatientDatabase.txt";

        public JButton doctorsButton;
            public JButton patientsButton;
    private  JTable patientTable;
    private final JTextField FullnameField;

        public CustomFrame() {
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
            gbc.fill = GridBagConstraints.HORIZONTAL;
      
    setTitle("Hospital Management System");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setLayout(new BorderLayout());

// Create the navigation panel on the left
        JPanel navPanel = new JPanel();
    navPanel.setLayout(new GridLayout(5, 1, 5, 5)); // Reduce gaps for smaller buttons
    navPanel.setBackground(new Color(173, 216, 230)); // Light blue color

// Create navigation buttons
        JButton doctorsButton = createNavButton("DOCTORS", "doctor_icon.png");
    JButton patientsButton = createNavButton("PATIENTS", "patient_icon.png");
    JButton productButton = createNavButton("PRODUCT", "product_icon.png");
    JButton stockLogsButton = createNavButton("STOCK LOGS", "logs_icon.png");
    JButton logOutButton = createNavButton("LOG OUT", "logout_icon.png");
    



    // Add buttons to the navigation panel
    navPanel.add(doctorsButton);
    navPanel.add(patientsButton);
    navPanel.add(productButton);
    navPanel.add(stockLogsButton);
    navPanel.add(logOutButton);

// Add the navigation panel to the JFrame
add(navPanel, BorderLayout.WEST);
            // Create the middle panel with a welcome message
            middlePanel = new JPanel();
            middlePanel.setBackground(new Color(200, 230, 201)); // Light green color
            JLabel welcomeLabel = new JLabel("Welcome", SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
            middlePanel.setLayout(new BorderLayout());
            middlePanel.add(welcomeLabel, BorderLayout.CENTER);

            // Create the doctor panel (initially hidden)
            doctorPanel = new JPanel(new BorderLayout());
            doctorPanel.setVisible(false);
            patientPanel = new JPanel(new BorderLayout());
            patientPanel.setVisible(false);
            
            //Patient Table
            String [] patientNames = {"Patient ID", "Full Name", "Symptoms", "Status"};
            patientTableModel = new DefaultTableModel(patientNames,0){
                @Override
             public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
            patientTable = new JTable(patientTableModel);
             patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane1 = new JScrollPane(patientTable);
        JTableHeader tableHeader1 = patientTable.getTableHeader();
            tableHeader1.setFont(new Font("Arial", Font.BOLD, 20));
             loadPatientData();
        
             JPanel formPanel1 = new JPanel(new GridBagLayout());
              formPanel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            formPanel1.setBackground(Color.decode("#e4f2fd"));
            // Doctor Table
            String[] columnNames = {"Doctor ID", "Name", "Email", "Status"};
            tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            doctorTable = new JTable(tableModel);
            doctorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane tableScrollPane = new JScrollPane(doctorTable);
            JTableHeader tableHeader = doctorTable.getTableHeader();
            tableHeader.setFont(new Font("Arial", Font.BOLD, 20));

            
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST; // Align label to the right
            JLabel FULLnameJLabel = new JLabel("Full Name");
            FULLnameJLabel.setFont(new Font("Arial", Font.BOLD, 16));
            formPanel1.add(FULLnameJLabel, gbc);
            
             gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST; // Align field to the left
            FullnameField = new JTextField(15); // Slim text field to fit 15 characters
            formPanel1.add(FullnameField, gbc);
            
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.EAST;
            JLabel Symptoms = new JLabel("Symptoms");
            Symptoms.setFont(new Font("Arial", Font.BOLD, 16));
            formPanel1.add(Symptoms, gbc);
            
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            symptomsField = new JTextField(15);
            formPanel1.add(symptomsField, gbc);
            
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.EAST;
            JLabel statusLabel1 = new JLabel("STATUS");
            statusLabel1.setFont(new Font("Arial", Font.BOLD, 16));
            formPanel1.add(statusLabel1, gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            String[] options1 = {"Alive", "Deceased"};
            JComboBox<String> comboBox1 = new JComboBox<>(options1);
            formPanel1.add(comboBox1, gbc);
            
            
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 2; // Span across two columns
            gbc.anchor = GridBagConstraints.CENTER;
            JPanel buttonPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
            buttonPanel1.setBackground(Color.decode("#e4f2fd"));
            JButton updateButton1 = new JButton("Update");
            JButton saveButton1 = new JButton("Save");
            JButton deleteButton1 = new JButton("Delete");
            JButton clearButton1 = new JButton("Clear");
            buttonPanel1.add(updateButton1);
            buttonPanel1.add(saveButton1);
            buttonPanel1.add(deleteButton1);
            buttonPanel1.add(clearButton1);
            formPanel1.add(buttonPanel1, gbc);
            
            
            patientPanel.add(tableScrollPane1, BorderLayout.CENTER);
            patientPanel.add(formPanel1, BorderLayout.EAST);
            
            // Load data from file
            loadData();

         // Create formPanel for the doctor panel
JPanel formPanel = new JPanel(new GridBagLayout());
formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
formPanel.setBackground(Color.decode("#e4f2fd"));

GridBagConstraints gbc1 = new GridBagConstraints();
gbc1.insets = new Insets(5, 5, 5, 5); // Add padding around components

// Name Label and Field for doctor
gbc1.gridx = 0;
gbc1.gridy = 0;
gbc1.anchor = GridBagConstraints.EAST; // Align label to the right
JLabel nameLabel = new JLabel("NAME");
nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
formPanel.add(nameLabel, gbc1);

gbc1.gridx = 1;
gbc1.anchor = GridBagConstraints.WEST; // Align field to the left
nameField = new JTextField(15); // Slim text field to fit 15 characters
formPanel.add(nameField, gbc1);

// Email Label and Field for doctor
gbc1.gridx = 0;
gbc1.gridy = 1;
gbc1.anchor = GridBagConstraints.EAST;
JLabel emailLabel = new JLabel("EMAIL");
emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
formPanel.add(emailLabel, gbc1);

gbc1.gridx = 1;
gbc1.anchor = GridBagConstraints.WEST;
emailField = new JTextField(15);
formPanel.add(emailField, gbc1);

// Wrong Email Label for doctor
gbc1.gridx = 1;
gbc1.gridy = 2; // Position below the email field
gbc1.anchor = GridBagConstraints.WEST;
JLabel wrongEmailLabel = new JLabel("Invalid email format");
wrongEmailLabel.setForeground(Color.RED);
wrongEmailLabel.setVisible(false);
formPanel.add(wrongEmailLabel, gbc1);

// Password Label and Field for doctor
gbc1.gridx = 0;
gbc1.gridy = 3;
gbc1.anchor = GridBagConstraints.EAST;
JLabel passwordLabel = new JLabel("PASSWORD");
passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
formPanel.add(passwordLabel, gbc1);

gbc1.gridx = 1;
gbc1.anchor = GridBagConstraints.WEST;
passwordField = new JTextField(15);
formPanel.add(passwordField, gbc1);

// Status Label and Combo Box for doctor
gbc1.gridx = 0;
gbc1.gridy = 4;
gbc1.anchor = GridBagConstraints.EAST;
JLabel statusLabel = new JLabel("STATUS");
statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
formPanel.add(statusLabel, gbc1);

gbc1.gridx = 1;
gbc1.anchor = GridBagConstraints.WEST;
String[] options = {"Active", "Inactive"};
JComboBox<String> comboBox = new JComboBox<>(options);
formPanel.add(comboBox, gbc1);

// Button Panel (Save, Update, Delete) for doctor
gbc1.gridx = 0;
gbc1.gridy = 5;
gbc1.gridwidth = 2; // Span across two columns
gbc1.anchor = GridBagConstraints.CENTER;
JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
buttonPanel.setBackground(Color.decode("#e4f2fd"));
JButton updateButton = new JButton("Update");
JButton saveButton = new JButton("Save");
JButton deleteButton = new JButton("Delete");
JButton clearButton = new JButton("Clear");
buttonPanel.add(updateButton);
buttonPanel.add(saveButton);
buttonPanel.add(deleteButton);
buttonPanel.add(clearButton);
formPanel.add(buttonPanel, gbc1);

// Add table and form to doctorPanel
doctorPanel.add(tableScrollPane, BorderLayout.CENTER);
doctorPanel.add(formPanel, BorderLayout.EAST);


            // Add ActionListener to Doctors button
            
    
           doctorsButton.addActionListener(e -> {
    middlePanel.setVisible(false);
    patientPanel.setVisible(false);
    doctorPanel.setVisible(true);
    add(doctorPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
}); 
            patientsButton.addActionListener(e -> {
    middlePanel.setVisible(false);
    doctorPanel.setVisible(false);
    patientPanel.setVisible(true);  
    add(patientPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
});
            logOutButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    int a = JOptionPane.showConfirmDialog(null, "Do you want to log out?", "Select", JOptionPane.YES_NO_OPTION);
                    if (a == 0) {
                         dispose();
                        new Login().setVisible(true);
                        setVisible(false);
                       
                        
                       
                    }
                }
            });
            productButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                  
                   
                    
                }
            });
            
            stockLogsButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                
            }
            });
        
            saveButton1.addActionListener(e -> {
        if (patientNameField.getText().trim().isEmpty() || symptomsField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Text fields cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else {
            patientTableModel.addRow(new Object[]{
                patientTableModel.getRowCount() + 1, // Auto increment row number
                patientNameField.getText(),
                symptomsField.getText(),
                comboBox1.getSelectedItem()
            });
            savePatientData();
            clearPatientFields();
        }
    });
     
    
updateButton1.addActionListener(e -> {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow != -1) {
            patientTableModel.setValueAt(patientNameField.getText(), selectedRow, 1);
            patientTableModel.setValueAt(symptomsField.getText(), selectedRow, 2);
            patientTableModel.setValueAt(comboBox1.getSelectedItem(), selectedRow, 3);
            savePatientData();
        }
    });
            
           deleteButton1.addActionListener(e -> {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow != -1) {
            patientTableModel.removeRow(selectedRow);
            savePatientData();
            clearPatientFields();
        }
    });
            clearButton1.addActionListener(e -> clearPatientFields());


            
              

           

            // Add ListSelectionListener to the table
            doctorTable.getSelectionModel().addListSelectionListener(event -> {
                int selectedRow = doctorTable.getSelectedRow();
                if (selectedRow != -1) {
                    nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    emailField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    comboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 3).toString());
                }
            });

            // Add action to Update button
            updateButton.addActionListener(e -> {
                int selectedRow = doctorTable.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.setValueAt(nameField.getText(), selectedRow, 1);
                    tableModel.setValueAt(emailField.getText(), selectedRow, 2);
                    tableModel.setValueAt(comboBox.getSelectedItem(), selectedRow, 3);
                    saveData();
                }
            });


            // Add action to Save button
          saveButton.addActionListener(e -> {
    if (nameField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Text field/s cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
    } else if (isEmailInFile(emailField.getText(), DATA_FILE)) {
        JOptionPane.showMessageDialog(this, "The email already exists!", "Input Error", JOptionPane.ERROR_MESSAGE);
    } else {
        // Add the account to the table model
        tableModel.addRow(new Object[]{
            tableModel.getRowCount() + 1, // Auto increment row number
            nameField.getText(),
            emailField.getText(),
            comboBox.getSelectedItem()
        });
 saveData();
        // Save the account data with the correct password
        saveAccData(emailField.getText(), passwordField.getText());

        
        // Clear the input fields for the next entry
        clearFields();

        // Save the data to the main data file
       
    }
});

            // Add action to Delete button
           deleteButton.addActionListener(e -> {
    int selectedRow = doctorTable.getSelectedRow();
    if (selectedRow != -1) {
        // Get the email of the selected account (assuming the email is in the second column, index 1)
        String emailToDelete = (String) tableModel.getValueAt(selectedRow, 2);

        // Remove the row from the table model
        tableModel.removeRow(selectedRow);

        // Delete the account from the database file
        deleteAccountFromDatabase(emailToDelete);

        // Clear the input fields
        clearFields();

        // Save the updated data
        saveData();
    }
});


            //Add action to clear the text fields
            clearButton.addActionListener(e ->{
                clearFields();
            });

            // Add panels to the JFrame
            add(navPanel, BorderLayout.WEST);
            add(middlePanel, BorderLayout.CENTER);

            // Make the frame visible
            setVisible(true);


            nameField.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if(!Character.isLetter(c)&&!Character.isISOControl(c)){
                    evt.consume();
                }
            }

            });

        emailField.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyReleased(java.awt.event.KeyEvent evt) {
            // Correct email validation pattern
            String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            Pattern patt = Pattern.compile(pattern);
            Matcher match = patt.matcher(emailField.getText());

            // Show or hide the wrongEmailLabel based on validation
            if (!match.matches()) {
                wrongEmailLabel.setVisible(true);
            } else {
                wrongEmailLabel.setVisible(false);
            }
            formPanel.revalidate();
            formPanel.repaint();
        }
    });

         




        }
        
       
        
        
        
         
        
        private void clearFields() {
            nameField.setText("");
            emailField.setText("");
            passwordField.setText("");
            
            patientNameField.setText("");
            symptomsField.setText("");
        }
        
        
       private void deleteAccountFromDatabase(String emailToDelete) {
    File tempFile = new File(AccDatabase + "_temp");
    File originalFile = new File(AccDatabase);
    
    try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
         
        String line;
        boolean accountFound = false;

        while ((line = reader.readLine()) != null) {
            String[] accountData = line.split(",");
            if (accountData.length > 0 && !accountData[0].equalsIgnoreCase(emailToDelete)) {
                writer.write(line);
                writer.newLine();
            } else {
                accountFound = true;
            }
        }

        if (!accountFound) {
            JOptionPane.showMessageDialog(this, 
                "No account found with email: " + emailToDelete, 
                "Delete Error", 
                JOptionPane.WARNING_MESSAGE);
        }
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Error while deleting account: " + e.getMessage(), 
            "Delete Error", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Replace original file with updated file
    if (originalFile.delete() && tempFile.renameTo(originalFile)) {
        JOptionPane.showMessageDialog(this, 
            "Account deleted successfully!", 
            "Success", 
            JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, 
            "Failed to update the database file.", 
            "File Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}


       public static boolean isEmailInFile(String email, String path) {
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3 && parts[2].trim().equalsIgnoreCase(email.trim())) { 
                return true;
            }
        }
    } catch (IOException e) {
        System.err.println("An error occurred while reading the file: " + e.getMessage());
    }
    return false; 
}


       private void saveAccData(String email, String password) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(AccDatabase, true))) {
        if (!email.isEmpty() && !password.isEmpty()) {
            String line = email + "," + password;
            writer.write(line);
            writer.newLine();
        }
        System.out.println("Account data saved successfully to " + AccDatabase);
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saving account data: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
    }
}
        

        private void saveData() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String line = tableModel.getValueAt(i, 0) + "," +
                          tableModel.getValueAt(i, 1) + "," +
                          tableModel.getValueAt(i, 2) + "," +
                          tableModel.getValueAt(i, 3);
            writer.write(line);
            writer.newLine();
        }
        System.out.println("Data saved successfully to " + DATA_FILE);
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(),
                "Save Error", JOptionPane.ERROR_MESSAGE);
    }

    // Save email and password to AccDatabase
    
}

        private void loadData() {
            try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        tableModel.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3]});
                    }
                }
                System.out.println("Data loaded successfully from " + DATA_FILE);
            } catch (FileNotFoundException e) {
                System.out.println("Data file not found, starting fresh.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     
        // Method to create a navigation button
     private JButton createNavButton(String text, String iconPath) {
    JButton button = new JButton(text);
    button.setLayout(new BorderLayout());
    button.setHorizontalAlignment(SwingConstants.CENTER); // Center align
    button.setBackground(new Color(173, 216, 230)); // Light blue
    button.setForeground(Color.BLACK); // Black text
    button.setFocusPainted(false);
    button.setFont(new Font("Arial", Font.BOLD, 18)); // Larger bold font
    return button;
}

        public static void main(String[] args) {
        
            
            
            
        }

    private void loadPatientData() {
    try (BufferedReader reader = new BufferedReader(new FileReader(PATIENT_DATA_FILE))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                patientTableModel.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3]});
            }
        }
        System.out.println("Patient data loaded successfully from " + PATIENT_DATA_FILE);
    } catch (FileNotFoundException e) {
        System.out.println("Patient data file not found, starting fresh.");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private void savePatientData() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATIENT_DATA_FILE))) {
        for (int i = 0; i < patientTableModel.getRowCount(); i++) {
            String line = patientTableModel.getValueAt(i, 0) + "," +
                          patientTableModel.getValueAt(i, 1) + "," +
                          patientTableModel.getValueAt(i, 2) + "," +
                          patientTableModel.getValueAt(i, 3);
            writer.write(line);
            writer.newLine();
            
            
        }
        System.out.println("Patient data saved successfully to " + PATIENT_DATA_FILE);
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saving patient data: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
    }
}
    

    

    private void clearPatientFields() {
     FullnameField.setText("");
     symptomsField.setText("");
    }
    }
