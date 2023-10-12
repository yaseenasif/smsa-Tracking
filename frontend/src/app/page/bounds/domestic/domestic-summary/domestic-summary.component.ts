import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-domestic-summary',
  templateUrl: './domestic-summary.component.html',
  styleUrls: ['./domestic-summary.component.scss']
})
export class DomesticSummaryComponent {
  name!:string;
  location!:Location[];
  status!:Status[];
  selectedStatus!:Status;
  selectedLocation!:Location;
  fromDate: Date | undefined;
  toDate: Date | undefined;


  constructor() { }
  products:any=[{preAlertNumber:"320012345678",vehicalNumber:"N/A",vehiclaType:"N/A",mode:"Courier",origin:"UAE",originLocation:"DXB",destinationLocation:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",vehicalNumber:"N/A",vehiclaType:"N/A",mode:"Courier",origin:"UAE",originLocation:"DXB",destinationLocation:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",vehicalNumber:"N/A",vehiclaType:"N/A",mode:"Courier",origin:"UAE",originLocation:"DXB",destinationLocation:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",vehicalNumber:"N/A",vehiclaType:"N/A",mode:"Courier",origin:"UAE",originLocation:"DXB",destinationLocation:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",vehicalNumber:"N/A",vehiclaType:"N/A",mode:"Courier",origin:"UAE",originLocation:"DXB",destinationLocation:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",vehicalNumber:"N/A",vehiclaType:"N/A",mode:"Courier",origin:"UAE",originLocation:"DXB",destinationLocation:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",vehicalNumber:"N/A",vehiclaType:"N/A",mode:"Courier",origin:"UAE",originLocation:"DXB",destinationLocation:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",vehicalNumber:"N/A",vehiclaType:"N/A",mode:"Courier",origin:"UAE",originLocation:"DXB",destinationLocation:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",vehicalNumber:"N/A",vehiclaType:"N/A",mode:"Courier",origin:"UAE",originLocation:"DXB",destinationLocation:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},
  {preAlertNumber:"320012345678",vehicalNumber:"N/A",vehiclaType:"N/A",mode:"Courier",origin:"UAE",originLocation:"DXB",destinationLocation:"DMM",weight:"2000",totalShipment:"200",pallets:"20",bags:"95",etd:"##########",eta:"#########",ata:"N/A",status:"Departure"},];
  items: MenuItem[] | undefined;

 

  ngOnInit() {
      this.items = [{ label: 'Domestic Summary'}];
      this.location=[
        {
          locationName:"In bound",
          id:1
        },
        {
          locationName:"Out bound",
          id:2
        }
      ]

      this.status=[
        {
          status:"Open"
        },
        {
          status:"Close"
        }
      ]
  }

}

interface Location{
  locationName:string,
  id:number
}


interface Status{
  status:string
}