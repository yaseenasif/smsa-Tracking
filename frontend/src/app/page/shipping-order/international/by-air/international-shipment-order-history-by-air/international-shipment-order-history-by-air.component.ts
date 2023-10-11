import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-international-shipment-order-history-by-air',
  templateUrl: './international-shipment-order-history-by-air.component.html',
  styleUrls: ['./international-shipment-order-history-by-air.component.scss']
})
export class InternationalShipmentOrderHistoryByAirComponent {
  constructor() { }
  domesticShipmentHistory:any=[{status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},];
  items: MenuItem[] | undefined;


  ngOnInit() {
      this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Air',routerLink:'/international-shipment-by-air'},{ label: 'International Shipment History By Air'}];
  }
}
