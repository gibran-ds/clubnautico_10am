
package guis;

import entidades.Socio;
import guis.utils.JButtonCellEditor;
import guis.utils.JButtonRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import persistencia.ISociosDAO;

public class SociosForm extends javax.swing.JFrame {

    private final ISociosDAO sociosDAO;
    
    public SociosForm(ISociosDAO sociosDAO) {
        initComponents();
        this.sociosDAO = sociosDAO;
        this.initTablaSocios();
        this.llenarTabla();
    }
    
    private void initTablaSocios(){
        ActionListener onEditarClickListener = new ActionListener() {
            final int columnaId = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                editar();
            }
        };        
        int indiceColumnaEditar = 3;
        TableColumnModel modeloColumnas = this.tblSocios.getColumnModel();
        modeloColumnas.getColumn(indiceColumnaEditar)
                .setCellRenderer(new JButtonRenderer("Editar"));
        modeloColumnas.getColumn(indiceColumnaEditar)
                .setCellEditor(new JButtonCellEditor("Editar", 
                    onEditarClickListener));   
        
        ActionListener onEliminarClickListener = new ActionListener() {
            final int columnaId = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar();
            }
        };        
        int indiceColumnaEliminar = 4;
        modeloColumnas = this.tblSocios.getColumnModel();
        modeloColumnas.getColumn(indiceColumnaEliminar)
                .setCellRenderer(new JButtonRenderer("Eliminar"));
        modeloColumnas.getColumn(indiceColumnaEliminar)
                .setCellEditor(new JButtonCellEditor("Eliminar", 
                    onEliminarClickListener));
        
        
        ActionListener onAgregarClickListener = new ActionListener() {
            final int columnaId = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarSocio();
            }
        };        
        int indiceColumnaAgregar = 5;
        modeloColumnas = this.tblSocios.getColumnModel();
        modeloColumnas.getColumn(indiceColumnaAgregar)
                .setCellRenderer(new JButtonRenderer("Agregar"));
        modeloColumnas.getColumn(indiceColumnaAgregar)
                .setCellEditor(new JButtonCellEditor("Agregar", 
                    onAgregarClickListener));
    }

    private void guardar(){
        if(this.txtIdSocio.getText().isEmpty()){
            this.agregar();
        }else{
            this.actualizar();
        }
    }
    
    private void agregar(){
        String nombre = this.txtNombre.getText();
        String curp = this.txtCurp.getText();
        // TODO: AGREGAR VALIDACIONES
        Socio socioNuevo = new Socio(nombre, curp);
        boolean seAgregoSocio = this.sociosDAO.agregar(socioNuevo);
        if(seAgregoSocio){
            JOptionPane.showMessageDialog(this, "Se agregó el nuevo socio", 
                "Información", JOptionPane.INFORMATION_MESSAGE);
            this.limpiarFormulario();
            this.llenarTabla();
        }else{
            JOptionPane.showMessageDialog(this, "No fue posible agregar al socio", 
                "Información", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizar(){
        Long idSocio = Long.parseLong(this.txtIdSocio.getText());
        String nombre = this.txtNombre.getText();
        String curp = this.txtCurp.getText();
        boolean seActualizoSocio = this.sociosDAO.actualizar(
                new Socio(idSocio, nombre, curp));
        if(seActualizoSocio){
            JOptionPane.showMessageDialog(this, "Se actualizó el socio", 
                "Información", JOptionPane.INFORMATION_MESSAGE);
            this.limpiarFormulario();
            this.llenarTabla();
        }else{
            JOptionPane.showMessageDialog(this, "No fue posible actualizar al socio", 
                "Información", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void agregarSocio(){
        // SACAR LO QUE OCUPAMOS DE LA TABLA FUENTE
        int indiceFilaSeleccionada = this.tblSocios.getSelectedRow();
        Long idSocioSeleccionado = (Long)this.tblSocios.getValueAt(indiceFilaSeleccionada, 0);
        String nombre = (String)this.tblSocios.getValueAt(indiceFilaSeleccionada, 1);
        Long cantidad = 1L;
        Integer indiceColumnaId = 0;
        Integer indiceColumnaCantidad = 2;
        // AGREGAR LA FILA A LA TABLA DESTINO
        DefaultTableModel modelo = (DefaultTableModel)this.tblSociosSeleccionados.getModel();
        boolean existeSocio = false;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            // PARA CADA FILA, SACAMOS EL ID DEL SOCIO Y LO COMPARAMOS CON EL SELECCIONADO
            Long id = (Long)modelo.getValueAt(i, indiceColumnaId);
            if(id.equals(idSocioSeleccionado)){
                Long cantidadAnterior = (Long)modelo.getValueAt(i, indiceColumnaCantidad);
                modelo.setValueAt(++cantidadAnterior, i, indiceColumnaCantidad);
                existeSocio = true;
            }
        }
        // SI YA EXISTE, SE ACTUALIZA LA CANTIDAD...
        if(!existeSocio){
            modelo.addRow(new Object[]{
                idSocioSeleccionado, nombre, cantidad
            });
        }
    }
    
    private boolean socioSeleccionadoAnteriormente(Long idSocioSeleccionado){
        // AGREGAR LA FILA A LA TABLA DESTINO
        DefaultTableModel modelo = (DefaultTableModel)this.tblSociosSeleccionados.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            // PARA CADA FILA, SACAMOS EL ID DEL SOCIO Y LO COMPARAMOS CON EL SELECCIONADO
            Long id = (Long)modelo.getValueAt(i, 0);
            if(id.equals(idSocioSeleccionado)){
                return true;
            }
        }
        return false;
    }
    
    private void llenarTabla(){
        List<Socio> listaSocios = this.sociosDAO.consultarTodos();
        DefaultTableModel modeloTabla = (DefaultTableModel)this.tblSocios.getModel();
         if (modeloTabla.getRowCount() > 0) {
            for (int i = modeloTabla.getRowCount() - 1; i > -1; i--) {
                modeloTabla.removeRow(i);
            }
        }
        listaSocios.forEach(socio -> {
            Object[] fila = new Object[5];
            fila[0] = socio.getIdSocio();
            fila[1] = socio.getNombre();
            fila[2] = socio.getCurp();
            fila[3] = socio;
            fila[4] = socio;
            modeloTabla.addRow(fila); 
        });
    }
    
    private void limpiarFormulario(){
        this.txtIdSocio.setText("");
        this.txtNombre.setText("");
        this.txtCurp.setText("");
    }
    
    private void eliminar(){
        Long idSocioSeleccionado = getIdSocioSeleccionado();
        if(idSocioSeleccionado == null){
            JOptionPane.showMessageDialog(this, "Debes seleccionar un socio", 
                "Información", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int opcionSeleccionada = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar al socio?", 
                "Confirmación", JOptionPane.YES_NO_OPTION);
        if(opcionSeleccionada == JOptionPane.NO_OPTION){
            return;
        }
        boolean seEliminoSocio = this.sociosDAO.eliminar(idSocioSeleccionado);
        if(seEliminoSocio){
            JOptionPane.showMessageDialog(this, "Se eliminó el socio correctamente", 
                "Información", JOptionPane.INFORMATION_MESSAGE);
            this.llenarTabla();
        }else{
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el socio", 
                "Información", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private Long getIdSocioSeleccionado(){
        int indiceFilaSeleccionada = this.tblSocios.getSelectedRow();
        if(indiceFilaSeleccionada != -1){
            DefaultTableModel modelo = (DefaultTableModel)this.tblSocios.getModel();
            int indiceColumnaId = 0;
            Long idSocioSeleccionado = (Long)modelo.getValueAt(indiceFilaSeleccionada, 
                indiceColumnaId);
            return idSocioSeleccionado;
        }else{
            return null;
        }
    }
    
    private void editar(){
        Long idSocioSeleccionado = this.getIdSocioSeleccionado();
        if(idSocioSeleccionado == null){
            JOptionPane.showMessageDialog(this, "Debes seleccionar un socio", 
                "Información", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Socio socio = this.sociosDAO.consultar(idSocioSeleccionado);
        if(socio != null){
            this.llenarFormulario(socio);
        }
    }
    
    private void llenarFormulario(Socio socio){
        this.txtIdSocio.setText(socio.getIdSocio().toString());
        this.txtNombre.setText(socio.getNombre());
        this.txtCurp.setText(socio.getCurp());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIdSocio = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblCurp = new javax.swing.JLabel();
        txtIdSocio = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtCurp = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        pnlTablaSocios = new javax.swing.JScrollPane();
        tblSocios = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSociosSeleccionados = new javax.swing.JTable();
        lblSociosSeleccionados = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Administración de Socios");

        lblIdSocio.setText("Id Socio");

        lblNombre.setText("Nombre");

        lblCurp.setText("Curp");

        txtIdSocio.setEditable(false);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        tblSocios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Socio", "Nombre", "Curp", "Editar", "Eliminar", "Agregar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        pnlTablaSocios.setViewportView(tblSocios);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        tblSociosSeleccionados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Socio", "Nombre", "Cantidad"
            }
        ));
        jScrollPane1.setViewportView(tblSociosSeleccionados);

        lblSociosSeleccionados.setText("Socios Seleccionados");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblIdSocio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                    .addComponent(lblCurp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addComponent(btnCancelar))
                    .addComponent(txtIdSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre)
                    .addComponent(txtCurp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSociosSeleccionados, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlTablaSocios, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(76, 76, 76))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblIdSocio)
                            .addComponent(txtIdSocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombre)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCurp)
                            .addComponent(txtCurp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardar)
                            .addComponent(btnCancelar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlTablaSocios, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnEditar)
                                .addGap(18, 18, 18)
                                .addComponent(btnEliminar)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSociosSeleccionados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        this.guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        //this.eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        this.editar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.limpiarFormulario();
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCurp;
    private javax.swing.JLabel lblIdSocio;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblSociosSeleccionados;
    private javax.swing.JScrollPane pnlTablaSocios;
    private javax.swing.JTable tblSocios;
    private javax.swing.JTable tblSociosSeleccionados;
    private javax.swing.JTextField txtCurp;
    private javax.swing.JTextField txtIdSocio;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
