import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-international-summary-by-air',
  templateUrl: './international-summary-by-air.component.html',
  styleUrls: ['./international-summary-by-air.component.scss']
})
export class InternationalSummaryByAirComponent {
  constructor() { }
  products:any=[{preAlertNumber:"320012345678",flightDetails:"N/A",mode:"Courier",origin:"UAE",originPort:"DXB",destinationPort:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",flightDetails:"N/A",mode:"Courier",origin:"UAE",originPort:"DXB",destinationPort:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",flightDetails:"N/A",mode:"Courier",origin:"UAE",originPort:"DXB",destinationPort:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",flightDetails:"N/A",mode:"Courier",origin:"UAE",originPort:"DXB",destinationPort:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",flightDetails:"N/A",mode:"Courier",origin:"UAE",originPort:"DXB",destinationPort:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",flightDetails:"N/A",mode:"Courier",origin:"UAE",originPort:"DXB",destinationPort:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",flightDetails:"N/A",mode:"Courier",origin:"UAE",originPort:"DXB",destinationPort:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",flightDetails:"N/A",mode:"Courier",origin:"UAE",originPort:"DXB",destinationPort:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",flightDetails:"N/A",mode:"Courier",origin:"UAE",originPort:"DXB",destinationPort:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",flightDetails:"N/A",mode:"Courier",origin:"UAE",originPort:"DXB",destinationPort:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},];
  items: MenuItem[] | undefined;

 

  ngOnInit() {
      this.items = [{ label: 'International Summary By Air'}];
  }
}
