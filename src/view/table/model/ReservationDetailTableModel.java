/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.table.model;

import domain.PhotographyServices;
import domain.Reservation;
import domain.ReservationDetail;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Nadja
 */
public class ReservationDetailTableModel extends AbstractTableModel{
    
     private final Reservation reservation;
    private final String[] columnNames = new String[]{ "Service", "Price"};

    public ReservationDetailTableModel(Reservation reservation) {
        this.reservation = reservation;
    }

  

    @Override
    public int getRowCount() {
        if (reservation != null) {
            return reservation.getReservationDetails().size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ReservationDetail item = reservation.getReservationDetails().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return item.getService().getName();
            case 1:
                return item.getCost();
           
           
            default:
                return "n/a";
        }
    }

    public void addItem(PhotographyServices service, double cost) {
        ReservationDetail item = new ReservationDetail();
        item.setReservation(reservation);
        item.setService(service);
     
        item.setCost(cost);
       
        reservation.getReservationDetails().add(item);
        reservation.setCost(reservation.getCost()+item.getCost());
        fireTableRowsInserted(reservation.getReservationDetails().size() - 1, reservation.getReservationDetails().size() - 1);
    }

    public void removeInvoiceItem(int rowIndex) {
        ReservationDetail item = reservation.getReservationDetails().get(rowIndex);
        reservation.getReservationDetails().remove(rowIndex);
        reservation.setCost(reservation.getCost()-item.getCost());
        
        fireTableRowsDeleted(reservation.getReservationDetails().size() - 1, reservation.getReservationDetails().size() - 1);
    }

    

    public Reservation getInvoice() {
        return reservation;
    }

    
    
}
