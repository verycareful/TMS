package com.jtms;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

// Task class
class Task {
    private int priority;
    private String title;
    private String description;
    private boolean isCompleted;

    public Task(int priority, String title, String description) {
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.isCompleted = false;
    }

    public int getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public void markAsIncomplete() {
        this.isCompleted = false;
    }

    @Override
    public String toString() {
        return priority + ": " + title + " - " + description + (isCompleted ? " (Completed)" : "");
    }
}

// TaskManager class
class TaskManager {
    private Connection connection;

    public TaskManager() {
        try {
            String dbUrl = System.getenv("DB_URL") != null ? 
                System.getenv("DB_URL") : "jdbc:mysql://localhost:3306/task_management";
            String dbUser = System.getenv("DB_USER") != null ? 
                System.getenv("DB_USER") : "root";
            String dbPassword = System.getenv("DB_PASSWORD") != null ? 
                System.getenv("DB_PASSWORD") : "";
            
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTask(int priority, String title, String description) {
        String sql = "INSERT INTO tasks (id, title, description) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, priority);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Task task = new Task(rs.getInt("id"), rs.getString("title"), rs.getString("description"));
                if (rs.getBoolean("is_completed")) {
                    task.markAsCompleted();
                }
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void markTaskAsCompleted(int priority) {
        String sql = "UPDATE tasks SET is_completed = TRUE WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, priority);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markTaskAsIncomplete(int priority) {
        String sql = "UPDATE tasks SET is_completed = FALSE WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, priority);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeTask(int priority) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, priority);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// TaskManagementSystem class
public class TaskManagementSystem extends JFrame {
    private TaskManager taskManager;
    private DefaultTableModel taskTableModel;
    private JTable taskTable;
    private String currentTheme = "Flat Dark";

    public TaskManagementSystem() {
        // Apply FlatLaf default theme
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        taskManager = new TaskManager();
        taskManager.testConnection(); // Check the database connection here
        taskTableModel = new DefaultTableModel(new String[]{"Priority", "Title", "Description", "Completed"}, 0);
        taskTable = new JTable(taskTableModel);
        setTitle("Task Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JButton addButton = new JButton("Add Task");
        JButton completeButton = new JButton("Mark as Completed");
        JButton incompleteButton = new JButton("Mark as Incomplete");
        JButton removeButton = new JButton("Remove Task");
        JButton saveExitButton = new JButton("Save & Exit");
        JButton changeThemeButton = new JButton("Change Theme");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(addButton, gbc);

        gbc.gridx = 1;
        panel.add(completeButton, gbc);

        gbc.gridx = 2;
        panel.add(incompleteButton, gbc);

        gbc.gridx = 3;
        panel.add(removeButton, gbc);

        gbc.gridx = 4;
        panel.add(saveExitButton, gbc);

        gbc.gridx = 5;
        panel.add(changeThemeButton, gbc);

        add(new JScrollPane(taskTable), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String priorityStr = JOptionPane.showInputDialog("Enter task priority:");
                String title = JOptionPane.showInputDialog("Enter task title:");
                String description = JOptionPane.showInputDialog("Enter task description:");
                if (priorityStr != null && title != null && description != null) {
                    try {
                        int priority = Integer.parseInt(priorityStr);
                        taskManager.addTask(priority, title, description);
                        updateTaskTable();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Priority must be an integer.");
                    }
                }
            }
        });

        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    int priority = Integer.parseInt(taskTableModel.getValueAt(selectedRow, 0).toString());
                    taskManager.markTaskAsCompleted(priority);
                    updateTaskTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a task to mark as completed.");
                }
            }
        });

        incompleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    int priority = Integer.parseInt(taskTableModel.getValueAt(selectedRow, 0).toString());
                    taskManager.markTaskAsIncomplete(priority);
                    updateTaskTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a task to mark as incomplete.");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    int priority = Integer.parseInt(taskTableModel.getValueAt(selectedRow, 0).toString());
                    taskManager.removeTask(priority);
                    updateTaskTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a task to remove.");
                }
            }
        });

        saveExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskManager.closeConnection(); // Ensure you have a method to close the connection
                System.exit(0);
            }
        });

        changeThemeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Flat Light", "Flat Dark", "IntelliJ", "Darcula", "Neon Dark"};
                String theme = (String) JOptionPane.showInputDialog(null, "Choose theme", "Theme Selector",
                        JOptionPane.QUESTION_MESSAGE, null, options, currentTheme);

                if (theme != null) {
                    currentTheme = theme;
                    try {
                        switch (theme) {
                            case "Flat Light":
                                UIManager.setLookAndFeel(new FlatLightLaf());
                                break;
                            case "Flat Dark":
                                UIManager.setLookAndFeel(new FlatDarkLaf());
                                break;
                            case "IntelliJ":
                                UIManager.setLookAndFeel(new FlatIntelliJLaf());
                                break;
                            case "Darcula":
                                UIManager.setLookAndFeel(new FlatDarculaLaf());
                                break;
                            case "Neon Dark":
                                LafManager.install(new DarculaTheme());
                                break;
                        }
                        SwingUtilities.updateComponentTreeUI(TaskManagementSystem.this);
                    } catch (UnsupportedLookAndFeelException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                taskManager.closeConnection(); // Ensure the application closes on window close
                System.exit(0);
            }
        });

        updateTaskTable();
        setVisible(true);
    }

    private void updateTaskTable() {
        taskTableModel.setRowCount(0); // Clear the table
        ArrayList<Task> tasks = taskManager.getTasks();
        // Sort tasks by priority
        tasks.sort(Comparator.comparingInt(Task::getPriority));
        for (Task task : tasks) {
            taskTableModel.addRow(new Object[]{task.getPriority(), task.getTitle(), task.getDescription(), task.isCompleted() ? "Yes" : "No"});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaskManagementSystem().setVisible(true);
            }
        });
    }
}