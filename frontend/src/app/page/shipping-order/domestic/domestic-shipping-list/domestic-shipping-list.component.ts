import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { DomesticShippingService } from '../service/domestic-shipping.service';

@Component({
  selector: 'app-domestic-shipping-list',
  templateUrl: './domestic-shipping-list.component.html',
  styleUrls: ['./domestic-shipping-list.component.scss']
})
export class DomesticShippingListComponent implements OnInit{

  myApiResponse:any;

  constructor(private domesticShipmentService:DomesticShippingService) { }
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
      this.items = [{ label: 'Domestic Shipment'}];
      this.getAllDomesticShipments();
  }

  getAllDomesticShipments(){
    this.domesticShipmentService.getALLShipments().subscribe((res:any)=>{
      debugger
      this.myApiResponse=res;
    },(error:any)=>{
      console.log("some error occurred");
    })
  }
}
