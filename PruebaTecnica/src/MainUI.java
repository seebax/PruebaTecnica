import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class MainUI extends JFrame {
    private JTextField txtNombre, txtEdad, txtDireccion, txtTelefono, txtId;
    private JLabel lblFoto;
    private byte[] fotoBytes;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private DefaultListModel<String> listModel;
    private JList<String> usuarioList;
    private List<Usuario> usuarios;

    public MainUI() {
        super("CRUD de Usuarios");
        initComponents();
        cargarUsuarios();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Panel del formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        panelForm.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        panelForm.add(txtId, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panelForm.add(new JLabel("Nombre Completo:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField();
        panelForm.add(txtNombre, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panelForm.add(new JLabel("Edad:"), gbc);
        gbc.gridx = 1;
        txtEdad = new JTextField();
        panelForm.add(txtEdad, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panelForm.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        txtDireccion = new JTextField();
        panelForm.add(txtDireccion, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panelForm.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        txtTelefono = new JTextField();
        panelForm.add(txtTelefono, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panelForm.add(new JLabel("Fotografía:"), gbc);
        gbc.gridx = 1;
        JButton btnCargarFoto = new JButton("Cargar Foto");
        btnCargarFoto.addActionListener(e -> cargarFoto());
        panelForm.add(btnCargarFoto, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panelForm.add(new JLabel("Vista Previa:"), gbc);
        gbc.gridx = 1;
        lblFoto = new JLabel();
        lblFoto.setPreferredSize(new Dimension(100, 100));
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelForm.add(lblFoto, gbc);

        add(panelForm, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnCrear = new JButton("Crear");
        btnCrear.addActionListener(e -> crearUsuario());
        panelBotones.add(btnCrear);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizarUsuario());
        panelBotones.add(btnActualizar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarUsuario());
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.CENTER);

        // Lista de usuarios
        listModel = new DefaultListModel<>();
        usuarioList = new JList<>(listModel);
        usuarioList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usuarioList.addListSelectionListener(e -> seleccionarUsuario());
        JScrollPane scrollPane = new JScrollPane(usuarioList);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        add(scrollPane, BorderLayout.SOUTH);

        // Ajustar la ventana correctamente
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cargarFoto() {
        JFileChooser fileChooser = new JFileChooser();
        int res = fileChooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                fotoBytes = Files.readAllBytes(file.toPath());
                ImageIcon icon = new ImageIcon(fotoBytes);
                Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblFoto.setIcon(new ImageIcon(img));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void crearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre(txtNombre.getText());
        try {
            usuario.setEdad(Integer.parseInt(txtEdad.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Edad debe ser un número");
            return;
        }
        usuario.setDireccion(txtDireccion.getText());
        usuario.setTelefono(txtTelefono.getText());
        usuario.setFotografia(fotoBytes);

        if (usuarioDAO.crearUsuario(usuario)) {
            JOptionPane.showMessageDialog(this, "Usuario creado con éxito");
            limpiarCampos();
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear usuario");
        }
    }

    private void actualizarUsuario() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la lista");
            return;
        }
        Usuario usuario = new Usuario();
        usuario.setId(Integer.parseInt(txtId.getText()));
        usuario.setNombre(txtNombre.getText());
        try {
            usuario.setEdad(Integer.parseInt(txtEdad.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Edad debe ser un número");
            return;
        }
        usuario.setDireccion(txtDireccion.getText());
        usuario.setTelefono(txtTelefono.getText());
        usuario.setFotografia(fotoBytes);

        if (usuarioDAO.actualizarUsuario(usuario)) {
            JOptionPane.showMessageDialog(this, "Usuario actualizado con éxito");
            limpiarCampos();
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar usuario");
        }
    }

    private void eliminarUsuario() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la lista");
            return;
        }
        int id = Integer.parseInt(txtId.getText());
        if (usuarioDAO.eliminarUsuario(id)) {
            JOptionPane.showMessageDialog(this, "Usuario eliminado con éxito");
            limpiarCampos();
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar usuario");
        }
    }

    private void cargarUsuarios() {
        listModel.clear();
        usuarios = usuarioDAO.obtenerUsuarios();
        for (Usuario u : usuarios) {
            listModel.addElement(u.getId() + " - " + u.getNombre());
        }
    }

    private void seleccionarUsuario() {
        int index = usuarioList.getSelectedIndex();
        if (index != -1) {
            Usuario u = usuarios.get(index);
            txtId.setText(String.valueOf(u.getId()));
            txtNombre.setText(u.getNombre());
            txtEdad.setText(String.valueOf(u.getEdad()));
            txtDireccion.setText(u.getDireccion());
            txtTelefono.setText(u.getTelefono());
            fotoBytes = u.getFotografia();
            lblFoto.setIcon(fotoBytes != null ? new ImageIcon(new ImageIcon(fotoBytes).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)) : null);
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        lblFoto.setIcon(null);
        fotoBytes = null;
    }

    public static void main(String[] args) {
        Database.initialize();
        SwingUtilities.invokeLater(() -> new MainUI());
    }
}
